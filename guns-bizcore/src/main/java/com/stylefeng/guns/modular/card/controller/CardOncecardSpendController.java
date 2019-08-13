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
import com.stylefeng.guns.modular.system.model.CardOncecardSpend;
import com.stylefeng.guns.modular.card.service.ICardOncecardSpendService;

/**
 * 次卡消次记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:37:19
 */
@Controller
@RequestMapping("/cardOncecardSpend")
public class CardOncecardSpendController extends BaseController {

    private String PREFIX = "/card/cardOncecardSpend/";

    @Autowired
    private ICardOncecardSpendService cardOncecardSpendService;

    /**
     * 跳转到次卡消次记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardOncecardSpend.html";
    }

    /**
     * 跳转到添加次卡消次记录
     */
    @RequestMapping("/cardOncecardSpend_add")
    public String cardOncecardSpendAdd() {
        return PREFIX + "cardOncecardSpend_add.html";
    }

    /**
     * 跳转到修改次卡消次记录
     */
    @RequestMapping("/cardOncecardSpend_update/{cardOncecardSpendId}")
    public String cardOncecardSpendUpdate(@PathVariable Integer cardOncecardSpendId, Model model) {
        CardOncecardSpend cardOncecardSpend = cardOncecardSpendService.selectById(cardOncecardSpendId);
        model.addAttribute("item",cardOncecardSpend);
        LogObjectHolder.me().set(cardOncecardSpend);
        return PREFIX + "cardOncecardSpend_edit.html";
    }

    /**
     * 获取次卡消次记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardOncecardSpend> page = new PageFactory<CardOncecardSpend>().defaultPage();
    	
    	page = cardOncecardSpendService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增次卡消次记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardOncecardSpend cardOncecardSpend) {
        cardOncecardSpendService.insert(cardOncecardSpend);
        return SUCCESS_TIP;
    }

    /**
     * 删除次卡消次记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardOncecardSpendId) {
        cardOncecardSpendService.deleteById(cardOncecardSpendId);
        return SUCCESS_TIP;
    }

    /**
     * 修改次卡消次记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardOncecardSpend cardOncecardSpend) {
        cardOncecardSpendService.updateById(cardOncecardSpend);
        return SUCCESS_TIP;
    }

    /**
     * 次卡消次记录详情
     */
    @RequestMapping(value = "/detail/{cardOncecardSpendId}")
    @ResponseBody
    public Object detail(@PathVariable("cardOncecardSpendId") Integer cardOncecardSpendId) {
        return cardOncecardSpendService.selectById(cardOncecardSpendId);
    }
}
