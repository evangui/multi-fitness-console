package com.stylefeng.guns.modular.club.service.impl;

import com.stylefeng.guns.modular.system.model.ClubAdvPosition;
import com.stylefeng.guns.modular.system.dao.ClubAdvPositionMapper;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.club.service.IClubAdvPositionService;
import com.stylefeng.guns.modular.club.warpper.ClubAdvPositionWarpper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 广告位表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-10-10
 */
@Service
public class ClubAdvPositionServiceImpl extends ServiceImpl<ClubAdvPositionMapper, ClubAdvPosition> implements IClubAdvPositionService {
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
	public Page<ClubAdvPosition> pageList(Page<ClubAdvPosition> page, Integer clubId, String condition) {
    	
    	Wrapper<ClubAdvPosition> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition)) {
//    		ew = ew.like("nickname", condition).or().like("realname", condition);
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<ClubAdvPosition>) new ClubAdvPositionWarpper(result).warp());
	}
}
