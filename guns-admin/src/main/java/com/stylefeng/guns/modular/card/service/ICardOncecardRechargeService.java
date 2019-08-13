package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardOncecardRecharge;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 次卡充值记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardOncecardRechargeService extends IService<CardOncecardRecharge> {

	Page<CardOncecardRecharge> pageList(Page<CardOncecardRecharge> page, Integer clubId, String condition);

}
