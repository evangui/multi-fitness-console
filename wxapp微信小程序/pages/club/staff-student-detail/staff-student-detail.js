const util = require('../../../utils/util.js')
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		vipId: 0,
		vipInfo: null,
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function(options) {
		var self = this;
		//检查登录状态
		app.checkLogin();

		var vipId = util.getParam(options, 'vipId');
		var clubId = util.getParam(options, 'clubId');
		if(!vipId || !clubId) {
			util.alert("无效来源");
			return;
		}

		this.initVipInfo(vipId, clubId);
	},

	
	initVipInfo(vipId, clubId) {
		var self = this;
		util.getRequest('usr/member/vipUser/detail', {
			id: vipId,
			clubId: clubId
		}, function(res) {
			if(parseInt(res.code) !== 0) {
				return;
			}
			self.setData({
				vipInfo: res.data
			})
		}, 'v1', true, 1800)

	},

	callMember() {
		var self = this;
		wx.makePhoneCall({
			phoneNumber: self.data.vipInfo.phone
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