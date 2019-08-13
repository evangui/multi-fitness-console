package com.stylefeng.guns.modular.mch.member.service.impl;

import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.UserCommonWarpper;
import com.stylefeng.guns.rest.common.exception.BizExistException;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.modular.system.dao.UserCommonMapper;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.card.service.ICardTimecardService;
import com.stylefeng.guns.modular.mch.member.service.IUserClubService;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-06-22
 */
@Service
public class UserCommonServiceImpl extends ServiceImpl<UserCommonMapper, UserCommon> implements IUserCommonService {
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	IUserClubService userClubService;
	
	@Autowired
	IVipUserService vipUserService;
	
	@Autowired
	ICardTimecardService cardTimecardService;
	
	
	/**
	 * 将普通用户加入加入俱乐部，并成为vip
	 * 
	 * @param mapUserCommnon
	 * @param clubId
	 */
	@Override
	public VipUser beVip(HashMap<String, Object> mapUserCommnon, int clubId, String cardType) {

		//1 成为俱乐部用户
		try {
			userClubService.addFromCommonUser(mapUserCommnon, clubId);
		} catch (BizExistException e) {
			// 用户已存在，继续后续流程
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	    
		int userId = Convert.toInt(mapUserCommnon.get("id"));
		int gender = Convert.toInt(mapUserCommnon.get("gender"));
		String phone = Convert.toStr(mapUserCommnon.get("phone"));
		String nickname = Convert.toStr(mapUserCommnon.get("nickname"));
		String realname = Convert.toStr(mapUserCommnon.get("realname"));
		realname = ToolUtil.isEmpty(realname) ? nickname : realname;
		
		//2  成为俱乐部vip会员
		VipUser vipUser = this.joinVip(clubId, userId, phone, nickname, realname, gender, cardType);
	
		return vipUser;
	}
	
	/**
	 * 将普通用户加入加入俱乐部，并开通试用时间卡
	 * 
	 * @param mapUserCommnon
	 * @param clubId
	 */
	@Override
	public boolean beTrialVip(HashMap<String, Object> mapUserCommnon, int clubId) {

		VipUser vipUser = this.beVip(mapUserCommnon, clubId, "系统试用时间卡");
		if (vipUser == null) {
			return false;
		}
	
	    // 赠送俱乐部时间卡
		cardTimecardService.receiveTrialCard(vipUser);
	    
		return true;
	}
	
	public VipUser joinVip(Integer clubId, Integer userId, String phone, String nickname, String realname, Integer gender, String cardType) {
		VipUser retVipUser = null;
		// 验证手机号用户是否存在，如存在且唯一，做自动绑定
		try {
			retVipUser = vipUserService.joinByExistPhone(clubId, userId, phone, nickname);
			if (retVipUser != null) {
				return retVipUser;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new BizExistException(501, "绑定已存在手机号用户失败");
		}
		if (ToolUtil.isEmpty(cardType)) {
			cardType = "系统自动添加";
		}
 
		//手机号不存在对应vip用户，则系统自动新增加一个
		VipUser vipUser = new VipUser();
		vipUser.setClubId(clubId);
		vipUser.setPhone(phone);
		vipUser.setRealname(realname);
		vipUser.setGender(gender);
		vipUser.setCardNumber(IdGenerator.getTimeId());
		vipUser.setCardType(cardType);
		vipUser.setRemark(cardType + "用户");
		vipUser.setSourceId(0);	
		//绑定会员信息
		vipUser.setUserId(userId);
		vipUser.setNickname(nickname);
		vipUser.setInsertTime(DateUtil.timeStamp());

		try {
			vipUserService.insert(vipUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizExistException(501, "vip用户添加失败");
		}
		return vipUser;
	}
	
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
	public Page<UserCommon> pageList(Page<UserCommon> page, Integer clubId, HashMap<String, Object> condition) {
    	
    	Wrapper<UserCommon> ew = new EntityWrapper<>();
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	
    	if (ToolUtil.isNotEmpty(condition.get("phone"))) {
    		ew = ew.eq("phone", (String) condition.get("phone"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("virtual")) && !condition.get("virtual").equals("0")) {
    		ew = ew.eq("virtual", Convert.toInt(condition.get("virtual")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("nickname"))) {
    		ew = ew.like("nickname", (String) condition.get("nickname"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("realname"))) {
    		ew = ew.like("realname", (String) condition.get("realname"));
    	}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<UserCommon>) new UserCommonWarpper(result).warp());
	}
	
	
	/**
     * 微信登录
     * @param $weChatInfo
     * @return array
     */
	@Override
    public AuthResponse authForWeChat(String unionid, String openid)
    {
    	UserCommon userCommon = null;
    	if (ToolUtil.isNotEmpty(unionid)) {
    		userCommon = this.selectOne(new EntityWrapper<UserCommon>().eq("weixin_unionid", unionid));
    	}
//    	if (ToolUtil.isEmpty(userCommon)) {
//    		userCommon = this.selectOne(new EntityWrapper<UserCommon>().eq("weixin_openid", openid));
//    	}
    	
        if (ToolUtil.isNotEmpty(userCommon)){
            //更新信息
        	userCommon.setWeixinUnionid(unionid);
        	userCommon.setWeixinOpenid(openid);
        	this.updateById(userCommon);
        }

        return this.getToken(userCommon);
    }
    
    /**
     * 获取 awt token
     * @param object $member 会员对象信息
     * @return array
     */
	@Override
    public AuthResponse getToken(UserCommon userCommon)
    {
    	if (userCommon == null) {
    		return new AuthResponse("", "", userCommon);
    	}
    	
    	final String randomKey = jwtTokenUtil.getRandomKey();
        String jwtSubject = JSON.toJSONString(userCommon);
        final String token = jwtTokenUtil.generateToken(jwtSubject, randomKey);
        
        userCommon.setPasswd("***");

        return new AuthResponse(token, randomKey, userCommon);
    }
	
	
}
