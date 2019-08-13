package com.stylefeng.guns.modular.activity.service;

import com.stylefeng.guns.modular.system.model.ActivityCourseSubscribe;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 课程活动报名记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IActivityCourseSubscribeService extends IService<ActivityCourseSubscribe> {

	Page<ActivityCourseSubscribe> pageList(Page<ActivityCourseSubscribe> page, Integer clubId, String condition);

}
