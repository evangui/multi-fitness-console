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
		loaded: false,
		
		fromAuthCode: '',
		clubId: 0,
		currentRole: '',
		clubInfo: null,

		roleCodes: [],
		roleCodeIndex: 0,
		roleVals: [],
		localLogged: false,

		spinShow: false,
		coachInfo: null,
		realname: '',
		goodAt: '',
		
		authMode: 0,
		
		currentStep : 2,
    verticalStep : 1
	},
	
	changeAuthMode1: function() {
		this.setData({
			authMode: 1,
		})
	},
	changeAuthMode2: function() {
		this.setData({
			authMode: 2,
		})
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

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function(options) {
		//检查登录状态
		app.checkLogin();
		
		var self = this;
		var authCode = util.getParam(options, 'authCode');
		this.setData({
			fromAuthCode: authCode
		})
		if(!authCode) {
			util.toast("无效的认证码来源");
			setTimeout(function() {
				wx.navigateBack()
			}, 1500);
		}

		util.setCache('fromAuthCode', authCode, 86400);
		this.getClubInfo(authCode, 0);
	},
	
	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady: function() {
		var self = this
		setTimeout(function() {
			if(self.data.clubInfo) {
				var title = '成为教练 - ' + self.data.clubInfo.name;
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

					self.getRolesInfo(self.data.clubId);
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
//			checkVip: 1,
			checkCoach: 1,
		};
		util.getRequest('usr/member/userCommon/checkRoles', data, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
				return;
			}
			//如果不是俱乐部用户，自动加入俱乐部普通用户
			if(res.data.isClubUser == 0) {
				self.autoJoinClub(clubId, function(data) {
					self.setData({
						loaded: true,
					})
				});
			} else {
				//如果不是俱乐部教练，自动加入俱乐部教练
				if(res.data.isCoach == 0) {
					self.setData({
						loaded: true,
					})
				} else {
					util.toast("认证成功", "success");
					self.setData({
						authMode: 200
					})
					setTimeout(function() {
						self.redirectToClub();
					}, 3000);
				}
			}
		})
	},
	
	//识别来源俱乐部认证码，如果存在，且当前会员不是俱乐部用户，则自己加为俱乐部用户
	autoJoinClub: function(clubId, onSuccess) {
		if(!this.data.fromAuthCode) {
			return;
		}
		//开始签到（调用服务端签到接口）
		util.postRequest('usr/member/userClub/joinClub', {
			clubId
		}, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0 && parseInt(res.code) !== 200) {
				return;
			} else {
				//成为了该俱乐部用户
				console.log(res);
				onSuccess(res.data);
			}
		})

	},
	
	//识别来源俱乐部认证码，如果存在，且当前会员不是俱乐部用户，则自己加为俱乐部用户
	autoJoinCoach: function(clubId, onSuccess, onError) {
		onError();
		//暂时不根据手机号自动匹配已有教练
	},
	
	instantJoin: function() {
		var self = this;
		if(!this.data.realname) {
			util.alert("请输入教练姓名");
			return;
		}
		//调用服务端签到接口
		var data = {
			clubId: self.data.clubId,
			realname: self.data.realname,
			goodAt: self.data.goodAt,
		}
		util.postRequest('usr/club/clubCoach/joinCoach', data, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0 && parseInt(res.code) !== 200) {
				util.alert("认证失败：" + res.message);
				return;
			}
			
			util.toast("认证成功", "success");
			self.setData({
				authMode: 200
			})
			setTimeout(function() {
				self.redirectToClub();
			}, 3000);
		})
		
		
	},
	
	redirectToClub: function() {
		app.redirectTo('/pages/club/home/home?clubId=' + this.data.clubId)
	},
	
	bindInputGoodat: function(e) {
		this.setData({
			goodAt: e.detail.value,
		})
	},
	bindInputRealname: function(e) {
		this.setData({
			realname: e.detail.value,
		})
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
	 * 页面上拉触底事件的处理函数
	 */
	onReachBottom: function() {

	},

	callClub: function() {
		var self = this;
		wx.makePhoneCall({
			phoneNumber: self.data.clubInfo.clubPhone
		})
	},

})