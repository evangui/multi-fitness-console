package com.stylefeng.guns.modular.mch.syllabus.service;

import com.stylefeng.guns.modular.system.model.SyllabusGroup;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 课程种类 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISyllabusGroupService extends IService<SyllabusGroup> {

	Page<SyllabusGroup> pageList(Page<SyllabusGroup> page, Integer clubId, String condition);

}
