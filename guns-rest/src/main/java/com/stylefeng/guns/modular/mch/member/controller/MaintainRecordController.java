package com.stylefeng.guns.modular.mch.member.controller;

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

import com.stylefeng.guns.modular.system.model.MaintainRecord;
import com.stylefeng.guns.modular.mch.member.service.IMaintainRecordService;

/**
 * 维护记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:43:12
 */
@Controller
@RequestMapping("/maintainRecord")
public class MaintainRecordController extends BaseController {

    private String PREFIX = "/member/maintainRecord/";

    @Autowired
    private IMaintainRecordService maintainRecordService;

    /**
     * 跳转到维护记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "maintainRecord.html";
    }

    /**
     * 跳转到添加维护记录
     */
    @RequestMapping("/maintainRecord_add")
    public String maintainRecordAdd() {
        return PREFIX + "maintainRecord_add.html";
    }

    /**
     * 跳转到修改维护记录
     */
    @RequestMapping("/maintainRecord_update/{maintainRecordId}")
    public String maintainRecordUpdate(@PathVariable Integer maintainRecordId, Model model) {
        MaintainRecord maintainRecord = maintainRecordService.selectById(maintainRecordId);
        model.addAttribute("item",maintainRecord);
        LogObjectHolder.me().set(maintainRecord);
        return PREFIX + "maintainRecord_edit.html";
    }

    /**
     * 获取维护记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<MaintainRecord> page = new PageFactory<MaintainRecord>().defaultPage();
    	
    	page = maintainRecordService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增维护记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MaintainRecord maintainRecord) {
        maintainRecordService.insert(maintainRecord);
        return SUCCESS_TIP;
    }

    /**
     * 删除维护记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer maintainRecordId) {
        maintainRecordService.deleteById(maintainRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改维护记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MaintainRecord maintainRecord) {
        maintainRecordService.updateById(maintainRecord);
        return SUCCESS_TIP;
    }

    /**
     * 维护记录详情
     */
    @RequestMapping(value = "/detail/{maintainRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("maintainRecordId") Integer maintainRecordId) {
        return maintainRecordService.selectById(maintainRecordId);
    }
}
