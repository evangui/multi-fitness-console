package com.stylefeng.guns.modular.mch.card.service;

import com.stylefeng.guns.modular.system.model.CardOncecard;

import java.util.HashMap;
import java.util.Map;

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

	Page<CardOncecard> pageList(Page<CardOncecard> page, Integer clubId, HashMap<String, Object> condition,
			Integer type);

	boolean save(Map<String, Object> mapEntity) throws Exception;

	Map<String, Object> statCountItem(Integer clubId, Integer type);

}
