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
import com.stylefeng.guns.modular.system.model.CardStoredvaluecardSpend;
import com.stylefeng.guns.modular.card.service.ICardStoredvaluecardSpendService;

/**
 * 储值卡消费记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:40:13
 */
@Controller
@RequestMapping("/cardStoredvaluecardSpend")
public class CardStoredvaluecardSpendController extends BaseController {

    private String PREFIX = "/card/cardStoredvaluecardSpend/";

    @Autowired
    private ICardStoredvaluecardSpendService cardStoredvaluecardSpendService;

    /**
     * 跳转到储值卡消费记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardStoredvaluecardSpend.html";
    }

    /**
     * 跳转到添加储值卡消费记录
     */
    @RequestMapping("/cardStoredvaluecardSpend_add")
    public String cardStoredvaluecardSpendAdd() {
        return PREFIX + "cardStoredvaluecardSpend_add.html";
    }

    /**
     * 跳转到修改储值卡消费记录
     */
    @RequestMapping("/cardStoredvaluecardSpend_update/{cardStoredvaluecardSpendId}")
    public String cardStoredvaluecardSpendUpdate(@PathVariable Integer cardStoredvaluecardSpendId, Model model) {
        CardStoredvaluecardSpend cardStoredvaluecardSpend = cardStoredvaluecardSpendService.selectById(cardStoredvaluecardSpendId);
        model.addAttribute("item",cardStoredvaluecardSpend);
        LogObjectHolder.me().set(cardStoredvaluecardSpend);
        return PREFIX + "cardStoredvaluecardSpend_edit.html";
    }

    /**
     * 获取储值卡消费记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardStoredvaluecardSpend> page = new PageFactory<CardStoredvaluecardSpend>().defaultPage();
    	
    	page = cardStoredvaluecardSpendService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增储值卡消费记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardStoredvaluecardSpend cardStoredvaluecardSpend) {
        cardStoredvaluecardSpendService.insert(cardStoredvaluecardSpend);
        return SUCCESS_TIP;
    }

    /**
     * 删除储值卡消费记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardStoredvaluecardSpendId) {
        cardStoredvaluecardSpendService.deleteById(cardStoredvaluecardSpendId);
        return SUCCESS_TIP;
    }

    /**
     * 修改储值卡消费记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardStoredvaluecardSpend cardStoredvaluecardSpend) {
        cardStoredvaluecardSpendService.updateById(cardStoredvaluecardSpend);
        return SUCCESS_TIP;
    }

    /**
     * 储值卡消费记录详情
     */
    @RequestMapping(value = "/detail/{cardStoredvaluecardSpendId}")
    @ResponseBody
    public Object detail(@PathVariable("cardStoredvaluecardSpendId") Integer cardStoredvaluecardSpendId) {
        return cardStoredvaluecardSpendService.selectById(cardStoredvaluecardSpendId);
    }
}
