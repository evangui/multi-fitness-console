package com.stylefeng.guns.modular.mch.card.service;

import com.stylefeng.guns.modular.system.model.CardRechargeLog;

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
 * 私教卡充值记录 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface ICardRechargeLogService extends IService<CardRechargeLog> {

	Page<CardRechargeLog> pageList(Page<CardRechargeLog> page, Integer clubId,
			HashMap<String, Object> condition);

	List<Map<String, Object>> getExportList(Integer clubId, HashMap<String, Object> condition);

	XSSFWorkbook exportInfoExcel(List<Map<String, Object>> infoList)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException,
			IntrospectionException, ParseException;

	Map<String, Object> statRecords(Integer clubId, HashMap<String, Object> condition);

}
