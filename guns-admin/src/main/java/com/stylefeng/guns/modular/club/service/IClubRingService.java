package com.stylefeng.guns.modular.club.service;

import com.stylefeng.guns.modular.system.model.ClubRing;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 手机端工作人员-会籍前台主管 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IClubRingService extends IService<ClubRing> {

	Page<ClubRing> pageList(Page<ClubRing> page, Integer clubId, String condition);

}
