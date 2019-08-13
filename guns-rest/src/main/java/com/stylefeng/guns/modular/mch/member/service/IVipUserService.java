package com.stylefeng.guns.modular.mch.member.service;

import com.stylefeng.guns.modular.system.model.VipUser;

import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * VIP会员表 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IVipUserService extends IService<VipUser> {


	/**
	 * <p>
	 * 将vip用户 与特定用户将星绑定
	 * </p>
	 * @param clubId
	 * @param vipId
	 * @param bindingId
	 * @param type ： user coach membership
	 * 
	 * @return
	 */
	boolean bindMember(int clubId, int vipId, int bindingId, String name, String type);

	/**
     * <p>
     * 分页列表查询
     * </p>
     *
     * @param page 分页查询条件
     * @param clubId   俱乐部id
     * @param condition   其他模糊查询条件
     * @param hdSearchKey   查询范围表头
     * @return List<>
     */
	Page<VipUser> pageList(Page<VipUser> page, Integer clubId, HashMap<String, Object> condition, String hdSearchKey);

	
	boolean resetTimeCardInfo(Integer vipId);

	boolean resetPtrainCardInfo(Integer vipId);

	boolean resetOnceCardInfo(Integer vipId);

	Boolean fillCard(Integer clubId, Integer vipId, String cardNumber, BigDecimal serviceCharge) throws Exception;

	boolean uploadVipUserInfoExcel(Integer clubId, InputStream in, MultipartFile file) throws Exception;

	boolean resetStoredvalueCardInfo(Integer vipId);
	/**
     * <p>
     * 查询某项具体表头信息
     * </p>
     *
     * @param hdSearchKey   查询范围表头
     * @return List<>
     */
	Map<String, Object> statCountMap(Integer clubId, String hdSearchKey);

	XSSFWorkbook exportVipUserInfoExcel(List<Map<String, Object>> vipUserInfoList)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException,
			IntrospectionException, ParseException;

	List<Map<String, Object>> getExportList(Integer clubId, HashMap<String, Object> condition, String hdSearchKey);

	Map<String, Object> getPtrainCardInfo(Integer vipId);

	Map<String, Object> getOnceCardInfo(Integer vipId);

	List<Map<String, Object>> listVipCards(Integer clubId, Integer vipId);

	Boolean unbindUser(Integer userId);

	/**
	 * 根据手机号自动匹配俱乐部已有未绑定的vip用户
	 * 
	 * @param clubId
	 * @param userId
	 * @param phone
	 * @param nickname
	 * @return
	 */
	VipUser joinByExistPhone(int clubId, int userId, String phone, String nickname);

}
