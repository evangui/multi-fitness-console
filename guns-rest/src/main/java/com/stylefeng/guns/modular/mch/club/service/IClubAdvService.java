package com.stylefeng.guns.modular.mch.club.service;

import com.stylefeng.guns.modular.system.model.ClubAdv;

import java.util.HashMap;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 广告表 服务类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-10-10
 */
public interface IClubAdvService extends IService<ClubAdv> {

	Page<ClubAdv> pageList(Page<ClubAdv> page, Integer clubId, HashMap<String, Object> condition);

}
