package com.stylefeng.guns.modular.mch.activity.controller;

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
import com.stylefeng.guns.modular.system.model.ActivityCourseSubscribe;
import com.stylefeng.guns.modular.mch.activity.service.IActivityCourseSubscribeService;

/**
 * 课程活动报名记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:15:55
 */
@Controller
@RequestMapping("/activityCourseSubscribe")
public class ActivityCourseSubscribeController extends BaseController {

    private String PREFIX = "/activity/activityCourseSubscribe/";

    @Autowired
    private IActivityCourseSubscribeService activityCourseSubscribeService;

    /**
     * 跳转到课程活动报名记录首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "activityCourseSubscribe.html";
    }

    /**
     * 跳转到添加课程活动报名记录
     */
    @RequestMapping("/activityCourseSubscribe_add")
    public String activityCourseSubscribeAdd() {
        return PREFIX + "activityCourseSubscribe_add.html";
    }

    /**
     * 跳转到修改课程活动报名记录
     */
    @RequestMapping("/activityCourseSubscribe_update/{activityCourseSubscribeId}")
    public String activityCourseSubscribeUpdate(@PathVariable Integer activityCourseSubscribeId, Model model) {
        ActivityCourseSubscribe activityCourseSubscribe = activityCourseSubscribeService.selectById(activityCourseSubscribeId);
        model.addAttribute("item",activityCourseSubscribe);
        LogObjectHolder.me().set(activityCourseSubscribe);
        return PREFIX + "activityCourseSubscribe_edit.html";
    }

    /**
     * 获取课程活动报名记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<ActivityCourseSubscribe> page = new PageFactory<ActivityCourseSubscribe>().defaultPage();
    	
    	page = activityCourseSubscribeService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增课程活动报名记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ActivityCourseSubscribe activityCourseSubscribe) {
        activityCourseSubscribeService.insert(activityCourseSubscribe);
        return SUCCESS_TIP;
    }

    /**
     * 删除课程活动报名记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer activityCourseSubscribeId) {
        activityCourseSubscribeService.deleteById(activityCourseSubscribeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改课程活动报名记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ActivityCourseSubscribe activityCourseSubscribe) {
        activityCourseSubscribeService.updateById(activityCourseSubscribe);
        return SUCCESS_TIP;
    }

    /**
     * 课程活动报名记录详情
     */
    @RequestMapping(value = "/detail/{activityCourseSubscribeId}")
    @ResponseBody
    public Object detail(@PathVariable("activityCourseSubscribeId") Integer activityCourseSubscribeId) {
        return activityCourseSubscribeService.selectById(activityCourseSubscribeId);
    }
}
