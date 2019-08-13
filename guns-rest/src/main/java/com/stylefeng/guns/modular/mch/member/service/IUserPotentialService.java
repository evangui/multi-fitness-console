package com.stylefeng.guns.modular.mch.member.service;

import com.stylefeng.guns.modular.system.model.UserPotential;
import com.stylefeng.guns.modular.system.model.VipUser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 潜在客户表 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IUserPotentialService extends IService<UserPotential> {

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
	Page<UserPotential> pageList(Page<UserPotential> page, Integer clubId, HashMap<String, Object> condition, String hdSearchKey);

	boolean bindMember(int clubId, int potentialId, int bindingId, String name, String type);

	boolean uploadPotentialInfoExcel(Integer clubId, InputStream in, MultipartFile file) throws Exception;

	/**
     * <p>
     * 查询某项具体表头信息
     * </p>
     *
     * @param hdSearchKey   查询范围表头
     * @return List<>
     */
	Map<String, Object> statCountMap(Integer clubId, String hdSearchKey);


}
