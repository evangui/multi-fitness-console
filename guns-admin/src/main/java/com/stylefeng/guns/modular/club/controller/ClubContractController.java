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

import com.stylefeng.guns.modular.system.model.ClubContract;
import com.stylefeng.guns.modular.club.service.IClubContractService;

/**
 * 俱乐部合同控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:05:30
 */
@Controller
@RequestMapping("/clubContract")
public class ClubContractController extends BaseController {

    private String PREFIX = "/club/clubContract/";

    @Autowired
    private IClubContractService clubContractService;

    /**
     * 跳转到俱乐部合同首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	model.addAttribute("clubId", clubId);
        return PREFIX + "clubContract.html";
    }

    /**
     * 跳转到添加俱乐部合同
     */
    @RequestMapping("/clubContract_add")
    public String clubContractAdd() {
        return PREFIX + "clubContract_add.html";
    }

    /**
     * 跳转到修改俱乐部合同
     */
    @RequestMapping("/clubContract_update/{clubContractId}")
    public String clubContractUpdate(@PathVariable Integer clubContractId, Model model) {
        ClubContract clubContract = clubContractService.selectById(clubContractId);
        model.addAttribute("item",clubContract);
        LogObjectHolder.me().set(clubContract);
        return PREFIX + "clubContract_edit.html";
    }

    /**
     * 获取俱乐部合同列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<ClubContract> page = new PageFactory<ClubContract>().defaultPage();
    	
    	page = clubContractService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增俱乐部合同
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ClubContract clubContract) {
        clubContractService.insert(clubContract);
        return SUCCESS_TIP;
    }

    /**
     * 删除俱乐部合同
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clubContractId) {
        clubContractService.deleteById(clubContractId);
        return SUCCESS_TIP;
    }

    /**
     * 修改俱乐部合同
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ClubContract clubContract) {
        clubContractService.updateById(clubContract);
        return SUCCESS_TIP;
    }

    /**
     * 俱乐部合同详情
     */
    @RequestMapping(value = "/detail/{clubContractId}")
    @ResponseBody
    public Object detail(@PathVariable("clubContractId") Integer clubContractId) {
        return clubContractService.selectById(clubContractId);
    }
}
