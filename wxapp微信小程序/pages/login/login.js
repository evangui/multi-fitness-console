//index.js
//获取应用实例
const util = require('../../utils/util.js')
const app = getApp()
var refer = ''; // 来源 door门禁 onlineCard在线售卡 locker租柜 store商城
var parentClubInfo = '';
Page({
	data: {
//		clubInfo:'',
		parentClubInfo: parentClubInfo,
		phone: null,
		code: null,
		clubLogoMr: '/images/logo.png',
		isCode: true,
		codeNum: 60,
		refer: refer,
		isLoginBut: true,
		codeButText: "获取验证码",

		motto: 'Hello World',
		userInfo: {},
		hasUserInfo: false,
		canIUse: wx.canIUse('button.open-type.getUserInfo')
	},
	//事件处理函数
	bindViewTap: function() {
		wx.navigateTo({
			url: '../logs/logs'
		})
	},

	afterLogined: function(ms) {
		util.toast("已登录成功", "success");
		setTimeout(function() {
			//跳回到登录跳转前的来源页
			app.redirectTo('/' + app.getLoginRedirectUrl(), _ => {
				wx.navigateBack()
			});
		}, ms===undefined ? 1500 : ms);
	},

	onReady() {
    var title = '杏运树 - 绑定手机号'
    wx.setNavigationBarTitle({ title: title})
  },
  
	onLoad: function(options) {
		//检测用户登录状态，如果是登录状态，则跳回到登录跳转前的来源页
		var self = this;
		if(app.isWeixinLogin()) {
			self.afterLogined()
			return;
		}
		
		//!!! 如果用户已经授权过，根据unionid等信息，去服务端识别是否有绑定用户，如有则记录登录态，登录成功
		//调用登录接口，获取 code
		wx.login({
			success: function(loginRes) {
				wx.getSetting({
					success(setRes) {
						// 判断是否已授权
						if(!setRes.authSetting['scope.userInfo']) {
							//未登录，未授权=》最终认为未登录，转到登录页
							//							self.redirectToLoginPage(redirectToLogin, recordRef);
							return false;
						}

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
									//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
									if(parseInt(result.code) !== 0) {
										console.log(result.message);
										return false;
									}

									if(result.data.user) {
										//服务端存在微信用户
										app.setLocalToken(result.data.token);
										app.setSalt(result.data.randomKey);
										app.setLocalLoginData(result.data.user);

										app.globalData.openid = result.data.openid;
										app.globalData.unionid = result.data.unionid;
										app.globalData.sessionKey = result.data.sessionKey;
										app.globalData.wxUserInfo = result.data.wxUserInfo;

										self.afterLogined(300);
										return;
									} else {
										//未登录，未授权=》最终认为未登录，转到登录页
										//										self.redirectToLoginPage(redirectToLogin, recordRef);
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

	// 登录
	bindLoginBut: function() {
		var self = this;
		if(!util.isPhoneNum(self.data.phone)) {
			util.alert('请输入正确的手机号')
			return
		}
		if(!self.data.code) {
			util.alert('请输入验证码')
			return
		}
		if(!self.data.isLoginBut) {
			//			return
		}
		self.setData({
			isLoginBut: false
		})

		wx.getSetting({
			success(setRes) {
				/**
				 * 1 判断是否已授权
				 */
				if(!setRes.authSetting['scope.userInfo']) {
					//未登录，未授权=》给提示重新授权
					self.setData({
						isLoginBut: true
					})
					util.alert('请点登录按钮，并允许微信授权', 4000)
					return false;
				}

				/**
				 * 2  如果已授权，根据encryptedData中的unionid等信息，去服务端识别是否有绑定用户，如有则记录登录态，登录成功
				 */
				wx.login({
					success: function(loginRes) {
						wx.getUserInfo({
							lang: "zh_CN",
							success: function(userRes) {
								//发起网络请求
								var data = {
									code: loginRes.code,
									encryptedData: userRes.encryptedData,
									iv: userRes.iv
								};
								util.getRequest('wxma/user/login', data, function(loginRes) {
									//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
									if(parseInt(loginRes.code) !== 0) {
										console.log(loginRes.message);
										return false;
									}

									var bindNewPhone = true;	//当微信用户信息已存在时，是否允许重新绑定手机号
									if(!bindNewPhone && loginRes.data.user) {
										//服务端存在微信用户，认为登录成功，跳出整个逻辑
										app.setLocalToken(loginRes.data.token);
										app.setSalt(loginRes.data.randomKey);
										app.setLocalLoginData(loginRes.data.user);

										app.globalData.openid = loginRes.data.openid;
										app.globalData.unionid = loginRes.data.unionid;
										app.globalData.sessionKey = loginRes.data.sessionKey;
										app.globalData.wxUserInfo = loginRes.data.wxUserInfo;

										self.afterLogined();
										return;
									} else {

										var fromAuthCode = util.getCache('fromAuthCode')
				
										//已授权 未绑定用户=》最终认为未登录，将当前微信用户发到服务端注册并绑定手机号
										var data = {
//											clubId: self.data.clubInfo ? self.data.clubInfo.id : 0,
											authCode: fromAuthCode,
											phone: self.data.phone,
											verfiyCode: self.data.code,
											openid: loginRes.data.openid,
											unionid: loginRes.data.unionid,
											wxUserInfo: loginRes.data.wxUserInfo,
										}

										util.postRequest('general/member/userCommon/regWx', data, function(regRes) {
											debugger;
											if(regRes.cn == 0) {
												app.setLocalToken(regRes.data.token);
												app.setSalt(regRes.data.randomKey);
												app.setLocalLoginData(regRes.data.user);
		
												app.globalData.openid = loginRes.data.openid;
												app.globalData.unionid = loginRes.data.unionid;
												app.globalData.sessionKey = loginRes.data.sessionKey;
												app.globalData.wxUserInfo = loginRes.data.wxUserInfo;
												
												self.afterLogined()
												return;
											} else {
												//绑定失败
												util.alert(regRes.message, 5000)
											}
										});
										return;
									}
								});

							}
						})
					}
				});

			}
		});
		return;
	},

	bindInputPhone: function(e) {
		this.setData({
			phone: e.detail.value,
		})
	},
	bindInputCode: function(e) {
		this.setData({
			code: e.detail.value,
		})
	},

	// 获取验证码
	bindCodeBut: function() {
		var self = this;
		if(!util.isPhoneNum(self.data.phone)) {
			util.alert('请输入正确的手机号')
			return
		}

		if(!this.data.isCode) {
			return
		}

		self.setData({
			codeButText: self.data.codeNum + '秒'
		})

		var phone = self.data.phone;
		var type = 'bindMobile'
		var timestamp = util.timestamp();
		var data = {
			phone: phone,
			type: type,
			timestamp: timestamp,
			sign: util.md5(util.md5(phone + type + timestamp) + 'eRs49dd5a32raa'),
			hashkey: '14s123R13',
		};

		util.postRequest('general/tool/sms/send', data, function(ret) {
			//如果服务端没有查询到unionid相关的绑定用户，则返回空用户信息
			if(parseInt(ret.code) !== 0) {
				//请求失败
				self.setData({
					codeButText: '获取验证码'
				})
				util.alert(ret.message, 2000)
				return;
			}
			self.setData({
				isCode: false,
				codeButText: self.data.codeNum + '秒'
			})

			var codeSin;
			function countDown(seconds) {
				codeSin = setTimeout(function() {
					var codeNum = self.data.codeNum-1;
					self.setData({
						codeNum: codeNum,
						codeButText: codeNum + '秒'
					})

					if(codeNum <= 0) {
						self.setData({
							isCode: true,
							codeNum: seconds,
							codeButText: '获取验证码'
						})

						clearTimeout(codeSin);
					} else {
						countDown(60)
					}
				}, 1000);
			}
			countDown(60)
			util.toast("短信已发送", "success");
		});

	},
	
	//收集formid的点击事件
	formSubmit: function(e) {
		let formId = e.detail.formId;
		app.dealFormIds(formId); //处理保存推送码

		let type = e.detail.target.dataset.type;
		console.log('form发生了submit事件，推送码为：', formId, type)
		//根据type的值来执行相应的点击事件
		//...
		app.saveFormIds()
	},

})