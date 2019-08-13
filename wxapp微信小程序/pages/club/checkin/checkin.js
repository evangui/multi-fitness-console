// pages/club/checkin/checkin.js
const util = require('../../../utils/util.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
  	clubId: 0,
  	checkFinished: false,
  	checkSuccess: false,
  	checkMessage: "",
  	checkinRecord: {},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  	var self = this;
  	var clubId = util.getParam(options, 'clubId');
		if (!clubId) {
			util.alert("签到俱乐部不存在");
  		return;
		}
		
  	//检查登录状态
  	app.checkLogin(true, true, function() {
  		var currentTimeStr = util.timeFormat(util.timestamp());
			wx.showLoading({
			  title: '签到中',
			  mask: true,
			})
	  	
	  	setTimeout(function(){
			  wx.hideLoading()
			},1000)
	  	
			//检查俱乐部的vip会员中(暂时只取当前会员绑定俱乐部)，是否有当前用户。
			app.checkClubVip(clubId, function() {
				//开始签到（调用服务端签到接口）
		  	util.postRequest('usr/member/checkin/checkin', {clubId}, function(checkinRes) {
		  		wx.hideLoading()
		  		self.setData({
						checkFinished: true
					})
		  		
					//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
					if(parseInt(checkinRes.code) !== 0) {
						self.setData({
							checkMessage: checkinRes.message
						})
						util.toast(checkinRes.message);
						setTimeout(function() {
							//跳回到登录跳转前的来源页
							wx.navigateBack()
						}, 1500);
				
					} else {
						self.setData({
							checkSuccess: true,
							checkMessage: checkinRes.message,
							checkinRecord: checkinRes.data,
							clubId: clubId
						})
						util.toast("签到成功", "success", 2000);
						wx.setNavigationBarTitle({
							title: `签到-${checkinRes.data.clubName}`
						})
						
						//发送微信模板消息
						var keywords = [
							{"key":"keyword1",  "value": `${checkinRes.data.realname}` },
							{"key":"keyword2",  "value": currentTimeStr},
							{"key":"keyword3", "value": `${checkinRes.data.clubName}` },
				      {"key":"keyword4",  "value": `入场签到扫码签到` },
			      ]
						var detailUrl = `pages/club/home/home?clubId=${clubId}`;
						
						
						var openid = util.getCache('tempWxOpenid');
						if (!openid) {
							app.sendTemplateMsg('checkin-success', '', app.getMember().id, keywords, detailUrl)
						} else {
							//新注册用户，通过openid发送消息
							app.sendTemplateMsg('checkin-success', openid, '', keywords, detailUrl)
						}
	
					}
				})
			})
	  	
  	});
		
  },

	redirectToClub: function() {
		app.redirectTo('/pages/club/home/home?clubId=' + this.data.clubId)
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
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})