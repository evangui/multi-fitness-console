package com.stylefeng.guns.modular.syllabus.service;

import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 私教课预约记录 服务类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-08-18
 */
public interface IPtrainBespeakRecordService extends IService<PtrainBespeakRecord> {

	Page<PtrainBespeakRecord> pageList(Page<PtrainBespeakRecord> page, Integer clubId, String condition);

}
