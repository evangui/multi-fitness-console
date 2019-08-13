package com.stylefeng.guns.modular.usr.member.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
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
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.CheckinRecordWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.member.service.ICheckinRecordService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;

/**
 * 签到记录控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller("usr_checkinController")
@RequestMapping("/usr/member/checkin")
public class CheckinController extends BaseController {

    @Autowired
    private ICheckinRecordService checkinRecordService;
    @Autowired
    private IVipUserService vipUserService;
    
    /**
     * 用户签到
     */
    @PostMapping(value = "/checkin")
    @ResponseBody
    public Object checkin() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	HashMap<String, String> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	Integer clubId = Convert.toInt(mapParams.get("clubId"), 0);
    	if (clubId.equals(0)) {
    		return new ReturnTip(500, "俱乐部信息失效");
    	}
    	
    	String cardNumber = Convert.toStr(mapParams.get("userQrcode"), "");
    	String status = Convert.toStr(mapParams.get("status"), "");
    	Integer currentTime = DateUtil.timeStamp();
//    	if (cardNumber.equals("")) {
//    		return new ReturnTip(501, "用户不满足签到条件");
//    	}
    	
		/**
		 * 查找vip用户
		 */
    	Wrapper<VipUser> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("user_id", userId);
        wrapper = wrapper.eq("club_id", clubId);
        
        VipUser vipUser = null;
		List<VipUser> listVipUser = vipUserService.selectList(wrapper);
        if (listVipUser.size() > 0) {
        	vipUser = listVipUser.get(0);
        } else {
        	return new ReturnTip(501, "当前用户还不是俱乐部vip会员");
        }
    	
		/**
		 * 找到该用户当天的签到记录
		 */
		CheckinRecord checkinRecord = checkinRecordService.getUserLatestRecord(vipUser.getId());
		//如果签到状态未传入，则自动处理签到状态(查找当天签到记录，进行处理)
		if (status.equals("")) {
			status = "out";
		}
		
    	/**
    	 * 增加签到记录
    	 */
		if (checkinRecord == null) {
    		// 签到
			checkinRecord = new CheckinRecord();
        	checkinRecord.setStatus(3);
        	checkinRecord.setLastCheckInTime(currentTime);
        	checkinRecord.setInsertTime(currentTime);
        	
        	checkinRecord.setClubId(clubId);
        	checkinRecord.setVipId(vipUser.getId());
        	checkinRecord.setRealname(vipUser.getRealname());
        	checkinRecord.setPhone(vipUser.getPhone());
        	checkinRecord.setOpeUserId(0);
        	checkinRecord.setOpeUsername("扫码签到");
        	checkinRecord.setPort(3);
        	
        	checkinRecordService.insert(checkinRecord);
    	} else {
    		
    		//入场状态 1 入场中 3已入场  2已出场
    		if (status.equals("in") && checkinRecord.getStatus().equals(3)) {
    			//归还 + 离场
    			checkinRecord.setRingNum("");
            	checkinRecord.setStatus(2);
            	checkinRecord.setCheckOutTime(currentTime);
    		} else if (status.equals("out") && !checkinRecord.getStatus().equals(3)){
    			//借出 + 签到
    			checkinRecord.setStatus(3);
            	checkinRecord.setLastCheckInTime(currentTime);
            	checkinRecord.setCheckOutTime(0);
    		} else {
    			//状态传入失败，可能重复签到/离场 。暂时返回成功处理
    			Map mapRet = ToolUtil.convertBean(checkinRecord);
    			mapRet.put("clubName", ConstantFactory.me().getClubNameById(clubId));
    			return new ReturnTip(0, "今日已签到", mapRet);
    		}
        	
        	//验证信息是否存在
        	try {
        		checkinRecordService.updateById(checkinRecord);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return new ReturnTip(502, "操作失败，请稍后再试！");
    		}
    	}
		
		//更新vip表的签到统计字段
		vipUser.setLastSignTime(currentTime);
		vipUserService.updateById(vipUser);
    	
		Map mapRet = ToolUtil.convertBean(checkinRecord);
		mapRet.put("clubName", ConstantFactory.me().getClubNameById(clubId));
		
    	return new ReturnTip(0, "成功", mapRet);
    }
    
}
