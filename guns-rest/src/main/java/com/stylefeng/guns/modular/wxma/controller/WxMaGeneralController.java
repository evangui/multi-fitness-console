package com.stylefeng.guns.modular.wxma.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage.Data;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.syllabus.service.IPtrainBespeakRecordService;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.wxma.service.IWxMaCommonService;
import com.stylefeng.guns.rest.common.ReturnTip;

import me.chanjar.weixin.common.error.WxErrorException;

import java.awt.List;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序通用接口
 */
@RestController
@RequestMapping("/wxma/general")
public class WxMaGeneralController extends BaseController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxMaService wxService;
	@Autowired
	private IWxMaCommonService wxMaCommonService;
	
	/**
	 * 获取微信小程序的小程序码
	 * 
	 * @param path 已经发布的小程序存在的页面（否则报错） 例如 "pages/index/index" 
	 * 			不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getwxacodeunlimit")
	public ReturnTip getwxacodeunlimit() throws Exception {
		HttpServletRequest request = HttpKit.getRequest();
		String page = request.getParameter("page");
		String scene = request.getParameter("scene");
		scene = URLDecoder.decode(scene, "utf-8");
		
		int width = Convert.toInt(request.getParameter("width"), 430);
		Integer refresh = Convert.toInt(request.getParameter("refresh"), 0);
		
		System.err.println(scene);
		System.err.println(request.getParameterMap());
		
		
		if (StringUtils.isBlank(page)) {
			return new ReturnTip(501, "empty page");
		}
		if (ToolUtil.isEmpty(width)) {
			width = 430;
		}

		// 自定义的文件名称
        String trueFileName = MD5Util.encrypt(page+scene) + ".png";
        
        // 设置存放图片文件的路径
        Map<String, Object> generatingFileInfo = FileUtil.generateFilePath(trueFileName);
        String desPath = (String) generatingFileInfo.get("path");
        String url = (String) generatingFileInfo.get("url");
        
        //文件如已存在，则不重新生成
        if (refresh.equals(0) && new File(desPath).exists()) {
        	return new ReturnTip(0, "成功", url);
        }
        
		try {
			File codeFile = this.wxService.getQrcodeService().createWxaCodeUnlimit(scene, page, width, true, null, false);
            try{
                //使用apache提供的工具类操作
            	FileUtils.moveFile(codeFile, new File(desPath));
            }catch(Exception ee){
                throw new Exception("写入文件失败，"+ee.getMessage());
            }
            
	        return new ReturnTip(0, "成功", url);
	        
		} catch (WxErrorException e) {
			this.logger.error(e.getMessage(), e);
			return new ReturnTip(501, e.toString());
		}
	}
	
	/**
	 * 获取微信小程序的小程序码
	 * 适用于需要的码数量较少的业务场景
	 * 
	 * @param path 小程序的页面 例如 "pages/index/index?p=1&p2=2" 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getwxacode")
	public ReturnTip getwxacode() throws Exception {
		HttpServletRequest request = HttpKit.getRequest();
		String path = request.getParameter("path");
		Integer width = Convert.toInt(request.getParameter("width"), 430);
		Integer refresh = Convert.toInt(request.getParameter("refresh"), 0);
		System.err.println(path);
		//屏蔽该功能
		if (true) {
			return new ReturnTip(501, "empty path");
		}
		
		if (StringUtils.isBlank(path)) {
			return new ReturnTip(501, "empty path");
		}
		
		// 自定义的文件名称
        String trueFileName = MD5Util.encrypt(path) + ".png";
        
        // 设置存放图片文件的路径
        Map<String, Object> generatingFileInfo = FileUtil.generateFilePath(trueFileName);
        String desPath = (String) generatingFileInfo.get("path");
        String url = (String) generatingFileInfo.get("url");
        
        //文件如已存在，则不重新生成
        if (refresh.equals(0) && new File(desPath).exists()) {
        	return new ReturnTip(0, "成功", url);
        }
 
		try {
			File codeFile = this.wxService.getQrcodeService().createWxaCode(path, width, true, null, false);
            try{
                //使用apache提供的工具类操作
            	FileUtils.moveFile(codeFile, new File(desPath));
            }catch(Exception ee){
                throw new Exception("写入文件失败，"+ee.getMessage());
            }
            
	        return new ReturnTip(0, "成功", url);
//	        return JSON.toJSONString(ResponseEntity.ok(new ReturnTip(0, "成功", mapData)));
	        
		} catch (WxErrorException e) {
			this.logger.error(e.getMessage(), e);
			return new ReturnTip(501, e.toString());
		}
	}
	
	/**
	 * 发送模板消息
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/sendTemplateMsg")
	public ReturnTip sendTemplateMsg() throws Exception {
		HttpServletRequest request = HttpKit.getRequest();
		//获取post数据
    	HashMap<String, Object> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	
		String type = Convert.toStr(mapParams.get("type"));
		String page = Convert.toStr(mapParams.get("page"));
		Integer userId = Convert.toInt(mapParams.get("userId"), 0);
		String openid = Convert.toStr(mapParams.get("openid"));
		String formId = Convert.toStr(mapParams.get("formId"));
		JSONArray keywords = (JSONArray) mapParams.get("keywords");
		
		if (StringUtils.isBlank(openid) && !userId.equals(0) ) {
			//根据userid找openid
			UserCommon userCommon = ConstantFactory.me().getUserCommonById(userId);
			openid = userCommon.getWeixinOpenid();
		}
		if (StringUtils.isBlank(openid) ) {
			return new ReturnTip(501, "缺少必要参数");
		}
		//从队列去formid
		for (int i = 0; i < 3; i++) {
			if (StringUtils.isBlank(formId)) {
				formId = wxMaCommonService.getFormId(openid);
			} else {
				break;
			}
		}
		if (StringUtils.isBlank(formId)) {
			return new ReturnTip(501, "缺少formId");
		}
		
		WxMaTemplateMessage templateMessage = new WxMaTemplateMessage();
		templateMessage.setTemplateId(wxMaCommonService.getTemplateId(type));
		templateMessage.setToUser(openid);
		templateMessage.setFormId(formId);
		templateMessage.setPage(page);
		
		for (int i = 0; i < keywords.size(); i++) {
			JSONObject _mapKey = (JSONObject) keywords.get(i);
			String _keyword = (String) _mapKey.get("key");
			String _value = (String) _mapKey.get("value");
			String _color = Convert.toStr(_mapKey.get("color"), "");
			templateMessage.addData(new Data(_keyword, _value, _color));
		}
		
		try {
			this.wxService.getMsgService().sendTemplateMsg(templateMessage);
	        return new ReturnTip(0, "成功");
	        
		} catch (WxErrorException e) {
			this.logger.error(e.getMessage(), e);
			return new ReturnTip(501, e.toString());
		}
	}
	
	/**
	 * 保存推送码formId
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveFormIds")
	public ReturnTip saveFormIds() throws Exception {
		//获取post数据
    	HashMap<String, Object> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	
		String openid = Convert.toStr(mapParams.get("openid"));
		JSONArray formIds = (JSONArray) mapParams.get("formIds");
		
		if (StringUtils.isAnyBlank(openid)) {
			return new ReturnTip(501, "缺少必要参数");
		}
		
		wxMaCommonService.saveFormIds(openid, formIds);
		return new ReturnTip(0, "成功");
        
	}

}
