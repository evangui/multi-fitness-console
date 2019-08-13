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
import com.stylefeng.guns.modular.system.model.CardPtraincardSpend;
import com.stylefeng.guns.modular.card.service.ICardPtraincardSpendService;

/**
 * 私教卡消课记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:38:15
 */
@Controller
@RequestMapping("/cardPtraincardSpend")
public class CardPtraincardSpendController extends BaseController {

    private String PREFIX = "/card/cardPtraincardSpend/";

    @Autowired
    private ICardPtraincardSpendService cardPtraincardSpendService;

    /**
     * 跳转到私教卡消课记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardPtraincardSpend.html";
    }

    /**
     * 跳转到添加私教卡消课记录
     */
    @RequestMapping("/cardPtraincardSpend_add")
    public String cardPtraincardSpendAdd() {
        return PREFIX + "cardPtraincardSpend_add.html";
    }

    /**
     * 跳转到修改私教卡消课记录
     */
    @RequestMapping("/cardPtraincardSpend_update/{cardPtraincardSpendId}")
    public String cardPtraincardSpendUpdate(@PathVariable Integer cardPtraincardSpendId, Model model) {
        CardPtraincardSpend cardPtraincardSpend = cardPtraincardSpendService.selectById(cardPtraincardSpendId);
        model.addAttribute("item",cardPtraincardSpend);
        LogObjectHolder.me().set(cardPtraincardSpend);
        return PREFIX + "cardPtraincardSpend_edit.html";
    }

    /**
     * 获取私教卡消课记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardPtraincardSpend> page = new PageFactory<CardPtraincardSpend>().defaultPage();
    	
    	page = cardPtraincardSpendService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增私教卡消课记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardPtraincardSpend cardPtraincardSpend) {
        cardPtraincardSpendService.insert(cardPtraincardSpend);
        return SUCCESS_TIP;
    }

    /**
     * 删除私教卡消课记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardPtraincardSpendId) {
        cardPtraincardSpendService.deleteById(cardPtraincardSpendId);
        return SUCCESS_TIP;
    }

    /**
     * 修改私教卡消课记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardPtraincardSpend cardPtraincardSpend) {
        cardPtraincardSpendService.updateById(cardPtraincardSpend);
        return SUCCESS_TIP;
    }

    /**
     * 私教卡消课记录详情
     */
    @RequestMapping(value = "/detail/{cardPtraincardSpendId}")
    @ResponseBody
    public Object detail(@PathVariable("cardPtraincardSpendId") Integer cardPtraincardSpendId) {
        return cardPtraincardSpendService.selectById(cardPtraincardSpendId);
    }
}
