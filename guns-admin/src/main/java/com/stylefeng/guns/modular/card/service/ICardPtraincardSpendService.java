package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardPtraincardSpend;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 私教卡消课记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardPtraincardSpendService extends IService<CardPtraincardSpend> {

	Page<CardPtraincardSpend> pageList(Page<CardPtraincardSpend> page, Integer clubId, String condition);

}
