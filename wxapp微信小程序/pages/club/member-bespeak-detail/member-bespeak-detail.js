const util = require('../../../utils/util.js')
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		recordId: 0,
		bespeakInfo: null,
		coachInfo: null,

		commentLen: 0,
		comment: '',
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function(options) {
		var self = this;
		//检查登录状态
		app.checkLogin();

		var recordId = util.getParam(options, 'id');
		if(!recordId) {
			util.alert("无效来源");
			return;
		}

		self.initBespeakInfo(recordId);
		
//		app.checkClubVip(clubId, function() {
//		
//	});
	},

	
	initBespeakInfo(recordId) {
		var self = this;
		var bespeakInfo = util.getCache('bespeakDetail-' + recordId);
		if(!bespeakInfo) {
			util.getRequest('usr/syllabus/ptrainBespeak/detail', {
				id: recordId
			}, function(res) {
				if(parseInt(res.code) !== 0) {
					return;
				}
				
				//验证身份所属
				if (res.data.userId != app.getMember().id) {
					util.alert("无效来源", 6000);
					return;
				}
				
				self.setData({
					bespeakInfo: res.data
				})
				self.initCoachInfo(res.data.coachId, res.data.clubId);
			})

		} else {
			self.setData({
				bespeakInfo
			});
			self.initCoachInfo(bespeakInfo.coachId, bespeakInfo.clubId);
		}

	},

	initCoachInfo(coachId, clubId) {
		var self = this;
		app.getCoachDetail(coachId, clubId, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
				return;
			}
			self.setData({
				coachInfo: res.data,
			});
		});
		return;
	},

	callCoach() {
		var self = this;
		wx.makePhoneCall({
			phoneNumber: self.data.coachInfo.phone
		})
	},

	cancelBespeak: function(e) {
		var self = this;
		var bespeakInfo = this.data.bespeakInfo;
		//识别是否自己点击
		if(bespeakInfo.userId != app.getMember().id) {
			return;
		}

		//确认是否预约
		wx.showModal({
			title: '取消预约确认',
			content: `您确定要取消该预约吗?`,
			confirmText: "确定",
			cancelText: "再看看",
			success: function(res) {
				console.log(res);
				if(!res.confirm) {
					return;
				}

				var data = {
					cancelFrom: 'user',
					recordId: bespeakInfo.id,
				}
				util.postRequest('usr/syllabus/ptrainBespeak/cancel', data, function(res) {
					//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
					if(parseInt(res.code) !== 0) {
						util.toast('取消失败');
					} else {
						util.toast('取消预约成功', 'success');
						setTimeout(function() {
							self.gotoBespeakRecords();
						}, 500);
					}

				}, 'v1', false);

			}
		});

	},
	
	gotoBespeakRecords() {
		app.redirectTo('/pages/club/member-bespeak-record/member-bespeak-record?clubId=' + this.data.bespeakInfo.clubId);
	},

	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady: function() {

	},

	/**
	 * 生命周期函数--监听页面显示
	 */
	onShow: function() {

	},

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

	}
})