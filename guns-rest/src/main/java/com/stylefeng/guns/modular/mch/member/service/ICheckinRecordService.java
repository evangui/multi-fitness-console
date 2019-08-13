package com.stylefeng.guns.modular.mch.member.service;

import com.stylefeng.guns.modular.system.model.CheckinRecord;

import java.util.HashMap;
import java.util.Map;

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

	Page<CheckinRecord> pageList(Page<CheckinRecord> page, Integer clubId, HashMap<String, Object> condition);

	Map<String, Object> getWrapedUserLatestRecord(Integer vipId) throws Exception;

	CheckinRecord getUserLatestRecord(Integer vipId);

}
