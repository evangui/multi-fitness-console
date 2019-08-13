// pages/scancode/scancode.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
  
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  	wx.scanCode({
  		onlyFromCamera: false,
  		success: (res) => {
  			console.log(res);
  			//目前只识别 当前小程序的合法二维码
  			if (res.path !== undefined && res.path) {
  				getApp().redirectTo(res.path);
  			} else {
  				wx.showToast({
  					title: '当前只支持扫描杏运树小程序码',
			      content: res.result + res.rawData,
			      icon: 'none',
			      duration: 6000,
			    })
  				return;
  				wx.showToast({
			      content: res.result,
			      icon: 'none',
			      duration: 6000,
			    })
  			}
  		},
  		fail: (res) => {
  			console.log(res);
  		}
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