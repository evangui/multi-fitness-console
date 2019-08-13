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

import com.stylefeng.guns.modular.system.model.SettingContract;
import com.stylefeng.guns.modular.setting.service.ISettingContractService;

/**
 * 合同设置控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:58:49
 */
@Controller
@RequestMapping("/settingContract")
public class SettingContractController extends BaseController {

    private String PREFIX = "/setting/settingContract/";

    @Autowired
    private ISettingContractService settingContractService;

    /**
     * 跳转到合同设置首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "settingContract.html";
    }

    /**
     * 跳转到添加合同设置
     */
    @RequestMapping("/settingContract_add")
    public String settingContractAdd() {
        return PREFIX + "settingContract_add.html";
    }

    /**
     * 跳转到修改合同设置
     */
    @RequestMapping("/settingContract_update/{settingContractId}")
    public String settingContractUpdate(@PathVariable Integer settingContractId, Model model) {
        SettingContract settingContract = settingContractService.selectById(settingContractId);
        model.addAttribute("item",settingContract);
        LogObjectHolder.me().set(settingContract);
        return PREFIX + "settingContract_edit.html";
    }

    /**
     * 获取合同设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SettingContract> page = new PageFactory<SettingContract>().defaultPage();
    	
    	page = settingContractService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增合同设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SettingContract settingContract) {
        settingContractService.insert(settingContract);
        return SUCCESS_TIP;
    }

    /**
     * 删除合同设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer settingContractId) {
        settingContractService.deleteById(settingContractId);
        return SUCCESS_TIP;
    }

    /**
     * 修改合同设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SettingContract settingContract) {
        settingContractService.updateById(settingContract);
        return SUCCESS_TIP;
    }

    /**
     * 合同设置详情
     */
    @RequestMapping(value = "/detail/{settingContractId}")
    @ResponseBody
    public Object detail(@PathVariable("settingContractId") Integer settingContractId) {
        return settingContractService.selectById(settingContractId);
    }
}
