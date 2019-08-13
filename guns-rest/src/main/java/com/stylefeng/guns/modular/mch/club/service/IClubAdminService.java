package com.stylefeng.guns.modular.mch.club.service;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 俱乐部管理员表（电脑端管理员） 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-25
 */
public interface IClubAdminService extends IService<ClubAdmin> {

	Page<ClubAdmin> pageList(Page<ClubAdmin> page, Integer clubId, String condition);

}
