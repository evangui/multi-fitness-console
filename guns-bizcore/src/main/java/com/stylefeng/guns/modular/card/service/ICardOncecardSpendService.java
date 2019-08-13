package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardOncecardSpend;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 次卡消次记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardOncecardSpendService extends IService<CardOncecardSpend> {

	Page<CardOncecardSpend> pageList(Page<CardOncecardSpend> page, Integer clubId, String condition);

}
