package com.stylefeng.guns.modular.member.service;

import com.stylefeng.guns.modular.system.model.VipUserField;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * Vip用户自定义字段 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IVipUserFieldService extends IService<VipUserField> {

	Page<VipUserField> pageList(Page<VipUserField> page, Integer clubId, String condition);

}
