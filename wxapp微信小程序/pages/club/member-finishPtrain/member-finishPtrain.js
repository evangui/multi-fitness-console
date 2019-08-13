const util = require('../../../utils/util.js')
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		recordId: 0,
		coachInfo: null,
		ptrainFinished: false,
		checkSuccess: false,
		checkMessage: "",

		starIndex5: 0,
		comment:'',
		commentLen: 0,
		commentSuccess: false,
	},

	onChange5(e) {
		const index = e.detail.index;
		this.setData({
			'starIndex5': index
		})
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function(options) {
		var self = this;
		//检查登录状态
		app.checkLogin();

		var conformCode = util.getParam(options, 'a');
		var coachId = util.getParam(options, 'b');
		var clubId = util.getParam(options, 'c');
		if(coachId === undefined || !coachId) {
			util.alert("教练不存在");
			return;
		}
		
		if(!conformCode || !clubId) {
			util.alert("小程序码无效");
			return;
		}
		
		this.setData({clubId});
		debugger
		//检查俱乐部的vip会员中(暂时只取当前会员绑定俱乐部)，是否有当前用户。
  	app.checkClubVip(clubId, function() {
  		self.doBespeak(conformCode, coachId, clubId);
  	});

	},
	
	//服务端处理预约
	doBespeak(conformCode, coachId, clubId) {
		var self = this;
		wx.showLoading({
			title: '正在消课中',
			mask: true,
		})
		setTimeout(function() {
			wx.hideLoading()
		}, 5000)

		//获取教练详情
		this.getCoachDetail(coachId, clubId)

		var data = {
			clubId,
			conformCode,
			coachId
		};
		//开始消课（调用服务端生成私教记录接口）
		util.postRequest('usr/card/spend/finishPtrain', data, function(checkinRes) {
			wx.hideLoading()
			self.setData({
				ptrainFinished: true
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
					recordId: checkinRes.data.id,
					checkSuccess: true,
					checkMessage: checkinRes.message,
					commentSuccess: checkinRes.data.commentContent ? true : false,
					comment: checkinRes.data.commentContent || '',
					starIndex5: checkinRes.data.commentRank || 0,
				})
				util.toast("消课成功", "success", 2000);

				//显示评价输入内容

			}
		})
	},

	getCoachDetail(coachId, clubId) {
		//获取用户绑定教练详情
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
	sendComment() {
		if (this.data.commentSuccess) {
			util.alert('已经成功评价过');
			return;
		}
		
		if (!this.data.recordId) {
			util.alert('信息异常');
			return;
		}
		if (!this.data.comment) {
			util.alert('请填写评价内容');
			return;
		}
		
		var self = this;
		var data = {
			recordId: this.data.recordId,
			content: this.data.comment,
			rank: this.data.starIndex5,
		};
		wx.showLoading({
			title: '正在消课中',
			mask: true,
		})
		setTimeout(function() {
			wx.hideLoading()
		}, 5000)
		
		//开始消课（调用服务端生成私教记录接口）
		util.postRequest('usr/card/spend/comment', data, function(checkinRes) {
			wx.hideLoading()
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(checkinRes.code) !== 0) {
				self.setData({
					checkMessage: checkinRes.message
				})
				util.toast(checkinRes.message);
			} else {
				self.setData({
					commentSuccess: true,
				})
				util.toast("私教评价成功", "success", 2000);
				setTimeout(function() {
					//跳回到登录跳转前的来源页
					wx.navigateBack()
				}, 1500);
			}
		})
	},
	
	bindInputComment: function(e) {
		this.setData({
			comment: e.detail.value,
			commentLen: e.detail.value.length
		})
	},
	
	redirectToClub: function() {
		app.redirectTo('/pages/club/home/home?clubId=' + this.data.clubId)
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