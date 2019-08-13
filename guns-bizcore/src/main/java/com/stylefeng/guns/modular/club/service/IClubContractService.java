package com.stylefeng.guns.modular.club.service;

import com.stylefeng.guns.modular.system.model.ClubContract;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 俱乐部合同 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IClubContractService extends IService<ClubContract> {

	Page<ClubContract> pageList(Page<ClubContract> page, Integer clubId, String condition);

}
