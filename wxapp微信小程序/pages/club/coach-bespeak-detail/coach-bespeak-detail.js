const util = require('../../../utils/util.js')
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		recordId: 0,
		bespeakInfo: null,
		userInfo: null,
		
		commentLen: 0,
		comment: '',
	},
	
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
		var self = this;
		//检查登录状态
		app.checkLogin();

		var recordId = util.getParam(options, 'id');
		if(!recordId) {
			util.alert("无效来源");
			return;
		}
		
		this.initBespeakInfo(recordId);
	},
	
	initBespeakInfo(recordId) {
		var self = this;
		var bespeakInfo = util.getCache('bespeakDetail-' + recordId);
		if (!bespeakInfo) {
			util.getRequest('usr/syllabus/ptrainBespeak/detail', {id: recordId}, function(res) {
			if(parseInt(res.code) !== 0) {
				return;
			}
			self.setData({bespeakInfo: res.data})
//			self.initUserInfo(res.data.userId);
		})
			
		} else {
			self.setData({bespeakInfo});
//			self.initUserInfo(bespeakInfo.userId);
		}
		
	},
	
	initUserInfo(userId) {
		var self = this;
		var data = {
			id: userId,
		};
		util.getRequest('general/member/userCommon/detail', data, function(res) {
			if(parseInt(res.code) !== 0) {
				return;
			}
			self.setData({
				userInfo: res.data
			})
		})
	},
 
	callMember() {
		var self = this;
		wx.makePhoneCall({
			phoneNumber: self.data.bespeakInfo.phone
		})
	},
	
	acceptBespeak() {
		var self = this;
		//确认是否预约
		wx.showModal({
			title: '接受预约确认',
			content: `您确定要接受该预约吗?`,
			confirmText: "确定",
			cancelText: "再看看",
			success: function(res) {
				console.log(res);
				if(!res.confirm) {
					return;
				}
				
				wx.showLoading({
					title: '正在处理中',
					mask: true,
				})
				setTimeout(function() {
					wx.hideLoading()
				}, 5000)

				var data = {
					recordId: self.data.bespeakInfo.id,
					remark: self.data.comment,
				};
				util.postRequest('usr/syllabus/ptrainBespeak/accept', data, function(res) {
					wx.hideLoading()
					//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
					if(parseInt(res.code) !== 0) {
						util.toast('操作失败');
					} else {
						util.toast('预约接受成功', 'success');
						
						//发送微信模板消息
						var keywords = [
							{"key":"keyword1",  "value": `${self.data.bespeakInfo.realname}` },
							{"key":"keyword2",  "value": `${self.data.bespeakInfo.date} ${self.data.bespeakInfo.fromTimeStr}`},
							{"key":"keyword3", "value": `${self.data.bespeakInfo.coachName}`},
				      {"key":"keyword4",  "value": `${self.data.bespeakInfo.coachName}-私教课` },
				      {"key":"keyword5",  "value": `${self.data.bespeakInfo.phone}` },
			      ]
						var detailUrl = `pages/club/member-bespeak-detail/member-bespeak-detail?id=${self.data.bespeakInfo.id}`;
						app.sendTemplateMsg('bespeak-confirm', '', self.data.bespeakInfo.userId, keywords, detailUrl)

						
						setTimeout(function() {
							self.gotoCoachBespeakRecord();
						}, 600);
					}
				})
			}
		});
	},
	
	gotoCoachBespeakRecord() {
		app.redirectTo('/pages/club/coach-bespeak-record/coach-bespeak-record?clubId=' + this.data.bespeakInfo.clubId);
	},
	
	cancelBespeak: function(e) {
		var self = this;
		var bespeakInfo = this.data.bespeakInfo;
		//识别是否自己点击
		if(bespeakInfo.userId != app.getMember().id) {
//			return;
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
					cancelFrom: 'coach',
					recordId: bespeakInfo.id,
					remark: self.data.comment,
				}
				util.postRequest('usr/syllabus/ptrainBespeak/cancel', data, function(res) {
					//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
					if(parseInt(res.code) !== 0) {
						util.toast('取消失败');
					} else {
						util.toast('取消预约成功', 'success');
						
						//发送微信模板消息
						var keywords = [
							{"key":"keyword1", "value": `${self.data.bespeakInfo.coachName}-私教课`},
				      {"key":"keyword2",  "value": util.timeFormat(util.timestamp())},
				      {"key":"keyword3",  "value": `${self.data.bespeakInfo.date} ${self.data.bespeakInfo.fromTimeStr}`},
				      {"key":"keyword4",  "value": `${self.data.bespeakInfo.coachName}-私教课`},
				      {"key":"keyword5",  "value": `教练取消-${self.data.comment}` },
			      ]
						var detailUrl = `pages/club/member-bespeak-detail/member-bespeak-detail?id=${self.data.bespeakInfo.id}`;
						app.sendTemplateMsg('bespeak-cancel', '', self.data.bespeakInfo.userId, keywords, detailUrl)
						
						setTimeout(function() {
							self.gotoCoachBespeakRecord();
						}, 500);
					}

				}, 'v1', false);

			}
		});

	},
	
	bindInputComment: function(e) {
		this.setData({
			comment: e.detail.value,
			commentLen: e.detail.value.length
		})
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