package com.stylefeng.guns.modular.club.service;

import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 教练 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IClubCoachService extends IService<ClubCoach> {

	Page<ClubCoach> pageList(Page<ClubCoach> page, Integer clubId, String condition);

}
