package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardPtraincard;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员私教卡 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardPtraincardService extends IService<CardPtraincard> {

	Page<CardPtraincard> pageList(Page<CardPtraincard> page, Integer clubId, String condition);

}
