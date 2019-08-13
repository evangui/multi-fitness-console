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

import com.stylefeng.guns.modular.system.model.SyllabusSubscribeRecord;
import com.stylefeng.guns.modular.syllabus.service.ISyllabusSubscribeRecordService;

/**
 * 团操课预约记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:03:48
 */
@Controller
@RequestMapping("/syllabusSubscribeRecord")
public class SyllabusSubscribeRecordController extends BaseController {

    private String PREFIX = "/syllabus/syllabusSubscribeRecord/";

    @Autowired
    private ISyllabusSubscribeRecordService syllabusSubscribeRecordService;

    /**
     * 跳转到团操课预约记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "syllabusSubscribeRecord.html";
    }

    /**
     * 跳转到添加团操课预约记录
     */
    @RequestMapping("/syllabusSubscribeRecord_add")
    public String syllabusSubscribeRecordAdd() {
        return PREFIX + "syllabusSubscribeRecord_add.html";
    }

    /**
     * 跳转到修改团操课预约记录
     */
    @RequestMapping("/syllabusSubscribeRecord_update/{syllabusSubscribeRecordId}")
    public String syllabusSubscribeRecordUpdate(@PathVariable Integer syllabusSubscribeRecordId, Model model) {
        SyllabusSubscribeRecord syllabusSubscribeRecord = syllabusSubscribeRecordService.selectById(syllabusSubscribeRecordId);
        model.addAttribute("item",syllabusSubscribeRecord);
        LogObjectHolder.me().set(syllabusSubscribeRecord);
        return PREFIX + "syllabusSubscribeRecord_edit.html";
    }

    /**
     * 获取团操课预约记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SyllabusSubscribeRecord> page = new PageFactory<SyllabusSubscribeRecord>().defaultPage();
    	
    	page = syllabusSubscribeRecordService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增团操课预约记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SyllabusSubscribeRecord syllabusSubscribeRecord) {
        syllabusSubscribeRecordService.insert(syllabusSubscribeRecord);
        return SUCCESS_TIP;
    }

    /**
     * 删除团操课预约记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer syllabusSubscribeRecordId) {
        syllabusSubscribeRecordService.deleteById(syllabusSubscribeRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改团操课预约记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SyllabusSubscribeRecord syllabusSubscribeRecord) {
        syllabusSubscribeRecordService.updateById(syllabusSubscribeRecord);
        return SUCCESS_TIP;
    }

    /**
     * 团操课预约记录详情
     */
    @RequestMapping(value = "/detail/{syllabusSubscribeRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("syllabusSubscribeRecordId") Integer syllabusSubscribeRecordId) {
        return syllabusSubscribeRecordService.selectById(syllabusSubscribeRecordId);
    }
}
