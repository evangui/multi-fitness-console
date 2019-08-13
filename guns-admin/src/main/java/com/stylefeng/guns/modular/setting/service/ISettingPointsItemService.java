package com.stylefeng.guns.modular.setting.service;

import com.stylefeng.guns.modular.system.model.SettingPointsItem;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 积分项目设置 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISettingPointsItemService extends IService<SettingPointsItem> {

	Page<SettingPointsItem> pageList(Page<SettingPointsItem> page, Integer clubId, String condition);

}
