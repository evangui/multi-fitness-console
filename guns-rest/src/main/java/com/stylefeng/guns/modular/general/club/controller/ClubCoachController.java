package com.stylefeng.guns.modular.general.club.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
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

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.stylefeng.guns.modular.system.warpper.ClubAdminWarpper;
import com.stylefeng.guns.modular.system.warpper.ClubCoachWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardPtraincardService;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;
import com.stylefeng.guns.modular.mch.club.service.IClubCoachService;
import com.stylefeng.guns.modular.mch.syllabus.service.IPtrainBespeakRecordService;

/**
 * 教练控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:06:04
 */
@Controller("general_ClubCoachController")
@RequestMapping("/general/club/clubCoach")

public class ClubCoachController extends BaseController {

    @Autowired
    private IClubCoachService clubCoachService;
    @Autowired
    private IPtrainBespeakRecordService ptrainBespeakRecordService;
    @Autowired
    private ICardSpendLogService cardSpendLogService;
    @Autowired
    private ICardPtraincardService cardPtraincardService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/pagelist")
    @ResponseBody
    public Object pagelist() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
     	if (clubId.equals(0)) {
     		return new ReturnTip(500, "俱乐部信息失效");
     	}
     	
     	String name = ToolUtil.toStr(request.getParameter("name"), null);
     	String[] coachIds = request.getParameterValues("coachIds[]");
    	Page<ClubCoach> page = new PageFactory<ClubCoach>().defaultPage("id", "desc");
    	page = clubCoachService.pageList(page, clubId, name, coachIds);
        
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
    	mapEntity.put("realname", Convert.toStr(mapParams.get("realname")[0]));
    	mapEntity.put("avatar", Convert.toStr(mapParams.get("avatar")[0]));
    	mapEntity.put("goodAt", Convert.toStr(mapParams.get("goodAt")[0]));
    	mapEntity.put("desc", Convert.toStr(mapParams.get("desc")[0]));
    	mapEntity.put("userId", Convert.toInt(mapParams.get("userId")[0]));
    	mapEntity.put("auth", JSON.toJSONString(mapParams.get("auth[]")));
    	
    	ClubCoach clubCoach = (ClubCoach) ToolUtil.convertMap(ClubCoach.class, mapEntity);
    	//绑定用户信息
    	if (mapEntity.get("userId").equals(0)) {
    		//清空原有绑定
    		clubCoach.setNickname("");
    	} else {
    		//绑定用户昵称
        	String nickname = ConstantFactory.me().getUserCommonNicknameById((int) mapEntity.get("userId"));
        	clubCoach.setNickname(nickname);
    	}
    	
    	//查询参数
    	Wrapper<ClubCoach> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("id", _id);
    	
    	//验证信息是否存在
    	try {
			if (0 == clubCoachService.selectCount(ew)) {
				clubCoach.setId(null);
				clubCoach.setInsertTime(DateUtil.timeStamp());
				clubCoachService.insert(clubCoach);
			} else {
				clubCoach.setId(_id);
				clubCoachService.update(clubCoach, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ReturnTip(0, "成功");
    }

    
    /**
     * 详情
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        HttpServletRequest request = this.getHttpServletRequest();
        Integer id = Convert.toInt(request.getParameter("id"));
        Integer clubId = Convert.toInt(request.getParameter("clubId"));
     	if (ToolUtil.isEmpty(id) || ToolUtil.isEmpty(clubId)) {
     		return new ReturnTip(500, "参数错误");
     	}
    	
    	//获取内容
    	ClubCoach itemInDb = clubCoachService.selectById(id);
    	
    	//验证所属俱乐部
    	if (itemInDb == null || !itemInDb.getClubId().equals(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(501, "访问受限"));
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
     * 教练面板数据统计
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/homeStat")
    @ResponseBody
    public Object homeStat() {
    	HttpServletRequest request = this.getHttpServletRequest();
        Integer coachId = Convert.toInt(request.getParameter("coachId"));
        Integer clubId = Convert.toInt(request.getParameter("clubId"));
     	if (ToolUtil.isEmpty(coachId) || ToolUtil.isEmpty(clubId)) {
     		return new ReturnTip(500, "参数错误");
     	}
    	
    	//今日消课数
     	Integer newFinishedPtrainNum = cardSpendLogService.countLatestFinishPtrain(clubId, coachId, 86400);
    	//获取某教练新增预约数量（未确认状态）
     	Integer newBespeakNum = ptrainBespeakRecordService.countLatestForCoach(clubId, coachId, 86400);
    	//最近销售的课程数量
     	Integer newPtrainCardNum = cardPtraincardService.countLatestForCoach(clubId, coachId, 86400);
    	
    	Map<String, Integer> mapRet = new HashMap<>();
    	//不显示后台权限数据
    	mapRet.put("newFinishedPtrainNum", newFinishedPtrainNum);
    	mapRet.put("newBespeakNum", newBespeakNum);
    	mapRet.put("newPtrainCardNum", newPtrainCardNum);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
}
