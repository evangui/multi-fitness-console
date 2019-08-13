const util = require('../../../utils/util.js')
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		scrolltop: 0,
		hideTip: 0,
		memberInfo: {},
		coachId: 0,
		coachInfo: {},
		dayTable: [],
		timeTable: [],
		currentTimestamp: 0,
		lastActiveDayIndex: 0,
		lastActiveDay: 0,

		multiArray: [
			['预约开始时间'],
			[],
			['时长60分钟']
		],
		multiIndex: [0, 0, 0],
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
		app.checkLogin();

		var coachId = util.getParam(options, 'coachId');
		if(!coachId) {
			util.alert("教练不存在");
			return;
		}
		
		console.log(util.getCache('coachDetail-' + coachId))
		this.setData({
			coachId: coachId,
			coachInfo: util.getCache('coachDetail-' + coachId),
			memberInfo: app.getMember(),
		});
		
		//检查是否是俱乐部vip会员
  	app.checkClubVip(this.data.coachInfo.clubId);

		var dayTable = [];

		var currentTimestamp = util.timestamp();

		dayTable.push({
			date: '今天',
			day: util.dateFormat(currentTimestamp),
			weekday: util.getWeekday(currentTimestamp*1000),
			active: true,
		});
		dayTable.push({
			date: '明天',
			day: util.dateFormat(currentTimestamp + 1 * 86400),
			weekday: util.getWeekday((currentTimestamp + 1 * 86400) * 1000),
			active: false,
		});
		
		for (var i=2; i<=4; i++) {
			dayTable.push({
				date: util.dateFormat(currentTimestamp + i * 86400).substr(5),
				day: util.dateFormat(currentTimestamp + i * 86400),
				weekday: util.getWeekday((currentTimestamp + i * 86400) * 1000),
				active: false,
			});
		}

		this.setData({
			currentTimestamp: currentTimestamp,
			dayTable: dayTable,
			lastActiveDay: util.dateFormat(currentTimestamp),
			hideTip: util.getCache('member-bespeak-hidetip')
		});
		this.loadTimetable(util.dateFormat(currentTimestamp));
		
	},

	loadTimetable: function(date) {
		var self = this;
		var data = {
			date: date,
			coachId: self.data.coachInfo.id,
		}

		var cacheTime = 0;
		util.getRequest('usr/club/clubCoach/coacheTimeTable', data, function(listRes) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(listRes.code) !== 0) {
				return;
			}
			self.setData({
				timeTable: listRes.data.timeTable,
			});

			var _data = {
				multiArray: self.data.multiArray,
			};
			for(var i = 0; i < listRes.data.timeTable.length; i++) {
				var _item = listRes.data.timeTable[i];
				if(_item.canBespeak == 1) {
					_data.multiArray[1].push(_item.startTimeStr);
				}
			}
			self.setData(_data)

		}, 'v1', false);

		return;
	},
	
	bindMultiPickerChange: function(e) {
		var self = this;
		console.log('picker发送选择改变，携带值为', e.detail.value)
		this.setData({
			multiIndex: e.detail.value
		})

		var fromTimeStr = self.data.lastActiveDay + ' ' + self.data.multiArray[1][self.data.multiIndex[1]];
		var fromTime = util.timestamp(fromTimeStr + ':00');
		var toTime = fromTime + 3600;
		var toTimeStr = util.timeFormat(toTime);
		
		//确认是否预约
		wx.showModal({
			title: '预约确认',
			content: `您确定要预约教练${self.data.coachInfo.realname}的私教课吗,开始时间:${fromTimeStr};结束时间:${toTimeStr}`,
			confirmText: "确定预约",
			cancelText: "取消",
			success: function(res) {
				console.log(res);
				if(!res.confirm) {
					return;
				}

				var data = {
					recordType: 0,
					coachId: self.data.coachInfo.id,
					clubId: self.data.coachInfo.clubId,
					starttime: fromTime,
					endtime: toTime,
				}
				util.postRequest('usr/syllabus/ptrainBespeak/bespeak', data, function(res) {
					//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
					if(parseInt(res.code) !== 0) {
						util.toast(res.message);
					} else {
						util.toast('预约成功', 'success');
						
						//发送微信模板消息
						var keywords = [
							{"key":"keyword1", "value": `${self.data.coachInfo.clubName}-私教课`},
				      {"key":"keyword2",  "value": self.data.coachInfo.realname},
				      {"key":"keyword3",  "value": `${fromTimeStr}` },
				      {"key":"keyword4",  "value": `60分钟` },
				      {"key":"keyword5",  "value": `${res.data.realname}` },
				      {"key":"keyword6",  "value": `${res.data.phone}` },
			      ]
						var detailUrl = `pages/club/member-bespeak-detail/member-bespeak-detail?id=${res.data.id}`;
						app.sendTemplateMsg('bespeak-start', app.getMember().weixinOpenid, 
							0, keywords, detailUrl)
						
						//发送微信模板消息
						var detailUrl = `pages/club/coach-bespeak-detail/coach-bespeak-detail?id=${res.data.id}`;
						app.sendTemplateMsg('bespeak-start', '', self.data.coachInfo.userId, keywords, detailUrl)
						
						setTimeout(function() {
							self.loadTimetable(self.data.lastActiveDay);
						}, 500);
					}

				}, 'v1', false);

			}
		});
	},

	cancelBespeak: function(e) {
		var self = this;
		var item = e.currentTarget.dataset.item;
		//非自己的预约，不能取消
		if(item.bespeakInfo.userId != app.getMember().id) {
			return;
		}
		//已结束的预约不让取消
		if (item.bespeakInfo.status == 5) {
			return;
		}

		//确认是否预约
		wx.showModal({
			title: '取消预约确认',
			content: `您确定要取消该预约吗?`,
			confirmText: "确定",
			cancelText: "再看看",
			success: function(res) {
				console.log(res);
				if(!res.confirm) {
					return;
				}

				var data = {
					cancelFrom: 'user',
					recordId: item.bespeakId,
					remark: '',
				}
				util.postRequest('usr/syllabus/ptrainBespeak/cancel', data, function(res) {
					//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
					if(parseInt(res.code) !== 0) {
						util.toast(res.message);
					} else {
						util.toast('取消预约成功', 'success');
						
						//发送微信模板消息
						var keywords = [
							{"key":"keyword1", "value": `${self.data.coachInfo.clubName}-私教课`},
				      {"key":"keyword2",  "value": util.timeFormat(util.timestamp())},
				      {"key":"keyword3",  "value": `${item.bespeakInfo.fromTimeStr}` },
				      {"key":"keyword4",  "value": `${self.data.coachInfo.clubName}-私教课`},
				      {"key":"keyword5",  "value": `自己取消` },
			      ]
						var detailUrl = `pages/club/member-bespeak-detail/member-bespeak-detail?id=${item.bespeakInfo.id}`;
						app.sendTemplateMsg('bespeak-cancel', app.getMember().weixinOpenid, 
							0, keywords, detailUrl)
						
						setTimeout(function() {
							self.loadTimetable(self.data.lastActiveDay);
						}, 500);
					}

				}, 'v1', false);

			}
		});

	},

	bindMultiPickerColumnChange: function(e) {
		var self = this;
		console.log('修改的列为', e.detail.column, '，值为', e.detail.value);
		var data = {
			multiArray: this.data.multiArray,
			multiIndex: this.data.multiIndex
		};
		data.multiIndex[e.detail.column] = e.detail.value;
		this.setData(data);

	},

	startBespeak: function(e) {
		var item = e.currentTarget.dataset.item;
		var data = {
			multiIndex: this.data.multiIndex
		};

		for(var i = 0; i < this.data.multiArray[1].length; i++) {
			var _item = this.data.multiArray[1][i];
			if(_item == item.startTimeStr) {
				data.multiIndex[1] = i;
				break;
			}
		}
		this.setData(data);
	},

	changeDay: function(e) {
		var index = e.currentTarget.dataset.index;
		var dayTable = this.data.dayTable;

		dayTable[index].active = true;
		dayTable[this.data.lastActiveDayIndex].active = false;

		this.setData({
			dayTable: dayTable,
			lastActiveDayIndex: index,
			lastActiveDay: dayTable[index].day,
			scrolltop: 0,
		});

		//请求新的的时间预约表
		this.loadTimetable(dayTable[index].day);
	},
	
	callCoach() {
		var self = this;
		wx.makePhoneCall({
		  phoneNumber: self.data.coachInfo.phone
		})
	},
	
	handleClick() {
		util.setCache('member-bespeak-hidetip', 1, 86400*3);
		this.setData({
			hideTip: 1,
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