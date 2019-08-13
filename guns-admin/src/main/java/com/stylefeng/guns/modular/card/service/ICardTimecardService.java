package com.stylefeng.guns.modular.card.service;

import com.stylefeng.guns.modular.system.model.CardTimecard;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员时间卡 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardTimecardService extends IService<CardTimecard> {

	Page<CardTimecard> pageList(Page<CardTimecard> page, Integer clubId, String condition);

}
