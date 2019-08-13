package com.stylefeng.guns.modular.mch.card.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.CardPtraincard;
import com.stylefeng.guns.modular.system.model.CardRechargeLog;
import com.stylefeng.guns.modular.system.model.CardSpendLog;
import com.stylefeng.guns.modular.system.model.CardStoredvaluecard;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.CardPtraincardWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardCategoryService;
import com.stylefeng.guns.modular.mch.card.service.ICardPtraincardService;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;
import com.stylefeng.guns.modular.mch.card.service.ICardRechargeLogService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;

/**
 * 会员私教卡控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller
@RequestMapping("/mch/card/ptraincard")
public class CardPtraincardController extends BaseController {

	@Autowired
    private ICardPtraincardService cardPtraincardService;
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
     	listRet.add(cardPtraincardService.statCountItem(clubId, 0));
     	listRet.add(cardPtraincardService.statCountItem(clubId, 1));
     	listRet.add(cardPtraincardService.statCountItem(clubId, 2));
     	listRet.add(cardPtraincardService.statCountItem(clubId, 5));
     	listRet.add(cardPtraincardService.statCountItem(clubId, 3));
     	listRet.add(cardPtraincardService.statCountItem(clubId, 4));
     	
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
     	
     	Page<CardPtraincard> page = new PageFactory<CardPtraincard>().defaultPage("id", "desc");
     	page = cardPtraincardService.pageList(page, clubId, mapCondition, cardStatus);
         
     	Map<String, Object> ret = super.packForPannelTable(page);
     	
        return new ReturnTip(0, "成功", ret);
     }
     
     /**
      * 新增+更新
      */
     @RequestMapping(value = "/save")
     @ResponseBody
     public Object save() {
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
     		contractNumber = IdGenerator.getTimeId();     	
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
     		boolean res = cardPtraincardService.save(mapEntity);
     		if (res) {
     			return new ReturnTip(0, "成功");
     		} else {
     			return new ReturnTip(502, "失败，请返回稍后再试");
     		}
     		
		} catch (Exception e) {
			// TODO: handle exception
			return new ReturnTip(501, e.getMessage());
		}
     	
     }

     

     /**
      * 删除
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
      	Wrapper<CardPtraincard> ew = new EntityWrapper<>();
      	ew = ew.eq("id", id);
     	ew = ew.eq("club_id", clubId);
     	try {
     		cardPtraincardService.delete(ew);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return ResponseEntity.ok(new ReturnTip(501, "删除失败"));
 		}
     	return new ReturnTip(0, "成功");
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
     	CardPtraincard itemInDb = cardPtraincardService.selectById(id);
     	
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
     	new CardPtraincardWarpper(null).warpTheMap(mapRet);
     	
     	return new ReturnTip(0, "成功",  mapRet);
     }
     
     /**
      * 续费
      */
 	 @RequestMapping(value = "/renew")
     @ResponseBody
     public Object renew() {
        HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//续卡 or 启用 //是否续卡(1续卡 2启用 0新卡及其他)
     	Integer opType = Convert.toInt(request.getParameter("opType"), 1);
     	
     	//获取内容
     	Integer id = Convert.toInt(request.getParameter("cardId"));
     	CardPtraincard cardPtraincard = cardPtraincardService.selectById(id);
     	
     	Integer disabled = Convert.toInt(request.getParameter("disabled"));
     	Integer vipId = cardPtraincard.getVipId();
     	Integer coachId = Convert.toInt(request.getParameter("coachId"), 0);
     	Integer counts = Convert.toInt(request.getParameter("counts"), 0);
     	String title = Convert.toStr(request.getParameter("title"), "");
     	String expireTime = Convert.toStr(request.getParameter("expireTime"));
     	Integer currentTime = DateUtil.timeStamp();
     	String contractNumber = Convert.toStr(request.getParameter("contractNumber"));
     	if (ToolUtil.isEmpty(contractNumber)) {
     		contractNumber = IdGenerator.getId();     	
     	}
     	
     	Map<String, Object> mapEntity = new HashMap<>();
     	mapEntity.put("clubId", clubId);
     	mapEntity.put("vipId",  vipId);
     	mapEntity.put("realname", vipId.equals(0) ? "" : ConstantFactory.me().getVipRealnameById(vipId));
     	mapEntity.put("title", title.equals("") ? cardPtraincard.getTitle() : title);
     	mapEntity.put("counts", counts);
     	mapEntity.put("leftCounts", counts);
     	mapEntity.put("unitPrice", Convert.toBigDecimal(request.getParameter("unitPrice")));
     	mapEntity.put("actualMoney", Convert.toBigDecimal(request.getParameter("money")));
     	if (!expireTime.equals("")) {
     		mapEntity.put("expireTime", DateUtil.date2TimeStamp(expireTime, "yyyy-MM-dd"));
     	}
     	mapEntity.put("remark", Convert.toStr(request.getParameter("remark")));
     	mapEntity.put("contractNumber", contractNumber);
     	mapEntity.put("contractId", Convert.toInt(request.getParameter("contractId")));
     	mapEntity.put("isUnitedCard", Convert.toInt(request.getParameter("isUnitedCard"), 0));
     	mapEntity.put("payMethod", Convert.toStr(request.getParameter("payMethod")));
     	mapEntity.put("actualInsertTime", currentTime);
     	mapEntity.put("cardTypeId", cardPtraincard.getCardTypeId());
     	mapEntity.put("cardTypeName", cardPtraincard.getCardTypeName());
     	mapEntity.put("coachId", coachId);
     	mapEntity.put("coachName", coachId.equals(0) ? "" : ConstantFactory.me().getCoachNameById(coachId));
     	mapEntity.put("sourceId", Convert.toInt(request.getParameter("source")));
     	mapEntity.put("opeUserId", clubAdmin.getId());
     	mapEntity.put("opeUsername", clubAdmin.getRealname());
     	
     	//增加原有卡次数
     	cardPtraincard.setExpireTime((Integer) mapEntity.get("expireTime"));
     	if (ToolUtil.isNotEmpty(disabled)) {
     		cardPtraincard.setdisabled(disabled);
     	}
     	if (!counts.equals(0)) {
     		cardPtraincard.setCounts(cardPtraincard.getCounts() + counts);
     		cardPtraincard.setLeftCounts(cardPtraincard.getLeftCounts() + counts);
     	}
     	
     	try {
     		//卡更新
 			cardPtraincardService.updateById(cardPtraincard);
 			//重置记录流水添加
 			CardRechargeLog cardRechargeLog = (CardRechargeLog) ToolUtil.convertMap(CardRechargeLog.class, mapEntity);
 			cardRechargeLog.setLogType(2);	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡
			cardRechargeLog.setPort(2);	//端口 1 新卡 2 启用&续费 3在线售卡 4 数据导入 5转卡 6延卡 7补余款'
			cardRechargeLog.setIsRenewCard(opType);	//是否续卡(1续卡 2启用 0新卡及其他 3 延期)
			cardRechargeLog.setId(null);
			cardRechargeLog.setInsertTime(currentTime);
			cardRechargeLogService.insert(cardRechargeLog);
			
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	
     	/**
		 * vip会员的时间卡统计字段同步
		 */
		vipUserService.resetPtrainCardInfo(cardPtraincard.getVipId());
     	return new ReturnTip(0, "成功");
     }
 	 
 	 /**
      * 消次
      */
 	 @RequestMapping(value = "/personalTrainerCardDeduct")
     @ResponseBody
     public Object personalTrainerCardDeduct() {
        HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//获取内容
     	Integer cardId = ToolUtil.toInt(request.getParameter("cardId"));
     	Integer costs = Convert.toInt(request.getParameter("costs"), 0);
     	Integer coachId = Convert.toInt(request.getParameter("coachId"), 0);
     	String remark = Convert.toStr(request.getParameter("remark"));
     	Integer type = Convert.toInt(request.getParameter("type"), 0);	//代销类型，3前台代销
     	
     	try {
     		cardSpendLogService.agentCostPtrain(cardId, coachId, costs, remark);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return new ReturnTip(501, e.getMessage());
 		}
     	
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
     		CardPtraincard card = new CardPtraincard();
     		card.setId(id);
     		card.setdisabled(disabled);
     		card.setDisableRemark(remark);
         	
 			cardPtraincardService.updateById(card);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return new ReturnTip(502, "操作失败");
 		}
     	
     	return new ReturnTip(0, "成功");
     }
 	 
}
