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

import com.stylefeng.guns.modular.system.model.SettingPtrain;
import com.stylefeng.guns.modular.setting.service.ISettingPtrainService;

/**
 * 私教课程设置控制器
 *
 * @author guiyj
 * @Date 2018-06-19 17:19:31
 */
@Controller
@RequestMapping("/settingPtrain")
public class SettingPtrainController extends BaseController {

    private String PREFIX = "/setting/settingPtrain/";

    @Autowired
    private ISettingPtrainService settingPtrainService;

    /**
     * 跳转到私教课程设置首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "settingPtrain.html";
    }

    /**
     * 跳转到添加私教课程设置
     */
    @RequestMapping("/settingPtrain_add")
    public String settingPtrainAdd() {
        return PREFIX + "settingPtrain_add.html";
    }

    /**
     * 跳转到修改私教课程设置
     */
    @RequestMapping("/settingPtrain_update/{settingPtrainId}")
    public String settingPtrainUpdate(@PathVariable Integer settingPtrainId, Model model) {
        SettingPtrain settingPtrain = settingPtrainService.selectById(settingPtrainId);
        model.addAttribute("item",settingPtrain);
        LogObjectHolder.me().set(settingPtrain);
        return PREFIX + "settingPtrain_edit.html";
    }

    /**
     * 获取私教课程设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SettingPtrain> page = new PageFactory<SettingPtrain>().defaultPage();
    	
    	page = settingPtrainService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增私教课程设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SettingPtrain settingPtrain) {
        settingPtrainService.insert(settingPtrain);
        return SUCCESS_TIP;
    }

    /**
     * 删除私教课程设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer settingPtrainId) {
        settingPtrainService.deleteById(settingPtrainId);
        return SUCCESS_TIP;
    }

    /**
     * 修改私教课程设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SettingPtrain settingPtrain) {
        settingPtrainService.updateById(settingPtrain);
        return SUCCESS_TIP;
    }

    /**
     * 私教课程设置详情
     */
    @RequestMapping(value = "/detail/{settingPtrainId}")
    @ResponseBody
    public Object detail(@PathVariable("settingPtrainId") Integer settingPtrainId) {
        return settingPtrainService.selectById(settingPtrainId);
    }
}
