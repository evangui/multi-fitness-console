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
import com.stylefeng.guns.modular.system.model.CardPtraincardRecharge;
import com.stylefeng.guns.modular.card.service.ICardPtraincardRechargeService;

/**
 * 私教卡充值记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:37:53
 */
@Controller
@RequestMapping("/cardPtraincardRecharge")
public class CardPtraincardRechargeController extends BaseController {

    private String PREFIX = "/card/cardPtraincardRecharge/";

    @Autowired
    private ICardPtraincardRechargeService cardPtraincardRechargeService;

    /**
     * 跳转到私教卡充值记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardPtraincardRecharge.html";
    }

    /**
     * 跳转到添加私教卡充值记录
     */
    @RequestMapping("/cardPtraincardRecharge_add")
    public String cardPtraincardRechargeAdd() {
        return PREFIX + "cardPtraincardRecharge_add.html";
    }

    /**
     * 跳转到修改私教卡充值记录
     */
    @RequestMapping("/cardPtraincardRecharge_update/{cardPtraincardRechargeId}")
    public String cardPtraincardRechargeUpdate(@PathVariable Integer cardPtraincardRechargeId, Model model) {
        CardPtraincardRecharge cardPtraincardRecharge = cardPtraincardRechargeService.selectById(cardPtraincardRechargeId);
        model.addAttribute("item",cardPtraincardRecharge);
        LogObjectHolder.me().set(cardPtraincardRecharge);
        return PREFIX + "cardPtraincardRecharge_edit.html";
    }

    /**
     * 获取私教卡充值记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardPtraincardRecharge> page = new PageFactory<CardPtraincardRecharge>().defaultPage();
    	
    	page = cardPtraincardRechargeService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增私教卡充值记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardPtraincardRecharge cardPtraincardRecharge) {
        cardPtraincardRechargeService.insert(cardPtraincardRecharge);
        return SUCCESS_TIP;
    }

    /**
     * 删除私教卡充值记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardPtraincardRechargeId) {
        cardPtraincardRechargeService.deleteById(cardPtraincardRechargeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改私教卡充值记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardPtraincardRecharge cardPtraincardRecharge) {
        cardPtraincardRechargeService.updateById(cardPtraincardRecharge);
        return SUCCESS_TIP;
    }

    /**
     * 私教卡充值记录详情
     */
    @RequestMapping(value = "/detail/{cardPtraincardRechargeId}")
    @ResponseBody
    public Object detail(@PathVariable("cardPtraincardRechargeId") Integer cardPtraincardRechargeId) {
        return cardPtraincardRechargeService.selectById(cardPtraincardRechargeId);
    }
}
