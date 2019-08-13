const util = require('../../../utils/util.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
  	clubId: 0,
		cardList: null,
		vipInfo: {},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  	var self = this;
  	//检查登录状态
  	app.checkLogin();
  	
  	var clubId = util.getParam(options, 'clubId');
		if(clubId === undefined || !clubId) {
			util.alert("俱乐部不存在");
			return;
		}
  	
  	this.setData({
			cardList: [],
			clubId: clubId,
		});
		
  	//检查是否是俱乐部vip会员
  	app.checkClubVip(clubId, function() {
  		self.getVipInfo();
			self.getCards();
  	});
  },
  
  //获取所有会员卡
  getVipInfo() {
  	var self = this;
  	var data = {
			clubId: self.data.clubId,
		}

		var cacheTime = 0;
		util.getRequest('/usr/member/userCommon/vipDetail', data, function(listRes) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(listRes.code) !== 0) {
				return;
			}
			
			self.setData({
				vipInfo: listRes.data,
			});

		}, 'v1', false);

		return;
  },
  
  //获取所有会员卡
  getCards() {
  	var self = this;
  	var data = {
			clubId: self.data.clubId,
		}

		var cacheTime = 0;
		util.getRequest('/usr/member/vipUser/listCards', data, function(listRes) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(listRes.code) !== 0) {
				return;
			}
			
			self.setData({
				cardList: listRes.data.cardList,
			});

		}, 'v1', false);

		return;
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