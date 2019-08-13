package com.stylefeng.guns.modular.setting.controller;

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

import com.stylefeng.guns.modular.system.model.SettingSyllabus;
import com.stylefeng.guns.modular.setting.service.ISettingSyllabusService;

/**
 * 团操课设置控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:59:52
 */
@Controller
@RequestMapping("/settingSyllabus")
public class SettingSyllabusController extends BaseController {

    private String PREFIX = "/setting/settingSyllabus/";

    @Autowired
    private ISettingSyllabusService settingSyllabusService;

    /**
     * 跳转到团操课设置首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "settingSyllabus.html";
    }

    /**
     * 跳转到添加团操课设置
     */
    @RequestMapping("/settingSyllabus_add")
    public String settingSyllabusAdd() {
        return PREFIX + "settingSyllabus_add.html";
    }

    /**
     * 跳转到修改团操课设置
     */
    @RequestMapping("/settingSyllabus_update/{settingSyllabusId}")
    public String settingSyllabusUpdate(@PathVariable Integer settingSyllabusId, Model model) {
        SettingSyllabus settingSyllabus = settingSyllabusService.selectById(settingSyllabusId);
        model.addAttribute("item",settingSyllabus);
        LogObjectHolder.me().set(settingSyllabus);
        return PREFIX + "settingSyllabus_edit.html";
    }

    /**
     * 获取团操课设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SettingSyllabus> page = new PageFactory<SettingSyllabus>().defaultPage();
    	
    	page = settingSyllabusService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增团操课设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SettingSyllabus settingSyllabus) {
        settingSyllabusService.insert(settingSyllabus);
        return SUCCESS_TIP;
    }

    /**
     * 删除团操课设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer settingSyllabusId) {
        settingSyllabusService.deleteById(settingSyllabusId);
        return SUCCESS_TIP;
    }

    /**
     * 修改团操课设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SettingSyllabus settingSyllabus) {
        settingSyllabusService.updateById(settingSyllabus);
        return SUCCESS_TIP;
    }

    /**
     * 团操课设置详情
     */
    @RequestMapping(value = "/detail/{settingSyllabusId}")
    @ResponseBody
    public Object detail(@PathVariable("settingSyllabusId") Integer settingSyllabusId) {
        return settingSyllabusService.selectById(settingSyllabusId);
    }
}
