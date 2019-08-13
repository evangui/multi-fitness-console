package com.stylefeng.guns.modular.syllabus.controller;

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

import com.stylefeng.guns.modular.system.model.SyllabusGroup;
import com.stylefeng.guns.modular.syllabus.service.ISyllabusGroupService;

/**
 * 课程种类控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:02:38
 */
@Controller
@RequestMapping("/syllabusGroup")
public class SyllabusGroupController extends BaseController {

    private String PREFIX = "/syllabus/syllabusGroup/";

    @Autowired
    private ISyllabusGroupService syllabusGroupService;

    /**
     * 跳转到课程种类首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "syllabusGroup.html";
    }

    /**
     * 跳转到添加课程种类
     */
    @RequestMapping("/syllabusGroup_add")
    public String syllabusGroupAdd() {
        return PREFIX + "syllabusGroup_add.html";
    }

    /**
     * 跳转到修改课程种类
     */
    @RequestMapping("/syllabusGroup_update/{syllabusGroupId}")
    public String syllabusGroupUpdate(@PathVariable Integer syllabusGroupId, Model model) {
        SyllabusGroup syllabusGroup = syllabusGroupService.selectById(syllabusGroupId);
        model.addAttribute("item",syllabusGroup);
        LogObjectHolder.me().set(syllabusGroup);
        return PREFIX + "syllabusGroup_edit.html";
    }

    /**
     * 获取课程种类列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SyllabusGroup> page = new PageFactory<SyllabusGroup>().defaultPage();
    	
    	page = syllabusGroupService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增课程种类
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SyllabusGroup syllabusGroup) {
        syllabusGroupService.insert(syllabusGroup);
        return SUCCESS_TIP;
    }

    /**
     * 删除课程种类
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer syllabusGroupId) {
        syllabusGroupService.deleteById(syllabusGroupId);
        return SUCCESS_TIP;
    }

    /**
     * 修改课程种类
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SyllabusGroup syllabusGroup) {
        syllabusGroupService.updateById(syllabusGroup);
        return SUCCESS_TIP;
    }

    /**
     * 课程种类详情
     */
    @RequestMapping(value = "/detail/{syllabusGroupId}")
    @ResponseBody
    public Object detail(@PathVariable("syllabusGroupId") Integer syllabusGroupId) {
        return syllabusGroupService.selectById(syllabusGroupId);
    }
}
