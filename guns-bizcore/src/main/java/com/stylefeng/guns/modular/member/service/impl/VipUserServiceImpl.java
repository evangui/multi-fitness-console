package com.stylefeng.guns.modular.member.service.impl;

import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.dao.VipUserMapper;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.member.warpper.VipUserWarpper;
import com.stylefeng.guns.modular.member.service.IVipUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * VIP会员表 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@Service
public class VipUserServiceImpl extends ServiceImpl<VipUserMapper, VipUser> implements IVipUserService {
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
	public Page<VipUser> pageList(Page<VipUser> page, Integer clubId, String condition) {
    	
    	Wrapper<VipUser> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition)) {
    		ew = ew.like("nickname", condition).or().like("realname", condition);
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<VipUser>) new VipUserWarpper(result).warp());
	}
}
