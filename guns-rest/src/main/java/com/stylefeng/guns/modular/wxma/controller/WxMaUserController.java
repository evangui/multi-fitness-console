package com.stylefeng.guns.modular.wxma.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;

import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.general.tool.service.ISmsService;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;

import me.chanjar.weixin.common.error.WxErrorException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/wxma/user")
public class WxMaUserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxMaService wxService;
	
	@Autowired
	private IUserCommonService userCommonService;
	
	@Autowired
	private ISmsService dySmsService;
	
	/**
	 * 获取微信小程序的session_key和openid
	 * 
	 * @param code 微信前端login()方法返回的code
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/login")
	public ReturnTip login(String code, String encryptedData, String iv) throws Exception {
//		SendSmsResponse reString = dySmsService.sendSms("18571593115", "bindMobile", "{\"code\":\"123\"}", null);

		if (StringUtils.isBlank(code)) {
			return new ReturnTip(501, "empty jscode");
		}

		try {
			WxMaUserInfo wxUserInfo = null;
			WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
			String openid = session.getOpenid();
			String sessionKey = session.getSessionKey();
			String unionid = session.getUnionid();
			
			if (ToolUtil.isNotEmpty(encryptedData) && ToolUtil.isNotEmpty(iv)) {
				// 解密用户信息
				wxUserInfo = this.wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
				unionid = wxUserInfo.getUnionId();
			}
			
			this.logger.info(openid);
			this.logger.info(sessionKey);
			this.logger.info(unionid);
			
			// 根据unionid 识别本地用户信息，并获取awt token
			AuthResponse loginAuth = userCommonService.authForWeChat(unionid, openid);
			Map<String, Object> mapData = ToolUtil.convertBean(loginAuth);
			mapData.put("openid", openid);
			mapData.put("unionid", unionid);
			mapData.put("sessionKey", sessionKey);
			mapData.put("wxUserInfo", wxUserInfo);
			
	        return new ReturnTip(0, "成功", mapData);
//	        return JSON.toJSONString(ResponseEntity.ok(new ReturnTip(0, "成功", mapData)));
	        
		} catch (WxErrorException e) {
			this.logger.error(e.getMessage(), e);
			return new ReturnTip(501, e.toString());
		}
	}
	
	/**
	 * 获取微信小程序的session_key和openid
	 * 
	 * @param code 微信前端login()方法返回的code
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSessionInfo")
	public ReturnTip getSessionInfo(String code) throws Exception {
//		SendSmsResponse reString = dySmsService.sendSms("18571593115", "bindMobile", "{\"code\":\"123\"}", null);

		if (StringUtils.isBlank(code)) {
			return new ReturnTip(501, "empty jscode");
		}

		try {
			WxMaUserInfo wxUserInfo = null;
			WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
			String openid = session.getOpenid();
			String sessionKey = session.getSessionKey();
			String unionid = session.getUnionid();
			
			// 根据unionid 识别本地用户信息，并获取awt token
			AuthResponse loginAuth = userCommonService.authForWeChat(unionid, openid);
			Map<String, Object> mapData = new HashMap<>();
			mapData.put("openid", openid);
			mapData.put("unionid", unionid);
			mapData.put("sessionKey", sessionKey);
			
	        return new ReturnTip(0, "成功", mapData);
	        
		} catch (WxErrorException e) {
			this.logger.error(e.getMessage(), e);
			return new ReturnTip(501, e.toString());
		}
	}
	
	/**
	 * <pre>
	 * 获取用户信息接口
	 * </pre>
	 */
	@GetMapping("/info")
	public ReturnTip info(String sessionKey, String signature, String rawData, String encryptedData, String iv) {
		// 用户信息校验
		if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
			return new ReturnTip(501, "user check failed");
		}

		// 解密用户信息
		WxMaUserInfo userInfo = this.wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
		return new ReturnTip(0, "成功", userInfo);
	}

	/**
	 * <pre>
	 * 获取用户绑定手机号信息
	 * </pre>
	 */
	@GetMapping("/phone")
	public ReturnTip phone(String sessionKey, String signature, String rawData, String encryptedData, String iv) {
		// 用户信息校验
		if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
			return new ReturnTip(501, "user check failed");
		}

		// 解密
		WxMaPhoneNumberInfo phoneNoInfo = this.wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
		return new ReturnTip(0, "成功", phoneNoInfo);
	}

}
