package com.stylefeng.guns.modular.member.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.member.service.IUserCommonService;

/**
 * 注册用户控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:45:38
 */
@Controller
@RequestMapping("/userCommon")
public class UserCommonController extends BaseController {

    private String PREFIX = "/member/userCommon/";

    @Autowired
    private IUserCommonService userCommonService;

    /**
     * 跳转到注册用户首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "userCommon.html";
    }

    /**
     * 跳转到添加注册用户
     */
    @RequestMapping("/userCommon_add")
    public String userCommonAdd() {
        return PREFIX + "userCommon_add.html";
    }

    /**
     * 跳转到修改注册用户
     */
    @RequestMapping("/userCommon_update/{userCommonId}")
    public String userCommonUpdate(@PathVariable Integer userCommonId, Model model) {
        UserCommon userCommon = userCommonService.selectById(userCommonId);
        model.addAttribute("item",userCommon);
        LogObjectHolder.me().set(userCommon);
        return PREFIX + "userCommon_edit.html";
    }

    /**
     * 获取注册用户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<UserCommon> page = new PageFactory<UserCommon>().defaultPage();
    	
    	page = userCommonService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增注册用户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserCommon userCommon) {
        userCommonService.insert(userCommon);
        return SUCCESS_TIP;
    }

    /**
     * 删除注册用户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userCommonId) {
        userCommonService.deleteById(userCommonId);
        return SUCCESS_TIP;
    }

    /**
     * 修改注册用户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserCommon userCommon) {
        userCommonService.updateById(userCommon);
        return SUCCESS_TIP;
    }

    /**
     * 注册用户详情
     */
    @RequestMapping(value = "/detail/{userCommonId}")
    @ResponseBody
    public Object detail(@PathVariable("userCommonId") Integer userCommonId) {
        return userCommonService.selectById(userCommonId);
    }
}
