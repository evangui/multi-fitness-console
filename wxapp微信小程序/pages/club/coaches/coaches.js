const util = require('../../../utils/util.js')
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		clubId: 0,
		coachList: null,
		
		allowInfinite: true,
		lastPage: 1,
		showListBottom: false,
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
		//检查登录状态
//	app.checkLogin();
  	
  	var clubId = util.getParam(options, 'clubId');
		if(clubId === undefined || !clubId) {
			util.alert("俱乐部不存在");
			return;
		}
  	
		this.setData({
			coachList: [],
			clubId: clubId,
		});
		this.loadMore();
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

		util.getRequest('general/club/clubCoach/pagelist', data, function(listRes) {
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
				self.data.coachList.push(listRes.data.list[i]);
			}
			self.setData({
				coachList: self.data.coachList
			});

		}, 'v1', true, cacheTime);

		self.setData({
			lastPage: self.data.lastPage + 1
		});
		return;
	},

	goToBespeak: function(e) {
		var coachInfo = e.currentTarget.dataset.coachinfo;
		util.setCache('coachDetail-' + coachInfo.id, coachInfo, 600);
		app.navigateTo(`../member-bespeak/member-bespeak?coachId=${coachInfo.id}`);
	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function() {

	}
})