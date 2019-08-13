package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardTimecardRecharge;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 时间卡充值记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardTimecardRechargeService extends IService<CardTimecardRecharge> {

	Page<CardTimecardRecharge> pageList(Page<CardTimecardRecharge> page, Integer clubId, String condition);

}
