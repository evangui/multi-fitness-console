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
import com.stylefeng.guns.modular.system.model.CardCategory;
import com.stylefeng.guns.modular.card.service.ICardCategoryService;

/**
 * 卡种控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:17:38
 */
@Controller
@RequestMapping("/cardCategory")
public class CardCategoryController extends BaseController {

    private String PREFIX = "/card/cardCategory/";

    @Autowired
    private ICardCategoryService cardCategoryService;

    /**
     * 跳转到卡种首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "cardCategory.html";
    }

    /**
     * 跳转到添加卡种
     */
    @RequestMapping("/cardCategory_add")
    public String cardCategoryAdd() {
        return PREFIX + "cardCategory_add.html";
    }

    /**
     * 跳转到修改卡种
     */
    @RequestMapping("/cardCategory_update/{cardCategoryId}")
    public String cardCategoryUpdate(@PathVariable Integer cardCategoryId, Model model) {
        CardCategory cardCategory = cardCategoryService.selectById(cardCategoryId);
        model.addAttribute("item",cardCategory);
        LogObjectHolder.me().set(cardCategory);
        return PREFIX + "cardCategory_edit.html";
    }

    /**
     * 获取卡种列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<CardCategory> page = new PageFactory<CardCategory>().defaultPage();
    	
    	page = cardCategoryService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增卡种
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardCategory cardCategory) {
        cardCategoryService.insert(cardCategory);
        return SUCCESS_TIP;
    }

    /**
     * 删除卡种
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer cardCategoryId) {
        cardCategoryService.deleteById(cardCategoryId);
        return SUCCESS_TIP;
    }

    /**
     * 修改卡种
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CardCategory cardCategory) {
        cardCategoryService.updateById(cardCategory);
        return SUCCESS_TIP;
    }

    /**
     * 卡种详情
     */
    @RequestMapping(value = "/detail/{cardCategoryId}")
    @ResponseBody
    public Object detail(@PathVariable("cardCategoryId") Integer cardCategoryId) {
        return cardCategoryService.selectById(cardCategoryId);
    }
}
