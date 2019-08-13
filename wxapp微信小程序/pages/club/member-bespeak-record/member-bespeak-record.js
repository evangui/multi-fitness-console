const util = require('../../../utils/util.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
  	allowInfinite: true,
		lastPage: 1,
		showListBottom: false,
		
		recordList: null,
		clubId: 0,
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
		//检查俱乐部的vip会员中(暂时只取当前会员绑定俱乐部)，是否有当前用户。
		self.setData({
			recordList: [],
			clubId: clubId,
		});
		
		wx.showLoading({
		  title: '加载中',
		});
  	app.checkClubVip(clubId, function() {
  		self.loadMore();
  	});
  	
  },

	/**
	 * 页面上拉触底事件的处理函数
	 */
	loadMore: function() {
		var self = this;

		if(!self.data.allowInfinite) return;
		//最多50页请求
		if(self.data.lastPage >= 50) {
			return;
		}

		self.setData({
			allowInfinite: false,
		});
		self.loadList(self.data.lastPage);
		self.setData({
			allowInfinite: true,
		});
	},

	loadList: function(page) {
		var self = this;
		var pageSize = 10;
		var data = {
			pageIndex: page - 1,
			pageSize: pageSize,
			clubId: self.data.clubId,
		}

		var cacheTime = 0;

		util.getRequest('usr/syllabus/ptrainBespeak/mylist', data, function(listRes) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(listRes.code) !== 0) {
				return;
			}

			if(page * pageSize >= listRes.data.total) {
				if(self.data.lastPage > 1) {
					self.setData({
						showListBottom: true
					});
				}
				self.setData({
					lastPage: 10000
				});
			}

			for(var i = 0; i < listRes.data.list.length; i++) {
				self.data.recordList.push(listRes.data.list[i]);
			}
			self.setData({
				recordList: self.data.recordList
			});

		}, 'v1', true, cacheTime);

		self.setData({
			lastPage: self.data.lastPage + 1
		});
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