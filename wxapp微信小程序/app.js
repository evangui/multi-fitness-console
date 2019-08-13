const util = require('utils/util.js')

App({
	globalData: {
		wechatUserType: 'normal',
		featureManager: {},
		userInfo: null,
		currentCustomer: null,

		openid: null,
		unionid: null,
		sessionKey: null,
		wxUserInfo: null,
		gloabalFomIds: [],
		// API_URL: 'http://localhost:3000',
	},

	onLaunch: function() {
		//		this.checkLogin();
		//		this.redirectTo("/pages/club/club?authCode=333333")
	},

	getTempOpenid: function(onSuccess) {
		var self = this;
		var openid = util.getCache('tempWxOpenid');
		if (openid) {
			onSuccess(openid);
			return;
		}
		
		wx.login({
			success: function(loginRes) {
				//发起网络请求
				var data = {
					code: loginRes.code
				};
				util.getRequest('wxma/user/getSessionInfo', data, function(res) {
					//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
					if(parseInt(res.code) !== 0) {
						console.log(res.message);
						return false;
					}
					
					util.setCache('tempWxOpenid', res.data.openid, 120);
					onSuccess(res.data.openid);
				})
			}
		});
	},

	/**
	 * 对所有需要用户登录后操作的页面，进行统一的登录检测
	 * 
	 * @param {Object} param
	 */
	checkLogin: function(redirectToLogin, recordRef, alwaysCallback) {
		var self = this;

		console.log('start checklogin');
		if(this.isWeixinLogin()) {
			console.log('local logined');
			if(alwaysCallback !== undefined) {
				alwaysCallback();
			}
			return true;
		}

		//!!! 如果用户已经授权过，根据unionid等信息，去服务端识别是否有绑定用户，如有则记录登录态，登录成功
		//调用登录接口，获取 code
		wx.login({
			success: function(loginRes) {
				wx.getSetting({
					fail(setRes) {
						console.log('wx.getSetting fail');
						console.log(setRes);
						debugger;
					},
					success(setRes) {
						// 判断是否已授权
						if(!setRes.authSetting['scope.userInfo']) {
							//未登录，未授权=》最终认为未登录，转到登录页
							self.redirectToLoginPage(redirectToLogin, recordRef);
							if(alwaysCallback !== undefined) {
								alwaysCallback();
							}
							return false;
						}
						console.log('wx.getSetting success');
						console.log(setRes);
						console.log('start wxma/user/login ');
						//如果已授权，根据encryptedData中的unionid等信息，去服务端识别是否有绑定用户，如有则记录登录态，登录成功
						wx.getUserInfo({
							lang: "zh_CN",
							success: function(userRes) {
								//发起网络请求
								var data = {
									code: loginRes.code,
									encryptedData: userRes.encryptedData,
									iv: userRes.iv
								};
								util.getRequest('wxma/user/login', data, function(result) {
									console.log('wxma/user/login res');
									console.log(result);
									//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
									if(parseInt(result.code) !== 0) {
										console.log(result.message);
										if(alwaysCallback !== undefined) {
											alwaysCallback();
										}
										return;
									}

									if(result.data.user) {
										//服务端存在微信用户
										self.setLocalToken(result.data.token);
										self.setSalt(result.data.randomKey);
										self.setLocalLoginData(result.data.user);

										self.globalData.openid = result.data.weixinOpenid;
										self.globalData.unionid = result.data.weixinUnionid;
										self.globalData.sessionKey = result.data.sessionKey;
										self.globalData.wxUserInfo = result.data.wxUserInfo;
										//重新加载当前界面
										if(alwaysCallback !== undefined) {
											alwaysCallback();
										} else {
											debugger;
											self.redirectTo('/' + util.getCurrentPageUrlWithArgs());
										}

									} else {
										//已授权 未绑定用户=》最终认为未登录，转到登录页
										self.redirectToLoginPage(redirectToLogin, recordRef);
										if(alwaysCallback !== undefined) {
											alwaysCallback();
										}
										return false;
									}
								});

							}
						})
					}
				})
			}
		})

		return true;
	},

	//记录登录跳转前的来源页 与 进行自动跳转到登录页面
	redirectToLoginPage: function(redirectToLogin, recordRef) {
		var self = this;
		recordRef = undefined === recordRef ? true : recordRef;
		redirectToLogin = undefined === redirectToLogin ? true : redirectToLogin;
		//记录登录跳转前的来源页
		if(redirectToLogin && recordRef) {
			this.setLoginRedirectUrl();
		}

		//识别redirectToLogin 参数进行自动跳转到登录页
		if(redirectToLogin) {
			wx.hideToast()
			wx.showModal({
				title: '请用手机号登录',
				confirmText: '去登录',
				//				content: '请前往 “我的” 页面绑定手机号',
				showCancel: false,
				success: function(res) {
					if(res.confirm) {
						// 清除没用的token
						self.clearLocalToken()
						wx.navigateTo({
							url: '/pages/login/login?id=1'
						})
					}
				}
			})
		}
		return;
	},

	/**
	 * 微信浏览器环境下，检测用户的登录状态
	 * 1. 如果本地不存在token则取服务端取token，取到后写token，即认为已登录。并将返回的用户信息写入localStorge
	 * 2. 如果服务端也没有取到token，则转到服务端微信授权处理api url。
	 */
	isWeixinLogin: function() {
		//				debugger
		var loginToken = wx.getStorageSync("token");
		var userinfo = this.getLocalLoginData();
		var unionid = (!userinfo || 'undefined' === typeof(userinfo.weixinUnionid)) ?
			'' : userinfo.weixinUnionid;
		if(!unionid || !userinfo) {
			//			wap.clearLocalToken();
			loginToken = userinfo = '';
		}
		console.log(loginToken)
		console.log(userinfo)

		if(loginToken && userinfo && userinfo.phone) {
			return true;
		}
		return false;
	},

	//验证用户手机号是否绑定，如果未绑定手机号，则自动转到绑定手机号页面
	checkMobileBind: function() {
		var self = this;
		var userinfo = this.getLocalLoginData();
		if(!userinfo.phone) {
			//去服务端取最新的身份信息
			this.refreshToken();
			userinfo = this.getLocalLoginData();
			if(!userinfo.phone) {

				wx.hideToast()
				wx.showModal({
					title: '未登录',
					content: '请前往 “我的” 页面绑定手机号',
					showCancel: false,
					success: function(res) {
						// 清除没用的token
						wx.removeStorage({
							key: 'userToken'
						})
						self.globalData.token = undefined
						wx.navigateTo({
							url: 'login/login?id=1'
						})
					}
				})
			}
		}
		return true;
	},

	//获取本地token数据
	getLocalToken: function() {
		var _token = wx.getStorageSync('token');
		if(!_token) {
			return false;
		}
		return _token;
	},

	//设置本地token数据
	setLocalToken: function(token) {
		if('undefined' === typeof(token)) {
			return false;
		}
		wx.setStorageSync('token', token);
	},

	//清除本地token数据
	clearLocalToken: function() {
		wx.removeStorageSync('token');
		wx.removeStorageSync('logindata');
		//		wap.checkLogin();
		return;
	},

	//设置高权限认证接口的加密通信salt
	setSalt: function(salt) {
		if('undefined' === typeof(salt)) {
			return false;
		}
		wx.setStorageSync('salt', salt);
	},

	//获取高权限认证接口的加密通信salt
	getSalt: function() {
		var _salt = wx.getStorageSync('salt');
		if(!_salt) {
			return false;
		}
		return _salt;
	},

	//设置本地用户登录后的用户信息logindata
	setLocalLoginData: function(_data) {
		if('undefined' === typeof(_data)) {
			return false;
		}
		wx.setStorageSync('logindata', _data);
	},

	//获取本地用户登录后的用户信息
	getLocalLoginData: function() {
		return wx.getStorageSync('logindata');
	},

	//获取已登录会员信息
	getMember: function() {
		if(!this.getLocalToken()) {
			return {};
		}
		return this.getLocalLoginData();
	},

	setCurrentRole: function(clubId, val) {
		return util.setCache('currentRole-' + clubId, val, 86400 * 365);
	},
	getCurrentRole: function(clubId) {
		return util.getCache('currentRole-' + clubId);
	},

	/*
	 刷新token,取到所需要的信息
	 */
	refreshToken: function() {
		var token = this.getLocalToken();
		var postdata = {
			Token: token
		};
		var self = this;
		this.getajax("api/user/auth/refresh", postdata, function(data) {
			if(data.status == 'success') {
				console.log(data.data.token);
				//				wx.removeStorageSync(token);
				//				wx.removeStorageSync('logindata');
				self.setLocalToken(data.data.token);
				self.setLocalLoginData(data.data.member_info);
			} else if(403 == data.code && data.msg == 'Token 错误') {
				self.clearLocalToken();
			}
		}, 'v1', false);
	},

	//登出方法
	dologout: function() {
		wx.clearStorageSync();
		wx.navigateTo({
			url: '/pages/login/login?id=1'
		})
	},

	//获取登录跳转前的来源页
	getLoginRedirectUrl: function() {
		return wx.getStorageSync('loginRedirectUrl') || 'pages/index/index';
	},

	//记录登录跳转前的来源页
	setLoginRedirectUrl: function(_url) {
		if('undefined' === typeof(_url)) {
			_url = util.getCurrentPageUrlWithArgs();
		}
		console.log(_url);
		wx.setStorageSync('loginRedirectUrl', _url);
	},

	//获取教练详情
	getCoachDetail: function(coachId, clubId, onSuccess) {
		onSuccess = arguments[2] ? arguments[2] : function() {};
		//获取用户绑定教练详情
		var self = this;
		var data = {
			id: coachId,
			clubId: clubId,
		}
		var cacheTime = 86400 * 3;
		util.getRequest('general/club/clubCoach/detail', data, function(res) {
			onSuccess(res);
		}, 'v1', true, cacheTime);
		return;
	},

	//获取当前已登录用户对应俱乐部的教练详情
	getCurrentCoachDetail: function(clubId, onSuccess) {
		onSuccess = arguments[1] ? arguments[1] : function() {};
		//获取用户绑定教练详情
		var self = this;
		var data = {
			clubId: clubId,
		}
		var cacheTime = 3600;

		util.getRequest(util.memberCoachDetailUri, data, function(res) {
			onSuccess(res);
		}, 'v1', true, cacheTime);
		return;
	},

	//获取当前已登录用户对应俱乐部vip详情
	getCurrentVipDetail: function(clubId, onSuccess) {
		onSuccess = arguments[1] ? arguments[1] : function() {};
		//获取用户绑定教练详情
		var self = this;
		var data = {
			clubId: clubId,
		}
		var cacheTime = 3600;

		util.getRequest(util.memberVipDetailUri, data, function(res) {
			onSuccess(res);
		}, 'v1', true, cacheTime);
		return;
	},

	//检查当前登录用户是否是俱乐部会员
	checkClubVip: function(clubId, onSuccess) {
		onSuccess = arguments[1] ? arguments[1] : function() {};

		var self = this;
		var userId = this.getMember().id;
		if(!userId) {
			return false;
		}
		var isVip = util.getCache('clubvip' + userId + '-' + clubId);
		if(isVip == 1) {
			onSuccess();
			return true;
		}

		//如果不是俱乐部vip，则服务端验证
		var data = {
			clubId: clubId,
			checkVip: 1,
			autoJoinVip: 1,
		};
		util.getRequest('usr/member/userCommon/checkRoles', data, function(res) {
			console.log(res)
			if(parseInt(res.code) !== 0) {
				self.redirectTo(`/pages/club/notvip/notvip?clubId=${clubId}`);
				return false;
			}
			if(res.data.isVip == 1) {
				self.setClubVip(clubId);
				onSuccess();
				return true;
			} else {
				self.redirectTo(`/pages/club/notvip/notvip?clubId=${clubId}`);
				return false;
			}
		});
	},

	setClubVip: function(clubId) {
		var userId = this.getMember().id;
		if(!userId) {
			return false;
		}
		util.setCache('clubvip' + userId + '-' + clubId, 1, 3600);
	},

	//本地保存formid
	dealFormIds: function(formId) {
		let formIds = this.globalData.gloabalFomIds; //获取全局数据中的推送码gloabalFomIds数组
		if(!formIds) formIds = [];
		let data = {
			formId: formId,
			expire: parseInt(new Date().getTime() / 1000) + 604800 //计算7天后的过期时间时间戳
		}
		formIds.push(data); //将data添加到数组的末尾
		this.globalData.gloabalFomIds = formIds; //保存推送码并赋值给全局变量
	},

	//推送formid到服务端
	saveFormIds: function() {
		var self = this;
		var formIds = this.globalData.gloabalFomIds; // 获取gloabalFomIds
		if(formIds.length) { //gloabalFomIds存在的情况下 将数组转换为JSON字符串
			this.globalData.gloabalFomIds = '';
		}
		var openid;
		if(this.isWeixinLogin()) {
			openid = this.getMember().weixinOpenid;
			var data = {
				openid: openid,
				formIds: formIds
			}
	
			util.postRequest('wxma/general/saveFormIds', data, function(detailRes) {
			})
		} else {
			this.getTempOpenid(function(openid) {
				var data = {
					openid: openid,
					formIds: formIds
				}
		
				util.postRequest('wxma/general/saveFormIds', data, function(detailRes) {
		
				})
			})
		}
		
	},

	//keywords: [
	//				{"key":"keyword1", "value": "测试数据一", "color": "#173177" },
	//	      {"key":"keyword2",  "value": "测试数据二", "color": "#173177" },
	//	      {"key":"keyword3",  "value": "测试数据三" },
	//    ]
	sendTemplateMsg: function(type, openid, userId, keywords, page) {
		var data = {
			openid: openid || "",
			userId: userId || 0,
			keywords: keywords,
			page: page || "",
			type: type,
		};
		util.postRequest('wxma/general/sendTemplateMsg', data, function(detailRes) {
			debugger;
		})
	},

	navigateTo: function(_url, onError) {
		var onError = arguments[1] ? arguments[1] : function() {};

		wx.navigateTo({
			url: _url,
			success: function() {
				console.log("navigateTo " + _url)
			},
			fail() {
				wx.switchTab({
					url: _url,
					success: function() {
						console.log("switchTab " + _url)
					},
					fail() {
						onError();
						console.log("location fail")
					}
				})
			}
		})
	},

	redirectTo: function(_url, onError) {
		var onError = arguments[1] ? arguments[1] : function() {};
		wx.redirectTo({
			url: _url,
			success: function() {
				console.log("redirectTo " + _url)
			},
			fail() {
				wx.switchTab({
					url: _url,
					success: function() {
						console.log("switchTab " + _url)
					},
					fail() {
						onError();
						console.log("location fail")
					}
				})
			}
		})
	},

})