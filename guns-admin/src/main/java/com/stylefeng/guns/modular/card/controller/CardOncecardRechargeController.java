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
import com.stylefeng.guns.modular.system.model.CardOncecardRecharge;
import com.stylefeng.guns.modular.card.service.ICardOncecardRechargeService;

/**
 * 次卡充值记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:36:44
 */
@Controller
@RequestMapping("/cardOncecardRecharge")
public class CardOncecardRechargeController extends BaseController {

    private String PREFIX = "/card/cardOncecardRecharge/";

    @Autowired
    private ICardOncecardRechargeService cardOncecardRechargeService;

    /**
     * 跳转到次卡充值记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardOncecardRecharge.html";
    }

    /**
     * 跳转到添加次卡充值记录
     */
    @RequestMapping("/cardOncecardRecharge_add")
    public String cardOncecardRechargeAdd() {
        return PREFIX + "cardOncecardRecharge_add.html";
    }

    /**
     * 跳转到修改次卡充值记录
     */
    @RequestMapping("/cardOncecardRecharge_update/{cardOncecardRechargeId}")
    public String cardOncecardRechargeUpdate(@PathVariable Integer cardOncecardRechargeId, Model model) {
        CardOncecardRecharge cardOncecardRecharge = cardOncecardRechargeService.selectById(cardOncecardRechargeId);
        model.addAttribute("item",cardOncecardRecharge);
        LogObjectHolder.me().set(cardOncecardRecharge);
        return PREFIX + "cardOncecardRecharge_edit.html";
    }

    /**
     * 获取次卡充值记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardOncecardRecharge> page = new PageFactory<CardOncecardRecharge>().defaultPage();
    	
    	page = cardOncecardRechargeService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增次卡充值记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardOncecardRecharge cardOncecardRecharge) {
        cardOncecardRechargeService.insert(cardOncecardRecharge);
        return SUCCESS_TIP;
    }

    /**
     * 删除次卡充值记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardOncecardRechargeId) {
        cardOncecardRechargeService.deleteById(cardOncecardRechargeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改次卡充值记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardOncecardRecharge cardOncecardRecharge) {
        cardOncecardRechargeService.updateById(cardOncecardRecharge);
        return SUCCESS_TIP;
    }

    /**
     * 次卡充值记录详情
     */
    @RequestMapping(value = "/detail/{cardOncecardRechargeId}")
    @ResponseBody
    public Object detail(@PathVariable("cardOncecardRechargeId") Integer cardOncecardRechargeId) {
        return cardOncecardRechargeService.selectById(cardOncecardRechargeId);
    }
}
