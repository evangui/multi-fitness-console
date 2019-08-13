package com.stylefeng.guns.modular.mch.member.service.impl;

import com.stylefeng.guns.modular.system.model.CheckinRecord;
import com.stylefeng.guns.modular.system.model.CheckinRecord;
import com.stylefeng.guns.modular.system.warpper.CheckinRecordWarpper;
import com.stylefeng.guns.modular.system.warpper.CheckinRecordWarpper;
import com.stylefeng.guns.modular.system.dao.CheckinRecordMapper;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.member.service.ICheckinRecordService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 签到记录 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@Service
public class CheckinRecordServiceImpl extends ServiceImpl<CheckinRecordMapper, CheckinRecord>
		implements ICheckinRecordService {
	/**
	 * <p>
	 * 分页列表查询
	 * </p>
	 *
	 * @param page
	 *            分页查询条件
	 * @param clubId
	 *            俱乐部id
	 * @param condition
	 *            其他模糊查询条件
	 * @return List<>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<CheckinRecord> pageList(Page<CheckinRecord> page, Integer clubId, HashMap<String, Object> condition) {

		Wrapper<CheckinRecord> ew = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
			ew = ew.eq("club_id", clubId);
		}

		if (ToolUtil.isNotEmpty(condition.get("vipId"))) {
			ew = ew.eq("vip_id", Convert.toStr(condition.get("vipId")));
		}
		if (ToolUtil.isNotEmpty(condition.get("ringNum"))) {
			ew = ew.eq("ring_num", Convert.toStr(condition.get("ringNum")));
		}
		if (ToolUtil.isNotEmpty(condition.get("timeRangeStart"))) {
			ew = ew.gt("insert_time", Convert.toInt(condition.get("timeRangeStart")));
		}
		if (ToolUtil.isNotEmpty(condition.get("timeRangeEnd"))) {
			ew = ew.lt("insert_time", Convert.toInt(condition.get("timeRangeEnd")));
		}
		if (ToolUtil.isNotEmpty(condition.get("realname"))) {
			ew = ew.like("realname", Convert.toStr(condition.get("realname")));
		}
		if (ToolUtil.isNotEmpty(condition.get("phone"))) {
			ew = ew.like("phone", Convert.toStr(condition.get("phone")));
		}
		if (ToolUtil.isNotEmpty(condition.get("membershipName"))) {
			ew = ew.like("membership_name", Convert.toStr(condition.get("membershipName")));
		}
		if (ToolUtil.isNotEmpty(condition.get("opeUsername"))) {
			ew = ew.like("ope_username", Convert.toStr(condition.get("opeUsername")));
		}

		List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
		return page.setRecords((List<CheckinRecord>) new CheckinRecordWarpper(result).warp());
	}

	/**
	 * 获取用户当前有效的签到记录
	 * 
	 * @param vipId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getWrapedUserLatestRecord(Integer vipId) throws Exception {
		int currentTime = DateUtil.timeStamp();

		Wrapper<CheckinRecord> ew = new EntityWrapper<>();
		ew = ew.eq("vip_id", vipId);
		ew = ew.gt("insert_time", currentTime-86400);
		Map<String, Object> mapRecordItem = this.selectMap(ew);
		if (mapRecordItem == null) {
			mapRecordItem = ToolUtil.convertBean(new CheckinRecord());
		}
		return mapRecordItem;
	}
	
	/**
	 * 获取用户当前有效的签到记录
	 * 
	 * @param vipId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CheckinRecord getUserLatestRecord(Integer vipId) {
		int currentTime = DateUtil.timeStamp();

		Wrapper<CheckinRecord> ew = new EntityWrapper<>();
		ew = ew.eq("vip_id", vipId);
		ew = ew.gt("insert_time", currentTime-86400);
		return this.selectOne(ew);
	}
}
