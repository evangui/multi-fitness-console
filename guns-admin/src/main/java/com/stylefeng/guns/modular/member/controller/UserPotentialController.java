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

import com.stylefeng.guns.modular.system.model.UserPotential;
import com.stylefeng.guns.modular.member.service.IUserPotentialService;

/**
 * 潜在客户控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:13:08
 */
@Controller
@RequestMapping("/userPotential")
public class UserPotentialController extends BaseController {

    private String PREFIX = "/member/userPotential/";

    @Autowired
    private IUserPotentialService userPotentialService;

    /**
     * 跳转到潜在客户首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "userPotential.html";
    }

    /**
     * 跳转到添加潜在客户
     */
    @RequestMapping("/userPotential_add")
    public String userPotentialAdd() {
        return PREFIX + "userPotential_add.html";
    }

    /**
     * 跳转到修改潜在客户
     */
    @RequestMapping("/userPotential_update/{userPotentialId}")
    public String userPotentialUpdate(@PathVariable Integer userPotentialId, Model model) {
        UserPotential userPotential = userPotentialService.selectById(userPotentialId);
        model.addAttribute("item",userPotential);
        LogObjectHolder.me().set(userPotential);
        return PREFIX + "userPotential_edit.html";
    }

    /**
     * 获取潜在客户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<UserPotential> page = new PageFactory<UserPotential>().defaultPage();
    	
    	page = userPotentialService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增潜在客户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserPotential userPotential) {
        userPotentialService.insert(userPotential);
        return SUCCESS_TIP;
    }

    /**
     * 删除潜在客户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userPotentialId) {
        userPotentialService.deleteById(userPotentialId);
        return SUCCESS_TIP;
    }

    /**
     * 修改潜在客户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserPotential userPotential) {
        userPotentialService.updateById(userPotential);
        return SUCCESS_TIP;
    }

    /**
     * 潜在客户详情
     */
    @RequestMapping(value = "/detail/{userPotentialId}")
    @ResponseBody
    public Object detail(@PathVariable("userPotentialId") Integer userPotentialId) {
        return userPotentialService.selectById(userPotentialId);
    }
}
