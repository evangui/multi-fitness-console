package com.stylefeng.guns.modular.mch.setting.service;

import com.stylefeng.guns.modular.system.model.SettingSyllabus;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 团操课设置 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISettingSyllabusService extends IService<SettingSyllabus> {

	Page<SettingSyllabus> pageList(Page<SettingSyllabus> page, Integer clubId, String condition);

}
