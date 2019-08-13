package com.stylefeng.guns.modular.mch.setting.controller;

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

import com.stylefeng.guns.modular.system.model.SettingPointsItem;
import com.stylefeng.guns.modular.mch.setting.service.ISettingPointsItemService;

/**
 * 积分项目设置控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:58:37
 */
@Controller
@RequestMapping("/settingPointsItem")
public class SettingPointsItemController extends BaseController {

    private String PREFIX = "/setting/settingPointsItem/";

    @Autowired
    private ISettingPointsItemService settingPointsItemService;

    /**
     * 跳转到积分项目设置首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "settingPointsItem.html";
    }

    /**
     * 跳转到添加积分项目设置
     */
    @RequestMapping("/settingPointsItem_add")
    public String settingPointsItemAdd() {
        return PREFIX + "settingPointsItem_add.html";
    }

    /**
     * 跳转到修改积分项目设置
     */
    @RequestMapping("/settingPointsItem_update/{settingPointsItemId}")
    public String settingPointsItemUpdate(@PathVariable Integer settingPointsItemId, Model model) {
        SettingPointsItem settingPointsItem = settingPointsItemService.selectById(settingPointsItemId);
        model.addAttribute("item",settingPointsItem);
        LogObjectHolder.me().set(settingPointsItem);
        return PREFIX + "settingPointsItem_edit.html";
    }

    /**
     * 获取积分项目设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SettingPointsItem> page = new PageFactory<SettingPointsItem>().defaultPage();
    	
    	page = settingPointsItemService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增积分项目设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SettingPointsItem settingPointsItem) {
        settingPointsItemService.insert(settingPointsItem);
        return SUCCESS_TIP;
    }

    /**
     * 删除积分项目设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer settingPointsItemId) {
        settingPointsItemService.deleteById(settingPointsItemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改积分项目设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SettingPointsItem settingPointsItem) {
        settingPointsItemService.updateById(settingPointsItem);
        return SUCCESS_TIP;
    }

    /**
     * 积分项目设置详情
     */
    @RequestMapping(value = "/detail/{settingPointsItemId}")
    @ResponseBody
    public Object detail(@PathVariable("settingPointsItemId") Integer settingPointsItemId) {
        return settingPointsItemService.selectById(settingPointsItemId);
    }
}
