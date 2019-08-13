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
import com.stylefeng.guns.modular.system.model.ClubAdv;
import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.club.service.IClubAdvService;

/**
 * 俱乐部广告控制器
 *
 * @author fengshuonan
 * @Date 2018-10-10 12:30:30
 */
@Controller
@RequestMapping("/clubAdv")
public class ClubAdvController extends BaseController {

    private String PREFIX = "/club/clubAdv/";

    @Autowired
    private IClubAdvService clubAdvService;

    /**
     * 跳转到俱乐部广告首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	model.addAttribute("clubId", clubId);
        return PREFIX + "clubAdv.html";
    }

    /**
     * 跳转到添加俱乐部广告
     */
    @RequestMapping("/clubAdv_add")
    public String clubAdvAdd() {
        return PREFIX + "clubAdv_add.html";
    }

    /**
     * 跳转到修改俱乐部广告
     */
    @RequestMapping("/clubAdv_update/{clubAdvId}")
    public String clubAdvUpdate(@PathVariable Integer clubAdvId, Model model) {
        ClubAdv clubAdv = clubAdvService.selectById(clubAdvId);
        model.addAttribute("item",clubAdv);
        LogObjectHolder.me().set(clubAdv);
        return PREFIX + "clubAdv_edit.html";
    }

    /**
     * 获取俱乐部广告列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<ClubAdv> page = new PageFactory<ClubAdv>().defaultPage();
    	
    	page = clubAdvService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增俱乐部广告
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ClubAdv clubAdv) {
        clubAdvService.insert(clubAdv);
        return SUCCESS_TIP;
    }

    /**
     * 删除俱乐部广告
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clubAdvId) {
        clubAdvService.deleteById(clubAdvId);
        return SUCCESS_TIP;
    }

    /**
     * 修改俱乐部广告
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ClubAdv clubAdv) {
        clubAdvService.updateById(clubAdv);
        return SUCCESS_TIP;
    }

    /**
     * 俱乐部广告详情
     */
    @RequestMapping(value = "/detail/{clubAdvId}")
    @ResponseBody
    public Object detail(@PathVariable("clubAdvId") Integer clubAdvId) {
        return clubAdvService.selectById(clubAdvId);
    }
}
