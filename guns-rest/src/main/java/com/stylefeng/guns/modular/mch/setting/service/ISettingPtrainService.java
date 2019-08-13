package com.stylefeng.guns.modular.mch.setting.service;

import com.stylefeng.guns.modular.system.model.SettingPtrain;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 私教课程设置 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-19
 */
public interface ISettingPtrainService extends IService<SettingPtrain> {

	Page<SettingPtrain> pageList(Page<SettingPtrain> page, Integer clubId, String condition);

}
