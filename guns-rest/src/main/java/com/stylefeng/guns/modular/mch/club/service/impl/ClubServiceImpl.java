package com.stylefeng.guns.modular.mch.club.service.impl;

import com.stylefeng.guns.modular.system.model.Club;
import com.stylefeng.guns.modular.system.warpper.ClubWarpper;
import com.stylefeng.guns.modular.system.dao.ClubMapper;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.club.service.IClubService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 俱乐部主体信息 服务实现类
 * </p>
 *
 * @author guiyj007
 * @since 2018-06-22
 */
@Service
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements IClubService {
	 @Override
    public List<Map<String, Object>> searchList(String condition) {
        return this.baseMapper.searchList(condition);
    }
	
	
	 /**
     * <p>
     * 分页列表查询
     * </p>
     *
     * @param page 分页查询条件
     * @param condition   其他模糊查询条件
     * @return List<>
     */
	@SuppressWarnings("unchecked")
	@Override
	public Page<Club> pageList(Page<Club> page, HashMap<String, Object> condition) {
		Wrapper<Club> ew = new EntityWrapper<>();
		if (ToolUtil.isNotEmpty(condition.get("id"))) {
    		ew = ew.eq("id", Convert.toInt(condition.get("id")));
    	}
		if (ToolUtil.isNotEmpty(condition.get("trialClub"))) {
    		ew = ew.ge("club_sort", 0);
    	}
    	if (ToolUtil.isNotEmpty(condition.get("trialClub"))) {
    		ew = ew.eq("trial_club", Convert.toInt(condition.get("trialClub")));
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
    	return page.setRecords( (List<Club>) new ClubWarpper(result).warp());
        
//    	List<Club> result = baseMapper.selectPage(page, ew);
//    	return page.setRecords(result);
	}
}
