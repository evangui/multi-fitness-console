package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.Club;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 俱乐部主体信息 Mapper 接口
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ClubMapper extends BaseMapper<Club> {
	/**
     * 俱乐部列表信息查询
     */
    List<Map<String, Object>> searchList(@Param("condition") String condition);
	
}
