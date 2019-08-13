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

import com.stylefeng.guns.modular.system.model.SyllabusScheduleSetting;
import com.stylefeng.guns.modular.syllabus.service.ISyllabusScheduleSettingService;

/**
 * 团操课排期设置控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:03:28
 */
@Controller
@RequestMapping("/syllabusScheduleSetting")
public class SyllabusScheduleSettingController extends BaseController {

    private String PREFIX = "/syllabus/syllabusScheduleSetting/";

    @Autowired
    private ISyllabusScheduleSettingService syllabusScheduleSettingService;

    /**
     * 跳转到团操课排期设置首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "syllabusScheduleSetting.html";
    }

    /**
     * 跳转到添加团操课排期设置
     */
    @RequestMapping("/syllabusScheduleSetting_add")
    public String syllabusScheduleSettingAdd() {
        return PREFIX + "syllabusScheduleSetting_add.html";
    }

    /**
     * 跳转到修改团操课排期设置
     */
    @RequestMapping("/syllabusScheduleSetting_update/{syllabusScheduleSettingId}")
    public String syllabusScheduleSettingUpdate(@PathVariable Integer syllabusScheduleSettingId, Model model) {
        SyllabusScheduleSetting syllabusScheduleSetting = syllabusScheduleSettingService.selectById(syllabusScheduleSettingId);
        model.addAttribute("item",syllabusScheduleSetting);
        LogObjectHolder.me().set(syllabusScheduleSetting);
        return PREFIX + "syllabusScheduleSetting_edit.html";
    }

    /**
     * 获取团操课排期设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SyllabusScheduleSetting> page = new PageFactory<SyllabusScheduleSetting>().defaultPage();
    	
    	page = syllabusScheduleSettingService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增团操课排期设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SyllabusScheduleSetting syllabusScheduleSetting) {
        syllabusScheduleSettingService.insert(syllabusScheduleSetting);
        return SUCCESS_TIP;
    }

    /**
     * 删除团操课排期设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer syllabusScheduleSettingId) {
        syllabusScheduleSettingService.deleteById(syllabusScheduleSettingId);
        return SUCCESS_TIP;
    }

    /**
     * 修改团操课排期设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SyllabusScheduleSetting syllabusScheduleSetting) {
        syllabusScheduleSettingService.updateById(syllabusScheduleSetting);
        return SUCCESS_TIP;
    }

    /**
     * 团操课排期设置详情
     */
    @RequestMapping(value = "/detail/{syllabusScheduleSettingId}")
    @ResponseBody
    public Object detail(@PathVariable("syllabusScheduleSettingId") Integer syllabusScheduleSettingId) {
        return syllabusScheduleSettingService.selectById(syllabusScheduleSettingId);
    }
}
