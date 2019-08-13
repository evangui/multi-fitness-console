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

import com.stylefeng.guns.modular.system.model.UserSource;
import com.stylefeng.guns.modular.member.service.IUserSourceService;

/**
 * 自定义客户来源控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:13:36
 */
@Controller
@RequestMapping("/userSource")
public class UserSourceController extends BaseController {

    private String PREFIX = "/member/userSource/";

    @Autowired
    private IUserSourceService userSourceService;

    /**
     * 跳转到自定义客户来源首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "userSource.html";
    }

    /**
     * 跳转到添加自定义客户来源
     */
    @RequestMapping("/userSource_add")
    public String userSourceAdd() {
        return PREFIX + "userSource_add.html";
    }

    /**
     * 跳转到修改自定义客户来源
     */
    @RequestMapping("/userSource_update/{userSourceId}")
    public String userSourceUpdate(@PathVariable Integer userSourceId, Model model) {
        UserSource userSource = userSourceService.selectById(userSourceId);
        model.addAttribute("item",userSource);
        LogObjectHolder.me().set(userSource);
        return PREFIX + "userSource_edit.html";
    }

    /**
     * 获取自定义客户来源列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<UserSource> page = new PageFactory<UserSource>().defaultPage();
    	
    	page = userSourceService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增自定义客户来源
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserSource userSource) {
        userSourceService.insert(userSource);
        return SUCCESS_TIP;
    }

    /**
     * 删除自定义客户来源
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer userSourceId) {
        userSourceService.deleteById(userSourceId);
        return SUCCESS_TIP;
    }

    /**
     * 修改自定义客户来源
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserSource userSource) {
        userSourceService.updateById(userSource);
        return SUCCESS_TIP;
    }

    /**
     * 自定义客户来源详情
     */
    @RequestMapping(value = "/detail/{userSourceId}")
    @ResponseBody
    public Object detail(@PathVariable("userSourceId") Integer userSourceId) {
        return userSourceService.selectById(userSourceId);
    }
}
