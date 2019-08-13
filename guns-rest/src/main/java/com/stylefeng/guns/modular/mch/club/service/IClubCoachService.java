package com.stylefeng.guns.modular.mch.club.service;

import com.stylefeng.guns.modular.system.model.ClubCoach;

import java.util.Collection;
import java.util.HashMap;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 教练 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IClubCoachService extends IService<ClubCoach> {

	Page<ClubCoach> pageList(Page<ClubCoach> page, Integer clubId, String condition, String[] coachIds);

	ClubCoach getByRealname(Integer clubId, String realname);

	/**
	 * 从userCommon信息表，直接绑定为某俱乐部教练
	 * @param mapUserCommnon
	 * @param clubId
	 * @param coacheRealname
	 * @param goodAt
	 * @return
	 */
	ClubCoach addFromCommonUser(HashMap<String, Object> mapUserCommnon, int clubId, String coacheRealname,
			String goodAt);

	/**
	 * 根据用户注册手机号，自动匹配成为教练后的处理
	 * 
	 * @param mapUserCommnon
	 * @param clubId
	 * @param coacheRealname
	 * @param goodAt
	 * @return
	 */
	ClubCoach joinByExistPhone(HashMap<String, Object> mapUserCommnon, int clubId, String coacheRealname,
			String goodAt);

}
