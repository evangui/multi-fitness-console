package com.stylefeng.guns.modular.setting.service;

import com.stylefeng.guns.modular.system.model.SettingContract;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 合同设置 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISettingContractService extends IService<SettingContract> {

	Page<SettingContract> pageList(Page<SettingContract> page, Integer clubId, String condition);

}
