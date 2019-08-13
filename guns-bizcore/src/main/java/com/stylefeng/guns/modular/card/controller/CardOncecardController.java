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
import com.stylefeng.guns.modular.system.model.CardOncecard;
import com.stylefeng.guns.modular.card.service.ICardOncecardService;

/**
 * 会员次卡控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:36:12
 */
@Controller
@RequestMapping("/cardOncecard")
public class CardOncecardController extends BaseController {

    private String PREFIX = "/card/cardOncecard/";

    @Autowired
    private ICardOncecardService cardOncecardService;

    /**
     * 跳转到会员次卡首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardOncecard.html";
    }

    /**
     * 跳转到添加会员次卡
     */
    @RequestMapping("/cardOncecard_add")
    public String cardOncecardAdd() {
        return PREFIX + "cardOncecard_add.html";
    }

    /**
     * 跳转到修改会员次卡
     */
    @RequestMapping("/cardOncecard_update/{cardOncecardId}")
    public String cardOncecardUpdate(@PathVariable Integer cardOncecardId, Model model) {
        CardOncecard cardOncecard = cardOncecardService.selectById(cardOncecardId);
        model.addAttribute("item",cardOncecard);
        LogObjectHolder.me().set(cardOncecard);
        return PREFIX + "cardOncecard_edit.html";
    }

    /**
     * 获取会员次卡列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardOncecard> page = new PageFactory<CardOncecard>().defaultPage();
    	
    	page = cardOncecardService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增会员次卡
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardOncecard cardOncecard) {
        cardOncecardService.insert(cardOncecard);
        return SUCCESS_TIP;
    }

    /**
     * 删除会员次卡
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardOncecardId) {
        cardOncecardService.deleteById(cardOncecardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会员次卡
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardOncecard cardOncecard) {
        cardOncecardService.updateById(cardOncecard);
        return SUCCESS_TIP;
    }

    /**
     * 会员次卡详情
     */
    @RequestMapping(value = "/detail/{cardOncecardId}")
    @ResponseBody
    public Object detail(@PathVariable("cardOncecardId") Integer cardOncecardId) {
        return cardOncecardService.selectById(cardOncecardId);
    }
}
