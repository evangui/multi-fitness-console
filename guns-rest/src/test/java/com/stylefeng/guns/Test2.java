package com.stylefeng.guns;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.BeanMap;
import org.junit.experimental.theories.Theories;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.modular.mch.member.service.impl.UserCommonServiceImpl;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.rest.common.SimpleObject;
import com.stylefeng.guns.rest.modular.auth.converter.BaseTransferEntity;

/**
 * json测试
 *
 * @author fengshuonan
 * @date 2017-08-25 16:11
 */


public class Test2 {
	
    
	public static void main(String[] args) {
		String sOpenCardTime = "2018-12-03 20:08";
		String sOpenCardTimeFormat = "yyyy-MM-dd";
		int time = DateUtil.date2TimeStamp(sOpenCardTime, sOpenCardTimeFormat);
		System.err.println(time);
		System.err.println(DateUtil.timeStamp2Date(time, null));
//		
//		UserCommonServiceImpl userService = new UserCommonServiceImpl();
//		
//		HashMap<String, Object> mapUserCommnon = new HashMap<>();
//		mapUserCommnon.put("id", 2);
//		mapUserCommnon.put("realname", "fsfsf");
//		mapUserCommnon.put("avatar", "fsfsf");
//		mapUserCommnon.put("gender", "1");
//		mapUserCommnon.put("phone", "18571592111");
//		
//		userService.beTrialVip(mapUserCommnon, 7);
    }
	 
    
    
}