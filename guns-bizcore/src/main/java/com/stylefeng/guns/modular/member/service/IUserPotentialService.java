package com.stylefeng.guns.modular.member.service;

import com.stylefeng.guns.modular.system.model.UserPotential;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 潜在客户表 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IUserPotentialService extends IService<UserPotential> {

	Page<UserPotential> pageList(Page<UserPotential> page, Integer clubId, String condition);

}
