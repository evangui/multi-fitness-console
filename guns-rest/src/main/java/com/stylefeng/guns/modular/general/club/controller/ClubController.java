package com.stylefeng.guns.modular.general.club.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysql.fabric.xmlrpc.base.Array;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;

import com.stylefeng.guns.modular.system.model.Club;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.club.service.IClubService;

/**
 * 俱乐部主体信息控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:01:23
 */
@Controller("general_clubController")
@RequestMapping("/general/club/club")
public class ClubController extends BaseController {

    @Autowired
    private IClubService clubService;
 
    /**
     * 俱乐部主体信息详情
     */
    @SuppressWarnings("unchecked")
	@GetMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	String authCode = Convert.toStr(request.getParameter("authCode"), "");
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
    	
    	Club club = null;
    	if (!clubId.equals(0)) {
    		club = clubService.selectById(clubId);
    	} else if (!authCode.equals("")) {
    		Wrapper<Club> ew = new EntityWrapper<Club>().eq("auth_code", authCode);
    		club = clubService.selectOne(ew);
    	} else {
    		return ResponseEntity.ok(new ReturnTip(501, "参数错误"));
    	}
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", club));
    }
    
    /**
     * 获取俱乐部主体信息列表
     */
    @RequestMapping(value = "/pagelist")
    @ResponseBody
    public Object pagelist() {
    	//综合查询条件
     	HashMap<String, Object> mapCondition = new HashMap<>();
     	mapCondition.put("trialClub", 0);
     	
    	Page<Club> page = new PageFactory<Club>().defaultPage("clubSort", "desc");
    	page = clubService.pageList(page, mapCondition);
//        return super.packForBT(page);
        return new ReturnTip(0, "成功", super.packForPannelTable(page));
    }

    /**
     * 修改俱乐部主体信息
     * @throws Exception 
     */
    @RequestMapping(value = "/updateBasic")
    @ResponseBody
    public Object updateBasic(@RequestBody HashMap<String, Object> mapParams) throws Exception {
    	Club club = (Club) ToolUtil.convertMap(Club.class, mapParams);
    	
    	//经纬度
    	JSONObject jsonGps = (JSONObject) mapParams.get("gps");
    	if (ToolUtil.isNotEmpty(jsonGps.get("x"))) {
    		club.setLng(jsonGps.get("x").toString());
    		club.setLat(jsonGps.get("y").toString());
    	}
    	//省市名称
    	JSONObject jsonCity = (JSONObject) mapParams.get("city");
    	if (ToolUtil.isNotEmpty(jsonCity.get("province"))) {
    		club.setClubProvince(jsonCity.get("province").toString());
    	}
    	if (ToolUtil.isNotEmpty(jsonCity.get("city"))) {
    		club.setClubCity(jsonCity.get("city").toString());
    	}

        clubService.updateById(club);
        return SUCCESS_TIP;
    }

   
    
    /**
     * 俱乐部主体信息详情
     */
    @RequestMapping(value = "/menus")
    @ResponseBody
    public ResponseEntity<ReturnTip> menus() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	
    	List<Object> layer1List = new ArrayList<Object>();
    	List<Object> layer2List = new ArrayList<Object>();
    	Map layer1Obj = new HashMap<>();
    	Map layer2Obj = new HashMap<>();
    	
    	layer2Obj.put("title", "系统设置");
    	layer2Obj.put("type", "sysSetting");
    	layer2Obj.put("jumpUrl", "/panel/club/1390/setting/index/");
    	layer2Obj.put("icon", "iconfont2");
    	layer2Obj.put("iconText", "&#xe617;");
    	layer2Obj.put("iconType", "1");
    	layer2List.add(layer2Obj);
    	
    	layer2Obj.put("title", "工作人员");
    	layer2Obj.put("type", "switchingClient");
    	layer2Obj.put("jumpUrl", "/panel/club/1390/setting/index/");
    	layer2Obj.put("icon", "iconfont2");
    	layer2Obj.put("iconText", "&#xe617;");
    	layer2Obj.put("iconType", "1");
    	layer2List.add(layer2Obj);
    	System.out.println("111");
    	System.out.println(layer2List);
    	
    	layer1Obj.put("list", layer2List);
    	layer1Obj.put("title", "会员管理");
    	layer1Obj.put("type", "vipUserModel");
    	
    	layer1List.add(layer1Obj);
    	layer1List.add(layer1Obj);
    	
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", layer2List));
    }
    
   
}
