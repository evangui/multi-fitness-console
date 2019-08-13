package com.stylefeng.guns.modular.mch.card.service;

import com.stylefeng.guns.modular.system.model.CardSpendLog;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 私教记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardSpendLogService extends IService<CardSpendLog> {

	Page<CardSpendLog> pageList(Page<CardSpendLog> page, Integer clubId, HashMap<String, Object> condition);

	CardSpendLog getByConfromCode(String conformCode);

	List<Map<String, Object>> getExportList(Integer clubId, HashMap<String, Object> condition);

	XSSFWorkbook exportInfoExcel(List<Map<String, Object>> infoList)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException,
			IntrospectionException, ParseException;

	CardSpendLog finishPtrain(Integer clubId, Integer coachId, Integer userId, String conformCode,
			String remark);

	CardSpendLog agentCostPtrain(int cardId, Integer coachId, int costs, String remark);

	CardSpendLog agentCostOnce(int cardId, Integer coachId, int costs, String receptionRemark);

	Map<String, Object> statRecords(Integer clubId, HashMap<String, Object> condition);

	Integer countLatestFinishPtrain(int clubId, int coachId, int seconds);

}
