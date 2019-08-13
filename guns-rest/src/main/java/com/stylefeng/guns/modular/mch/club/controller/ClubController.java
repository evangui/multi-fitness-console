package com.stylefeng.guns.modular.mch.club.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysql.fabric.xmlrpc.base.Array;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.dao.ClubMapper;
import com.stylefeng.guns.modular.system.model.Club;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.common.SimpleObject;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.modular.mch.club.service.IClubService;

/**
 * 俱乐部主体信息控制器
 *
 * @author guiyajun
 * @Date 2018-06-22 16:01:23
 */
@Controller
@RequestMapping("/mch/club")
public class ClubController extends BaseController {

    private String PREFIX = "/club/club/";

    @Autowired
    private IClubService clubService;
 
    /**
     * 跳转到添加俱乐部主体信息
     */
    @RequestMapping("/club_add")
    public String clubAdd() {
        return PREFIX + "club_add.html";
    }

    /**
     * 跳转到修改俱乐部主体信息
     */
    @RequestMapping("/club_update/{clubId}")
    public String clubUpdate(@PathVariable Integer clubId, Model model) {
        Club club = clubService.selectById(clubId);
        model.addAttribute("item",club);
        LogObjectHolder.me().set(club);
        return PREFIX + "club_edit.html";
    }

    /**
     * 新增俱乐部主体信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Club club) {
    	if (ToolUtil.isEmpty(club.getStartTime())) {
    		club.setStartTime(null);
    	}
    	if (ToolUtil.isEmpty(club.getExpireTime())) {
    		club.setExpireTime(null);
    	}
  
        clubService.insert(club);
        return SUCCESS_TIP;
    }

    /**
     * 删除俱乐部主体信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clubId) {
        clubService.deleteById(clubId);
        return SUCCESS_TIP;
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

    @RequestMapping("")
    public ResponseEntity<List<Club>> hello(@RequestBody SimpleObject simpleObject) {
    	System.out.println(simpleObject.toString());
    	System.out.println(simpleObject.getClass());
        
//        return ResponseEntity.ok(simpleObject);
        return ResponseEntity.ok(clubService.selectList(null));
    }
    
    /**
     * 俱乐部主体信息详情
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	Club club = clubService.selectById(clubId);
    	
    	Map<String, Object> clubMap = null;
		try {
			clubMap = (Map<String, Object>) ToolUtil.convertBean(club);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		String[] arrayGps = {club.getLng(), club.getLat()};
    	clubMap.put("gps", arrayGps);
    	
    	HashMap<Object, Object> mapRet = new HashMap<>();
    	mapRet.put("basic", clubMap);
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", mapRet));
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
