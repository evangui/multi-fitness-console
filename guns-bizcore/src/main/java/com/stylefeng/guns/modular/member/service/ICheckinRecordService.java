package com.stylefeng.guns.modular.member.service;

import com.stylefeng.guns.modular.system.model.CheckinRecord;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 签到记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICheckinRecordService extends IService<CheckinRecord> {

	Page<CheckinRecord> pageList(Page<CheckinRecord> page, Integer clubId, String condition);

}
