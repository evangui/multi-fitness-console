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
import com.stylefeng.guns.modular.system.model.CardTimecard;
import com.stylefeng.guns.modular.card.service.ICardTimecardService;

/**
 * 会员时间卡控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:40:29
 */
@Controller
@RequestMapping("/cardTimecard")
public class CardTimecardController extends BaseController {

    private String PREFIX = "/card/cardTimecard/";

    @Autowired
    private ICardTimecardService cardTimecardService;

    /**
     * 跳转到会员时间卡首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardTimecard.html";
    }

    /**
     * 跳转到添加会员时间卡
     */
    @RequestMapping("/cardTimecard_add")
    public String cardTimecardAdd() {
        return PREFIX + "cardTimecard_add.html";
    }

    /**
     * 跳转到修改会员时间卡
     */
    @RequestMapping("/cardTimecard_update/{cardTimecardId}")
    public String cardTimecardUpdate(@PathVariable Integer cardTimecardId, Model model) {
        CardTimecard cardTimecard = cardTimecardService.selectById(cardTimecardId);
        model.addAttribute("item",cardTimecard);
        LogObjectHolder.me().set(cardTimecard);
        return PREFIX + "cardTimecard_edit.html";
    }

    /**
     * 获取会员时间卡列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardTimecard> page = new PageFactory<CardTimecard>().defaultPage();
    	
    	page = cardTimecardService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增会员时间卡
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardTimecard cardTimecard) {
        cardTimecardService.insert(cardTimecard);
        return SUCCESS_TIP;
    }

    /**
     * 删除会员时间卡
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardTimecardId) {
        cardTimecardService.deleteById(cardTimecardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会员时间卡
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardTimecard cardTimecard) {
        cardTimecardService.updateById(cardTimecard);
        return SUCCESS_TIP;
    }

    /**
     * 会员时间卡详情
     */
    @RequestMapping(value = "/detail/{cardTimecardId}")
    @ResponseBody
    public Object detail(@PathVariable("cardTimecardId") Integer cardTimecardId) {
        return cardTimecardService.selectById(cardTimecardId);
    }
}
