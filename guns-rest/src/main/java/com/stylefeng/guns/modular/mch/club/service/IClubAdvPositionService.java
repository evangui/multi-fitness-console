package com.stylefeng.guns.modular.mch.club.service;

import com.stylefeng.guns.modular.system.model.ClubAdvPosition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 广告位表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-10-10
 */
public interface IClubAdvPositionService extends IService<ClubAdvPosition> {

	Page<ClubAdvPosition> pageList(Page<ClubAdvPosition> page, Integer clubId, String condition);

}
