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

import com.stylefeng.guns.modular.system.model.VipUserField;
import com.stylefeng.guns.modular.member.service.IVipUserFieldService;

/**
 * VIP用户自定义字段控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:45:22
 */
@Controller
@RequestMapping("/vipUserField")
public class VipUserFieldController extends BaseController {

    private String PREFIX = "/member/vipUserField/";

    @Autowired
    private IVipUserFieldService vipUserFieldService;

    /**
     * 跳转到VIP用户自定义字段首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "vipUserField.html";
    }

    /**
     * 跳转到添加VIP用户自定义字段
     */
    @RequestMapping("/vipUserField_add")
    public String vipUserFieldAdd() {
        return PREFIX + "vipUserField_add.html";
    }

    /**
     * 跳转到修改VIP用户自定义字段
     */
    @RequestMapping("/vipUserField_update/{vipUserFieldId}")
    public String vipUserFieldUpdate(@PathVariable Integer vipUserFieldId, Model model) {
        VipUserField vipUserField = vipUserFieldService.selectById(vipUserFieldId);
        model.addAttribute("item",vipUserField);
        LogObjectHolder.me().set(vipUserField);
        return PREFIX + "vipUserField_edit.html";
    }

    /**
     * 获取VIP用户自定义字段列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<VipUserField> page = new PageFactory<VipUserField>().defaultPage();
    	
    	page = vipUserFieldService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增VIP用户自定义字段
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(VipUserField vipUserField) {
        vipUserFieldService.insert(vipUserField);
        return SUCCESS_TIP;
    }

    /**
     * 删除VIP用户自定义字段
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer vipUserFieldId) {
        vipUserFieldService.deleteById(vipUserFieldId);
        return SUCCESS_TIP;
    }

    /**
     * 修改VIP用户自定义字段
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(VipUserField vipUserField) {
        vipUserFieldService.updateById(vipUserField);
        return SUCCESS_TIP;
    }

    /**
     * VIP用户自定义字段详情
     */
    @RequestMapping(value = "/detail/{vipUserFieldId}")
    @ResponseBody
    public Object detail(@PathVariable("vipUserFieldId") Integer vipUserFieldId) {
        return vipUserFieldService.selectById(vipUserFieldId);
    }
}
