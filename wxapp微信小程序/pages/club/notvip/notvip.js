//index.js
//获取应用实例
const util = require('../../../utils/util.js')
const app = getApp()
var refer = ''; // 来源 door门禁 onlineCard在线售卡 locker租柜 store商城
var parentClubInfo = '';

Page({
	data: {
		clubInfo: null,
		parentClubInfo: parentClubInfo,
		clubLogoMr: '/images/logo.png',
		refer: refer,
		userInfo: {},
		hasUserInfo: false,
		canIUse: wx.canIUse('button.open-type.getUserInfo')
	},

	onReady() {
    var title = '请认证会员'
    wx.setNavigationBarTitle({ title: title})
  },
  
	onLoad: function(options) {
		var self = this;
		//检查登录状态
  	app.checkLogin();
		
		var clubId = options.clubId;
		if (clubId === undefined || !clubId) {
			util.alert("俱乐部不存在");
  		return;
		}
		
		self.setData({
			userInfo: app.getMember(),
		})
		
		//检查俱乐部的vip会员中(暂时只取当前会员绑定俱乐部)，是否有当前用户。
  	if (self.data.userInfo.clubId == clubId ) {
  		util.alert("系统异常，请稍后再试");
  		return;
  	}
  	
		//根据authCode去服务端查找俱乐部信息
		util.getRequest('general/club/club/detail', {clubId}, function(detailRes) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(detailRes.code) !== 0) {
				console.log(detailRes.message);
			} else {
				self.setData({
					clubInfo: detailRes.data
				});
				
				var fromAuthCode = util.getCache('fromAuthCode')
				if (fromAuthCode == detailRes.data.authCode) {
					self.autoJoinClub(clubId);
				}
				
			}
		})
		return true;
	},
	
	//识别来源俱乐部认证码，如果存在，且当前会员不是俱乐部用户，则自己加为俱乐部用户
	autoJoinClub: function(clubId) {
  	util.postRequest('usr/member/userClub/joinClub', {clubId}, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
		
			} else {
				//成为了该俱乐部用户
				console.log(res);
			}
		})
  	
	},

})