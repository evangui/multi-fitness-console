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
import com.stylefeng.guns.modular.system.model.PtrainRecord;
import com.stylefeng.guns.modular.system.model.SyllabusClassroom;
import com.stylefeng.guns.modular.syllabus.service.IPtrainRecordService;

/**
 * 私教记录控制器
 *
 * @author fengshuonan
 * @Date 2018-08-18 11:05:32
 */
@Controller
@RequestMapping("/ptrainRecord")
public class PtrainRecordController extends BaseController {

    private String PREFIX = "/syllabus/ptrainRecord/";

    @Autowired
    private IPtrainRecordService ptrainRecordService;

    /**
     * 跳转到私教记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "ptrainRecord.html";
    }

    /**
     * 跳转到添加私教记录
     */
    @RequestMapping("/ptrainRecord_add")
    public String ptrainRecordAdd() {
        return PREFIX + "ptrainRecord_add.html";
    }

    /**
     * 跳转到修改私教记录
     */
    @RequestMapping("/ptrainRecord_update/{ptrainRecordId}")
    public String ptrainRecordUpdate(@PathVariable Integer ptrainRecordId, Model model) {
        PtrainRecord ptrainRecord = ptrainRecordService.selectById(ptrainRecordId);
        model.addAttribute("item",ptrainRecord);
        LogObjectHolder.me().set(ptrainRecord);
        return PREFIX + "ptrainRecord_edit.html";
    }

    /**
     * 获取私教记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<PtrainRecord> page = new PageFactory<PtrainRecord>().defaultPage();
    	
    	page = ptrainRecordService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增私教记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PtrainRecord ptrainRecord) {
        ptrainRecordService.insert(ptrainRecord);
        return SUCCESS_TIP;
    }

    /**
     * 删除私教记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer ptrainRecordId) {
        ptrainRecordService.deleteById(ptrainRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改私教记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PtrainRecord ptrainRecord) {
        ptrainRecordService.updateById(ptrainRecord);
        return SUCCESS_TIP;
    }

    /**
     * 私教记录详情
     */
    @RequestMapping(value = "/detail/{ptrainRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("ptrainRecordId") Integer ptrainRecordId) {
        return ptrainRecordService.selectById(ptrainRecordId);
    }
}
