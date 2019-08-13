const md5 = require('./lib/md5.js')
const app = getApp()

var util = {
	cachePrefix: '',
	memberCoachDetailUri: 'usr/member/userCommon/coachDetail',	//已登录用户对应教练详情的接口uri
	memberVipDetailUri: 'usr/member/userCommon/vipDetail',	//已登录用户对应教练详情的接口uri
//	apiUrl: 'http://localhost:9091/',
//	apiUrl: 'https://evangui.ngrok.wendal.cn/',
	apiUrl: 'https://xysrest.whbws.cn/',
	
	showLoading: true,
	
	md5: function(s) {
		return md5.md5(s);
	},
	
	setShowLoading: function(showLoading) {
		this.showLoading = showLoading;
	},
	
	//post请求
	postRequest: function(url, params, onSuccess, version, isAsync, onError) {
		var self = this;
		var url = this.apiUrl + url;
		var onSuccess = arguments[2] ? arguments[2] : function() {};
		var isAsync = arguments[4] != null ? arguments[4] : true;
		var cacheTime = arguments[5] ? arguments[5] : 0;
		var onError = arguments[6] ? arguments[6] : function() {};

		var authstr = getApp().getLocalToken() != null ? 'Bearer ' + getApp().getLocalToken() : '';
		var ajaxObj = {
			method: "post",
			url: url,
			cache: false,
			async: isAsync,
			dataType: 'text', //服务器返回json格式数据
			data: params,
			timeout: 6000,
			header: {
//				'content-type': 'application/x-www-form-urlencoded',
				'Authorization': authstr,
			},
			
			success: function(ret) {
				wx.hideLoading();
				if (ret.statusCode != 200) {
					try{
						var data = JSON.parse(ret.data);
					}catch(e){
						//TODO handle the exception
						var data = {
							message: ret.data,
							code: ret.statusCode,
						};
					}
					
				} else {
					var data = JSON.parse(ret.data);
					if (data.message == 'token过期') {
						getApp().clearLocalToken();
						return false;
					}
				}
				onSuccess(data);
				 
			},
			fail: function(xhr, type, errorThrown) {
				//console.log('error:' + errorThrown);
				console.log(xhr.responseText);
				//			onError();
			}
		};
		wx.showLoading({
		  title: '加载中',
		});
		wx.request(ajaxObj);
	},

	//get请求
	getRequest: function(url, params, onSuccess, version, isAsync, cacheTime, onError, showLoading) {
		var self = this;
		var url = this.apiUrl + url;
		var onSuccess = arguments[2] ? arguments[2] : function() {};
		var isAsync = arguments[4] != null ? arguments[4] : true;
		var cacheTime = arguments[5] ? arguments[5] : 0;
		var onError = arguments[6] ? arguments[6] : function() {};
		var showLoading = arguments[7] != null ? arguments[7] : true;

		/*var AcceptInfo = 'application/vnd.cattlepie.v1+json';*/
		var cacheKey = encodeURI(url + JSON.stringify(params));
		//		console.log(params)
		var sRet;
		if(cacheTime) {
			var data = this.getCache(cacheKey);
			if(data) {
				onSuccess(data, true);
				return;
			}
		}
		var authstr = getApp().getLocalToken() != null ? 'Bearer ' + getApp().getLocalToken() : '';

		var ajaxObj = {
			type: "get",
			url: url,
			cache: true,
			async: isAsync,
			dataType: 'text', //服务器返回json格式数据
			data: params,
			timeout: 6000,
			header: {
				'content-type': 'application/json',
				'Authorization': authstr,
			},
			success: function(ret) {
				wx.hideLoading();
				if (ret.statusCode != 200) {
					var data = {
						message: ret.data,
						code: ret.statusCode,
					};
				} else {
					var data = JSON.parse(ret.data);
					if (data.message == 'token过期') {
						getApp().clearLocalToken();
						//考虑当前页面重新加载
						return false;
					}
	
					if(cacheTime && data.code==0) {
						self.setCache(cacheKey, data, cacheTime);
					}
				}
				onSuccess(data);
			},
			fail: function(xhr, type, errorThrown) {
				//console.log('error:' + errorThrown);
				//console.log(xhr.responseText);
				onError();
			}
		};
		if (self.showLoading) {
			wx.showLoading();
		}
		wx.request(ajaxObj);
	},

	setCache: function(key, value, exp) {
		var curTime = new Date().getTime();
		var expTime = curTime + exp * 1000;
		wx.setStorageSync(this.cachePrefix + key, {
			data: value,
			expTime: expTime
		});
	},

	getCache: function(key) {
		var data = wx.getStorageSync(this.cachePrefix + key);
		if(!data || 'undefined' === typeof(data)) {
			return false;
		}
		var dataObj = data;
		if(new Date().getTime() > dataObj.expTime) {
			console.log(`信息${key}已过期`);
			wx.removeStorageSync(this.cachePrefix + key);
			return null;
		} else {
			//console.log("data="+dataObj.data);
			//console.log(JSON.parse(dataObj.data));
			var dataObjDatatoJson = dataObj.data;
			if('undefined' == typeof(dataObjDatatoJson)) {
				dataObjDatatoJson = false;
			}

			return dataObjDatatoJson;
		}
	},

	setLocation: function(lng, lat, cacheTime) {
		this.setCache('user-location', {
			lng: lng,
			lat: lat
		}, cacheTime);
	},

	getLocation: function() {
		return this.getCache('user-location');
	},

	randomString: function(len) {
		len = len || 32;
		var $chars = 'abcdefhijkmnprstwxyz2345678';
		var maxPos = $chars.length;
		var pwd = '';
		for(var i = 0; i < len; i++) {
			pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
		}
		return pwd;
	},

	/**
	 * 获取url中的GET参数值
	 * @param {Object} param
	 */
	getQueryString: function(param, url) {
		var searchIndex = url.indexOf('?');
		var searchParams = url.slice(searchIndex + 1).split('&');
		for(var i = 0; i < searchParams.length; i++) {
			var items = searchParams[i].split('=');
			if(items[0].trim() == param) {
				return items[1].trim();
			}
		}
	},

	/*获取当前页url*/
	getCurrentPageUrl: function() {
		var pages = getCurrentPages() //获取加载的页面
		var currentPage = pages[pages.length - 1] //获取当前页面的对象
		var url = currentPage.route //当前页面url
		return url
	},

	/*获取当前页带参数的url*/
	getCurrentPageUrlWithArgs: function() {
		var pages = getCurrentPages() //获取加载的页面
		var currentPage = pages[pages.length - 1] //获取当前页面的对象
		if (currentPage === undefined) {
			return "pages/index/index"
		}
		var url = currentPage.route //当前页面url
		var options = currentPage.options //如果要获取url中所带的参数可以查看options

		//拼接url的参数
		var urlWithArgs = url + '?'
		for(var key in options) {
			var value = options[key]
			urlWithArgs += key + '=' + value + '&'
		}
		urlWithArgs = urlWithArgs.substring(0, urlWithArgs.length - 1)

		return urlWithArgs
	},
	
	/**
	 * 获取queryString中的param参数值
	 * @param {Object} param
	 */
	getQueryString: function(queryString, param) {
		if (queryString === undefined || !queryString) {
			return null;
		}
		
		var searchParams = queryString.split('&');
		for(var i = 0; i < searchParams.length; i++) {
			var items = searchParams[i].split('=');
			if(items[0].trim() == param) {
				return items[1].trim();
			}
		}
	},
	//获取options中的参数，以直接参数为主，如未找到则取options.scene中的参数
	getParam: function(options, paramName) {
		var val = paramName in options ? options[paramName] 
			: util.getQueryString(decodeURIComponent(options.scene), paramName);
			
		if (!val) {
			val = '';
		}
  	return val;
	},
	
	//验证手机号格式是否正确
	isPhoneNum: function(phone) {
		return (/(^[1][3456789]{1}\d{9}$)|(^\d{8}$)|(^\d{10}$)/.test(phone));
	},
	getWeekday: function(timestamp) {
		return "日一二三四五六".charAt(new Date(timestamp).getDay())
	},
	
	timestamp: function(time) {
		if (undefined === time) {
			return Math.round(new Date().getTime()/1000);
		} else {
			time = time.replace(/-/g, ':').replace(' ', ':')
    	time = time.split(':')
    	var time1 = new Date(time[0], (time[1] - 1), time[2], time[3], time[4], time[5])
			return Math.round(time1.getTime()/1000);
		}
	},
	
	//格式化时间
	timeFormat: function(value, defaultVal) {
	  if (parseInt(value) === 0 || !value) {
	  	return defaultVal || '';
	  }
	  
		// 时间戳转 2017-03-03 12:00 格式
		var date = new Date(value * 1000);
		var Y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var H = date.getHours();
		var i = date.getMinutes();
		if(m < 10) {
			m = '0' + m;
		}
		if(d < 10) {
			d = '0' + d;
		}
		if(H < 10) {
			H = '0' + H;
		}
		if(i < 10) {
			i = '0' + i;
		}
		var t = Y + '-' + m + '-' + d + ' ' + H + ':' + i;
		return t;
	},
	//格式化时间
	dateFormat: function(value, defaultVal) {
	  if (parseInt(value) === 0 || !value) {
	  	return defaultVal || '';
	  }
	
		var date = new Date(value * 1000);
		var Y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var H = date.getHours();
		var i = date.getMinutes();
		if(m < 10) {
			m = '0' + m;
		}
		if(d < 10) {
			d = '0' + d;
		}
		if(H < 10) {
			H = '0' + H;
		}
		if(i < 10) {
			i = '0' + i;
		}
		var t = Y + '-' + m + '-' + d;
		return t;
	},
	//友好时间
	friendlytime: function(date, defaultVal) {
	  if (parseInt(date) === 0 || !date) {
	  	return defaultVal || '';
	  }
	  
		//获取js 时间戳
		var time = new Date().getTime();
		//去掉 js 时间戳后三位，与php 时间戳保持一致
		time = parseInt((time - date * 1000) / 1000);

		//存储转换值 
		var s;
		if(time < 60) { //十分钟内
			return '刚刚';
		} else if((time < 60 * 60) && (time >= 60)) {
			//超过十分钟少于1小时
			s = Math.floor(time / 60);
			return s + "分钟前";
		} else if((time < 60 * 60 * 24) && (time >= 60 * 60)) {
			//超过1小时少于24小时
			s = Math.floor(time / 60 / 60);
			return s + "小时前";
		} else if((time < 60 * 60 * 24 * 3) && (time >= 60 * 60 * 24)) {
			//超过1天少于3天内
			s = Math.floor(time / 60 / 60 / 24);
			return s + "天前";
		} else {
			//超过3天
			var date = new Date(parseInt(date) * 1000);
			return(date.getMonth() + 1) + "月" + date.getDate() + "日";
		}
	},
	
	alert: function(title, ms) {
		wx.showToast({
      title: title,
      icon: 'none',
      duration: ms === undefined ? 1500 : ms,
    })
	},
	//图标，有效值 "success", "loading", "none"
	toast: function(title, icon, ms) {
		wx.showToast({
      title: title,
      icon: icon === undefined ? 'none' : icon,
      duration: ms === undefined ? 1500 : ms,
    })
	},
//	当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消',
	getBespeakStatusStr: function(value) {
		switch(value.toString()) {
			case '0':
				return '未知';
				break;
			case '1':
				return '未确认';
				break;
			case '2':
				return '已确认';
				break;
				case '3':
				return '学员已取消';
				break;
				case '4':
				return '教练已取消';
				break;
			default:
				return '';
		}
	},
	
}

function formatTime(time) {
	if(typeof time !== 'number' || time < 0) {
		return time
	}

	var hour = parseInt(time / 3600)
	time = time % 3600
	var minute = parseInt(time / 60)
	time = time % 60
	var second = time

	return([hour, minute, second]).map(function(n) {
		n = n.toString()
		return n[1] ? n : '0' + n
	}).join(':')
}

function formatLocation(longitude, latitude) {
	if(typeof longitude === 'string' && typeof latitude === 'string') {
		longitude = parseFloat(longitude)
		latitude = parseFloat(latitude)
	}

	longitude = longitude.toFixed(2)
	latitude = latitude.toFixed(2)

	return {
		longitude: longitude.toString().split('.'),
		latitude: latitude.toString().split('.')
	}
}

module.exports = util;