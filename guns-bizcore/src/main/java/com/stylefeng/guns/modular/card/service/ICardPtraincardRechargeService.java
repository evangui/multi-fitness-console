package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardPtraincardRecharge;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 私教卡充值记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardPtraincardRechargeService extends IService<CardPtraincardRecharge> {

	Page<CardPtraincardRecharge> pageList(Page<CardPtraincardRecharge> page, Integer clubId, String condition);

}
