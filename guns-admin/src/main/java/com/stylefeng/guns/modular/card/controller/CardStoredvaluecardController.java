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
import com.stylefeng.guns.modular.system.model.CardStoredvaluecard;
import com.stylefeng.guns.modular.card.service.ICardStoredvaluecardService;

/**
 * 会员储值卡控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:38:42
 */
@Controller
@RequestMapping("/cardStoredvaluecard")
public class CardStoredvaluecardController extends BaseController {

    private String PREFIX = "/card/cardStoredvaluecard/";

    @Autowired
    private ICardStoredvaluecardService cardStoredvaluecardService;

    /**
     * 跳转到会员储值卡首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardStoredvaluecard.html";
    }

    /**
     * 跳转到添加会员储值卡
     */
    @RequestMapping("/cardStoredvaluecard_add")
    public String cardStoredvaluecardAdd() {
        return PREFIX + "cardStoredvaluecard_add.html";
    }

    /**
     * 跳转到修改会员储值卡
     */
    @RequestMapping("/cardStoredvaluecard_update/{cardStoredvaluecardId}")
    public String cardStoredvaluecardUpdate(@PathVariable Integer cardStoredvaluecardId, Model model) {
        CardStoredvaluecard cardStoredvaluecard = cardStoredvaluecardService.selectById(cardStoredvaluecardId);
        model.addAttribute("item",cardStoredvaluecard);
        LogObjectHolder.me().set(cardStoredvaluecard);
        return PREFIX + "cardStoredvaluecard_edit.html";
    }

    /**
     * 获取会员储值卡列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardStoredvaluecard> page = new PageFactory<CardStoredvaluecard>().defaultPage();
    	
    	page = cardStoredvaluecardService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增会员储值卡
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardStoredvaluecard cardStoredvaluecard) {
        cardStoredvaluecardService.insert(cardStoredvaluecard);
        return SUCCESS_TIP;
    }

    /**
     * 删除会员储值卡
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardStoredvaluecardId) {
        cardStoredvaluecardService.deleteById(cardStoredvaluecardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会员储值卡
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardStoredvaluecard cardStoredvaluecard) {
        cardStoredvaluecardService.updateById(cardStoredvaluecard);
        return SUCCESS_TIP;
    }

    /**
     * 会员储值卡详情
     */
    @RequestMapping(value = "/detail/{cardStoredvaluecardId}")
    @ResponseBody
    public Object detail(@PathVariable("cardStoredvaluecardId") Integer cardStoredvaluecardId) {
        return cardStoredvaluecardService.selectById(cardStoredvaluecardId);
    }
}
