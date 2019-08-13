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

import com.stylefeng.guns.modular.system.model.SyllabusClassroom;
import com.stylefeng.guns.modular.syllabus.service.ISyllabusClassroomService;

/**
 * 团操教室控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:02:14
 */
@Controller
@RequestMapping("/syllabusClassroom")
public class SyllabusClassroomController extends BaseController {

    private String PREFIX = "/syllabus/syllabusClassroom/";

    @Autowired
    private ISyllabusClassroomService syllabusClassroomService;

    /**
     * 跳转到团操教室首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "syllabusClassroom.html";
    }

    /**
     * 跳转到添加团操教室
     */
    @RequestMapping("/syllabusClassroom_add")
    public String syllabusClassroomAdd() {
        return PREFIX + "syllabusClassroom_add.html";
    }

    /**
     * 跳转到修改团操教室
     */
    @RequestMapping("/syllabusClassroom_update/{syllabusClassroomId}")
    public String syllabusClassroomUpdate(@PathVariable Integer syllabusClassroomId, Model model) {
        SyllabusClassroom syllabusClassroom = syllabusClassroomService.selectById(syllabusClassroomId);
        model.addAttribute("item",syllabusClassroom);
        LogObjectHolder.me().set(syllabusClassroom);
        return PREFIX + "syllabusClassroom_edit.html";
    }

    /**
     * 获取团操教室列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SyllabusClassroom> page = new PageFactory<SyllabusClassroom>().defaultPage();
    	
    	page = syllabusClassroomService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增团操教室
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SyllabusClassroom syllabusClassroom) {
        syllabusClassroomService.insert(syllabusClassroom);
        return SUCCESS_TIP;
    }

    /**
     * 删除团操教室
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer syllabusClassroomId) {
        syllabusClassroomService.deleteById(syllabusClassroomId);
        return SUCCESS_TIP;
    }

    /**
     * 修改团操教室
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SyllabusClassroom syllabusClassroom) {
        syllabusClassroomService.updateById(syllabusClassroom);
        return SUCCESS_TIP;
    }

    /**
     * 团操教室详情
     */
    @RequestMapping(value = "/detail/{syllabusClassroomId}")
    @ResponseBody
    public Object detail(@PathVariable("syllabusClassroomId") Integer syllabusClassroomId) {
        return syllabusClassroomService.selectById(syllabusClassroomId);
    }
}
