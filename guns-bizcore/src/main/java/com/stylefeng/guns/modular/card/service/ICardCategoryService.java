package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardCategory;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 俱乐部卡种 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardCategoryService extends IService<CardCategory> {

	Page<CardCategory> pageList(Page<CardCategory> page, Integer clubId, String condition);

}
