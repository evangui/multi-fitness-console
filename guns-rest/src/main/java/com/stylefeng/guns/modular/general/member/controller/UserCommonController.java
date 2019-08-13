package com.stylefeng.guns.modular.general.member.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.Club;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.warpper.UserCommonWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.modular.mch.club.service.IClubService;
import com.stylefeng.guns.modular.mch.member.service.IUserClubService;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;

/**
 * 注册用户控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 17:45:38
 */
@Controller("general_userCommonController")
@RequestMapping("/general/member/userCommon")
public class UserCommonController extends BaseController {
	@Autowired
	private IUserClubService userClubService;

    @Autowired
    private IUserCommonService userCommonService;
    
    @Autowired
    private IClubService clubService;

    /**
     * 新增+更新
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/regWx")
    @ResponseBody
    public Object regWx() {
    	HashMap<String, Object> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	
//    	Integer clubId = Convert.toInt(mapParams.get("clubId"), 0);
    	String authCode = Convert.toStr(mapParams.get("authCode"));
    	
    	String phone = Convert.toStr(mapParams.get("phone"));
    	String verfiyCode = Convert.toStr(mapParams.get("verfiyCode"));
    	String unionid = Convert.toStr(mapParams.get("unionid"));
    	String openid = Convert.toStr(mapParams.get("openid"));
    	String wxUserInfoStr = mapParams.get("wxUserInfo").toString();
    	HashMap<String, String> wxUserInfo = JSON.parseObject(wxUserInfoStr, HashMap.class);
    	
    	if (!verfiyCode.equals(CacheKit.get("sms", phone))) {
    		return new ReturnTip(501, "验证码不正确");
    	}
    	
    	//查找相关俱乐部，后续直接绑定为俱乐部用户
    	Integer clubId = 0;
    	Wrapper<Club> ewClub = new EntityWrapper<Club>().eq("auth_code", authCode);
    	Club club = clubService.selectOne(ewClub);
    	if (null != club) {
    		clubId = club.getId();
    	}
    	
    	//根据union id找用户
    	Wrapper<UserCommon> ew = new EntityWrapper<UserCommon>().eq("weixin_unionid", unionid);
    	UserCommon weChatMember = userCommonService.selectOne(ew);
    	//根据手机号找用户
    	Wrapper<UserCommon> ew2 = new EntityWrapper<UserCommon>().eq("phone", phone);
    	UserCommon phoneMember = userCommonService.selectOne(ew2);
    	
    	if (ToolUtil.isNotEmpty(weChatMember) && ToolUtil.isNotEmpty(phoneMember)
    			&& weChatMember.getPhone().equals(phone)) {
    		/**
        	 * 1. 手机账号与微信账号都存在 并且 微信账号绑定的手机号是该手机号
        	 * 则直接登录成功
        	 */
//    		if (ToolUtil.isNotEmpty(club) && !phoneMember.getClubId().equals(clubId)) {
//    			phoneMember.setClubId(clubId);
//        	}
    		
    		try {
    			userCommonService.updateById(phoneMember);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return new ReturnTip(401, "账号处理失败");
			}
    		
    		if (!clubId.equals(0)) {
    			try {
    				userClubService.addFromCommonUser((HashMap<String, Object>) ToolUtil.convertBean(phoneMember), clubId);
				} catch (Exception e) {
					// TODO: handle exception
				}
    		}
    		
    		// 获取awt token
			AuthResponse loginAuth = userCommonService.getToken(weChatMember);
			return new ReturnTip(0, "成功", loginAuth);
			
    	} else if (ToolUtil.isNotEmpty(weChatMember) && ToolUtil.isNotEmpty(phoneMember)
    		&& !weChatMember.getPhone().equals("") && !weChatMember.getPhone().equals(phone)) {
    		/**
        	 * 2. //手机账号与微信账号都存在 , 并且 微信账号绑定的手机号不为空并且不是该手机号
        	 * 则方案一：冲突不能绑定。
        	 *  方案二：或者 直接以手机号为主，将手机账号的微信信息 unionid进行更新
        	 *  本系统选方案一
        	 */
    		return new ReturnTip(402, "当前微信账号已绑定手机号：" + weChatMember.getPhone() + "，请用该号码登录");
    		
//    		phoneMember.setWeixinUnionid(unionid);
//    		phoneMember.setWeixinOpenid(openid);
//    		phoneMember.setWeixinInfo(wxUserInfoStr);

//    		try {
//    			userCommonService.updateById(phoneMember);
//			} catch (Exception e) {
//				// TODO: handle exception
//				return new ReturnTip(402, "账号冲突处理失败");
//			}
//			AuthResponse loginAuth = userCommonService.getToken(phoneMember);
//			return new ReturnTip(0, "成功", loginAuth);
			
    	} else if (ToolUtil.isNotEmpty(weChatMember) && ToolUtil.isNotEmpty(phoneMember)
        		&& weChatMember.getPhone().equals("")) {
    		/**
        	 * 3. //手机账号与微信账号都存在 并且 微信账号未绑定手机号
        	 * （本系统不存在该情况，如有则未bug）
        	 * 则清空回收原微信账号。更进一步，如果回收前，用户有强业务数据，则不能回收，前端提示冲突不能绑定
        	 */
    		return new ReturnTip(403, "账号冲突处理失败");
    	
        } else if (ToolUtil.isNotEmpty(weChatMember) && ToolUtil.isEmpty(phoneMember)) {
        	/**
        	 * 4. 异常情况：没有手机号，有微信号
        	 *   将存在微信号记录的账号，绑定该新手机号
        	 */
        	weChatMember.setPhone(phone);
    		weChatMember.setWeixinOpenid(openid);
    		try {
    			userCommonService.updateById(weChatMember);
			} catch (Exception e) {
				// TODO: handle exception
				return new ReturnTip(404, "账号冲突处理失败");
			}
    		
    		if (!clubId.equals(0)) {
    			try {
    				userClubService.addFromCommonUser((HashMap<String, Object>) ToolUtil.convertBean(weChatMember), clubId);
				} catch (Exception e) {
					// TODO: handle exception
				}
    		}
    		
			AuthResponse loginAuth = userCommonService.getToken(weChatMember);
			return new ReturnTip(0, "成功", loginAuth);
    	
        } else if (ToolUtil.isEmpty(weChatMember) && ToolUtil.isNotEmpty(phoneMember)) {
        	/**
        	 * 5. 正常情况：有手机号并且没有微信号
    		 *   则 把该手机号会员信息与微信信息绑定
        	 */
        	phoneMember.setWeixinUnionid(unionid);
    		phoneMember.setWeixinOpenid(openid);
    		phoneMember.setWeixinInfo(wxUserInfoStr);
    		
//    		if (ToolUtil.isNotEmpty(club)) {
//    			phoneMember.setClubId(clubId);
//        	}
    		
    		try {
    			userCommonService.updateById(phoneMember);
			} catch (Exception e) {
				// TODO: handle exception
				return new ReturnTip(402, "账号冲突处理失败");
			}
    		
    		if (!clubId.equals(0)) {
    			try {
    				userClubService.addFromCommonUser((HashMap<String, Object>) ToolUtil.convertBean(phoneMember), clubId);
				} catch (Exception e) {
					// TODO: handle exception
				}
    		}
    		
			AuthResponse loginAuth = userCommonService.getToken(phoneMember);
			return new ReturnTip(0, "成功", loginAuth);
    	
        } else if (ToolUtil.isEmpty(weChatMember) && ToolUtil.isEmpty(phoneMember)) {
        	/**
        	 * 6. 正常情况：既没有手机号又没有微信号
        	 *   则 直接注册手机号与微信号关联的新账号
        	 */
        	UserCommon newUser = new UserCommon();
        	newUser.setPasswd("wxxx");;
        	newUser.setWeixinUnionid(unionid);
        	newUser.setWeixinOpenid(openid);
        	newUser.setPhone(phone);
//        	newUser.setWeixinInfo(wxUserInfoStr);
        	newUser.setRealname(wxUserInfo.get("realname"));
        	newUser.setNickname(wxUserInfo.get("nickName"));
        	newUser.setAvatar(wxUserInfo.get("avatarUrl"));
        	newUser.setGender(Convert.toInt(wxUserInfo.get("gender"), 0));
        	newUser.setRegIp(ToolUtil.getLocalIp(getHttpServletRequest()));
        	newUser.setInsertTime(DateUtil.timeStamp());
        	
//        	if (ToolUtil.isNotEmpty(club)) {
//        		newUser.setClubId(clubId);
//        	}
        	
        	userCommonService.insert(newUser);
        	
        	if (!clubId.equals(0)) {
        		try {
        			userClubService.addFromCommonUser((HashMap<String, Object>) ToolUtil.convertBean(newUser), clubId);
				} catch (Exception e) {
					// TODO: handle exception
				}
    			
    		}
    	
        	AuthResponse loginAuth = userCommonService.getToken(newUser);
			return new ReturnTip(0, "成功", loginAuth);
        } else {
        	
        }
    	
    	return new ReturnTip(505, "前方高能预警");
    }

    

    /**
     * 删除俱乐部管理员
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	Integer id = ToolUtil.toInt(request.getParameter("id"));
     	Integer type = ToolUtil.toInt(request.getParameter("type"));
     	Wrapper<UserCommon> ew = new EntityWrapper<>();
     	ew = ew.eq("id", id);
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("type", type);
    	try {
    		userCommonService.delete(ew);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.ok(new ReturnTip(501, "删除失败"));
		}
    	
    	return new ReturnTip(0, "操作成功");
    }

    /**
     * 俱乐部管理员详情
     */
    @SuppressWarnings("unchecked")
	@GetMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
    	HttpServletRequest request = this.getHttpServletRequest();
		Integer userId = Convert.toInt(request.getParameter("id"), 0);
    	if (ToolUtil.isEmpty(userId)) {
    		return ResponseEntity.ok(new ReturnTip(500, "id无效"));
    	}
    	
    	UserCommon itemInDb = null;
    	try {
    		//获取内容
        	itemInDb =ConstantFactory.me().getUserCommonById(userId);
        	
		} catch (NullPointerException e) {
			// TODO: handle exception
			return ResponseEntity.ok(new ReturnTip(501, "实体不存在"));
		}
    	
    	itemInDb.setPasswd(null);
    	itemInDb.setWeixinInfo(null);
    	
    	Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(itemInDb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	new UserCommonWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
}
