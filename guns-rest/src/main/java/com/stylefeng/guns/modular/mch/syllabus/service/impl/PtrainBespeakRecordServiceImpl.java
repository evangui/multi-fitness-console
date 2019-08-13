package com.stylefeng.guns.modular.mch.syllabus.service.impl;

import com.stylefeng.guns.modular.system.model.CardPtraincard;
import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.PtrainBespeakRecordWarpper;
import com.stylefeng.guns.rest.common.exception.BizException;
import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.stylefeng.guns.modular.mch.card.service.ICardPtraincardService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;
import com.stylefeng.guns.modular.mch.syllabus.service.IPtrainBespeakRecordService;
import com.stylefeng.guns.modular.system.dao.PtrainBespeakRecordMapper;
import com.stylefeng.guns.core.common.constant.Const;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 私教课预约记录 服务实现类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-08-18
 */
@Service
public class PtrainBespeakRecordServiceImpl extends ServiceImpl<PtrainBespeakRecordMapper, PtrainBespeakRecord> implements IPtrainBespeakRecordService {
	@Autowired
    private IVipUserService vipUserService;
	@Autowired
    private ICardPtraincardService cardPtraincardService;
	
	/**
     * <p>
     * 分页列表查询
     * </p>
     *
     * @param page 分页查询条件
     * @param clubId   俱乐部id
     * @param condition   其他模糊查询条件
     * @return List<>
     */
	@SuppressWarnings("unchecked")
	@Override
	public Page<PtrainBespeakRecord> pageList(Page<PtrainBespeakRecord> page, Integer clubId, HashMap<String, Object> condition) {
    	
    	Wrapper<PtrainBespeakRecord> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition.get("status"))) {
    		ew = ew.eq("status", Convert.toInt(condition.get("status")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("userId"))) {
    		ew = ew.eq("user_id", Convert.toInt(condition.get("userId")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("fromTime"))) {
    		ew = ew.ge("from_time", Convert.toInt(condition.get("fromTime")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("toTime"))) {
    		ew = ew.le("to_time", Convert.toInt(condition.get("toTime")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("realname"))) {
    		ew = ew.like("realname", (String) condition.get("realname"));
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<PtrainBespeakRecord>) new PtrainBespeakRecordWarpper(result).warp());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PtrainBespeakRecord bespeak(Integer recordType, Integer clubId, Integer coachId, Integer userId, Integer starttime, Integer endtime) {
		if (endtime.equals(0)) {
			endtime = starttime + 3600;	//如果不传结束时间，则默认预约一个小时
		}
		if (endtime <= starttime) {
			throw new BizException(500, "结束时间不能小于开始时间");
		}
		
		//1 判断用户是否是该俱乐部vip会员
		Wrapper<VipUser> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId).eq("user_id", userId);
    	VipUser vipUser = vipUserService.selectOne(ew);
		if (ToolUtil.isEmpty(vipUser)) {
			throw new BizException(500, "用户非vip会员");
		}
		Integer vipId = vipUser.getId();
		
		//2 判断是否是私教会员
    	CardPtraincard card = cardPtraincardService.getUserValidCard(vipId);
		if (ToolUtil.isEmpty(card) || card.getLeftCounts() <= 0) {
			throw new BizException(500, "用户无私教预约权限");
		}
		
    	//2 判断当前时间段是否有预约，是否有休息
		String date = DateUtil.timeStamp2Date(starttime, "yyyy-MM-dd");
		Wrapper<PtrainBespeakRecord> ew2 = new EntityWrapper<>();
		ew2 = ew2.eq("club_id", clubId).eq("coach_id", coachId);
		ew2.eq("date", date);
		ew2.in("status", "0,1,2");	//当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消
		
		//开始时间或截止时间 在该范围内的，即为冲突时段不允许预约
		ew2.ge("from_time", starttime);
		ew2.le("to_time", endtime);
		System.err.println(ew2.getSqlSegment());
		System.err.println(this.selectMaps(ew2));
		if (this.selectCount(ew2) > 0) {
			throw new BizException(501, "该时段当前不可预约");
		}
		
    	//3 添加私教预约记录
		PtrainBespeakRecord entity = new PtrainBespeakRecord();
		entity.setRecordType(recordType);
		entity.setUserType(2); //'预约人类型 0非会员 1vip会员 2私教会员 ',
		entity.setStatus(1); //当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消',
		entity.setFromTime(starttime);
		entity.setToTime(endtime);
		entity.setDate(DateUtil.timeStamp2Date(starttime, "yyyy-MM-dd"));
		
		entity.setVipId(vipId);
		entity.setUserId(userId);
		entity.setClubId(clubId);
		entity.setRealname(vipUser.getRealname());
		entity.setPhone(vipUser.getPhone());
		entity.setCoachId(coachId);
		entity.setCoachName(ConstantFactory.me().getCoachNameById(coachId));
		entity.setmembershipId(vipUser.getMembershipId());
		entity.setmembershipName(vipUser.getMembershipName());
		entity.setInsertTime(DateUtil.timeStamp());
		entity.setCardId(card.getId());
		entity.setCardType(card.getCardTypeName());
		
		try {
			this.insert(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(502, "预约失败");
		}
		
		return entity;
	}
	
	@Override
	public Integer countLatestForCoach(int clubId, int coachId, int seconds) {
		int currentTime = DateUtil.timeStamp();
		
		Wrapper<PtrainBespeakRecord> ew = new EntityWrapper<>();
		ew.eq("coach_id", coachId);
		ew.eq("club_id", clubId);
//		ew.in("status", "0,1,2");	//当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消
		ew.in("status", "1");	//当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消
		ew.ge("insert_time", currentTime - seconds);
		return this.selectCount(ew);
	}
}
