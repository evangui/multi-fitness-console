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

import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.club.service.IClubCoachService;

/**
 * 教练控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:06:04
 */
@Controller
@RequestMapping("/clubCoach")
public class ClubCoachController extends BaseController {

    private String PREFIX = "/club/clubCoach/";

    @Autowired
    private IClubCoachService clubCoachService;

    /**
     * 跳转到教练首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	model.addAttribute("clubId", clubId);
        return PREFIX + "clubCoach.html";
    }

    /**
     * 跳转到添加教练
     */
    @RequestMapping("/clubCoach_add")
    public String clubCoachAdd() {
        return PREFIX + "clubCoach_add.html";
    }

    /**
     * 跳转到修改教练
     */
    @RequestMapping("/clubCoach_update/{clubCoachId}")
    public String clubCoachUpdate(@PathVariable Integer clubCoachId, Model model) {
        ClubCoach clubCoach = clubCoachService.selectById(clubCoachId);
        model.addAttribute("item",clubCoach);
        LogObjectHolder.me().set(clubCoach);
        return PREFIX + "clubCoach_edit.html";
    }

    /**
     * 获取教练列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<ClubCoach> page = new PageFactory<ClubCoach>().defaultPage();
    	
    	page = clubCoachService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增教练
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ClubCoach clubCoach) {
        clubCoachService.insert(clubCoach);
        return SUCCESS_TIP;
    }

    /**
     * 删除教练
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clubCoachId) {
        clubCoachService.deleteById(clubCoachId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教练
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ClubCoach clubCoach) {
        clubCoachService.updateById(clubCoach);
        return SUCCESS_TIP;
    }

    /**
     * 教练详情
     */
    @RequestMapping(value = "/detail/{clubCoachId}")
    @ResponseBody
    public Object detail(@PathVariable("clubCoachId") Integer clubCoachId) {
        return clubCoachService.selectById(clubCoachId);
    }
}
