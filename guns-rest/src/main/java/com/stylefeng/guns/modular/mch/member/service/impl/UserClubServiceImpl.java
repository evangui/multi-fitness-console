package com.stylefeng.guns.modular.mch.member.service.impl;

import com.stylefeng.guns.modular.system.model.UserClub;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.warpper.UserClubWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.common.exception.BizException;
import com.stylefeng.guns.rest.common.exception.BizExistException;
import com.stylefeng.guns.modular.system.dao.UserClubMapper;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.member.service.IUserClubService;
import com.alibaba.fastjson.JSON;
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
 * 用户表 服务实现类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-06-22
 */
@Service
public class UserClubServiceImpl extends ServiceImpl<UserClubMapper, UserClub> implements IUserClubService {
	
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
	public Page<UserClub> pageList(Page<UserClub> page, Integer clubId, HashMap<String, Object> condition) {
    	
    	Wrapper<UserClub> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	
    	if (ToolUtil.isNotEmpty(condition.get("phone"))) {
    		ew = ew.eq("phone", (String) condition.get("phone"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("virtual")) && !condition.get("virtual").equals("0")) {
    		ew = ew.eq("virtual", Convert.toInt(condition.get("virtual")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("nickname"))) {
    		ew = ew.like("nickname", (String) condition.get("nickname"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("realname"))) {
    		ew = ew.like("realname", (String) condition.get("realname"));
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<UserClub>) new UserClubWarpper(result).warp());
	}
	
	@Override
	public UserClub addFromCommonUser(HashMap<String, Object> mapUserCommnon, int clubId) {
		Integer userId = Convert.toInt(mapUserCommnon.get("id"), 0);
		if (userId.equals(0)) {
			throw new BizException(500, "会员信息已失效");
    	}
		
		Wrapper<UserClub> ew = new EntityWrapper<>();
    	ew = ew.eq("user_id", Convert.toInt(mapUserCommnon.get("id"), 0));
    	ew = ew.eq("club_id", clubId);
    	//验证信息是否存在
    	if (this.selectCount(ew) > 0) {
			throw new BizExistException(500, "用户已加入该俱乐部");
		}
    	
    	try {
			UserClub userClub = new UserClub();
			userClub.setUserId(userId);
			userClub.setClubId(clubId);
			userClub.setNickname(Convert.toStr(mapUserCommnon.get("nickname")));
			userClub.setAvatar(Convert.toStr(mapUserCommnon.get("avatar")));
			userClub.setPhone(Convert.toStr(mapUserCommnon.get("phone")));
			userClub.setInsertTime(DateUtil.timeStamp());
			userClub.setGender(Convert.toInt(mapUserCommnon.get("gender")));
			
			this.insert(userClub);
			return userClub;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizException(501, "系统异常，请稍后再试");
		}
	}
	
}
