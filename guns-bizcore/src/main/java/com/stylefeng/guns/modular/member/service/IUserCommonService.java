package com.stylefeng.guns.modular.member.service;

import com.stylefeng.guns.modular.system.model.UserCommon;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-06-22
 */
public interface IUserCommonService extends IService<UserCommon> {

	Page<UserCommon> pageList(Page<UserCommon> page, Integer clubId, String condition);

}
