package com.stylefeng.guns.modular.mch.card.service.impl;

import com.stylefeng.guns.modular.system.model.CardRechargeLog;
import com.stylefeng.guns.modular.system.model.CardTimecard;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.CardTimecardWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.system.dao.CardTimecardMapper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.MapItemFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.card.service.ICardRechargeLogService;
import com.stylefeng.guns.modular.mch.card.service.ICardTimecardService;
import com.stylefeng.guns.modular.mch.member.service.IVipUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员时间卡 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@Service
public class CardTimecardServiceImpl extends ServiceImpl<CardTimecardMapper, CardTimecard> implements ICardTimecardService {
	@Autowired
    private ICardRechargeLogService cardRechargeLogService;
	@Autowired
    private IVipUserService vipUserService;
	
	@Override
	public CardTimecard getLongestCard(Integer vipId) {
		/**
		 * 找到会员所有时间卡中最长期限的一张卡
		 */
		Wrapper<CardTimecard> ew = new EntityWrapper<>();
    	ew = ew.eq("vip_id", vipId);
		
    	Page<CardTimecard> page = new Page<CardTimecard>(1, 1, "expire_time", false);
    	List<CardTimecard> listCard = baseMapper.selectPage(page, ew);
    	
    	if (ToolUtil.isEmpty(listCard)) {
    		return null;
    	}
		return listCard.get(0);
	}
	
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
	public Page<CardTimecard> pageList(Page<CardTimecard> page, Integer clubId, HashMap<String, Object> condition, Integer type) {
    	Wrapper<CardTimecard> ew = new EntityWrapper<>();
    	
    	if (ToolUtil.isNotEmpty(condition.get("id"))) {
    		ew = ew.eq("id", (Integer) condition.get("id"));
    	}
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition.get("vipId")) && !condition.get("vipId").equals(0)) {
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
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			break;
		case 2:
//			message = "请假中";
			ew = ew.gt("suspend_end_time", DateUtil.timeStamp());
			ew = ew.lt("suspend_start_time", DateUtil.timeStamp());
			break;
		case 3:
//			message = "已过期";
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			break;
		case 4:
//			message = "未开卡";
			ew = ew.eq("open_card_time", 0);
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
        return page.setRecords( (List<CardTimecard>) new CardTimecardWarpper(result).warp());
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
		
		Wrapper<CardTimecard> ew = new EntityWrapper<>();
		ew = ew.eq("club_id", clubId);
    	
    	//查询范围表头
    	switch (type) {
		case 0:
			message = "所有会员卡";
			break;
		case 1:
			message = "正常";
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			break;
		case 2:
			message = "请假中";
			ew = ew.gt("suspend_end_time", DateUtil.timeStamp());
			ew = ew.lt("suspend_start_time", DateUtil.timeStamp());
			break;
		case 3:
			message = "已过期";
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			break;
		case 4:
			message = "未开卡";
			ew = ew.eq("open_card_time", 0);
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
		if (ToolUtil.isEmpty(mapEntity.get("id"))) {
			mapEntity.put("id", 0);
		}
		if (ToolUtil.isEmpty(mapEntity.get("clubId")) || ToolUtil.isEmpty(mapEntity.get("vipId"))) {
			return false;
		}
		if (mapEntity.get("clubId").equals(0) || mapEntity.get("vipId").equals(0)) {
			return false;
		}
		Integer _id = (Integer) mapEntity.get("id");
		Integer clubId = (Integer) mapEntity.get("clubId");
     	Integer vipId = (Integer) mapEntity.get("vipId");
     	Integer currentTime = DateUtil.timeStamp();
     	
		CardTimecard cardTimecard = (CardTimecard) ToolUtil.convertMap(CardTimecard.class, mapEntity);
    	
     	Wrapper<CardTimecard> ew = new EntityWrapper<>();
     	ew = ew.eq("club_id", clubId);
     	ew = ew.eq("id", _id);
     	
     	//验证信息是否存在
     	try {
 			if (0 == this.selectCount(ew)) {
 				cardTimecard.setId(null);
 				cardTimecard.setInsertTime(currentTime);
 				
 				if (cardTimecard.getAutoOpenCardSwitch() !=null && cardTimecard.getAutoOpenCardSwitch().equals(1) ) {
 					if (cardTimecard.getExpireTime().equals(0)) {
 						//到期时间 必填
 						throw new Exception("到期时间不可为空");
 					} else if (cardTimecard.getOpenCardTime().equals(0)) {
 						//自动开卡时间如果不输入，则为今天开卡
 						cardTimecard.setOpenCardTime(currentTime);
 						cardTimecard.setOpenCardTime(currentTime);
 					}
 				} else {
 					if (cardTimecard.getExpireDays()!=null && cardTimecard.getExpireDays().equals(0)) {
 						//到期时间 必填
 						throw new Exception("充值天数不可为空");
 					}
 				}
 				
 				try {
 					//充值记录添加
 					this.insert(cardTimecard);
 					CardRechargeLog cardRechargeLog = (CardRechargeLog) ToolUtil.convertMap(CardRechargeLog.class, mapEntity);
 					cardRechargeLog.setLogType(1);	//针对卡的类型 1 时间卡 2私教卡 3次卡 4储值卡
 					cardRechargeLog.setPort(1);	//端口 1 充值新卡 2 续费
 					cardRechargeLog.setId(null);
 					cardRechargeLog.setInsertTime(currentTime);
 					cardRechargeLogService.insert(cardRechargeLog);
 					
 					/**
 					 * vip会员的时间卡统计字段同步
 					 */
 					vipUserService.resetTimeCardInfo(vipId);
 					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
 				
 				return true;
 			} else {
 				cardTimecard.setId(_id);
 				this.update(cardTimecard, ew);
 				return true;
 			}
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return false;
 		}
	}
	
	/**
	 * vip用户自动领取系统试用时间卡
	 * 
	 * @param vipUser
	 * @return
	 */
	@Override
	public boolean receiveTrialCard(VipUser vipUser) {
     	Integer vipId = vipUser.getId();
     	Integer currentTime = DateUtil.timeStamp();
     	
     	Map<String, Object> mapEntity = new HashMap<>();
     	mapEntity.put("clubId", vipUser.getClubId());
     	mapEntity.put("vipId",  vipId);
     	
     	mapEntity.put("realname", vipUser.getRealname());
 		mapEntity.put("phone", vipUser.getPhone());
 		mapEntity.put("cardNumber", vipUser.getCardNumber());
     	
     	mapEntity.put("title", "系统试用时间卡");
     	mapEntity.put("frontMoneyStatus", 0);
     	mapEntity.put("actualMoney", BigDecimal.valueOf(0.00));
     	//充值天数，如果在自动开卡开关打开时，数据重置为0
     	mapEntity.put("expireDays", 30);
     	
     	mapEntity.put("autoOpenCardSwitch", 0);
     	mapEntity.put("openCardTime", 0);
     	mapEntity.put("expireTime", 0);
     	
     	mapEntity.put("remark", "系统试用时间卡");
     	mapEntity.put("contractNumber", "");
     	mapEntity.put("contractId", 0);
     	mapEntity.put("actualInsertTime", currentTime);
     	
     	mapEntity.put("cardTypeId", 0);
     	mapEntity.put("cardTypeName", "");
     	mapEntity.put("sourceId", 0);
     	
     	mapEntity.put("opeUserId", 0);
     	mapEntity.put("opeUserName", "系统");
     	
     	try {
     		boolean res = this.save(mapEntity);
     		return res;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
