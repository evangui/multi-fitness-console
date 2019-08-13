package com.stylefeng.guns.modular.mch.syllabus.service;

import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;

import java.util.HashMap;

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


	PtrainBespeakRecord bespeak(Integer recordType, Integer clubId, Integer coachId, Integer userId, Integer starttime,
			Integer endtime);


	Page<PtrainBespeakRecord> pageList(Page<PtrainBespeakRecord> page, Integer clubId,
			HashMap<String, Object> condition);


	/**
	 * 获取教练的新预约数
	 * @param clubId
	 * @param coachId
	 * @param seconds
	 * @return
	 */
	Integer countLatestForCoach(int clubId, int coachId, int seconds);

}
