package com.stylefeng.guns.modular.activity.service;

import com.stylefeng.guns.modular.system.model.Activity;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 活动 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IActivityService extends IService<Activity> {

	Page<Activity> pageList(Page<Activity> page, Integer clubId, String condition);

}
