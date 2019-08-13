package com.stylefeng.guns.modular.wxma.service.impl;

import com.stylefeng.guns.modular.system.model.CardPtraincard;
import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.PtrainBespeakRecordWarpper;
import com.stylefeng.guns.modular.wxma.service.IWxMaCommonService;
import com.stylefeng.guns.rest.common.exception.BizException;
import com.stylefeng.guns.modular.system.model.PtrainBespeakRecord;
import com.stylefeng.guns.modular.mch.card.service.ICardPtraincardService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;
import com.stylefeng.guns.modular.mch.syllabus.service.IPtrainBespeakRecordService;
import com.stylefeng.guns.modular.system.dao.PtrainBespeakRecordMapper;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.common.constant.Const;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信小程序通用方法 服务实现类
 * </p>
 *
 * @author guiyj007123
 * @since 2018-08-18
 */
@Service
public class WxMaCommonServiceImpl implements IWxMaCommonService {
	
	
	@Override
	public String getTemplateId(String type) {
		String templateId = null;
    	switch (type) {
		case "bespeak-start":
			//预约待确认
			templateId = "dczcrLQVD1rGiXQiYw2YtT2c4aGGzQMh0JykNyMbcrQ";
			break;
		case "bespeak-confirm":
			//预约已确认
			templateId = "FXgFJCfDnHr_EOmV32zINuM6sQ5wjWiHmR-L0qlDHwA";
			break;
		case "bespeak-cancel":
			//预约已确认
			templateId = "DqThx8x3Ry1CUi2GbR9znapfCmi9uAecJWFHW8Vqbqs";
			break;	
		case "checkin-success":
			//签到成功
			templateId = "LYwI293VdgsIqp-SHEFJWMZyvPGL81Xqx9htc20f-Uo";
			break;	
		default:
			break;
		}
    	
    	
    	return templateId;
	}
	
	/**
	 * 保存推送码formId
	 * 
	 * @param openid
	 * @param formIds
	 * @return
	 */
	@Override
	public boolean saveFormIds(String openid, JSONArray formIds) {
		ArrayList<Object> listCacheFormIds = new ArrayList<>();
		listCacheFormIds = CacheKit.get("wxMa-formIds", openid);
		if (ToolUtil.isEmpty(listCacheFormIds)) {
			listCacheFormIds = new ArrayList<>();
		}
		
		for (int i = 0; i < formIds.size(); i++) {
			JSONObject _mapFormId = (JSONObject) formIds.get(i);
			if (_mapFormId.get("formId").toString().startsWith("the formId")) {
				continue;
			}
			listCacheFormIds.add(_mapFormId);
		}
		System.out.println(listCacheFormIds);
		CacheKit.put("wxMa-formIds", openid, listCacheFormIds);
		return true;
	}
	

	//1.取出一个可用的用户openId对应的推送码
	@Override
	public String getFormId(String openid){
		ArrayList<Object> listCacheFormIds = new ArrayList<>();
		listCacheFormIds = CacheKit.get("wxMa-formIds", openid);
		if (ToolUtil.isEmpty(listCacheFormIds) || listCacheFormIds.size() <= 0) {
			return "";
		}
		
		Integer currentTime = DateUtil.timeStamp();
		
		for (int i = 0; i < listCacheFormIds.size(); i++) {
			JSONObject _mapFormId = (JSONObject) listCacheFormIds.get(i);
			listCacheFormIds.remove(i);	//移除当前的
			
			if (Convert.toInt(_mapFormId.get("expire")) >= currentTime) {
				CacheKit.put("wxMa-formIds", openid, listCacheFormIds);
				return (String) _mapFormId.get("formId");
			}

		}
	    
		CacheKit.put("wxMa-formIds", openid, listCacheFormIds);
	    return "";
	}

}
