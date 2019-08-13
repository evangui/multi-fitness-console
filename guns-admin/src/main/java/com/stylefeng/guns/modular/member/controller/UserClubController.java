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
import com.stylefeng.guns.modular.system.model.UserClub;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.member.service.IUserClubService;

/**
 * 俱乐部普通用户控制器
 *
 * @author fengshuonan
 * @Date 2018-08-22 20:37:05
 */
@Controller
@RequestMapping("/userClub")
public class UserClubController extends BaseController {

    private String PREFIX = "/member/userClub/";

    @Autowired
    private IUserClubService userClubService;

    /**
     * 跳转到俱乐部普通用户首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "userClub.html";
    }

    /**
     * 跳转到添加俱乐部普通用户
     */
    @RequestMapping("/userClub_add")
    public String userClubAdd() {
        return PREFIX + "userClub_add.html";
    }

    /**
     * 跳转到修改俱乐部普通用户
     */
    @RequestMapping("/userClub_update/{userClubId}")
    public String userClubUpdate(@PathVariable Integer userClubId, Model model) {
        UserClub userClub = userClubService.selectById(userClubId);
        model.addAttribute("item",userClub);
        LogObjectHolder.me().set(userClub);
        return PREFIX + "userClub_edit.html";
    }

    /**
     * 获取俱乐部普通用户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<UserClub> page = new PageFactory<UserClub>().defaultPage();
    	
    	page = userClubService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增俱乐部普通用户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserClub userClub) {
        userClubService.insert(userClub);
        return SUCCESS_TIP;
    }

    /**
     * 删除俱乐部普通用户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userClubId) {
        userClubService.deleteById(userClubId);
        return SUCCESS_TIP;
    }

    /**
     * 修改俱乐部普通用户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserClub userClub) {
        userClubService.updateById(userClub);
        return SUCCESS_TIP;
    }

    /**
     * 俱乐部普通用户详情
     */
    @RequestMapping(value = "/detail/{userClubId}")
    @ResponseBody
    public Object detail(@PathVariable("userClubId") Integer userClubId) {
        return userClubService.selectById(userClubId);
    }
}
