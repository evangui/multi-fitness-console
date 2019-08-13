package com.stylefeng.guns.modular.mch.syllabus.service;

import com.stylefeng.guns.modular.system.model.SyllabusSubscribeRecord;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 团操课用户预约记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ISyllabusSubscribeRecordService extends IService<SyllabusSubscribeRecord> {

	Page<SyllabusSubscribeRecord> pageList(Page<SyllabusSubscribeRecord> page, Integer clubId, String condition);

}
