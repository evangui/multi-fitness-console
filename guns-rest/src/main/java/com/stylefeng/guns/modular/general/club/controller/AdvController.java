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
import com.stylefeng.guns.modular.system.model.ClubAdv;
import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.club.service.IClubAdvService;
import com.stylefeng.guns.modular.mch.club.service.IClubService;

/**
 * 俱乐部主体信息控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:01:23
 */
@Controller("general_advController")
@RequestMapping("/general/club/adv")
public class AdvController extends BaseController {

    @Autowired
    private IClubAdvService advService;
 
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
    	
    	ClubAdv adv = null;
    	if (!clubId.equals(0)) {
    		adv = advService.selectById(clubId);
    	} else if (!authCode.equals("")) {
    		Wrapper<ClubAdv> ew = new EntityWrapper<ClubAdv>().eq("auth_code", authCode);
    		adv = advService.selectOne(ew);
    	} else {
    		return ResponseEntity.ok(new ReturnTip(501, "参数错误"));
    	}
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", adv));
    }
    
    /**
     * 获取俱乐部主体信息列表
     */
    @RequestMapping(value = "/pagelist")
    @ResponseBody
    public Object pagelist() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
    	
    	//综合查询条件
     	HashMap<String, Object> mapCondition = new HashMap<>();
     	mapCondition.put("posId", Convert.toStr(request.getParameter("posId"), null));
     	
    	Page<ClubAdv> page = new PageFactory<ClubAdv>().defaultPage("sort", "asc");
    	page = advService.pageList(page, clubId, mapCondition);
//        return super.packForBT(page);
        return new ReturnTip(0, "成功", super.packForPannelTable(page));
    }

}
