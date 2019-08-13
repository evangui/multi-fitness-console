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
import com.stylefeng.guns.modular.system.model.ClubAdvPosition;
import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.club.service.IClubAdvPositionService;

/**
 * 俱乐部广告位控制器
 *
 * @author fengshuonan
 * @Date 2018-10-10 12:29:49
 */
@Controller
@RequestMapping("/clubAdvPosition")
public class ClubAdvPositionController extends BaseController {

    private String PREFIX = "/club/clubAdvPosition/";

    @Autowired
    private IClubAdvPositionService clubAdvPositionService;

    /**
     * 跳转到俱乐部广告位首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	model.addAttribute("clubId", clubId);
        return PREFIX + "clubAdvPosition.html";
    }

    /**
     * 跳转到添加俱乐部广告位
     */
    @RequestMapping("/clubAdvPosition_add")
    public String clubAdvPositionAdd() {
        return PREFIX + "clubAdvPosition_add.html";
    }

    /**
     * 跳转到修改俱乐部广告位
     */
    @RequestMapping("/clubAdvPosition_update/{clubAdvPositionId}")
    public String clubAdvPositionUpdate(@PathVariable Integer clubAdvPositionId, Model model) {
        ClubAdvPosition clubAdvPosition = clubAdvPositionService.selectById(clubAdvPositionId);
        model.addAttribute("item",clubAdvPosition);
        LogObjectHolder.me().set(clubAdvPosition);
        return PREFIX + "clubAdvPosition_edit.html";
    }

    /**
     * 获取俱乐部广告位列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<ClubAdvPosition> page = new PageFactory<ClubAdvPosition>().defaultPage();
    	
    	page = clubAdvPositionService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增俱乐部广告位
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ClubAdvPosition clubAdvPosition) {
        clubAdvPositionService.insert(clubAdvPosition);
        return SUCCESS_TIP;
    }

    /**
     * 删除俱乐部广告位
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clubAdvPositionId) {
        clubAdvPositionService.deleteById(clubAdvPositionId);
        return SUCCESS_TIP;
    }

    /**
     * 修改俱乐部广告位
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ClubAdvPosition clubAdvPosition) {
        clubAdvPositionService.updateById(clubAdvPosition);
        return SUCCESS_TIP;
    }

    /**
     * 俱乐部广告位详情
     */
    @RequestMapping(value = "/detail/{clubAdvPositionId}")
    @ResponseBody
    public Object detail(@PathVariable("clubAdvPositionId") Integer clubAdvPositionId) {
        return clubAdvPositionService.selectById(clubAdvPositionId);
    }
}
