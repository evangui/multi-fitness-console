package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardStoredvaluecard;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员储值卡 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardStoredvaluecardService extends IService<CardStoredvaluecard> {

	Page<CardStoredvaluecard> pageList(Page<CardStoredvaluecard> page, Integer clubId, String condition);

}
