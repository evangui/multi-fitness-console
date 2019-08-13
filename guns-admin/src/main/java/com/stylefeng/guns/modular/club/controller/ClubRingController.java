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

import com.stylefeng.guns.modular.system.model.ClubRing;
import com.stylefeng.guns.modular.club.service.IClubRingService;

/**
 * 手环控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:06:59
 */
@Controller
@RequestMapping("/clubRing")
public class ClubRingController extends BaseController {

    private String PREFIX = "/club/clubRing/";

    @Autowired
    private IClubRingService clubRingService;

    /**
     * 跳转到手环首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	model.addAttribute("clubId", clubId);
        return PREFIX + "clubRing.html";
    }

    /**
     * 跳转到添加手环
     */
    @RequestMapping("/clubRing_add")
    public String clubRingAdd() {
        return PREFIX + "clubRing_add.html";
    }

    /**
     * 跳转到修改手环
     */
    @RequestMapping("/clubRing_update/{clubRingId}")
    public String clubRingUpdate(@PathVariable Integer clubRingId, Model model) {
        ClubRing clubRing = clubRingService.selectById(clubRingId);
        model.addAttribute("item",clubRing);
        LogObjectHolder.me().set(clubRing);
        return PREFIX + "clubRing_edit.html";
    }

    /**
     * 获取手环列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<ClubRing> page = new PageFactory<ClubRing>().defaultPage();
    	
    	page = clubRingService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增手环
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ClubRing clubRing) {
        clubRingService.insert(clubRing);
        return SUCCESS_TIP;
    }

    /**
     * 删除手环
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clubRingId) {
        clubRingService.deleteById(clubRingId);
        return SUCCESS_TIP;
    }

    /**
     * 修改手环
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ClubRing clubRing) {
        clubRingService.updateById(clubRing);
        return SUCCESS_TIP;
    }

    /**
     * 手环详情
     */
    @RequestMapping(value = "/detail/{clubRingId}")
    @ResponseBody
    public Object detail(@PathVariable("clubRingId") Integer clubRingId) {
        return clubRingService.selectById(clubRingId);
    }
}
