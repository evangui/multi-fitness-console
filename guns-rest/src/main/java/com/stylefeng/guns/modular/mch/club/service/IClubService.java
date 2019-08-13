package com.stylefeng.guns.modular.mch.club.service;

import com.stylefeng.guns.modular.system.model.Club;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 俱乐部主体信息 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IClubService extends IService<Club> {
	
	public List<Map<String, Object>> searchList(String condition);

	Page<Club> pageList(Page<Club> page, HashMap<String, Object> condition);
}
