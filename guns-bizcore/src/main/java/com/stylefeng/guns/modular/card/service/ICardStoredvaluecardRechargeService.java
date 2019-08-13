package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardStoredvaluecardRecharge;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 储值卡充值记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardStoredvaluecardRechargeService extends IService<CardStoredvaluecardRecharge> {

	Page<CardStoredvaluecardRecharge> pageList(Page<CardStoredvaluecardRecharge> page, Integer clubId,
			String condition);

}
