package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardStoredvaluecardSpend;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 储值卡消费记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardStoredvaluecardSpendService extends IService<CardStoredvaluecardSpend> {

	Page<CardStoredvaluecardSpend> pageList(Page<CardStoredvaluecardSpend> page, Integer clubId, String condition);

}
