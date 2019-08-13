package com.stylefeng.guns.modular.usr.card.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.CardSpendLog;
import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.stylefeng.guns.modular.system.warpper.PtrainBespeakRecordWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;

/**
 * 私教记录控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller("usr_ptraincardSpendController")
@RequestMapping("/usr/card/ptraincardSpend")
public class PtraincardSpendController extends BaseController {

    @Autowired
    private ICardSpendLogService cardSpendLog;
    
    
    /**
     * 用户 根据，教练ID ，conformCode 生成唯一私教记录。返回 私教记录id
     */
    @SuppressWarnings("unchecked")
	@PostMapping(value = "/add")
    @ResponseBody
    public Object add() throws Exception {
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
    
    
}
