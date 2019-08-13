// pages/club/home/home.js
const util = require('../../../utils/util.js')
const {
	$Toast
} = require('../../../components/iview/base/index');
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		fromAuthCode: '',
		clubId: 0,
		currentRole: '',
		clubInfo: null,

		roleCodes: [],
		roleCodeIndex: 0,
		roleVals: [],
		roleTip: '我是会员',
		localLogged: false,

		spinShow: false,
		coachInfo: null,
		carouselAds: [],

		newFinishedPtrainNum: 0,
		newBespeakNum: 0,
		newPtrainCardNum: 0,
	},
	
	//收集formid的点击事件
	formSubmit: function(e) {
		let formId = e.detail.formId;
		app.dealFormIds(formId); //处理保存推送码

		let type = e.detail.target.dataset.type;
		console.log('form发生了submit事件，推送码为：', formId, type)
		//根据type的值来执行相应的点击事件
		//...
		app.saveFormIds()
	},

	//角色切换
	bindRoleCodeChange: function(e) {
		var self = this;
		console.log('picker country code 发生选择改变，携带值为', e.detail.value);

		var roleVal = self.data.roleVals[e.detail.value];
		this.setData({
			currentRole: roleVal,
			roleCodeIndex: e.detail.value,
			roleTip: self.data.roleCodes[e.detail.value],
		})
		app.setCurrentRole(self.data.clubId, {
			roleTip: self.data.roleCodes[e.detail.value],
			roleVal: roleVal,
		});

		self.getStatNum(self.data.clubId);

	},

	//获取概要统计信息
	getStatNum: function(clubId) {
		var self = this;

		var roleVal = self.data.roleVals[self.data.roleCodeIndex];
		if(roleVal != 'coach') {
			return;
		}

		//获取用户绑定教练详情
		app.getCurrentCoachDetail(clubId, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
				return;
			}
			self.setData({
				coachInfo: res.data,
			});
			var coachId = res.data.id;
			var data = {
				clubId,
				coachId
			};
			util.getRequest('general/club/clubCoach/homeStat', data, function(res) {
				if(parseInt(res.code) !== 0) {
					return;
				}

				self.setData({
					newFinishedPtrainNum: res.data.newFinishedPtrainNum,
					newBespeakNum: res.data.newBespeakNum,
					newPtrainCardNum: res.data.newPtrainCardNum,
				});
			}, 'v1', true, 300)
		});
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function(options) {
		var self = this;
		var authCode = util.getParam(options, 'authCode');
		var clubId = util.getParam(options, 'clubId');
		this.setData({
			fromAuthCode: authCode
		})
		if(authCode) {
			util.setCache('fromAuthCode', authCode, 86400);
		}

		//根据authCode去服务端查找俱乐部信息
		if(authCode || clubId) {
			if(clubId) {
				this.setData({
					clubId
				})
			}
			
			this.getClubInfo(authCode, clubId);
		} else {
			util.toast("无效的认证码来源");
			setTimeout(function() {
				//跳回到登录跳转前的来源页
				wx.navigateBack()
			}, 1500);
		}

	},
	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady: function() {
		var self = this
		setTimeout(function() {
			if(self.data.clubInfo) {
				var title = '杏运树 - ' + self.data.clubInfo.name;
				wx.setNavigationBarTitle({
					title: title
				})
			}
		}, 500);
	},

	//根据authCode去服务端查找俱乐部信息
	getClubInfo(authCode, clubId) {
		var self = this;
		var data = {
			authCode,
			clubId
		};

		util.getRequest('general/club/club/detail', data, function(detailRes) {

			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(detailRes.code) !== 0) {
				console.log(detailRes.message);
				util.toast("俱乐部认证信息已失效");
				setTimeout(function() {
					//跳回到登录跳转前的来源页
					wx.navigateBack()
				}, 1500);

			} else {
				if(detailRes.data.id !== undefined && detailRes.data.id != 0) {
					self.setData({
						clubInfo: detailRes.data,
						clubId: detailRes.data.id,
					});
					//轮播图信息
					self.getCarousel(detailRes.data.id);

					app.checkLogin(false, false, function() {
						self.getRolesInfo(self.data.clubId);
					});

				} else {
					util.toast("俱乐部认证信息已失效");
					setTimeout(function() {
						//跳回到登录跳转前的来源页
						wx.navigateBack()
					}, 1500);
				}
			}
		})
	},

	/**
	 * 获取当前用户的角色，暂只判断角色，不取角色信息
	 */
	getRolesInfo(clubId) {
		var self = this;
		var data = {
			clubId: clubId,
			checkClubUser: 1,
			checkVip: 1,
			checkCoach: 1,
		};
		util.getRequest('usr/member/userCommon/checkRoles', data, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
				return;
			}
			//如果不是俱乐部用户，自动加入俱乐部普通用户
			if(res.data.isClubUser == 0) {
				self.autoJoinClub(clubId);
			}

			var _data = {
				currentRole: '',
				roleCodes: [],
				roleCodeIndex: 0,
				roleTip: '',
				roleVals: [],
			}
			if(res.data.isVip == 1) {
				app.setClubVip(clubId);
				_data.roleCodes.push("身份：会员");
				_data.roleVals.push('vip');
			}
			if(res.data.isCoach == 1) {
				_data.roleCodes.push("身份：教练");
				_data.roleVals.push('coach');
			}

			//当前角色身份
			var currentRoleInfo = app.getCurrentRole(clubId);
			_data.currentRole = !currentRoleInfo ? '' : currentRoleInfo.roleVal;
			if(currentRoleInfo) {
				for(var j = 0; j < _data.roleVals.length; j++) {
					var _roleVal = _data.roleVals[j];
					if(_roleVal == currentRoleInfo.roleVal) {
						_data.roleCodeIndex = j;
						break;
					}
				}
			} else {
				_data.roleCodeIndex = 0;
			}

			if(_data.roleCodes.length == 0) {
				_data.currentRole = '';
			} else {
				_data.roleTip = _data.roleCodes[_data.roleCodeIndex];
				_data.currentRole = _data.roleVals[_data.roleCodeIndex];
			}

			self.setData(_data);
			self.getStatNum(clubId);
		})

	},

	//识别来源俱乐部认证码，如果存在，且当前会员不是俱乐部用户，则自己加为俱乐部用户
	autoJoinClub: function(clubId) {
		if(!this.data.fromAuthCode) {
			return;
		}
		//开始签到（调用服务端签到接口）
		util.postRequest('usr/member/userClub/joinClub', {
			clubId
		}, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0 && parseInt(res.code) !== 200) {

			} else {
				//成为了该俱乐部用户
				console.log(res);
			}
		})

	},
	
	/**
	 * 获取当前用户的角色，暂只判断角色，不取角色信息
	 */
	getCarousel(clubId) {
		var self = this;
		var data = {
			clubId: clubId,
			posId: 0,
		};
		util.getRequest('general/club/adv/pagelist', data, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
				return;
			}
			self.setData({
				carouselAds : res.data.list
			});
		}, 'v1', true, 3600)

	},

	/**
	 * 生命周期函数--监听页面显示
	 */
	onShow: function() {},

	/**
	 * 生命周期函数--监听页面隐藏
	 */
	onHide: function() {

	},

	/**
	 * 生命周期函数--监听页面卸载
	 */
	onUnload: function() {

	},

	/**
	 * 页面相关事件处理函数--监听用户下拉动作
	 */
	onPullDownRefresh: function() {
		var self = this;
		util.showLoading = false;
		self.setData({
			spinShow: true
		});
		$Toast({
			content: '加载中',
			type: 'loading',
			duration: 0,
		});

		setTimeout(function() {
			$Toast.hide();
		}, 400);
		this.getRolesInfo(this.data.clubId);

		wx.stopPullDownRefresh();
		util.showLoading = true;
	},

	/**
	 * 页面上拉触底事件的处理函数
	 */
	onReachBottom: function() {

	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function() {
		var title = self.data.clubInfo.name;
		return {
			title: title,
			desc: '杏运树 - ' + title + self.data.clubInfo.desc,
			path: util.getCurrentPageUrlWithArgs()
		}
	},

	callClub: function() {
		var self = this;
		wx.makePhoneCall({
			phoneNumber: self.data.clubInfo.clubPhone
		})
	},

	openLocation: function() {
		var self = this;
		wx.openLocation({
			latitude: parseFloat(self.data.clubInfo.lat),
			longitude: parseFloat(self.data.clubInfo.lng),
			scale: 18
		})
	},

	scancode: function() {
		wx.scanCode({
			onlyFromCamera: false,
			success: (res) => {
				console.log(res);
				//目前只识别 当前小程序的合法二维码
				if(res.path !== undefined && res.path) {
					getApp().redirectTo('/' + res.path);
				} else {
					wx.showToast({
						title: '当前只支持扫描杏运树小程序码',
						content: res.result + res.rawData,
						icon: 'none',
						duration: 6000,
					})
				}
			},
			fail: (res) => {
				console.log(res);
			}
		});
	},
})