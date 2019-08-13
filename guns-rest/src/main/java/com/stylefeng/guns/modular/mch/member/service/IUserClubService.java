package com.stylefeng.guns.modular.mch.member.service;

import com.stylefeng.guns.modular.system.model.UserClub;

import java.util.HashMap;

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
public interface IUserClubService extends IService<UserClub> {

	Page<UserClub> pageList(Page<UserClub> page, Integer clubId, HashMap<String, Object> mapCondition);

	/**
	 * 从普通用户表数据，添加用户未某俱乐部用户
	 * @param mapUserCommnon
	 * @param clubId
	 * @return
	 */
	UserClub addFromCommonUser(HashMap<String, Object> mapUserCommnon, int clubId);

}
