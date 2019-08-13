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
import com.stylefeng.guns.modular.system.model.CardPtraincard;
import com.stylefeng.guns.modular.card.service.ICardPtraincardService;

/**
 * 会员私教卡控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:37:38
 */
@Controller
@RequestMapping("/cardPtraincard")
public class CardPtraincardController extends BaseController {

    private String PREFIX = "/card/cardPtraincard/";

    @Autowired
    private ICardPtraincardService cardPtraincardService;

    /**
     * 跳转到会员私教卡首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardPtraincard.html";
    }

    /**
     * 跳转到添加会员私教卡
     */
    @RequestMapping("/cardPtraincard_add")
    public String cardPtraincardAdd() {
        return PREFIX + "cardPtraincard_add.html";
    }

    /**
     * 跳转到修改会员私教卡
     */
    @RequestMapping("/cardPtraincard_update/{cardPtraincardId}")
    public String cardPtraincardUpdate(@PathVariable Integer cardPtraincardId, Model model) {
        CardPtraincard cardPtraincard = cardPtraincardService.selectById(cardPtraincardId);
        model.addAttribute("item",cardPtraincard);
        LogObjectHolder.me().set(cardPtraincard);
        return PREFIX + "cardPtraincard_edit.html";
    }

    /**
     * 获取会员私教卡列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardPtraincard> page = new PageFactory<CardPtraincard>().defaultPage();
    	
    	page = cardPtraincardService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增会员私教卡
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardPtraincard cardPtraincard) {
        cardPtraincardService.insert(cardPtraincard);
        return SUCCESS_TIP;
    }

    /**
     * 删除会员私教卡
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardPtraincardId) {
        cardPtraincardService.deleteById(cardPtraincardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会员私教卡
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardPtraincard cardPtraincard) {
        cardPtraincardService.updateById(cardPtraincard);
        return SUCCESS_TIP;
    }

    /**
     * 会员私教卡详情
     */
    @RequestMapping(value = "/detail/{cardPtraincardId}")
    @ResponseBody
    public Object detail(@PathVariable("cardPtraincardId") Integer cardPtraincardId) {
        return cardPtraincardService.selectById(cardPtraincardId);
    }
}
