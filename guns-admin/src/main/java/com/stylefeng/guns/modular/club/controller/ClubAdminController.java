package com.stylefeng.guns.modular.club.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.club.service.IClubAdminService;

/**
 * 俱乐部管理员控制器
 *
 * @author guiyajun
 * @Date 2018-06-25 10:24:28
 */
@Controller
@RequestMapping("/clubAdmin")
public class ClubAdminController extends BaseController {

    private String PREFIX = "/club/clubAdmin/";

    @Autowired
    private IClubAdminService clubAdminService;

    /**
     * 跳转到俱乐部管理员首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "clubAdmin.html";
    }

    /**
     * 跳转到添加俱乐部管理员
     */
    @RequestMapping("/clubAdmin_add")
    public String clubAdminAdd(int clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
    	model.addAttribute("clubName", ConstantFactory.me().getClubNameById(clubId));
        return PREFIX + "clubAdmin_add.html";
    }

    /**
     * 跳转到修改俱乐部管理员
     */
    @RequestMapping("/clubAdmin_update/{clubAdminId}")
    public String clubAdminUpdate(@PathVariable Integer clubAdminId, Model model) {
    	
        ClubAdmin clubAdmin = clubAdminService.selectById(clubAdminId);
        model.addAttribute("item",clubAdmin);
        LogObjectHolder.me().set(clubAdmin);
        return PREFIX + "clubAdmin_edit.html";
    }

    /**
     * 获取俱乐部管理员列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<ClubAdmin> page = new PageFactory<ClubAdmin>().defaultPage();
    	
    	page = clubAdminService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增俱乐部管理员
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ClubAdmin clubAdmin) {
    	if (ToolUtil.isEmpty(clubAdmin.getInsertTime())) {
    		clubAdmin.setInsertTime(null);
    	}
    	
    	clubAdmin.setInsertTime(DateUtil.timeStamp());
    	clubAdmin.setSalt(IdGenerator.getTimeId());
    	
    	String username = clubAdmin.getUsername();
    	String password = clubAdmin.getPassword();
    	String salt = clubAdmin.getSalt();
    	if (ToolUtil.isEmpty(username) || username.length() < 4) {
    		return this.getRetMap(500, "用户名不合法");
    	}
    	
    	if (ToolUtil.isEmpty(password) || password.length() < 4) {
    		return this.getRetMap(500, "密码不合法");
    	} else {
    		//设置入库密码
    		clubAdmin.setPassword(MD5Util.encBySalt(password, salt));
    	}
    	
        clubAdminService.insert(clubAdmin);
        return SUCCESS_TIP;
    }

    /**
     * 删除俱乐部管理员
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clubAdminId) {
        clubAdminService.deleteById(clubAdminId);
        return SUCCESS_TIP;
    }

    /**
     * 修改俱乐部管理员
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ClubAdmin clubAdmin) {
    	if (ToolUtil.isEmpty(clubAdmin.getInsertTime())) {
    		clubAdmin.setInsertTime(null);
    	}
    	
    	String username = clubAdmin.getUsername();
    	String password = clubAdmin.getPassword();
    	String salt = clubAdmin.getSalt();
    	if (ToolUtil.isEmpty(username) || username.length() < 4) {
    		return this.getRetMap(500, "用户名不合法");
    	}
    	
    	if (ToolUtil.isEmpty(password)) {
    		clubAdmin.setPassword(null);
    	} else {
    		//设置入库密码
    		clubAdmin.setPassword(MD5Util.encBySalt(password, salt));
    	}
    	
        clubAdminService.updateById(clubAdmin);
        return SUCCESS_TIP;
    }

    /**
     * 俱乐部管理员详情
     */
    @RequestMapping(value = "/detail/{clubAdminId}")
    @ResponseBody
    public Object detail(@PathVariable("clubAdminId") Integer clubAdminId) {
        return clubAdminService.selectById(clubAdminId);
    }
}
