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
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.CardSpendLog;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.warpper.CardSpendLogWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;

/**
 * 私教记录控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller
@RequestMapping("/mch/card/spendLog")
public class CardSpendLogController extends BaseController {

    @Autowired
    private ICardSpendLogService ptrainRecordService;
    @Autowired
    private IVipUserService vipUserService;
    
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
		Map<String, Object> mapRet = ptrainRecordService.statRecords(clubId, mapCondition);

		return new ReturnTip(0, "成功", mapRet);
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
    	mapCondition.put("coachId", Convert.toStr(request.getParameter("coachId"), null));
    	mapCondition.put("coachName", Convert.toStr(request.getParameter("coachName"), null));
    	mapCondition.put("membershipName", Convert.toStr(request.getParameter("membershipName"), null));
    	mapCondition.put("receptionistName", Convert.toStr(request.getParameter("receptionistName"), null));
    	mapCondition.put("userPhone", Convert.toStr(request.getParameter("userPhone"), null));
    	mapCondition.put("userRealname", Convert.toStr(request.getParameter("userRealname"), null));
    	mapCondition.put("cardStatus", Convert.toStr(request.getParameter("cardStatus"), null));
    	mapCondition.put("isSyllabusSub", Convert.toStr(request.getParameter("isSyllabusSub"), null));
    	mapCondition.put("port", Convert.toStr(request.getParameter("port"), null));
    	mapCondition.put("onceCardTitle", Convert.toStr(request.getParameter("onceCardTitle"), null));
    	
    	mapCondition.put("startInsertTime", Convert.toStr(request.getParameter("startInsertTime"), null));
    	mapCondition.put("endInsertTime", Convert.toStr(request.getParameter("endInsertTime"), null));
     	
     	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡',
    	String logType = Convert.toStr(request.getParameter("logType"), "");
    	switch (logType) {
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
     	HashMap<String, Object> mapCondition = this.getMapCondition(request);
     	
    	Page<CardSpendLog> page = new PageFactory<CardSpendLog>().defaultPage("id", "desc");
    	page = ptrainRecordService.pageList(page, clubId, mapCondition);
        
    	Map<String, Object> ret = super.packForPannelTable(page);
    	ret.put("isLoad", true);
        return new ReturnTip(0, "成功", ret);
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
    	mapEntity.put("remark", Convert.toStr(mapParams.get("remark")[0]));
    	
    	CardSpendLog ptrainRecord = (CardSpendLog) ToolUtil.convertMap(CardSpendLog.class, mapEntity);
     
    	Wrapper<CardSpendLog> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("id", _id);
    	
    	//验证信息是否存在
    	try {
			if (0 == ptrainRecordService.selectCount(ew)) {
				ptrainRecord.setId(null);
				ptrainRecord.setInsertTime(DateUtil.timeStamp());
				ptrainRecordService.insert(ptrainRecord);
			} else {
				ptrainRecord.setId(_id);
				ptrainRecordService.update(ptrainRecord, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    	
    	CardSpendLog itemInDb = null;
    	try {
    		//获取内容
        	Integer id = ToolUtil.toInt(request.getParameter("id"));
        	itemInDb = ptrainRecordService.selectById(id);
        	
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
    	new CardSpendLogWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
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
  		
  	//综合查询条件
     	HashMap<String, Object> mapCondition = this.getMapCondition(request);
  		List<Map<String, Object>> infoList = ptrainRecordService.getExportList(clubId, mapCondition);
  		
  		response.reset();
  		String dateStr = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
  		// 指定下载的文件名
  		String fileName = clubName + "-卡消费记录" + dateStr + ".xlsx";
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
  			workbook = ptrainRecordService.exportInfoExcel(infoList);
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
    
    
}
