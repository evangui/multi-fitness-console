package com.stylefeng.guns.modular.card.controller;

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
import com.stylefeng.guns.modular.system.model.CardTimecardRecharge;
import com.stylefeng.guns.modular.card.service.ICardTimecardRechargeService;

/**
 * 时间卡充值记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:40:45
 */
@Controller
@RequestMapping("/cardTimecardRecharge")
public class CardTimecardRechargeController extends BaseController {

    private String PREFIX = "/card/cardTimecardRecharge/";

    @Autowired
    private ICardTimecardRechargeService cardTimecardRechargeService;

    /**
     * 跳转到时间卡充值记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardTimecardRecharge.html";
    }

    /**
     * 跳转到添加时间卡充值记录
     */
    @RequestMapping("/cardTimecardRecharge_add")
    public String cardTimecardRechargeAdd() {
        return PREFIX + "cardTimecardRecharge_add.html";
    }

    /**
     * 跳转到修改时间卡充值记录
     */
    @RequestMapping("/cardTimecardRecharge_update/{cardTimecardRechargeId}")
    public String cardTimecardRechargeUpdate(@PathVariable Integer cardTimecardRechargeId, Model model) {
        CardTimecardRecharge cardTimecardRecharge = cardTimecardRechargeService.selectById(cardTimecardRechargeId);
        model.addAttribute("item",cardTimecardRecharge);
        LogObjectHolder.me().set(cardTimecardRecharge);
        return PREFIX + "cardTimecardRecharge_edit.html";
    }

    /**
     * 获取时间卡充值记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardTimecardRecharge> page = new PageFactory<CardTimecardRecharge>().defaultPage();
    	
    	page = cardTimecardRechargeService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增时间卡充值记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardTimecardRecharge cardTimecardRecharge) {
        cardTimecardRechargeService.insert(cardTimecardRecharge);
        return SUCCESS_TIP;
    }

    /**
     * 删除时间卡充值记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardTimecardRechargeId) {
        cardTimecardRechargeService.deleteById(cardTimecardRechargeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改时间卡充值记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardTimecardRecharge cardTimecardRecharge) {
        cardTimecardRechargeService.updateById(cardTimecardRecharge);
        return SUCCESS_TIP;
    }

    /**
     * 时间卡充值记录详情
     */
    @RequestMapping(value = "/detail/{cardTimecardRechargeId}")
    @ResponseBody
    public Object detail(@PathVariable("cardTimecardRechargeId") Integer cardTimecardRechargeId) {
        return cardTimecardRechargeService.selectById(cardTimecardRechargeId);
    }
}
