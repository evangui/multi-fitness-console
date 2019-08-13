package com.stylefeng.guns.modular.mch.club.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.model.ClubRing;
import com.stylefeng.guns.modular.system.model.CheckinRecord;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.warpper.ClubRingWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.club.service.IClubRingService;
import com.stylefeng.guns.modular.mch.member.service.ICheckinRecordService;

/**
 * 手环控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller
@RequestMapping("/mch/club/clubRing")
public class ClubRingController extends BaseController {

	@Autowired
    private IClubRingService clubRingService;
	@Autowired
    private ICheckinRecordService checkinRecordService;
    
    /**
     * 获取签到记录列表
     */
    @RequestMapping(value = "/pagelist")
    @ResponseBody
    public Object pagelist() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//综合查询条件
     	HashMap<String, Object> mapCondition = new HashMap<>();
     	mapCondition.put("realname", Convert.toStr(request.getParameter("realname"), null));
     	mapCondition.put("phone", Convert.toStr(request.getParameter("phone"), null));
     	mapCondition.put("nickname", Convert.toStr(request.getParameter("nickname"), null));
     	mapCondition.put("virtual", Convert.toStr(request.getParameter("virtual"), null));
     	mapCondition.put("status", Convert.toStr(request.getParameter("status"), null));
     	
    	Page<ClubRing> page = new PageFactory<ClubRing>().defaultPage("id", "desc");
    	page.setLimit(50);
    	page = clubRingService.pageList(page, clubId, mapCondition);
        
        return new ReturnTip(0, "成功", super.packForPannelTable(page));
    }

    /**
     * 新增+更新
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return new ReturnTip(500, "俱乐部信息失效");
    	}
    	
    	Map<String, String[]> mapParams = request.getParameterMap();
    	Integer _id = Convert.toInt(mapParams.get("id")[0], 0);
    	//入库对象
    	Map<String, Object> mapEntity = new HashMap<>();
    	mapEntity.put("clubId", clubId);
    	mapEntity.put("id", _id);
    	mapEntity.put("type", Convert.toInt(mapParams.get("type")[0]));
    	mapEntity.put("realname", Convert.toStr(mapParams.get("realname")[0]));
    	mapEntity.put("nickname", Convert.toStr(mapParams.get("realname")[0]));
    	mapEntity.put("avatar", Convert.toStr(mapParams.get("avatar")[0]));
    	mapEntity.put("userId", Convert.toInt(mapParams.get("userId")[0]));
    	mapEntity.put("auth", JSON.toJSONString(mapParams.get("authArray[]")));
    	
    	ClubRing clubRing = (ClubRing) ToolUtil.convertMap(ClubRing.class, mapEntity);
     
    	Wrapper<ClubRing> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("id", _id);
    	
    	//验证信息是否存在
    	try {
			if (0 == clubRingService.selectCount(ew)) {
				clubRing.setId(null);
				clubRing.setInsertTime(DateUtil.timeStamp());
				clubRingService.insert(clubRing);
			} else {
				clubRing.setId(_id);
				clubRingService.update(clubRing, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ReturnTip(0, "成功");
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
     	Wrapper<ClubRing> ew = new EntityWrapper<>();
     	ew = ew.eq("id", id);
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("type", type);
    	try {
    		clubRingService.delete(ew);
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
	@RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
    	}
    	
    	ClubRing itemInDb = null;
    	try {
    		//获取内容
        	Integer id = ToolUtil.toInt(request.getParameter("id"));
        	itemInDb = clubRingService.selectById(id);
        	
        	//验证合同所属俱乐部
        	if (!itemInDb.getClubId().equals(clubId)) {
        		return ResponseEntity.ok(new ReturnTip(501, "合同访问受限"));
        	}
		} catch (NullPointerException e) {
			// TODO: handle exception
			return ResponseEntity.ok(new ReturnTip(501, "实体不存在"));
		}
    	
    	
    	Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(itemInDb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	new ClubRingWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
    
    /**
     * 借出/归还 手环
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/borrowReturn")
    @ResponseBody
    public Object borrowReturn() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return new ReturnTip(500, "俱乐部信息失效");
    	}
     
    	Integer currentTime = DateUtil.timeStamp();
    	Integer methodType = Integer.parseInt(request.getParameter("status"));	//借出 0  归还 2
    	Integer clubRingStatus = null;
    	Integer recordRingStatus = null;
    	if (methodType.equals(0)) {
    		clubRingStatus = 1;
    		recordRingStatus = 0;
    	} else if (methodType.equals(2)) {
    		clubRingStatus = 0;
    		recordRingStatus = 1;
    	}
    	
    	Integer checkInRecordId = Integer.parseInt(request.getParameter("id"));
    	CheckinRecord checkinRecord = checkinRecordService.selectById(checkInRecordId);
    	
    	String ringNum = Convert.toStr(request.getParameter("ringNum"));
    	Integer vipId = Convert.toInt(request.getParameter("vipId"));
    	
    	/**
    	 * 1 更改手环状态
    	 */
    	ClubRing clubRing = new ClubRing();
    	clubRing.setRingNum(ringNum);
    	clubRing.setClubId(clubId);
    	clubRing.setVipId(checkinRecord.getVipId());
    	clubRing.setRealname(checkinRecord.getRealname());
    	if (methodType.equals(0)) {
    		clubRing.setBorrowTime(currentTime);
    	} else {
    		clubRing.setReturnTime(currentTime);
    	}
    	clubRing.setAdmissionRecordId(checkInRecordId);
    	clubRing.setStatus(clubRingStatus);
    	
    	Wrapper<ClubRing> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("ring_num", ringNum);
    	
    	//验证信息是否存在
    	try {
			if (0 == clubRingService.selectCount(ew)) {
				clubRing.setId(null);
				clubRing.setInsertTime(currentTime);
				clubRingService.insert(clubRing);
			} else {
				clubRingService.update(clubRing, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ReturnTip(502, "手环状态有误");
		}
    	
    	/**
    	 * 2 更改签到记录信息
    	 */
    	/**
    	 * 1 更改手环状态
    	 */
    	checkinRecord.setRingStatus(recordRingStatus);
    	
    	if (methodType.equals(0)) {
    		//借出 + 签到
        	checkinRecord.setRingNum(ringNum);
        	
        	checkinRecord.setStatus(3);
        	checkinRecord.setLastCheckInTime(currentTime);
        	checkinRecord.setCheckOutTime(0);
        	checkinRecord.setInsertTime(currentTime);
    	} else {
    		//借出 + 离场
        	checkinRecord.setRingNum("");
        	
        	checkinRecord.setStatus(2);
        	checkinRecord.setCheckOutTime(currentTime);
    	}
    	
    	checkinRecordService.updateById(checkinRecord);
    	
    	return new ReturnTip(0, "成功");
    }
}
