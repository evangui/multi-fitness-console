package com.stylefeng.guns.modular.mch.card.service.impl;

import com.stylefeng.guns.modular.system.model.CardRechargeLog;
import com.stylefeng.guns.modular.system.model.CardStoredvaluecard;
import com.stylefeng.guns.modular.system.model.CardTimecard;
import com.stylefeng.guns.modular.system.warpper.CardStoredvaluecardWarpper;
import com.stylefeng.guns.modular.system.dao.CardStoredvaluecardMapper;
import com.stylefeng.guns.core.common.constant.factory.MapItemFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.card.service.ICardRechargeLogService;
import com.stylefeng.guns.modular.mch.card.service.ICardStoredvaluecardService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员储值卡 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@Service
public class CardStoredvaluecardServiceImpl extends ServiceImpl<CardStoredvaluecardMapper, CardStoredvaluecard> implements ICardStoredvaluecardService {
	@Autowired
    private ICardRechargeLogService cardRechargeLogService;
	@Autowired
    private IVipUserService vipUserService;
	
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
	public Page<CardStoredvaluecard> pageList(Page<CardStoredvaluecard> page, Integer clubId, HashMap<String, Object> condition, Integer type) {
    	Wrapper<CardStoredvaluecard> ew = new EntityWrapper<>();
    	
    	if (ToolUtil.isNotEmpty(condition.get("id"))) {
    		ew = ew.eq("id", (Integer) condition.get("id"));
    	}
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition.get("vipId"))) {
    		ew = ew.eq("vip_id", Convert.toInt(condition.get("vipId")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("title"))) {
    		ew = ew.like("title", (String) condition.get("title"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("realname"))) {
    		ew = ew.like("realname", (String) condition.get("realname"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("cardNumber"))) {
    		ew = ew.like("card_number", (String) condition.get("cardNumber"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("membershipName"))) {
    		ew = ew.like("membership_name", (String) condition.get("membershipName"));
    	}
    	
    	//查询范围表头
    	switch (type) {
		case 0:
//			message = "所有会员卡";
			break;
		case 1:
//			message = "正常";
			ew = ew.eq("disabled", 0);
			ew = ew.gt("left_money", 0);
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			break;
		case 2:
//			message = "已失效";	//次数用尽
			ew = ew.le("left_money", 0);
			break;
		case 3:
//			message = "已过期";
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			break;
		case 4:
//			message = "已禁用";
			ew = ew.eq("disabled", 1);
			break;
		case 5:
//			message = "即将过期";
			ew = ew.gt("expire_time", DateUtil.timeStamp());
			ew = ew.lt("expire_time", DateUtil.timeStamp() + 86400*30);
			break;
		default:
			break;
		}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<CardStoredvaluecard>) new CardStoredvaluecardWarpper(result).warp());
	}
	
	
	/**
     * <p>
     * 查询某项具体表头信息
     * </p>
     *
     * @param hdSearchKey   查询范围表头
     * @return List<>
     */
	@Override
	public Map<String, Object> statCountItem(Integer clubId, Integer type) {
    	String message = null;
		
		Wrapper<CardStoredvaluecard> ew = new EntityWrapper<>();
		ew = ew.eq("club_id", clubId);
    	
		//查询范围表头
    	switch (type) {
		case 0:
			message = "所有会员卡";
			break;
		case 1:
			message = "正常";
			ew = ew.eq("disabled", 0);
			ew = ew.gt("left_money", 0);
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			break;
		case 2:
			message = "已失效";	//次数用尽
			ew = ew.le("left_money", 0);
			break;
		case 3:
			message = "已过期";
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			break;
		case 4:
			message = "已禁用";
			ew = ew.eq("disabled", 1);
			break;
		case 5:
			message = "即将过期";
			ew = ew.gt("expire_time", DateUtil.timeStamp());
			ew = ew.lt("expire_time", DateUtil.timeStamp() + 86400*30);
			break;
		default:
			break;
		}
    	
    	Integer cnt = baseMapper.selectCount(ew);
    	
    	return MapItemFactory.getCardHeaderItem(cnt, type, message);
	}
	
	@Override
	public boolean save(Map<String, Object> mapEntity) throws Exception {
		//验证必要信息是否存在
		if (ToolUtil.isEmpty(mapEntity.get("id")) || ToolUtil.isEmpty(mapEntity.get("clubId")) || ToolUtil.isEmpty(mapEntity.get("vipId"))) {
			return false;
		}
		if (mapEntity.get("clubId").equals(0) || mapEntity.get("vipId").equals(0)) {
			return false;
		}
		Integer _id = (Integer) mapEntity.get("id");
		Integer clubId = (Integer) mapEntity.get("clubId");
     	Integer vipId = (Integer) mapEntity.get("vipId");
     	Integer currentTime = DateUtil.timeStamp();
     	
     	CardStoredvaluecard cardStoredvaluecard = (CardStoredvaluecard) ToolUtil.convertMap(CardStoredvaluecard.class, mapEntity);
    	
     	Wrapper<CardStoredvaluecard> ew = new EntityWrapper<>();
     	ew = ew.eq("club_id", clubId);
     	ew = ew.eq("id", _id);
     	
     	//验证信息是否存在
     	try {
 			if (0 == this.selectCount(ew)) {
 				cardStoredvaluecard.setId(null);
 				cardStoredvaluecard.setInsertTime(DateUtil.timeStamp());
 				this.insert(cardStoredvaluecard);
 				
 				try {
 					//充值记录添加
 					CardRechargeLog cardRechargeLog = (CardRechargeLog) ToolUtil.convertMap(CardRechargeLog.class, mapEntity);
 					cardRechargeLog.setLogType(4);	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡
 					cardRechargeLog.setPort(1);	//端口 1 充值新卡 2 续费
 					cardRechargeLog.setId(null);
 					cardRechargeLog.setInsertTime(currentTime);
 					cardRechargeLogService.insert(cardRechargeLog);
 					
 					/**
 					 * vip会员的时间卡统计字段同步
 					 */
 					vipUserService.resetStoredvalueCardInfo(vipId);
 					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
 				
 				return true;
 				
 				
 			} else {
 				cardStoredvaluecard.setId(_id);
 				this.update(cardStoredvaluecard, ew);
 			}
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return false;
 		}
     	
     	return true;
	}
}
