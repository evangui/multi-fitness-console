package com.stylefeng.guns.modular.usr.syllabus.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
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
import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.stylefeng.guns.modular.system.model.StaffSpecial;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.CheckinRecordWarpper;
import com.stylefeng.guns.modular.system.warpper.ClubCoachWarpper;
import com.stylefeng.guns.modular.system.warpper.PtrainBespeakRecordWarpper;
import com.stylefeng.guns.modular.system.warpper.UserClubWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.club.service.IClubCoachService;
import com.stylefeng.guns.modular.mch.club.service.IStaffSpecialService;
import com.stylefeng.guns.modular.mch.member.service.ICheckinRecordService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;
import com.stylefeng.guns.modular.mch.syllabus.service.IPtrainBespeakRecordService;

/**
 * 私教预约控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller("usr_ptrainBespeakController")
@RequestMapping("/usr/syllabus/ptrainBespeak")
public class PtrainBespeakController extends BaseController {

    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private IClubCoachService clubCoachService;
    @Autowired
    private IPtrainBespeakRecordService ptrainBespeakRecordService;
    
    /**
     * 用户预约记录列表
     */
    @SuppressWarnings("unchecked")
	@GetMapping(value = "/detail")
    @ResponseBody
    public Object detail() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	//获取token中的用户信息
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	Integer bespeakId = Convert.toInt(request.getParameter("id"), 0);
    	if (bespeakId.equals(0)) {
    		return new ReturnTip(500, "记录ID不能为空");
    	}
    	
    	// 综合查询条件
		PtrainBespeakRecord item = ptrainBespeakRecordService.selectById(bespeakId);
		
		Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(item);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ReturnTip(0, e.getMessage());
		}
    	new PtrainBespeakRecordWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
    
    
    /**
     * 用户预约记录列表
     */
    @SuppressWarnings("unchecked")
	@GetMapping(value = "/mylist")
    @ResponseBody
    public Object mylist() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	//获取token中的用户信息
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	Integer vipId = Convert.toInt(mapMember.get("vipId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
    	if (clubId.equals(0)) {
//    		return new ReturnTip(500, "俱乐部ID不能为空");
    	}
    	
    	// 综合查询条件
		HashMap<String, Object> mapCondition = new HashMap<>();
		mapCondition.put("userId", userId);
		mapCondition.put("status", Convert.toStr(request.getParameter("status")));
		mapCondition.put("coachId", Convert.toStr(request.getParameter("coachId")));
		mapCondition.put("fromTime", Convert.toStr(request.getParameter("fromTime")));
		mapCondition.put("toTime", Convert.toStr(request.getParameter("toTime")));

		Page<PtrainBespeakRecord> page = new PageFactory<PtrainBespeakRecord>().defaultPage("id", "desc");
		page = ptrainBespeakRecordService.pageList(page, clubId, mapCondition);

		Map<String, Object> ret = super.packForPannelTable(page);

		return new ReturnTip(0, "成功", ret);
    }
    
    
    /**
     * 某用户 预约 某教练，某时间段 的私教
     * 判断当前时间段是否有预约，是否有休息
        	添加私教预约记录
     */
    @SuppressWarnings("unchecked")
	@PostMapping(value = "/bespeak")
    @ResponseBody
    public Object bespeak() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	//获取token中的用户信息
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	Integer vipId = Convert.toInt(mapMember.get("vipId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	//获取post数据
    	HashMap<String, String> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	Integer recordType = Convert.toInt(mapParams.get("recordType"), 0);
    	Integer coachId = Convert.toInt(mapParams.get("coachId"), 0);
    	Integer clubId = Convert.toInt(mapParams.get("clubId"), 0);
    	Integer starttime = Convert.toInt(mapParams.get("starttime"), 0);
    	Integer endtime = Convert.toInt(mapParams.get("endtime"), 0);
    	
    	try {
    		PtrainBespeakRecord bespeakRecord = ptrainBespeakRecordService.bespeak(recordType, clubId, coachId, userId, starttime, endtime);
    		return new ReturnTip(0, "成功", bespeakRecord);
    	} catch (Exception e) {
			// TODO: handle exception
			return new ReturnTip(501, e.getMessage());
		}
    	
    }
    
    /**
     * 取消预约
     */
    @SuppressWarnings("unchecked")
	@PostMapping(value = "/cancel")
    @ResponseBody
    public Object cancel() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	//获取token中的用户信息
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	Integer vipId = Convert.toInt(mapMember.get("vipId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	//获取post数据
    	HashMap<String, String> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	Integer recordId = Convert.toInt(mapParams.get("recordId"), 0);
    	String cancelFrom = Convert.toStr(mapParams.get("cancelFrom"), "user");
    	String remark = Convert.toStr(mapParams.get("remark"), "");
    	
    	if (userId.equals(recordId)) {
    		return new ReturnTip(500, "参数错误");
    	}
    	
    	PtrainBespeakRecord ptrainBespeakRecord = ptrainBespeakRecordService.selectById(recordId);
    	
    	if ("user".equals(cancelFrom)) {
    		//判断是否是当前用户
    		if (!ptrainBespeakRecord.getUserId().equals(userId)) {
    			return new ReturnTip(500, "无权限操作");
    		}
    		//当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消
    		ptrainBespeakRecord.setStatus(3);
    		
    		
    	} else {
    		//判断自己是否是对应教练
    		ClubCoach coach = ConstantFactory.me().getCoachByUserId(userId);
    		if (ToolUtil.isEmpty(coach)) {
    			return new ReturnTip(500, "无权限操作");
    		}
    		//当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消
    		ptrainBespeakRecord.setStatus(4);
    		
    	}
    	if (!"".equals(remark)) {
			ptrainBespeakRecord.setRemark(remark);
		}
    	
    	try {
    		ptrainBespeakRecordService.updateById(ptrainBespeakRecord);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ReturnTip(500, "操作失败");
		}
		
    	return new ReturnTip(0, "成功");
    }
    
    /**
     * 教练接受预约
     */
    @SuppressWarnings("unchecked")
	@PostMapping(value = "/accept")
    @ResponseBody
    public Object accept() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	//获取token中的用户信息
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	Integer vipId = Convert.toInt(mapMember.get("vipId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	//获取post数据
    	HashMap<String, String> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	Integer recordId = Convert.toInt(mapParams.get("recordId"), 0);
    	String remark = Convert.toStr(mapParams.get("remark"), "");
    	
    	if (userId.equals(recordId)) {
    		return new ReturnTip(500, "参数错误");
    	}
    	
    	//判断自己是否是对应教练
    	PtrainBespeakRecord ptrainBespeakRecord = ptrainBespeakRecordService.selectById(recordId);
		ClubCoach coach = ConstantFactory.me().getCoachByUserId(userId);
		if (ToolUtil.isEmpty(coach)) {
			return new ReturnTip(500, "无权限操作");
		}
		
		//当前预约状态，0正常 1未确认 2已确认 3学员已取消 4 教练已取消
		ptrainBespeakRecord.setStatus(2);
    	if (!"".equals(remark)) {
			ptrainBespeakRecord.setRemark(remark);
		}
    	
    	try {
    		ptrainBespeakRecordService.updateById(ptrainBespeakRecord);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ReturnTip(500, "操作失败");
		}
		
    	return new ReturnTip(0, "成功");
    }
    
}
