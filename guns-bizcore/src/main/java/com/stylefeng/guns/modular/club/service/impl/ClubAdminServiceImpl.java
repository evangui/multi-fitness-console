package com.stylefeng.guns.modular.club.service.impl;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.dao.ClubAdminMapper;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.club.service.IClubAdminService;
import com.stylefeng.guns.modular.club.warpper.ClubAdminWarpper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 俱乐部管理员表（电脑端管理员） 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-25
 */
@Service
public class ClubAdminServiceImpl extends ServiceImpl<ClubAdminMapper, ClubAdmin> implements IClubAdminService {
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
	public Page<ClubAdmin> pageList(Page<ClubAdmin> page, Integer clubId, String condition) {
    	
    	Wrapper<ClubAdmin> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition)) {
    		ew = ew.like("username", condition).or().like("nickname", condition).or().like("phone", condition);
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<ClubAdmin>) new ClubAdminWarpper(result).warp());
    	
        //利用service接口获得结果
//    	Page<ClubAdmin> page = new PageFactory<ClubAdmin>().defaultPage();
//        Page<Map<String, Object>> page2 = clubAdminService.selectMapsPage(page, ew);
//        page2.setRecords((List<Map<String, Object>>) new ClubAdminWarpper(page2.getRecords()).warp());
	}
}
