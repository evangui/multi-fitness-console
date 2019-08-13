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
import com.stylefeng.guns.modular.system.model.CheckinRecord;
import com.stylefeng.guns.modular.member.service.ICheckinRecordService;

/**
 * 签到记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:42:29
 */
@Controller
@RequestMapping("/checkinRecord")
public class CheckinRecordController extends BaseController {

    private String PREFIX = "/member/checkinRecord/";

    @Autowired
    private ICheckinRecordService checkinRecordService;

    /**
     * 跳转到签到记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "checkinRecord.html";
    }

    /**
     * 跳转到添加签到记录
     */
    @RequestMapping("/checkinRecord_add")
    public String checkinRecordAdd() {
        return PREFIX + "checkinRecord_add.html";
    }

    /**
     * 跳转到修改签到记录
     */
    @RequestMapping("/checkinRecord_update/{checkinRecordId}")
    public String checkinRecordUpdate(@PathVariable Integer checkinRecordId, Model model) {
        CheckinRecord checkinRecord = checkinRecordService.selectById(checkinRecordId);
        model.addAttribute("item",checkinRecord);
        LogObjectHolder.me().set(checkinRecord);
        return PREFIX + "checkinRecord_edit.html";
    }

    /**
     * 获取签到记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CheckinRecord> page = new PageFactory<CheckinRecord>().defaultPage();
    	
    	page = checkinRecordService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增签到记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CheckinRecord checkinRecord) {
        checkinRecordService.insert(checkinRecord);
        return SUCCESS_TIP;
    }

    /**
     * 删除签到记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer checkinRecordId) {
        checkinRecordService.deleteById(checkinRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改签到记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CheckinRecord checkinRecord) {
        checkinRecordService.updateById(checkinRecord);
        return SUCCESS_TIP;
    }

    /**
     * 签到记录详情
     */
    @RequestMapping(value = "/detail/{checkinRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("checkinRecordId") Integer checkinRecordId) {
        return checkinRecordService.selectById(checkinRecordId);
    }
}
