package com.stylefeng.guns.modular.syllabus.service;

import com.stylefeng.guns.modular.system.model.SyllabusScheduleSetting;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 团操课排期设置 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISyllabusScheduleSettingService extends IService<SyllabusScheduleSetting> {

	Page<SyllabusScheduleSetting> pageList(Page<SyllabusScheduleSetting> page, Integer clubId, String condition);

}
