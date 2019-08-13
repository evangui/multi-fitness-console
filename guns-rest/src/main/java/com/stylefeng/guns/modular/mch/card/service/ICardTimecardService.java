package com.stylefeng.guns.modular.mch.card.service;

import com.stylefeng.guns.modular.system.model.CardTimecard;
import com.stylefeng.guns.modular.system.model.VipUser;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员时间卡 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardTimecardService extends IService<CardTimecard> {

	Page<CardTimecard> pageList(Page<CardTimecard> page, Integer clubId, HashMap<String, Object> condition,
			Integer type);

	CardTimecard getLongestCard(Integer vipId);

	boolean save(Map<String, Object> mapEntity) throws Exception;

	Map<String, Object> statCountItem(Integer clubId, Integer type);

	/**
	 * vip用户自动领取系统试用时间卡
	 * 
	 * @param vipUser
	 * @return
	 */
	boolean receiveTrialCard(VipUser vipUser);

}
