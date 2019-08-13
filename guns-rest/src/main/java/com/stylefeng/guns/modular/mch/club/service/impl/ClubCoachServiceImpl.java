package com.stylefeng.guns.modular.mch.club.service.impl;

import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.system.model.UserClub;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.ClubCoachWarpper;
import com.stylefeng.guns.rest.common.exception.BizException;
import com.stylefeng.guns.rest.common.exception.BizExistException;
import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.system.dao.ClubCoachMapper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.club.service.IClubCoachService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 教练 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@Service
public class ClubCoachServiceImpl extends ServiceImpl<ClubCoachMapper, ClubCoach> implements IClubCoachService {
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
	public Page<ClubCoach> pageList(Page<ClubCoach> page, Integer clubId, String condition, String[] coachIds) {
    	
    	Wrapper<ClubCoach> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(coachIds)) {
    		ew = ew.in("id", coachIds);
    	}
    	if (ToolUtil.isNotEmpty(condition)) {
    		ew = ew.like("nickname", condition).or().like("realname", condition);
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<ClubCoach>) new ClubCoachWarpper(result).warp());
	}
	
	@Override
	public ClubCoach getByRealname(Integer clubId, String realname) {
		Wrapper<ClubCoach> ew = new EntityWrapper<>();
		ew = ew.eq("club_id", clubId);
		ew = ew.eq("realname", realname);
		return this.selectOne(ew);
	}
	
	/**
	 * 根据用户注册手机号，自动匹配成为教练后的处理
	 * 
	 * @param mapUserCommnon
	 * @param clubId
	 * @param coacheRealname
	 * @param goodAt
	 * @return
	 */
	@Override
	public ClubCoach joinByExistPhone(HashMap<String, Object> mapUserCommnon, int clubId, 
			String coacheRealname, String goodAt) {
		String phone = Convert.toStr(mapUserCommnon.get("phone"));
		Integer userId = Convert.toInt(mapUserCommnon.get("id"), 0);
		
		ClubCoach entity = new ClubCoach();
		
		//根据phone检测还未绑定普通用户的 vip用户
		Wrapper<ClubCoach> ew = new EntityWrapper<>();
		ew = ew.eq("phone", phone);
		ew = ew.eq("club_id", clubId);
//		ew = ew.eq("user_id", 0);
		List<ClubCoach> listClubCoach = this.selectList(ew);
		
		//唯一一个才进行自动绑定
		int count = listClubCoach.size();
		if (count != 1) {
			return entity;
		}
		
		//如果是当前user，则已绑定，不处理
		entity = listClubCoach.get(0);
		if (entity.getUserId().equals(userId)) {
			return entity;
		}
		
		try {
			entity.setUserId(userId);
			entity.setNickname(Convert.toStr(mapUserCommnon.get("nickname")));
			entity.setGoodAt(goodAt);
			entity.setRealname(coacheRealname);
			this.updateById(entity);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return entity;
		}
		
		return entity;		
	}
	
	/**
	 * 从userCommon信息表，直接绑定为某俱乐部教练
	 * @param mapUserCommnon
	 * @param clubId
	 * @param coacheRealname
	 * @param goodAt
	 * @return
	 */
	@Override
	public ClubCoach addFromCommonUser(HashMap<String, Object> mapUserCommnon, int clubId, 
			String coacheRealname, String goodAt) {
		Integer userId = Convert.toInt(mapUserCommnon.get("id"), 0);
		if (userId.equals(0)) {
			throw new BizException(500, "会员信息已失效");
    	}
		
		Wrapper<ClubCoach> ew = new EntityWrapper<>();
    	ew = ew.eq("user_id", Convert.toInt(mapUserCommnon.get("id"), 0));
    	ew = ew.eq("club_id", clubId);
    	//验证信息是否存在
    	if (this.selectCount(ew) > 0) {
			throw new BizExistException(500, "用户已加入该俱乐部");
		}
    	
    	ClubCoach clubCoach = null;
    	//根据用户注册手机号，自动匹配成为教练后的处理
//    	clubCoach = joinByExistPhone(mapUserCommnon, clubId, coacheRealname, goodAt);
//    	if (clubCoach.getId() > 0) {
//    		//@TODO joinByExistPhone抛出异常的后续处理
//    		return clubCoach;
//    	}
    	
    	try {
    		clubCoach = new ClubCoach();
    		clubCoach.setUserId(userId);
    		clubCoach.setClubId(clubId);
    		clubCoach.setNickname(Convert.toStr(mapUserCommnon.get("nickname")));
    		clubCoach.setAvatar(Convert.toStr(mapUserCommnon.get("avatar")));
    		clubCoach.setGoodAt(goodAt);
    		clubCoach.setRealname(coacheRealname);
    		clubCoach.setAuth("[]");
//    		clubCoach.setPhone(Convert.toStr(mapUserCommnon.get("phone")));
    		clubCoach.setInsertTime(DateUtil.timeStamp());
//    		clubCoach.setGender(Convert.toInt(mapUserCommnon.get("gender")));
			
			this.insert(clubCoach);
			return clubCoach;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizException(501, "系统异常，请稍后再试");
		}
	}
}
