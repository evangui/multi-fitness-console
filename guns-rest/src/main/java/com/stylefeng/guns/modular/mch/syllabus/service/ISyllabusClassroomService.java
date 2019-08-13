package com.stylefeng.guns.modular.mch.syllabus.service;

import com.stylefeng.guns.modular.system.model.SyllabusClassroom;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 团操教室 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISyllabusClassroomService extends IService<SyllabusClassroom> {

	Page<SyllabusClassroom> pageList(Page<SyllabusClassroom> page, Integer clubId, String condition);

}
