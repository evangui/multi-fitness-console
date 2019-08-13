package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包装类
 *
 * @author guiyj007
 * @date 2018年6月26日 下午3:47:03
 */
public class CardTimecardWarpper extends BaseControllerWarpper {

    public CardTimecardWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
//        map.put("roles", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
//        map.put("insertTime", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
        
        map.put("type", "multiTimeCard");
        map.put("sourceName", ConstantFactory.me().getDictsByName("sales-source", (Integer) map.get("sourceId")));
        map.put("isUnitedCard", Convert.toBool(map.get("isUnitedCard")) ? "通卡" : "单店卡" );
        map.put("openTime", DateUtil.timeStamp2Date((Integer) map.get("openCardTime"), "yyyy-MM-dd"));
        map.put("insertTimeStr", DateUtil.timeStamp2Date((Integer) map.get("insertTime"), null));
        map.put("startUseTimeStr", DateUtil.timeStamp2Date((Integer) map.get("startUseTime"), null));
        map.put("cardId", map.get("id"));
        map.put("typeName", map.get("cardTypeName"));
        map.put("unionCardId", map.get("clubId") + "-172-1");
//        map.put("typeTitle", map.get("cardTypeName"));
      //充值天数，如果在自动开卡开关打开时，数据重置为0
        map.put("expires", Convert.toBool(map.get("autoOpenCardSwitch"), false).equals(false) && Convert.toInt(map.get("openCardTime")).equals(0)
        		? (Integer)map.get("expireDays") * 86400 : 0) ;
        
        Integer currentTime = DateUtil.timeStamp();
        Integer expireTime = (Integer) map.get("expireTime");
//        map.put("leftDay", (expireTime - currentTime)/86400);
        
        map.put("statusSummary", DateUtil.timeStamp2Date(expireTime, "yyyy-MM-dd"));
        
        //状态格式化：10 正常 ，30 未开卡 50请假中  60 已过期  
        if (map.get("openCardTime").equals(0)) {
        	//未开卡
        	map.put("status", 30);
        	map.put("statusStr", "未开卡 ");
        	Integer validDays = Convert.toInt(map.get("expireDays"), 0);
        	if (validDays.equals(0)) {
        		validDays = (expireTime - Convert.toInt(map.get("openCardTime")))/86400;
        	}
        	map.put("expireTimeStr", "未开卡 " + validDays + "天");
        	
        } else if (expireTime > currentTime) {
			//正常
        	map.put("status", 10);
        	map.put("statusStr", "正常");
        	map.put("expireTimeStr", DateUtil.timeStamp2Date(expireTime, "yyyy-MM-dd"));
		} else if (expireTime < currentTime) {
			//已经过期
        	map.put("status", 60);
        	map.put("statusStr", "已过期");
        	map.put("expireTimeStr", "已过期");
			
		} else if (Convert.toInt(map.get("suspendStartTime")) < currentTime 
				&& Convert.toInt(map.get("suspendEndTime")) > currentTime) {
			//请假中
        	map.put("status", 50);
        	map.put("statusStr", "请假中");
        	map.put("expireTimeStr", DateUtil.timeStamp2Date(expireTime, "yyyy-MM-dd"));
		}
        
    }
    
    @SuppressWarnings("unchecked")
    public Object warp2() {
        if (this.obj instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) this.obj;
            for (Map<String, Object> map : list) {
                warpTheMap2(map);
            }
            return list;
        } else if (this.obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) this.obj;
            warpTheMap2(map);
            return map;
        } else {
            return this.obj;
        }
    }
    
    public void warpTheMap2(Map<String, Object> map) {
        this.warpTheMap(map);
        
        HashMap<String, Object> mapStatus = new HashMap<>();
        mapStatus.put("code", map.get("status"));
    	mapStatus.put("str", map.get("codeStr"));
    	mapStatus.put("summary", map.get("statusSummary"));
        
     	map.put("status", mapStatus);
    }

}
