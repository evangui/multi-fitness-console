package com.stylefeng.guns.modular.mch.member.service;

import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;

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
public interface IUserCommonService extends IService<UserCommon> {

	Page<UserCommon> pageList(Page<UserCommon> page, Integer clubId, HashMap<String, Object> mapCondition);

	AuthResponse getToken(UserCommon userCommon);

	AuthResponse authForWeChat(String unionid, String openid);

	boolean beTrialVip(HashMap<String, Object> mapUserCommnon, int clubId);

	VipUser beVip(HashMap<String, Object> mapUserCommnon, int clubId, String cardType);

}
