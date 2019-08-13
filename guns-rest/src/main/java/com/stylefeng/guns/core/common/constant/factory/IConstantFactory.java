package com.stylefeng.guns.core.common.constant.factory;

import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.system.model.ClubRing;
import com.stylefeng.guns.modular.system.model.Dict;
import com.stylefeng.guns.modular.system.model.UserClub;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.model.VipUser;

import java.util.HashMap;
import java.util.List;

/**
 * 常量生产工厂的接口
 *
 * @author fengshuonan
 * @date 2017-06-14 21:12
 */
public interface IConstantFactory {

	 /**
     * 获取被缓存的对象(用户删除业务)
     */
    String getCacheObject(String para);
    
    /**
     * 获取字典名称
     */
    String getDictName(Integer dictId);
    
    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name, Integer val);

    /**
     * 通过角色ids获取角色名称
     */
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     */
    String getSingleRoleName(Integer roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    String getSingleRoleTip(Integer roleId);

    /**
     * 获取部门名称
     */
    String getDeptName(Integer deptId);

    /**
     * 获取菜单的名称们(多个)
     */
    String getMenuNames(String menuIds);

    /**
     * 获取菜单名称
     */
    String getMenuName(Long menuId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    

    /**
     * 获取通知标题
     */
    String getNoticeTitle(Integer dictId);

    /**
     * 获取性别名称
     */
    String getSexName(Integer sex);

    /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);

    /**
     * 获取菜单状态
     */
    String getMenuStatusName(Integer status);

    /**
     * 查询字典
     */
    List<Dict> findInDict(Integer id);

   

    /**
     * 获取子部门id
     */
    List<Integer> getSubDeptId(Integer deptid);

    /**
     * 获取所有父部门id
     */
    List<Integer> getParentDeptIds(Integer deptid);

	

	/**
     * 获取菜单名称通过编号
     */
    String getClubMenuNameByCode(String code);

    String getClubNameById(Integer clubId);
    
	String getCoachNameById(int bindingId);

	String getStaffSpecialNameById(int bindingId);

	String getUserCommonNicknameById(int bindingId);
	
	String getVipRealnameById(int id);

	VipUser getVipInfoById(int id);

	ClubRing getClubRing(Integer clubId, String ringNum);

	UserCommon getUserCommonById(int bindingId);

	ClubCoach getCoachByUserId(int bindingId);

	/**
	 * 根据俱乐部用户id 获取 平台用户信息
	 * @param clubUserId
	 * @return
	 */
	UserClub getUserClubById(int clubUserId);

	/**
	 * 根据俱乐部用户id 获取 平台用户id
	 * @param clubUserId
	 * @return
	 */
	Integer getClubUserCommonId(int clubUserId);

	/**
	 * 根据俱乐部和用户id获取 vipid
	 * @param userId
	 * @param clubId
	 * @return
	 */
	Integer getVipIdByClubUserid(int userId, int clubId);

	HashMap<String, Object> getPtrainCardBasic(int cardId);

	Integer getUseridByVipid(int vipId);
}
