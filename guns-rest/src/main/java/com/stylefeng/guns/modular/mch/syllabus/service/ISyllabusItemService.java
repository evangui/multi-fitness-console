package com.stylefeng.guns.modular.mch.syllabus.service;

import com.stylefeng.guns.modular.system.model.SyllabusItem;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 团操课排期表课程 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISyllabusItemService extends IService<SyllabusItem> {

	Page<SyllabusItem> pageList(Page<SyllabusItem> page, Integer clubId, String condition);

}
