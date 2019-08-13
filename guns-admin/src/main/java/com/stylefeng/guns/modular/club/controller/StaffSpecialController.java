package com.stylefeng.guns.modular.club.controller;

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

import com.stylefeng.guns.modular.system.model.StaffSpecial;
import com.stylefeng.guns.modular.club.service.IStaffSpecialService;

/**
 * 会籍-主管-前台控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 17:07:06
 */
@Controller
@RequestMapping("/staffSpecial")
public class StaffSpecialController extends BaseController {

    private String PREFIX = "/club/staffSpecial/";

    @Autowired
    private IStaffSpecialService staffSpecialService;

    /**
     * 跳转到会籍-主管-前台首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "staffSpecial.html";
    }

    /**
     * 跳转到添加会籍-主管-前台
     */
    @RequestMapping("/staffSpecial_add")
    public String staffSpecialAdd() {
        return PREFIX + "staffSpecial_add.html";
    }

    /**
     * 跳转到修改会籍-主管-前台
     */
    @RequestMapping("/staffSpecial_update/{staffSpecialId}")
    public String staffSpecialUpdate(@PathVariable Integer staffSpecialId, Model model) {
        StaffSpecial staffSpecial = staffSpecialService.selectById(staffSpecialId);
        model.addAttribute("item",staffSpecial);
        LogObjectHolder.me().set(staffSpecial);
        return PREFIX + "staffSpecial_edit.html";
    }

    /**
     * 获取会籍-主管-前台列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<StaffSpecial> page = new PageFactory<StaffSpecial>().defaultPage();
    	
    	page = staffSpecialService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增会籍-主管-前台
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(StaffSpecial staffSpecial) {
        staffSpecialService.insert(staffSpecial);
        return SUCCESS_TIP;
    }

    /**
     * 删除会籍-主管-前台
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer staffSpecialId) {
        staffSpecialService.deleteById(staffSpecialId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会籍-主管-前台
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(StaffSpecial staffSpecial) {
        staffSpecialService.updateById(staffSpecial);
        return SUCCESS_TIP;
    }

    /**
     * 会籍-主管-前台详情
     */
    @RequestMapping(value = "/detail/{staffSpecialId}")
    @ResponseBody
    public Object detail(@PathVariable("staffSpecialId") Integer staffSpecialId) {
        return staffSpecialService.selectById(staffSpecialId);
    }
}
