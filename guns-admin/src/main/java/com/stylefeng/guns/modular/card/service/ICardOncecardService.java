package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardOncecard;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员次卡 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardOncecardService extends IService<CardOncecard> {

	Page<CardOncecard> pageList(Page<CardOncecard> page, Integer clubId, String condition);

}
