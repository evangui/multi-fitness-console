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
import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.stylefeng.guns.modular.system.model.SyllabusClassroom;
import com.stylefeng.guns.modular.syllabus.service.IPtrainBespeakRecordService;

/**
 * 私教预约记录控制器
 *
 * @author fengshuonan
 * @Date 2018-08-18 11:08:00
 */
@Controller
@RequestMapping("/ptrainBespeakRecord")
public class PtrainBespeakRecordController extends BaseController {

    private String PREFIX = "/syllabus/ptrainBespeakRecord/";

    @Autowired
    private IPtrainBespeakRecordService ptrainBespeakRecordService;

    /**
     * 跳转到私教预约记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "ptrainBespeakRecord.html";
    }

    /**
     * 跳转到添加私教预约记录
     */
    @RequestMapping("/ptrainBespeakRecord_add")
    public String ptrainBespeakRecordAdd() {
        return PREFIX + "ptrainBespeakRecord_add.html";
    }

    /**
     * 跳转到修改私教预约记录
     */
    @RequestMapping("/ptrainBespeakRecord_update/{ptrainBespeakRecordId}")
    public String ptrainBespeakRecordUpdate(@PathVariable Integer ptrainBespeakRecordId, Model model) {
        PtrainBespeakRecord ptrainBespeakRecord = ptrainBespeakRecordService.selectById(ptrainBespeakRecordId);
        model.addAttribute("item",ptrainBespeakRecord);
        LogObjectHolder.me().set(ptrainBespeakRecord);
        return PREFIX + "ptrainBespeakRecord_edit.html";
    }

    /**
     * 获取私教预约记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<PtrainBespeakRecord> page = new PageFactory<PtrainBespeakRecord>().defaultPage();
    	
    	page = ptrainBespeakRecordService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增私教预约记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PtrainBespeakRecord ptrainBespeakRecord) {
        ptrainBespeakRecordService.insert(ptrainBespeakRecord);
        return SUCCESS_TIP;
    }

    /**
     * 删除私教预约记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer ptrainBespeakRecordId) {
        ptrainBespeakRecordService.deleteById(ptrainBespeakRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改私教预约记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PtrainBespeakRecord ptrainBespeakRecord) {
        ptrainBespeakRecordService.updateById(ptrainBespeakRecord);
        return SUCCESS_TIP;
    }

    /**
     * 私教预约记录详情
     */
    @RequestMapping(value = "/detail/{ptrainBespeakRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("ptrainBespeakRecordId") Integer ptrainBespeakRecordId) {
        return ptrainBespeakRecordService.selectById(ptrainBespeakRecordId);
    }
}
