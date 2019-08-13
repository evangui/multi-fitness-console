package com.stylefeng.guns.modular.member.service.impl;

import com.stylefeng.guns.modular.system.model.UserClub;
import com.stylefeng.guns.modular.system.model.UserClub;
import com.stylefeng.guns.modular.system.dao.UserClubMapper;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.member.service.IUserClubService;
import com.stylefeng.guns.modular.member.warpper.UserClubWarpper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 俱乐部普通用户表 服务实现类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-08-22
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
	public Page<UserClub> pageList(Page<UserClub> page, Integer clubId, String condition) {
    	
    	Wrapper<UserClub> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition)) {
    		ew = ew.like("nickname", condition).or().like("realname", condition);
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<UserClub>) new UserClubWarpper(result).warp());
	}
}
