const util = require('../../../utils/util.js')
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		scrolltop: 0,
		showDayTableLoding: false,
		dayTableLoopTimes: 0,
		
		coachInfo: {},
		dayTable: [],
		timeTable: [],
		currentTimestamp: 0,
		lastActiveDayIndex: 2,
		lastActiveDay: 0,

		multiArray: [
			['预约开始时间'],
			[],
			['时长60分钟']
		],
		multiIndex: [0, 0, 0],
	},

	upper: function() {
		var self = this;
		console.log(11);
		
		var dayTable = self.data.dayTable;
		var currentTimestamp = util.timestamp();
		var dayTableLoopTimes = self.data.dayTableLoopTimes;
		var pageStart = 14*dayTableLoopTimes + 4;
		if (dayTableLoopTimes>=2) {
			return;
		}
		
		self.setData({showDayTableLoding: true})
		util.toast("加载更多日期", 'loading', 300);
		for (var i=0; i<14; i++) {
			dayTable.unshift({
				date: util.dateFormat(currentTimestamp - (pageStart+i) * 86400).substr(5),
				day: util.dateFormat(currentTimestamp - (pageStart+i) * 86400),
				weekday: util.getWeekday((currentTimestamp - (pageStart+i) * 86400) * 1000),
				active: false,
			});
		}
		
		dayTableLoopTimes++;
		setTimeout(function() {
			self.setData({
				lastActiveDayIndex: self.data.lastActiveDayIndex + 7*dayTableLoopTimes,
	//			lastActiveDay: util.dateFormat(currentTimestamp),
				dayTable: dayTable,
				showDayTableLoding: false,
				dayTableLoopTimes: dayTableLoopTimes
			})
		}, 200);
		
	},
	
	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function(options) {
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
				coachInfo: res.data,
			});
			self.initTableData();
		});
		return;
	},
	
	initTableData() {
		var self = this;
		var dayTable = [];
		var currentTimestamp = util.timestamp();

		dayTable.push({
			date: util.dateFormat(currentTimestamp - 3 * 86400).substr(5),
			day: util.dateFormat(currentTimestamp - 3 * 86400),
			weekday: util.getWeekday((currentTimestamp - 3 * 86400) * 1000),
			active: false,
		});
		dayTable.push({
			date: '前天',
			day: util.dateFormat(currentTimestamp - 2 * 86400),
			weekday: util.getWeekday((currentTimestamp - 2 * 86400) * 1000),
			active: false,
		});
		dayTable.push({
			date: '昨天',
			day: util.dateFormat(currentTimestamp - 1 * 86400),
			weekday: util.getWeekday((currentTimestamp - 1 * 86400) * 1000),
			active: false,
		});
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
		dayTable.push({
			date: util.dateFormat(currentTimestamp + 2 * 86400).substr(5),
			day: util.dateFormat(currentTimestamp + 2 * 86400),
			weekday: util.getWeekday((currentTimestamp + 2 * 86400) * 1000),
			active: false,
		});
		dayTable.push({
			date: util.dateFormat(currentTimestamp + 3 * 86400).substr(5),
			day: util.dateFormat(currentTimestamp + 3 * 86400),
			weekday: util.getWeekday((currentTimestamp + 3 * 86400) * 1000),
			active: false,
		});
		dayTable.push({
			date: util.dateFormat(currentTimestamp + 4 * 86400).substr(5),
			day: util.dateFormat(currentTimestamp + 4 * 86400),
			weekday: util.getWeekday((currentTimestamp + 4 * 86400) * 1000),
			active: false,
		});
		dayTable.push({
			date: util.dateFormat(currentTimestamp + 5 * 86400).substr(5),
			day: util.dateFormat(currentTimestamp + 5 * 86400),
			weekday: util.getWeekday((currentTimestamp + 5 * 86400) * 1000),
			active: false,
		});

		self.setData({
			currentTimestamp: currentTimestamp,
			dayTable: dayTable,
			lastActiveDay: util.dateFormat(currentTimestamp),
		});

		self.loadTimetable(util.dateFormat(currentTimestamp));
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
		return;
		var self = this;
		console.log('picker发送选择改变，携带值为', e.detail.value)
		this.setData({
			multiIndex: e.detail.value
		})

		var fromTimeStr = self.data.lastActiveDay + ' ' + self.data.multiArray[1][self.data.multiIndex[1]];
		var fromTime = util.timestamp(fromTimeStr);
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
						util.toast('预约失败');
					} else {
						util.toast('预约成功', 'success');
						setTimeout(function() {
							self.loadTimetable(self.data.lastActiveDay);
						}, 500);
					}

				}, 'v1', false);

			}
		});
	},

	//跳转到预约详情界面
	showDetail: function(e) {
		var bespeakInfo = e.currentTarget.dataset.bespeakinfo;
		//计入缓存
		util.setCache('bespeakDetail-' + bespeakInfo.id, bespeakInfo, 600);
		app.navigateTo(`../coach-bespeak-detail/coach-bespeak-detail?id=${bespeakInfo.id}`);
	},

	cancelBespeak: function(e) {
		var self = this;
		var bespeakInfo = e.currentTarget.dataset.bespeakInfo;
		//识别是否自己点击
		if(bespeakInfo.userId != app.getMember().id) {
//			return;
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
					cancelFrom: 'coach',
					recordId: bespeakInfo.id,
					remark: '',
				}
				util.postRequest('usr/syllabus/ptrainBespeak/cancel', data, function(res) {
					//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
					if(parseInt(res.code) !== 0) {
						util.toast('取消失败');
					} else {
						util.toast('取消预约成功', 'success');
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

//		dayTable[index].active = true;
//		dayTable[this.data.lastActiveDayIndex].active = false;

		for (var i=0; i<dayTable.length;i++) {
			dayTable[i].active = index == i ? true : false;
		}
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