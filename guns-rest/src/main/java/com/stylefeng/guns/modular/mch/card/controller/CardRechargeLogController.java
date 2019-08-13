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

import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.CardRechargeLog;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.CardRechargeLogWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardCategoryService;
import com.stylefeng.guns.modular.mch.card.service.ICardRechargeLogService;

/**
 * 时间卡充值记录控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller
@RequestMapping("/mch/card/rechargeLog")
public class CardRechargeLogController extends BaseController {

    @Autowired
    private ICardRechargeLogService cardRechargeLogService;
    @Autowired
    private ICardCategoryService cardCategoryService;

    /**
	 * 获取VIP用户查询统计表头
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

		//综合查询条件
     	HashMap<String, Object> mapCondition = this.getMapCondition(request);
		Map<String, Object> mapRet = cardRechargeLogService.statRecords(clubId, mapCondition);

		return new ReturnTip(0, "成功", mapRet);
	}
	
     /**
      * 获取分页列表
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
     	HashMap<String, Object> mapCondition = this.getMapCondition(request);
     	
     	Page<CardRechargeLog> page = new PageFactory<CardRechargeLog>().defaultPage("id", "desc");
     	page = cardRechargeLogService.pageList(page, clubId, mapCondition);
     	Map<String, Object> ret = super.packForPannelTable(page);
     	
     	ret.put("isLoad", true);
     	
        return new ReturnTip(0, "成功", ret);
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
      	mapCondition.put("vipId", Convert.toStr(request.getParameter("vipId")));
      	mapCondition.put("rechargeDateStart", Convert.toStr(request.getParameter("rechargeDateStart")));
      	mapCondition.put("rechargeDateStart", Convert.toStr(request.getParameter("rechargeDateStart")));
      	mapCondition.put("minExpires", Convert.toStr(request.getParameter("minExpires")));
      	mapCondition.put("maxExpires", Convert.toStr(request.getParameter("maxExpires")));
      	mapCondition.put("banksure", Convert.toStr(request.getParameter("banksure")));
      	mapCondition.put("cardStatus", Convert.toStr(request.getParameter("cardStatus")));
      	mapCondition.put("remark", Convert.toStr(request.getParameter("remark")));
      	mapCondition.put("contractNumber", Convert.toStr(request.getParameter("contractNumber")));
      	mapCondition.put("realname", Convert.toStr(request.getParameter("realname")));
      	mapCondition.put("phone", Convert.toStr(request.getParameter("phone")));
      	mapCondition.put("port", Convert.toStr(request.getParameter("port")));
      	mapCondition.put("sourceId", Convert.toStr(request.getParameter("source")));
      	mapCondition.put("title", Convert.toStr(request.getParameter("titleName")));
      	mapCondition.put("membershipName", Convert.toStr(request.getParameter("membershipName")));
      	mapCondition.put("opeUserName", Convert.toStr(request.getParameter("opeUserName")));
      	
      	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡',
     	switch (Convert.toStr(request.getParameter("logType"), "")) {
     	case "timeCardRecharge":
			mapCondition.put("logType", 1);
			break;
		case "personalCardRecharge":
			mapCondition.put("logType", 2);
			break;
		case "onceCardRecharge":
			mapCondition.put("logType", 3);
			break;
		case "storedCardRecharge":
			mapCondition.put("logType", 4);
			break;
		default:
			break;
		}
      	
      	return mapCondition;
	}
     
     /**
  	 * excel导出数据
  	 * @throws UnsupportedEncodingException 
  	 */
  	@RequestMapping(value = "/down2Excel")
  	@ResponseBody
  	public void down2Excel(HttpServletResponse response,
  			HttpSession session) throws UnsupportedEncodingException {
  		HttpServletRequest request = this.getHttpServletRequest();
  		ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
  		if (ToolUtil.isEmpty(clubAdmin)) {
  			return ;
  		}
  		Integer clubId = clubAdmin.getClubId();
  		String clubName = ConstantFactory.me().getClubNameById(clubId);
  		
  		//获取下载数据
  	//综合查询条件
     	HashMap<String, Object> mapCondition = this.getMapCondition(request);

  		List<Map<String, Object>> infoList = cardRechargeLogService.getExportList(clubId, mapCondition);
  		
  		response.reset();
  		String dateStr = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
  		// 指定下载的文件名
  		String fileName = clubName + "-充值记录" + dateStr + ".xlsx";
  		response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
//  		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//  		response.setContentType("Application/Octet-stream;charset=utf-8");
  		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
  		response.setHeader("Pragma", "no-cache");
  		response.setHeader("Cache-Control", "no-cache");
  		response.setDateHeader("Expires", 0);
  		
  		response.setHeader("Connection", "keep-alive");
  		response.setHeader("Access-Control-Allow-Origin", "*");
	      response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	      response.setHeader("Access-Control-Max-Age", "3600");
	      response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
	      response.setHeader("Access-Control-Allow-Credentials","true");

  		XSSFWorkbook workbook = null;
  		try {
  			// 导出Excel对象
  			workbook = cardRechargeLogService.exportInfoExcel(infoList);
  		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException
  				| IntrospectionException | ParseException e1) {
  			e1.printStackTrace();
  		}
  		OutputStream output;
  		try {
  			output = response.getOutputStream();

  			BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
  			bufferedOutPut.flush();
  			workbook.write(bufferedOutPut);
  			bufferedOutPut.close();

  		} catch (IOException e) {
  			e.printStackTrace();
  		}
  	}
     
     /**
      * 新增+更新
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
     	
     	CardRechargeLog cardRechargeLog = (CardRechargeLog) ToolUtil.convertMap(CardRechargeLog.class, mapEntity);
    	
     	Wrapper<CardRechargeLog> ew = new EntityWrapper<>();
     	ew = ew.eq("club_id", clubId);
     	ew = ew.eq("id", _id);
     	
     	//验证信息是否存在
     	try {
 			if (0 == cardRechargeLogService.selectCount(ew)) {
 				cardRechargeLog.setId(null);
 				cardRechargeLog.setInsertTime(DateUtil.timeStamp());
 				cardRechargeLogService.insert(cardRechargeLog);
 			} else {
 				cardRechargeLog.setId(_id);
 				cardRechargeLogService.update(cardRechargeLog, ew);
 			}
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	return new ReturnTip(0, "成功");
     }

     /**
      * 删除充值记录
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
      	
      	String password = Convert.toStr(request.getParameter("password"));
    	String salt = clubAdmin.getSalt();
    	
    	//验证原密码是否正确
    	if (!MD5Util.encBySalt(password, salt).equals(clubAdmin.getPassword())) {
    		return new ReturnTip(501, "原密码不正确");
		}
    	
//    	recordId
    	
    	String type = ToolUtil.toStr(request.getParameter("type"));
    	String recordIds = ToolUtil.toStr(request.getParameter("recordId"));
//      	Integer id = ToolUtil.toInt(request.getParameter("id"));
      	
      	Wrapper<CardRechargeLog> ew = new EntityWrapper<>();
      	ew = ew.in("id", recordIds);
     	ew = ew.eq("club_id", clubId);
     	
//     	卡的类型 1 时间卡 2私教卡 3次卡 4储值卡', PRIMARY KEY (`id`),
     	switch (type) {
		case "timeCardRecharge":
			ew = ew.eq("log_type", 1);
			break;
		case "personalCardRecharge":
			ew = ew.eq("log_type", 2);
			break;
		case "onceCardRecharge":
			ew = ew.eq("log_type", 3);
			break;
		case "valueCardRecharge":
			ew = ew.eq("log_type", 4);
			break;
		default:
			break;
		}
     	
     	try {
     		cardRechargeLogService.delete(ew);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return ResponseEntity.ok(new ReturnTip(501, "删除失败"));
 		}
     	
     	return new ReturnTip(0, "操作成功");
     }

     /**
      * 充值记录详情
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
     	CardRechargeLog itemInDb = cardRechargeLogService.selectById(id);
     	
     	//验证合同所属俱乐部
     	if (!itemInDb.getClubId().equals(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(501, "合同访问受限"));
     	}
     	
     	Map<String, Object> mapRet = null;
 		try {
 			mapRet = ToolUtil.convertBean(itemInDb);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	new CardRechargeLogWarpper(null).warpTheMap(mapRet);
     	
     	return new ReturnTip(0, "成功",  mapRet);
     }
     
}
