const util = require('../../../utils/util.js')
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
  	coachId: 0,
  	codeImg: '',
  	finished: false,	//学员是否已扫码消课成功
  	ptrainInfo: {},		//消课的详细信息
  },
  
	//获取教练对应的二维码
	getwxacode: function (coachId, clubId) {
		var self = this;
  	//生成本次码的唯一编号
  	var conformCode = util.randomString(8);
  	var data = {
  		page: 'pages/club/member-finishPtrain/member-finishPtrain',
  		//注意scene最大32个字符的限制
  		scene: encodeURIComponent(`a=${conformCode}&b=${coachId}&c=${clubId}`),
		}
  	console.log(data)
  	
  	var timeoutFunc;
		util.getRequest('wxma/general/getwxacodeunlimit', data, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
//				util.alert("生成小程序码服务未上线，请联系杏运树工作人员", 5000);
				return;
			}
			self.setData({
				codeImg: res.data,
			});
			
			//定时识别用户是否已完成扫码消课
			timeoutFunc = setInterval(function() {
				if (self.data.finished) {
					return;
				}
		  	var data = {
		  		clubId,
		  		conformCode
				}
		  	util.setShowLoading(false)
				util.getRequest('usr/card/spend/detail', data, function(res) {
					if (res.code == 0 && res.data && res.data.id > 0) {
						util.setShowLoading(true)
						clearTimeout(timeoutFunc);
						
						self.setData({
							finished: true,
							ptrainInfo: res.data
						})
					}
				});
			}, 4000);	//end of timeoutFunc

		}, 'v1', true, 0);
		return;
 	},
 	
 	//获取教练对应的二维码
	checkFinished: function (clubId, conformCode) {
		var self = this;
		
  	var data = {
  		clubId,
  		conformCode
		}
		util.getRequest('usr/card/spend/detail', data, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
//				util.alert("生成小程序码服务未上线，请联系杏运树工作人员", 5000);
				return;
			}

		}, 'v1', true, 0);
		return;
 	},
 	
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  	//获取教练对应的二维码
  	
  	//生成本次码的唯一编号
  	var uniqueCode = util.md5(new Date().getTime());
  	
  	//检查登录状态
		app.checkLogin();

		var clubId = util.getParam(options, 'clubId');
		if(clubId === undefined || !clubId) {
			util.alert("俱乐部不存在");
			return;
		}
		
		//获取用户绑定教练详情
		var self = this;
		app.getCurrentCoachDetail(clubId, function(res) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(res.code) !== 0) {
				return;
			}
			self.setData({
				coachId: res.data.id,
			});

			self.getwxacode(res.data.id, clubId);
		});
  	
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