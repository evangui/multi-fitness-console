package com.stylefeng.guns.modular.usr.member.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.stylefeng.guns.modular.system.model.CardOncecard;
import com.stylefeng.guns.modular.system.model.CheckinRecord;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.system.model.StaffSpecial;
import com.stylefeng.guns.modular.system.model.UserClub;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.CheckinRecordWarpper;
import com.stylefeng.guns.modular.system.warpper.ClubCoachWarpper;
import com.stylefeng.guns.modular.system.warpper.VipUserWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.club.service.IClubCoachService;
import com.stylefeng.guns.modular.mch.club.service.IStaffSpecialService;
import com.stylefeng.guns.modular.mch.member.service.ICheckinRecordService;
import com.stylefeng.guns.modular.mch.member.service.IUserClubService;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;

/**
 * 签到记录控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller("usr_userCommonController")
@RequestMapping("/usr/member/userCommon")
public class UserCommonController extends BaseController {

    @Autowired
    private ICheckinRecordService checkinRecordService;
    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private IUserClubService userClubService;
    @Autowired
    private IClubCoachService clubCoachService;
    @Autowired
    private IUserCommonService userCommonService;
    
    /**
     * 获取当前用户的角色信息
     */
    @SuppressWarnings("unchecked")
	@GetMapping(value = "/checkRoles")
    @ResponseBody
    public Object checkRoles() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	HashMap<String, Object> mapMember = (HashMap<String, Object>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
    	if (clubId.equals(0)) {
    		return new ReturnTip(500, "俱乐部不能为空");
    	}
    	Integer checkClubUser = Convert.toInt(request.getParameter("checkClubUser"), 0);
    	Integer checkVip = Convert.toInt(request.getParameter("checkVip"), 0);
    	Integer checkCoach = Convert.toInt(request.getParameter("checkCoach"), 0);
    	Integer autoJoinVip = Convert.toInt(request.getParameter("autoJoinVip"), 0);
    	
    	int clubUserCnt = 0;
    	int vipUserCnt = 0;
    	int coachCnt = 0;
    	HashMap<String, Object> mapRet = new HashMap<>();
    	
    	if (!checkClubUser.equals(0)) {
    		clubUserCnt = userClubService.selectCount(new EntityWrapper<UserClub>().eq("user_id", userId).eq("club_id", clubId));
    		mapRet.put("isClubUser", 0 == clubUserCnt ? 0 : 1);
    	}
    	if (!checkVip.equals(0)) {
    		vipUserCnt = vipUserService.selectCount(new EntityWrapper<VipUser>().eq("user_id", userId).eq("club_id", clubId));
    		
    		if (vipUserCnt == 0 && autoJoinVip > 0) {
    			VipUser joinVipUser = null;
    			//自动加入俱乐部用户与vip用户
    				//根据手机号自动匹配俱乐部已有未绑定的vip用户
				joinVipUser = vipUserService.joinByExistPhone(clubId, userId, 
						(String) mapMember.get("phone"), (String) mapMember.get("nickname"));
				if (joinVipUser == null) {
					if (clubId.equals(80) || clubId.equals(19)) {
	    				joinVipUser = userCommonService.beVip(mapMember, clubId, "");
	    			}
				}
    			
    			vipUserCnt = joinVipUser != null ? 1 : 0;
    			mapRet.put("joinVipRes", vipUserCnt);
    		}
    		mapRet.put("isVip", 0 == vipUserCnt ? 0 : 1);
    		
    	}
    	if (!checkCoach.equals(0)) {
    		coachCnt = clubCoachService.selectCount(new EntityWrapper<ClubCoach>().eq("user_id", userId).eq("club_id", clubId));
    		mapRet.put("isCoach", 0 == coachCnt ? 0 : 1);
    	}
//		int membershipCnt = staffSpecialService.selectCount(new EntityWrapper<StaffSpecial>().eq("user_id", userId).eq("type", 0).eq("club_id", clubId));
	 
//		mapRet.put("isMembership", 0 == membershipCnt ? 0 : 1);
		
    	return new ReturnTip(0, "成功", mapRet);
    }
    
    /**
     * 用户绑定的俱乐部教练详情
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/coachDetail")
    @ResponseBody
    public Object coachDetail() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	//获取内容
		ClubCoach itemInDb = clubCoachService.selectOne(new EntityWrapper<ClubCoach>().eq("user_id", userId));
		//验证所属俱乐部
    	if (itemInDb == null || !itemInDb.getClubId().equals(clubId)) {
    		return new ReturnTip(501, "访问受限");
    	}
    	
    	Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(itemInDb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	new ClubCoachWarpper(null).warpTheMap(mapRet);
    	//不显示后台权限数据
    	mapRet.remove("auth");
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
    
    
    /**
     * 用户绑定的俱乐部vip信息
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/vipDetail")
    @ResponseBody
    public Object vipDetail() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	//获取内容
		VipUser itemInDb = vipUserService.selectOne(new EntityWrapper<VipUser>().eq("user_id", userId).eq("club_id", clubId));
		//验证所属俱乐部
    	if (itemInDb == null) {
    		return new ReturnTip(501, "访问受限");
    	}
    	
    	Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(itemInDb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
//    	new VipUserWarpper(null).warpTheMap(mapRet);
    	//不显示后台权限数据
    	return new ReturnTip(0, "成功",  mapRet);
    }
    
}
