package com.stylefeng.guns.modular.mch.club.service.impl;

import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.system.model.StaffSpecial;
import com.stylefeng.guns.modular.system.warpper.StaffSpecialWarpper;
import com.stylefeng.guns.modular.system.dao.StaffSpecialMapper;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.club.service.IStaffSpecialService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 手机端工作人员-会籍前台主管 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@Service
public class StaffSpecialServiceImpl extends ServiceImpl<StaffSpecialMapper, StaffSpecial> implements IStaffSpecialService {
	/**
     * <p>
     * 分页列表查询
     * </p>
     *
     * @param page 分页查询条件
     * @param clubId   俱乐部id
     * @param type   手机端工作人员类型 0会籍 1前台 2主管
     * @param condition   其他模糊查询条件
     * @return List<>
     */
	@SuppressWarnings("unchecked")
	@Override
	public Page<StaffSpecial> pageList(Page<StaffSpecial> page, Integer clubId, Integer type, String condition) {
    	
    	Wrapper<StaffSpecial> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	ew = ew.eq("type", type);
    	if (ToolUtil.isNotEmpty(condition)) {
    		ew = ew.like("nickname", condition).or().like("realname", condition);
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<StaffSpecial>) new StaffSpecialWarpper(result).warp());
	}
	
	@Override
	public StaffSpecial getByRealname(Integer clubId, String realname, Integer type) {
		Wrapper<StaffSpecial> ew = new EntityWrapper<>();
		ew = ew.eq("club_id", clubId);
		ew = ew.eq("type", type);
		ew = ew.eq("realname", realname);
		return this.selectOne(ew);
	}
}
