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

import com.stylefeng.guns.modular.system.model.SettingCard;
import com.stylefeng.guns.modular.card.service.ISettingCardService;

/**
 * 会员卡设置控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:48:19
 */
@Controller
@RequestMapping("/settingCard")
public class SettingCardController extends BaseController {

    private String PREFIX = "/card/settingCard/";

    @Autowired
    private ISettingCardService settingCardService;

    /**
     * 跳转到会员卡设置首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "settingCard.html";
    }

    /**
     * 跳转到添加会员卡设置
     */
    @RequestMapping("/settingCard_add")
    public String settingCardAdd() {
        return PREFIX + "settingCard_add.html";
    }

    /**
     * 跳转到修改会员卡设置
     */
    @RequestMapping("/settingCard_update/{settingCardId}")
    public String settingCardUpdate(@PathVariable Integer settingCardId, Model model) {
        SettingCard settingCard = settingCardService.selectById(settingCardId);
        model.addAttribute("item",settingCard);
        LogObjectHolder.me().set(settingCard);
        return PREFIX + "settingCard_edit.html";
    }

    /**
     * 获取会员卡设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SettingCard> page = new PageFactory<SettingCard>().defaultPage();
    	
    	page = settingCardService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增会员卡设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SettingCard settingCard) {
        settingCardService.insert(settingCard);
        return SUCCESS_TIP;
    }

    /**
     * 删除会员卡设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer settingCardId) {
        settingCardService.deleteById(settingCardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会员卡设置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SettingCard settingCard) {
        settingCardService.updateById(settingCard);
        return SUCCESS_TIP;
    }

    /**
     * 会员卡设置详情
     */
    @RequestMapping(value = "/detail/{settingCardId}")
    @ResponseBody
    public Object detail(@PathVariable("settingCardId") Integer settingCardId) {
        return settingCardService.selectById(settingCardId);
    }
}
