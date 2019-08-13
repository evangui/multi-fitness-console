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
		
		itemList: [],
		vipId: 0,
		itemTotal: 0,
		
		current: 'personalCard',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  	//检查登录状态
		app.checkLogin();
		
  	var self = this;
		var clubId = util.getParam(options, 'clubId');
		if(!clubId) {
			util.alert("签到俱乐部不存在");
			return;
		}
		
		self.setData({
			clubId: clubId,
		});
		
		this.reloadContent();
  },
  
  //下拉刷新
	reloadContent: function(e) {
		this.setData({
			itemList: [],
			lastPage: 1
		})
		this.loadList(1);
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
			userId: app.getMember().id,
			logType: self.data.current,
		}

		var cacheTime = 0;

		util.getRequest('usr/card/spend/listLog', data, function(listRes) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(listRes.code) !== 0) {
				return;
			}

			if(page * pageSize >= listRes.data.total) {
				if(self.data.lastPage > 2) {
					self.setData({
						showListBottom: true
					});
				}
				self.setData({
					lastPage: 10000
				});
			}

			for(var i = 0; i < listRes.data.list.length; i++) {
				self.data.itemList.push(listRes.data.list[i]);
			}
			self.setData({
				itemList: self.data.itemList,
				itemTotal: listRes.data.total,
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
  	this.loadMore();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})