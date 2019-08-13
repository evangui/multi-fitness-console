package com.stylefeng.guns.modular.syllabus.controller;

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

import com.stylefeng.guns.modular.system.model.SyllabusItem;
import com.stylefeng.guns.modular.syllabus.service.ISyllabusItemService;

/**
 * 团操课排期表课程控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:03:13
 */
@Controller
@RequestMapping("/syllabusItem")
public class SyllabusItemController extends BaseController {

    private String PREFIX = "/syllabus/syllabusItem/";

    @Autowired
    private ISyllabusItemService syllabusItemService;

    /**
     * 跳转到团操课排期表课程首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "syllabusItem.html";
    }

    /**
     * 跳转到添加团操课排期表课程
     */
    @RequestMapping("/syllabusItem_add")
    public String syllabusItemAdd() {
        return PREFIX + "syllabusItem_add.html";
    }

    /**
     * 跳转到修改团操课排期表课程
     */
    @RequestMapping("/syllabusItem_update/{syllabusItemId}")
    public String syllabusItemUpdate(@PathVariable Integer syllabusItemId, Model model) {
        SyllabusItem syllabusItem = syllabusItemService.selectById(syllabusItemId);
        model.addAttribute("item",syllabusItem);
        LogObjectHolder.me().set(syllabusItem);
        return PREFIX + "syllabusItem_edit.html";
    }

    /**
     * 获取团操课排期表课程列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SyllabusItem> page = new PageFactory<SyllabusItem>().defaultPage();
    	
    	page = syllabusItemService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增团操课排期表课程
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SyllabusItem syllabusItem) {
        syllabusItemService.insert(syllabusItem);
        return SUCCESS_TIP;
    }

    /**
     * 删除团操课排期表课程
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer syllabusItemId) {
        syllabusItemService.deleteById(syllabusItemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改团操课排期表课程
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SyllabusItem syllabusItem) {
        syllabusItemService.updateById(syllabusItem);
        return SUCCESS_TIP;
    }

    /**
     * 团操课排期表课程详情
     */
    @RequestMapping(value = "/detail/{syllabusItemId}")
    @ResponseBody
    public Object detail(@PathVariable("syllabusItemId") Integer syllabusItemId) {
        return syllabusItemService.selectById(syllabusItemId);
    }
}
