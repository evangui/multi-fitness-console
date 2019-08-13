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
import com.stylefeng.guns.modular.system.model.CardStoredvaluecardRecharge;
import com.stylefeng.guns.modular.card.service.ICardStoredvaluecardRechargeService;

/**
 * 储值卡充值记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:39:06
 */
@Controller
@RequestMapping("/cardStoredvaluecardRecharge")
public class CardStoredvaluecardRechargeController extends BaseController {

    private String PREFIX = "/card/cardStoredvaluecardRecharge/";

    @Autowired
    private ICardStoredvaluecardRechargeService cardStoredvaluecardRechargeService;

    /**
     * 跳转到储值卡充值记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardStoredvaluecardRecharge.html";
    }

    /**
     * 跳转到添加储值卡充值记录
     */
    @RequestMapping("/cardStoredvaluecardRecharge_add")
    public String cardStoredvaluecardRechargeAdd() {
        return PREFIX + "cardStoredvaluecardRecharge_add.html";
    }

    /**
     * 跳转到修改储值卡充值记录
     */
    @RequestMapping("/cardStoredvaluecardRecharge_update/{cardStoredvaluecardRechargeId}")
    public String cardStoredvaluecardRechargeUpdate(@PathVariable Integer cardStoredvaluecardRechargeId, Model model) {
        CardStoredvaluecardRecharge cardStoredvaluecardRecharge = cardStoredvaluecardRechargeService.selectById(cardStoredvaluecardRechargeId);
        model.addAttribute("item",cardStoredvaluecardRecharge);
        LogObjectHolder.me().set(cardStoredvaluecardRecharge);
        return PREFIX + "cardStoredvaluecardRecharge_edit.html";
    }

    /**
     * 获取储值卡充值记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardStoredvaluecardRecharge> page = new PageFactory<CardStoredvaluecardRecharge>().defaultPage();
    	
    	page = cardStoredvaluecardRechargeService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增储值卡充值记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardStoredvaluecardRecharge cardStoredvaluecardRecharge) {
        cardStoredvaluecardRechargeService.insert(cardStoredvaluecardRecharge);
        return SUCCESS_TIP;
    }

    /**
     * 删除储值卡充值记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardStoredvaluecardRechargeId) {
        cardStoredvaluecardRechargeService.deleteById(cardStoredvaluecardRechargeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改储值卡充值记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardStoredvaluecardRecharge cardStoredvaluecardRecharge) {
        cardStoredvaluecardRechargeService.updateById(cardStoredvaluecardRecharge);
        return SUCCESS_TIP;
    }

    /**
     * 储值卡充值记录详情
     */
    @RequestMapping(value = "/detail/{cardStoredvaluecardRechargeId}")
    @ResponseBody
    public Object detail(@PathVariable("cardStoredvaluecardRechargeId") Integer cardStoredvaluecardRechargeId) {
        return cardStoredvaluecardRechargeService.selectById(cardStoredvaluecardRechargeId);
    }
}
