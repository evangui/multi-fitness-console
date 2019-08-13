package com.stylefeng.guns.modular.mch.card.service;

import com.stylefeng.guns.modular.system.model.SettingCard;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 会员卡设置 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISettingCardService extends IService<SettingCard> {

	Page<SettingCard> pageList(Page<SettingCard> page, Integer clubId, String condition);

}
