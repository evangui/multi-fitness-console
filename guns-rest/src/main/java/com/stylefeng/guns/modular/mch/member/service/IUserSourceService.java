package com.stylefeng.guns.modular.mch.member.service;

import com.stylefeng.guns.modular.system.model.UserSource;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 客户来源自定义字段 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IUserSourceService extends IService<UserSource> {

	Page<UserSource> pageList(Page<UserSource> page, Integer clubId, String condition);

}
