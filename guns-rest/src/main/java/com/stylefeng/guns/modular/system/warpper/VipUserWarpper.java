package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.MapItemFactory;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.member.service.impl.CheckinRecordServiceImpl;
import com.stylefeng.guns.modular.mch.member.service.impl.VipUserServiceImpl;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 包装类
 *
 * @author guiyj007
 * @date 2018年6月26日 下午3:47:03
 */
public class VipUserWarpper extends BaseControllerWarpper {
	private CheckinRecordServiceImpl checkinRecordService = SpringContextHolder.getBean(CheckinRecordServiceImpl.class);
	private VipUserServiceImpl vipUserService = SpringContextHolder.getBean(VipUserServiceImpl.class);
	
    public VipUserWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
        map.put("genderCn", ConstantFactory.me().getDictsByName("性别", (Integer) map.get("gender")));
//        map.put("insertTime", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
        
        //教练返回数据兼容
        ArrayList<Integer> coachIds = new ArrayList<>();
        coachIds.add((Integer) map.get("coachId"));
        map.put("coachIds", coachIds);
        map.put("coachNames", map.get("coachName"));
        map.put("operatorId", 0);
        map.put("operatorName", "系统添加");
        map.put("insertDate", DateUtil.timeStamp2Date(Convert.toInt(map.get("insertTime")), null));
        map.put("birthdayStr", DateUtil.timeStamp2Date(Convert.toInt(map.get("birthday")), "yy-MM-dd"));
        map.put("lastSignTimeStr", DateUtil.timeStamp2Date(Convert.toInt(map.get("lastSignTime")), null));
        
        Integer expireTimeStamp = Convert.toInt(map.get("expireTime"));
        
        //绑定的app用户
        if (!Convert.toInt(map.get("userId")).equals(0)) {
//        	map.put("userInfo", ConstantFactory.me().getUserCommonById(Convert.toInt(map.get("userId"))));
        }
        
        /**
         * 时间卡信息
         */
        map.put("expireTimeStamp", expireTimeStamp);
        if (0 == expireTimeStamp) {
        	if (Convert.toInt(map.get("expireStatus")).equals(1)) {
        		map.put("expireTime", "未开卡");
        	} else {
        		map.put("expireTime", "无时间卡");
        	}
        } else if (expireTimeStamp < DateUtil.timeStamp()) {
        	map.put("expireTime", "已过期");
		} else {
			map.put("expireTime", DateUtil.timeStamp2Date(Convert.toInt(map.get("expireTime")), null));
		}
//        未充值
        map.put("expireStatus", 0);
        
        /**
         * 私教卡信息
         */
        Map<String, Object> mapPtrainCardInfo = vipUserService.getPtrainCardInfo(Convert.toInt(map.get("id")));
        map.put("personalTrainerCardNum", mapPtrainCardInfo.get("personalTrainerCardNum"));
        map.put("personalTrainerCardCounts", mapPtrainCardInfo.get("personalTrainerCardCounts"));
        map.put("personalTrainerCardUsedCounts", mapPtrainCardInfo.get("personalTrainerCardUsedCounts"));
        
        Integer personalTrainerCardNum = (Integer) map.get("personalTrainerCardNum");
        Integer personalTrainerCardCounts = (Integer) map.get("personalTrainerCardCounts");
        Integer personalTrainerCardUsedCounts = (Integer) map.get("personalTrainerCardUsedCounts");
        Integer personalTrainerCardLeftCounts = personalTrainerCardCounts - personalTrainerCardUsedCounts;
        if (personalTrainerCardNum > 0) {
        	map.put("personalTrainerCardCount", personalTrainerCardNum + "张，余" + personalTrainerCardLeftCounts + "次");
        } else {
        	map.put("personalTrainerCardCount", "无私教卡");
        }
        map.put("personalTrainerCardInfo", MapItemFactory.getCardCouts(personalTrainerCardCounts, personalTrainerCardUsedCounts));
        
        /**
         * 次卡信息
         */
        Map<String, Object> mapOnceCardInfo = vipUserService.getOnceCardInfo(Convert.toInt(map.get("id")));
        map.put("onceCardNum", mapOnceCardInfo.get("onceCardNum"));
        map.put("onceCardCounts", mapOnceCardInfo.get("onceCardCounts"));
        map.put("onceCardUsedCounts", mapOnceCardInfo.get("onceCardUsedCounts"));
        
        Integer onceCardNum = (Integer) map.get("onceCardNum");
        Integer onceCardCounts = (Integer) map.get("onceCardCounts");
        Integer onceCardUsedCounts = (Integer) map.get("onceCardUsedCounts");
        Integer onceCardLeftCounts = onceCardCounts - onceCardUsedCounts;
        if (onceCardNum > 0) {
        	map.put("onceCardCount", onceCardNum + "张，余" + onceCardLeftCounts + "次");
        } else {
        	map.put("onceCardCount", "无次卡");
        }
        map.put("onceCardInfo", MapItemFactory.getCardCouts(onceCardCounts, onceCardUsedCounts));
        
        /**
         * 储值卡信息
         */
        Integer storedValueCardNum = (Integer) map.get("storedValueCardNum");
        BigDecimal storedValueCardCounts = (BigDecimal) map.get("storedValueCardCounts");
        BigDecimal storedValueCardUsedCounts = (BigDecimal) map.get("storedValueCardUsedCounts");
        BigDecimal storedValueCardLeftCounts = storedValueCardCounts.subtract(storedValueCardUsedCounts);
        
        Map<String, Object> mapStoredValueCardInfo = new HashMap<>();
        mapStoredValueCardInfo.put("counts", storedValueCardCounts);
        mapStoredValueCardInfo.put("usedCounts", storedValueCardUsedCounts);
        mapStoredValueCardInfo.put("leftCounts", storedValueCardLeftCounts);
        mapStoredValueCardInfo.put("useMoney", storedValueCardUsedCounts);
        mapStoredValueCardInfo.put("sumMoney", storedValueCardCounts);
        map.put("storedValueCardInfo", mapStoredValueCardInfo);
        if (storedValueCardNum > 0) {
        	map.put("storedValueCardCount", mapStoredValueCardInfo);
        } else {
        	map.put("storedValueCardCount", "无储值卡");
        }
//        rechargeInfo
        
        //最大化兼容签到信息
        HttpServletRequest request = HttpKit.getRequest();
        if (ToolUtil.isNotEmpty(request.getParameter("signinMode"))) {
        	
        	HashMap<String, Object> vipUserInfo = new HashMap<>();
        	vipUserInfo.putAll(map);
        	map.clear();
        	map.put("vipUserInfo", vipUserInfo);
        	//获取用户当前有效的签到记录
        	try {
        		map.putAll(checkinRecordService.getWrapedUserLatestRecord((Integer) vipUserInfo.get("id")));
        		new CheckinRecordWarpper(null).warpTheMap(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("1111");
				e.printStackTrace();
			}
        	
//        	map.put("admissionRecordCount", 0);
//            map.put("admissionCount", 0);
//            map.put("admisstionId", 0);
//            map.put("admissionRecordId", 0);
//        	map.put("ringItem", new ArrayList<>());
//            map.put("ringBorrowedItems", new ArrayList<>());
//            map.put("ringId", 0);
//            map.put("ringNum", 0);
//            
//            map.put("todaySign", 0);
//            map.put("isOnceCard", 0);	//是否有可用次卡
        }
        
        
        
    }

}
