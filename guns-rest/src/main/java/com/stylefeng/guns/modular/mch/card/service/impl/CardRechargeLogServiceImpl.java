package com.stylefeng.guns.modular.mch.card.service.impl;

import com.stylefeng.guns.modular.system.model.CardRechargeLog;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.CardRechargeLogWarpper;
import com.stylefeng.guns.modular.system.dao.CardRechargeLogMapper;
import com.stylefeng.guns.core.support.ExcelBean;
import com.stylefeng.guns.core.support.ExcelUtils;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.card.service.ICardRechargeLogService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 私教卡充值记录 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@Service
public class CardRechargeLogServiceImpl extends ServiceImpl<CardRechargeLogMapper, CardRechargeLog> implements ICardRechargeLogService {
	/**
     * <p>
     * 分页列表查询
     * </p>
     *
     * @param page 分页查询条件
     * @param clubId   俱乐部id
     * @param condition   其他模糊查询条件
     * @param hdSearchKey   查询范围表头
     * @return List<>
     */
	@SuppressWarnings("unchecked")
	@Override
	public Page<CardRechargeLog> pageList(Page<CardRechargeLog> page, Integer clubId, HashMap<String, Object> condition) {
		Wrapper<CardRechargeLog> ew = getSearchEntityWrapper(clubId, condition);
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<CardRechargeLog>) new CardRechargeLogWarpper(result).warp());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> statRecords(Integer clubId, HashMap<String, Object> condition) {
		Wrapper<CardRechargeLog> ew = getSearchEntityWrapper(clubId, condition);
		int logType = Convert.toInt(condition.get("logType"), 0);
		//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡',
		if (logType == 1) {
			ew.setSqlSelect("count(1) AS totalCnt, sum(actual_money) AS sumMoney, sum(expire_days) AS totalCourse");
		} else if (logType == 4) {
			ew.setSqlSelect("count(1) AS totalCnt, sum(actual_money) AS sumMoney, sum(sum_money) AS totalCourse");
		} else {
			ew.setSqlSelect("count(1) AS totalCnt, sum(actual_money) AS sumMoney, sum(counts) AS totalCourse");
		}
		
		
    	Map<String, Object> mapRes = this.selectMap(ew);
    	
    	Map<String, Object> mapRet = new HashMap<>();
    	if (mapRes == null) {
    		mapRet.put("sumMoney", 0);
        	mapRet.put("totalCnt", 0);
        	mapRet.put("totalCourse", 0);
        	return mapRet;
    	}
    	
    	mapRet.put("sumMoney", mapRes.get("sumMoney"));
    	mapRet.put("totalCnt", mapRes.get("totalCnt"));
    	mapRet.put("totalCourse", mapRes.get("totalCourse"));
    	
    	return mapRet;
	}
	
	
	/**
	 * <p>
	 * 导出功能列表查询
	 * </p>
	 *
	 * @param clubId
	 *            俱乐部id
	 * @param condition
	 *            其他模糊查询条件
	 * @param hdSearchKey
	 *            查询范围表头
	 * @return 
	 * @return List<>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  List<Map<String, Object>> getExportList(Integer clubId, HashMap<String, Object> condition) {
		Wrapper<CardRechargeLog> ew = getSearchEntityWrapper(clubId, condition);

		//最多导出1000条数据
		Page<VipUser> page = new Page<>(0, 1000, "id",true);
		List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
		return (List<Map<String, Object>>) new CardRechargeLogWarpper(result).warp();
	}
	
	@Override
	public XSSFWorkbook exportInfoExcel(List<Map<String, Object>> infoList) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException {
		//debug
//		Wrapper<VipUser> ew = new EntityWrapper<>();
//		ew = ew.eq("club_id", clubId);
//		List<Map<String, Object>> infoList = this.getExportList(clubId, new HashMap<String, Object>(), "");

		List<ExcelBean> ems = new ArrayList<>();
		Map<Integer, List<ExcelBean>> map = new LinkedHashMap<>();
		
		XSSFWorkbook book = null;
		ems.add(new ExcelBean("充值时间", "insertTimeStr", 0));
		ems.add(new ExcelBean("合同号", "contractNumber", 0));
		ems.add(new ExcelBean("会员姓名", "realname", 0));
		ems.add(new ExcelBean("手机号", "phone", 0));//201807118660
		ems.add(new ExcelBean("充值额度", "counts", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("开卡时间", "startUseTime", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("到期时间", "expireTimeStr", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("会籍", "membershipName", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("所属教练", "coachName", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("操作人", "opeUserName", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("充值备注", "remark", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("财务确认", "bankStation", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("备注", "remarkby", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("会员卡号", "cardNumber", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("实收金额", "actualMoney", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("支付方式", "payMethod", 0));//2018-07-11 10:18
		ems.add(new ExcelBean("卡名称", "title", 0));//2018-07-11 10:18
	
		map.put(0, ems);
		// List<VipUser> afterChangeList = changeBuyerStatus(infoList);
		book = ExcelUtils.createExcelFile(VipUser.class, infoList, map, "充值记录");
		return book;
	}
	
	private Wrapper<CardRechargeLog> getSearchEntityWrapper(Integer clubId, HashMap<String, Object> condition) {
		Wrapper<CardRechargeLog> ew = new EntityWrapper<>();
    	
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition.get("id")) && !condition.get("id").equals("0")) {
    		ew = ew.eq("id", (Integer) condition.get("id"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("logType"))) {
    		ew = ew.eq("log_type", Convert.toInt(condition.get("logType")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("vipId"))) {
    		ew = ew.eq("vip_id", Convert.toInt(condition.get("vipId")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("banksure")) && !condition.get("banksure").equals("2")) {
    		ew = ew.eq("banksure", Convert.toInt(condition.get("banksure")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("port")) && !condition.get("port").equals("0")) {
    		ew = ew.eq("port", Convert.toInt(condition.get("port")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("sourceId")) && !condition.get("sourceId").equals("0") ) {
    		ew = ew.eq("source_id", Convert.toInt(condition.get("sourceId")));
    	}
    	
    	if (ToolUtil.isNotEmpty(condition.get("title"))) {
    		ew = ew.like("title", (String) condition.get("title"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("remark"))) {
    		ew = ew.like("remark", (String) condition.get("remark"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("contractNumber"))) {
    		ew = ew.like("contract_number", (String) condition.get("contractNumber"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("cardNumber"))) {
//    		ew = ew.like("card_number", (String) condition.get("cardNumber"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("realname"))) {
    		ew = ew.like("realname", (String) condition.get("realname"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("phone"))) {
    		ew = ew.like("phone", (String) condition.get("phone"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("membershipName"))) {
    		ew = ew.like("membership_name", (String) condition.get("membershipName"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("opeUserName"))) {
    		ew = ew.like("ope_user_name", (String) condition.get("opeUserName"));
    	}
    	
    	if (ToolUtil.isNotEmpty(condition.get("minExpires"))) {
    		ew = ew.ge("actual_money",  condition.get("minExpires"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("maxExpires"))) {
    		ew = ew.ge("actual_money",  condition.get("maxExpires"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("rechargeDateStart"))) {
    		ew = ew.ge("insert_time", DateUtil.date2TimeStamp((String) condition.get("rechargeDateStart"), "yyyy-MM-dd"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("rechargeDateEnd"))) {
    		ew = ew.le("insert_time", 86400 + DateUtil.date2TimeStamp((String) condition.get("rechargeDateEnd"), "yyyy-MM-dd"));
    	}
    	
    	return ew;
	}
}
