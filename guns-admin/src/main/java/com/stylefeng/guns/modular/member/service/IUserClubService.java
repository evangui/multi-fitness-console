package com.stylefeng.guns.modular.member.service;

import com.stylefeng.guns.modular.system.model.UserClub;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 俱乐部普通用户表 服务类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-08-22
 */
public interface IUserClubService extends IService<UserClub> {

	Page<UserClub> pageList(Page<UserClub> page, Integer clubId, String condition);

}
