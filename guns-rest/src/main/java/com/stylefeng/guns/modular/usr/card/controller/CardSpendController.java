package com.stylefeng.guns.modular.usr.card.controller;

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
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.CardSpendLog;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.warpper.CardSpendLogWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;

/**
 * 私教记录控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller("usr_cardSpendController")
@RequestMapping("/usr/card/spend")
public class CardSpendController extends BaseController {

    @Autowired
    private ICardSpendLogService cardSpendLog;
    
    /**
     * 私教消课信息详情
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
    	
    	Integer id = Convert.toInt(request.getParameter("id"), 0);
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
    	String conformCode = Convert.toStr(request.getParameter("conformCode"), "");
    	if (id.equals(0) && conformCode.equals("")) {
    		return new ReturnTip(500, "参数不能为空");
    	}
    	
    	CardSpendLog item = null;
    	Wrapper<CardSpendLog> ew = new EntityWrapper<>();
    	if (!id.equals(0)) {
    		ew.eq("id", id);
    		ew.eq("club_id", clubId);
    		item = cardSpendLog.selectOne(ew);
    	} else {
    		ew.eq("conform_code", conformCode);
    		ew.eq("club_id", clubId);
    		item = cardSpendLog.selectOne(ew);
    	}
		
		
		Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(item);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ReturnTip(501, e.getMessage());
		}
//    	new PtrainBespeakRecordWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
    
    /**
     * 用户 根据，教练ID ，conformCode 生成唯一私教记录。返回 私教记录id
     */
    @SuppressWarnings("unchecked")
	@PostMapping(value = "/finishPtrain")
    @ResponseBody
    public Object finishPtrain() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	//获取token中的用户信息
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
//    	Integer vipId = Convert.toInt(mapMember.get("vipId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	//获取post数据
    	HashMap<String, String> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	Integer clubId = Convert.toInt(mapParams.get("clubId"), 0);
    	Integer coachId = Convert.toInt(mapParams.get("coachId"), 0);
    	String conformCode = Convert.toStr(mapParams.get("conformCode"), "");
    	if (clubId.equals(0) || coachId.equals(0)) {
    		return new ReturnTip(500, "参数错误");
    	}
    	
    	CardSpendLog ptrainRecord = null;
    	//消课端口 0默认 3转卡扣费 4三方消课 5两方消课 6前台代消 7课程预约 8指纹消课 9它店消费 22无记录
    	try {
    		ptrainRecord = cardSpendLog.finishPtrain(clubId, coachId, userId, conformCode, "");
		} catch (Exception e) {
			// TODO: handle exception
			return new ReturnTip(501, e.getMessage());
		}
		
    	return new ReturnTip(0, "成功", ptrainRecord);
    }
    
    /**
     * 评价本次私教
     *  针对私教记录ID添加评价：评分，评价内容
     */
    @SuppressWarnings("unchecked")
	@PostMapping(value = "/comment")
    @ResponseBody
    public Object comment() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	//获取token中的用户信息
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
//    	Integer vipId = Convert.toInt(mapMember.get("vipId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	//获取post数据
    	HashMap<String, String> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	Integer recordId = Convert.toInt(mapParams.get("recordId"), 0);
    	String commentContent = Convert.toStr(mapParams.get("content"), "");
    	Integer commentRank = Convert.toInt(mapParams.get("rank"), 0);
    	String commentTags = Convert.toStr(mapParams.get("tags"), "");
    	
    	if (recordId.equals(0) || commentContent.equals("")) {
    		return new ReturnTip(500, "参数错误");
    	}
    	
    	CardSpendLog ptrainRecord = new CardSpendLog();
    	ptrainRecord.setId(recordId);
    	ptrainRecord.setCommentContent(commentContent);
    	ptrainRecord.setCommentRank(commentRank);
    	ptrainRecord.setCommentTags(commentTags);
    	
    	try {
    		cardSpendLog.updateById(ptrainRecord);
		} catch (Exception e) {
			// TODO: handle exception
			return new ReturnTip(501, e.getMessage());
		}
		
    	return new ReturnTip(0, "成功");
    }
    
    /**
     * 根据request查询条件，构造查询参数map
    * @param request
    * @return
    */
   private HashMap<String, Object> getMapCondition(HttpServletRequest request) {
	   //综合查询条件
    	HashMap<String, Object> mapCondition = new HashMap<>();
    	
    	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡',
    	mapCondition.put("logType", Convert.toStr(request.getParameter("logType")));
    	mapCondition.put("student", Convert.toStr(request.getParameter("student"), null));
    	mapCondition.put("vipId", Convert.toStr(request.getParameter("vipId"), null));
    	mapCondition.put("userId", Convert.toStr(request.getParameter("userId"), null));
    	mapCondition.put("coachId", Convert.toStr(request.getParameter("coachId"), null));
    	mapCondition.put("userPhone", Convert.toStr(request.getParameter("userPhone"), null));
    	mapCondition.put("port", Convert.toStr(request.getParameter("port"), null));
    	mapCondition.put("startInsertTime", Convert.toStr(request.getParameter("startInsertTime"), null));
    	mapCondition.put("endInsertTime", Convert.toStr(request.getParameter("endInsertTime"), null));
     	
     	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡',
    	String logType = Convert.toStr(request.getParameter("logType"), "");
    	switch (logType) {
    	case "timeCard":
			mapCondition.put("logType", 1);
			break;
		case "personalCard":
			mapCondition.put("logType", 2);
			break;
		case "onceCard":
			mapCondition.put("logType", 3);
			break;
		case "storedCard":
			mapCondition.put("logType", 4);
			break;
		default:
			break;
		}
     	
     	return mapCondition;
	}
	
    /**
     * 获取消费记录列表
     */
    @RequestMapping(value = "/listLog")
    @ResponseBody
    public Object listLog() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	//获取token中的用户信息
    	HashMap<String, String> mapMember = (HashMap<String, String>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
//    	Integer vipId = Convert.toInt(mapMember.get("vipId"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	Integer clubId = Convert.toInt(request.getParameter("clubId"), 0);
     	
     	//综合查询条件
     	HashMap<String, Object> mapCondition = this.getMapCondition(request);
     	
    	Page<CardSpendLog> page = new PageFactory<CardSpendLog>().defaultPage("id", "desc");
    	page = cardSpendLog.pageList(page, clubId, mapCondition);
        
    	Map<String, Object> ret = super.packForPannelTable(page);
        return new ReturnTip(0, "成功", ret);
    }
    
}
