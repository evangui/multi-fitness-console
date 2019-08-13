package com.stylefeng.guns.modular.mch.member.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.warpper.UserCommonWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;

/**
 * 注册用户控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:45:38
 */
@Controller
@RequestMapping("/mch/member/userCommon")
public class UserCommonController extends BaseController {


    @Autowired
    private IUserCommonService userCommonService;

    /**
     * 获取注册用户列表
     */
    @RequestMapping(value = "/pagelist")
    @ResponseBody
    public Object pagelist() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//综合查询条件
     	HashMap<String, Object> mapCondition = new HashMap<>();
     	mapCondition.put("realname", Convert.toStr(request.getParameter("realname"), null));
     	mapCondition.put("phone", Convert.toStr(request.getParameter("phone"), null));
     	mapCondition.put("nickname", Convert.toStr(request.getParameter("nickname"), null));
     	mapCondition.put("virtual", Convert.toStr(request.getParameter("virtual"), null));
     	
    	Page<UserCommon> page = new PageFactory<UserCommon>().defaultPage("id", "desc");
    	page = userCommonService.pageList(page, clubId, mapCondition);
        
        return new ReturnTip(0, "成功", super.packForPannelTable(page));
    }
    
}
