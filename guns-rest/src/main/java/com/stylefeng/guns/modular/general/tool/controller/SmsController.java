package com.stylefeng.guns.modular.general.tool.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.MD5Util;

import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.stylefeng.guns.modular.general.tool.service.ISmsService;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.modular.auth.converter.BaseTransferEntity;

/**
 * 俱乐部管理员控制器
 *
 * @author guiyj007
 * @Date 2018-06-25 10:24:28
 */
@Controller
@RequestMapping("/general/tool/sms")
public class SmsController extends BaseController {
	@Autowired
	private ISmsService dySmsService;
	
    /**
     * 服务端生成卡号（确保卡号唯一性，暂时用时间错+随机数代替）
     */
    @SuppressWarnings("unchecked")
	@PostMapping(value = "/send")
    @ResponseBody
    public Object send() {
    	HashMap<String, String> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
//        if (mapParams.containsKey("phone")) {
//        	return new ReturnTip(0, "短信已发送成功", CacheKit.get("sms", mapParams.get("phone")));
//        }
    	
    	String phone = mapParams.get("phone");
    	String type = mapParams.get("type");
    	Integer timestamp = Convert.toInt(mapParams.get("timestamp"));
    	String sign = mapParams.get("sign");
    	String hashkey = mapParams.get("hashkey");
    	
    	if (!"14s123R13".equals(hashkey)) {
    		return new ReturnTip(404, "error");
    	}
    	if (!sign.equals(MD5Util.encBySalt(phone + type + timestamp, "eRs49dd5a32raa"))) {
    		return new ReturnTip(405, "error");
    	}
    	
    	String code = "" + Math.round(Math.random()*(9999-1000)+1000);
    	try {
			SendSmsResponse sendResponse = dySmsService.sendSms(phone, type, "{\"code\":\"" + code + "\"}", null);
			
			if (sendResponse.getCode().equals("OK")) {
				CacheKit.put("sms", phone, code);
				return new ReturnTip(0, "短信发送成功");
			} else {
				return new ReturnTip(403, sendResponse.getMessage());
			}
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			return new ReturnTip(501, e.getErrMsg());
			return new ReturnTip(0, "短信已发送成功");
		}
    }
    
}
