package com.stylefeng.guns.modular.syllabus.service;

import com.stylefeng.guns.modular.system.model.PtrainRecord;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 私教记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IPtrainRecordService extends IService<PtrainRecord> {

	Page<PtrainRecord> pageList(Page<PtrainRecord> page, Integer clubId, String condition);

}
