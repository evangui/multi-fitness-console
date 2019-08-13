package com.stylefeng.guns.modular.usr.card.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.CardRechargeLog;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.warpper.CardRechargeLogWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardRechargeLogService;

/**
 * 时间卡充值记录控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller("usr_cardRechargeController")
@RequestMapping("/usr/card/recharge")
public class CardRechargeController extends BaseController {

    @Autowired
    private ICardRechargeLogService cardRechargeLogService;
	
     /**
      * 获取分页列表
      */
     @RequestMapping(value = "/listLog")
     @ResponseBody
     public Object listLog() {
    	HttpServletRequest request = this.getHttpServletRequest();
     	//获取token中的用户信息
     	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
     	Integer userId = Convert.toInt(mapMember.get("id"), 0);
//     	Integer vipId = Convert.toInt(mapMember.get("vipId"), 0);
     	if (userId.equals(0)) {
     		return new ReturnTip(500, "会员信息失效");
     	}
      	
     	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
     	
     	//综合查询条件
     	HashMap<String, Object> mapCondition = this.getMapCondition(request);
     	
     	Page<CardRechargeLog> page = new PageFactory<CardRechargeLog>().defaultPage("id", "desc");
     	page = cardRechargeLogService.pageList(page, clubId, mapCondition);
     	Map<String, Object> ret = super.packForPannelTable(page);
     	
        return new ReturnTip(0, "成功", ret);
     }
     
     
     /**
      * 根据request查询条件，构造查询参数map
     * @param request
     * @return
     */
    private HashMap<String, Object> getMapCondition(HttpServletRequest request) {
    	//综合查询条件
      	HashMap<String, Object> mapCondition = new HashMap<>();
      	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡',
      	mapCondition.put("logType", Convert.toStr(request.getParameter("logType")));
      	mapCondition.put("vipId", Convert.toStr(request.getParameter("vipId")));
      	mapCondition.put("rechargeDateStart", Convert.toStr(request.getParameter("rechargeDateStart")));
      	mapCondition.put("rechargeDateStart", Convert.toStr(request.getParameter("rechargeDateStart")));
      	mapCondition.put("minExpires", Convert.toStr(request.getParameter("minExpires")));
      	mapCondition.put("maxExpires", Convert.toStr(request.getParameter("maxExpires")));
      	mapCondition.put("banksure", Convert.toStr(request.getParameter("banksure")));
      	mapCondition.put("cardStatus", Convert.toStr(request.getParameter("cardStatus")));
      	mapCondition.put("remark", Convert.toStr(request.getParameter("remark")));
      	mapCondition.put("contractNumber", Convert.toStr(request.getParameter("contractNumber")));
      	mapCondition.put("realname", Convert.toStr(request.getParameter("realname")));
      	mapCondition.put("phone", Convert.toStr(request.getParameter("phone")));
      	mapCondition.put("port", Convert.toStr(request.getParameter("port")));
      	mapCondition.put("sourceId", Convert.toStr(request.getParameter("source")));
      	mapCondition.put("title", Convert.toStr(request.getParameter("titleName")));
      	mapCondition.put("membershipName", Convert.toStr(request.getParameter("membershipName")));
      	mapCondition.put("opeUserName", Convert.toStr(request.getParameter("opeUserName")));
      	
      	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡',
     	switch (Convert.toStr(request.getParameter("logType"), "")) {
     	case "timeCardRecharge":
			mapCondition.put("logType", 1);
			break;
		case "personalCardRecharge":
			mapCondition.put("logType", 2);
			break;
		case "onceCardRecharge":
			mapCondition.put("logType", 3);
			break;
		case "storedCardRecharge":
			mapCondition.put("logType", 4);
			break;
		default:
			break;
		}
      	
      	return mapCondition;
	}
     
    
     /**
      * 充值记录详情
      */
     @SuppressWarnings("unchecked")
 	@RequestMapping(value = "/detail")
     @ResponseBody
     public Object detail() {
         HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//获取内容
     	Integer id = ToolUtil.toInt(request.getParameter("id"));
     	CardRechargeLog itemInDb = cardRechargeLogService.selectById(id);
     	
     	//验证合同所属俱乐部
     	if (!itemInDb.getClubId().equals(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(501, "合同访问受限"));
     	}
     	
     	Map<String, Object> mapRet = null;
 		try {
 			mapRet = ToolUtil.convertBean(itemInDb);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	new CardRechargeLogWarpper(null).warpTheMap(mapRet);
     	
     	return new ReturnTip(0, "成功",  mapRet);
     }
     
}
