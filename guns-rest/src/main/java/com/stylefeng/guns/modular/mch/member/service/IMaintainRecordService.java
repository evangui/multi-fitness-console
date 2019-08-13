package com.stylefeng.guns.modular.mch.member.service;

import com.stylefeng.guns.modular.system.model.MaintainRecord;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 维护记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IMaintainRecordService extends IService<MaintainRecord> {

	Page<MaintainRecord> pageList(Page<MaintainRecord> page, Integer clubId, String condition);

}
