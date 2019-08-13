package com.stylefeng.guns.modular.wxma.service;

import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;

import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 微信通用方法 服务类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-08-18
 */
public interface IWxMaCommonService {

	String getTemplateId(String type);

	String getFormId(String openid);

	boolean saveFormIds(String openid, JSONArray formIds);

 
}
