package com.stylefeng.guns.modular.mch.card.controller;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.ToolUtil;


import com.stylefeng.guns.modular.system.model.CardStoredvaluecard;
import com.stylefeng.guns.modular.system.model.CardSpendLog;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.CardStoredvaluecardWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardCategoryService;
import com.stylefeng.guns.modular.mch.card.service.ICardRechargeLogService;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;
import com.stylefeng.guns.modular.mch.card.service.ICardStoredvaluecardService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;

/**
 * 会员储值卡控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller
@RequestMapping("/mch/card/storedvaluecard")
public class CardStoredvaluecardController extends BaseController {

    @Autowired
    private ICardStoredvaluecardService cardStoredvaluecardService;
    @Autowired
    private ICardCategoryService cardCategoryService;
    @Autowired
    private ICardRechargeLogService cardRechargeLogService;
    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private ICardSpendLogService cardSpendLogService;
    
    /**
     * 获取会员时间卡查询统计表头
     */
    @RequestMapping(value = "/statHeader")
    @ResponseBody
    public Object statHeader() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	ArrayList<Map<String, Object>> listRet = new ArrayList<Map<String, Object>>();
     	listRet.add(cardStoredvaluecardService.statCountItem(clubId, 0));
     	listRet.add(cardStoredvaluecardService.statCountItem(clubId, 1));
     	listRet.add(cardStoredvaluecardService.statCountItem(clubId, 4));
     	listRet.add(cardStoredvaluecardService.statCountItem(clubId, 2));
     	listRet.add(cardStoredvaluecardService.statCountItem(clubId, 5));
     	listRet.add(cardStoredvaluecardService.statCountItem(clubId, 3));
     	
        return new ReturnTip(0, "成功", listRet);
    }
    
    /**
     * 获取会员时间卡列表
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
     	mapCondition.put("vipId", ToolUtil.toStr(request.getParameter("vipId"), null));
     	mapCondition.put("id", ToolUtil.toStr(request.getParameter("cardId"), null));
     	mapCondition.put("title", ToolUtil.toStr(request.getParameter("title"), null));
     	mapCondition.put("realname", ToolUtil.toStr(request.getParameter("vipName"), null));
     	mapCondition.put("cardNumber", ToolUtil.toStr(request.getParameter("cardNumber"), null));
     	mapCondition.put("membershipName", ToolUtil.toStr(request.getParameter("membershipName"), null));
     	
     	//查询范围表头
     	Integer cardStatus = Convert.toInt(request.getParameter("cardStatus"), 0);
     	
     	Page<CardStoredvaluecard> page = new PageFactory<CardStoredvaluecard>().defaultPage("id", "desc");
     	page = cardStoredvaluecardService.pageList(page, clubId, mapCondition, cardStatus);
         
     	Map<String, Object> ret = super.packForPannelTable(page);
     	
        return new ReturnTip(0, "成功", ret);
     }
     
     /**
      * 新增+更新 教练
      */
     @RequestMapping(value = "/save")
     @ResponseBody
     public Object save() throws Exception {
    	/**
    	 * 验证token中的俱乐部信息
    	 */
     	HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return new ReturnTip(500, "俱乐部信息失效");
     	}
     	
     	/**
      	 * 入库对象构建
      	 */
      	Integer _id = Convert.toInt(request.getParameter("id"), 0);
      	Integer cardTypeId = Convert.toInt(request.getParameter("cardTypeId"), 0);
      	Integer membershipId = Convert.toInt(request.getParameter("membershipId"), 0);
      	Integer coachId = Convert.toInt(request.getParameter("coachId"), 0);
      	Integer vipId = Convert.toInt(request.getParameter("vipId"), 0);
      	Integer currentTime = DateUtil.timeStamp();
      	String contractNumber = Convert.toStr(request.getParameter("contractNumber"));
      	if (ToolUtil.isEmpty(contractNumber)) {
      		contractNumber = IdGenerator.getId();     	
      	}
      	
      	VipUser vipUser = null;
     	if (!vipId.equals(0)) {
     		vipUser = ConstantFactory.me().getVipInfoById(vipId);
     	} else {
     		return new ReturnTip(500, "会员ID跟踪失败，请从会员管理模块进行添加");
     	}
     	
      	Map<String, Object> mapEntity = new HashMap<>();
      	mapEntity.put("clubId", clubId);
      	mapEntity.put("id", _id);
      	mapEntity.put("vipId",  vipId);
      	if (vipUser != null) {
     		mapEntity.put("realname", vipUser.getRealname());
     		mapEntity.put("phone", vipUser.getPhone());
     		mapEntity.put("cardNumber", vipUser.getCardNumber());
     	}
      	mapEntity.put("title", Convert.toStr(request.getParameter("title")));
      	mapEntity.put("counts", Convert.toInt(request.getParameter("counts")));
      	mapEntity.put("leftCounts", Convert.toInt(request.getParameter("counts")));
      	mapEntity.put("unitPrice", Convert.toBigDecimal(request.getParameter("unitPrice")));
      	
      	mapEntity.put("frontMoneyStatus", Convert.toInt(request.getParameter("frontConsumeSwitchStatus")));
      	mapEntity.put("actualMoney", Convert.toBigDecimal(request.getParameter("actualMoney")));
      	mapEntity.put("retainage", Convert.toBigDecimal(request.getParameter("retainage")));
      	
      	mapEntity.put("openCardTime", currentTime);
      	mapEntity.put("expireTime", DateUtil.date2TimeStamp(Convert.toStr(request.getParameter("expireTime")), "yyyy-MM-dd"));
      	
      	mapEntity.put("remark", Convert.toStr(request.getParameter("remark")));
      	mapEntity.put("contractNumber", contractNumber);
      	mapEntity.put("contractId", Convert.toInt(request.getParameter("contractId")));
      	mapEntity.put("isUnitedCard", Convert.toInt(request.getParameter("isUnitedCard"), 0));
      	mapEntity.put("payMethod", Convert.toStr(request.getParameter("payMethod")));
      	mapEntity.put("actualInsertTime", DateUtil.date2TimeStamp(Convert.toStr(request.getParameter("cardInsertTime")), null));
      	
      	mapEntity.put("cardTypeId", cardTypeId);
      	mapEntity.put("cardTypeName", cardTypeId.equals(0) ? "" : cardCategoryService.selectById(cardTypeId).getTitle());
      	mapEntity.put("membershipId", membershipId);
      	mapEntity.put("membershipName", membershipId.equals(0) ? "" : ConstantFactory.me().getStaffSpecialNameById(membershipId));
      	mapEntity.put("coachId", coachId);
      	mapEntity.put("coachName", coachId.equals(0) ? "" : ConstantFactory.me().getCoachNameById(coachId));
      	mapEntity.put("sourceId", Convert.toInt(request.getParameter("source")));
      	
      	mapEntity.put("opeUserId", clubAdmin.getId());
     	mapEntity.put("opeUserName", ToolUtil.isEmpty(clubAdmin.getRealname()) ? clubAdmin.getRealname() : clubAdmin.getNickname());
     	
     	try {
     		cardStoredvaluecardService.save(mapEntity);
     		return new ReturnTip(0, "成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new ReturnTip(501, e.getMessage());
		}
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
      	Wrapper<CardStoredvaluecard> ew = new EntityWrapper<>();
      	ew = ew.eq("id", id);
     	ew = ew.eq("club_id", clubId);
     	try {
     		cardStoredvaluecardService.delete(ew);
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
     	
     	//获取内容
     	Integer id = ToolUtil.toInt(request.getParameter("id"));
     	CardStoredvaluecard itemInDb = cardStoredvaluecardService.selectById(id);
     	
     	//验证合同所属俱乐部
     	if (ToolUtil.isEmpty(itemInDb.getClubId()) || !itemInDb.getClubId().equals(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(501, "访问受限"));
     	}
     	
     	Map<String, Object> mapRet = null;
 		try {
 			mapRet = ToolUtil.convertBean(itemInDb);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	new CardStoredvaluecardWarpper(null).warpTheMap(mapRet);
     	return new ReturnTip(0, "成功",  mapRet);
     }
     
     /**
      * 代销次
      */
 	 @RequestMapping(value = "/agentCost")
     @ResponseBody
     public Object agentCost() {
        HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//获取内容
     	Integer id = ToolUtil.toInt(request.getParameter("cardId"));
     	CardStoredvaluecard cardStoredvaluecard = cardStoredvaluecardService.selectById(id);
     	
     	BigDecimal costs = Convert.toBigDecimal(request.getParameter("costs"));
     	
     	Integer coachId = Convert.toInt(request.getParameter("coachId"), 0);
     	String remark = Convert.toStr(request.getParameter("remark"));
     	Integer type = Convert.toInt(request.getParameter("type"), 0);	//代销类型，3前台代销
     	Integer currentTime = DateUtil.timeStamp();
     	
     	Map<String, Object> mapEntity = new HashMap<>();
     	mapEntity.put("clubId", clubId);
     	mapEntity.put("vipId",  cardStoredvaluecard.getVipId());
     	mapEntity.put("realname", cardStoredvaluecard.getRealname());
     	mapEntity.put("title", cardStoredvaluecard.getTitle());
     	mapEntity.put("costs", costs);
     	mapEntity.put("coachId", coachId);
     	mapEntity.put("coachName", coachId.equals(0) ? "" : ConstantFactory.me().getCoachNameById(coachId));
     	mapEntity.put("rcepteionId", clubAdmin.getId());
     	mapEntity.put("rcepteionName", clubAdmin.getRealname());
     	mapEntity.put("receptionRemark", remark);
     	
     	//减少原有卡次数
//     	cardStoredvaluecard.setCounts(cardStoredvaluecard.getCounts() - costs);
     	cardStoredvaluecard.setLeftMoney(cardStoredvaluecard.getLeftMoney().subtract(costs));
     	
     	try {
     		//卡更新
 			cardStoredvaluecardService.updateById(cardStoredvaluecard);
 			//流水添加
 			CardSpendLog cardSpendLog = (CardSpendLog) ToolUtil.convertMap(CardSpendLog.class, mapEntity);
 			cardSpendLog.setLogType(4);	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡
 			cardSpendLog.setPort(2);	//端口 1 充值新卡 2 续费
 			cardSpendLog.setId(null);
 			cardSpendLog.setInsertTime(currentTime);
			cardSpendLogService.insert(cardSpendLog);
			
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	
     	/**
		 * vip会员的时间卡统计字段同步
		 */
//		vipUserService.resetVCardInfo(cardStoredvaluecard.getVipId());
     	return new ReturnTip(0, "成功");
     }
 	 
 	/**
      * 禁用+启用
      */
 	 @RequestMapping(value = "/disable")
     @ResponseBody
     public Object disable() {
        HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//获取内容
     	Integer id = ToolUtil.toInt(request.getParameter("cardId"));
     	Integer disabled = ToolUtil.toInt(request.getParameter("disabled"));
     	String remark = Convert.toStr(request.getParameter("remark"));
     	
     	try {
     		CardStoredvaluecard card = new CardStoredvaluecard();
     		card.setId(id);
     		card.setdisabled(disabled);
     		card.setdisableRemark(remark);
         	
 			cardStoredvaluecardService.updateById(card);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return new ReturnTip(502, "操作失败");
 		}
     	
     	return new ReturnTip(0, "成功");
     }
}
