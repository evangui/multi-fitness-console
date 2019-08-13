package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 包装类
 *
 * @author guiyj007
 * @date 2018年6月26日 下午3:47:03
 */
public class CardPtraincardWarpper extends BaseControllerWarpper {

    public CardPtraincardWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
//        map.put("roles", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
//        map.put("insertTime", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
        map.put("sourceName", ConstantFactory.me().getDictsByName("sales-source", (Integer) map.get("sourceId")));
        map.put("type", "personalTrainerCard");
        map.put("typeName", map.get("cardTypeName"));
        map.put("isUnitedCard", Convert.toBool(map.get("isUnitedCard")) ? "通卡" : "单店卡" );
        map.put("openTime", DateUtil.timeStamp2Date((Integer) map.get("openCardTime"), "yyyy-MM-dd"));
        map.put("insertTimeStr", DateUtil.timeStamp2Date((Integer) map.get("insertTime"), null));
        map.put("startUseTimeStr", DateUtil.timeStamp2Date((Integer) map.get("startUseTime"), null));
        map.put("expires", map.get("openCardTime"));
        map.put("cardId", map.get("id"));
//        map.put("typeTitle", map.get("cardTypeName"));
        
        map.put("usedCounts", Convert.toInt(map.get("counts")) - Convert.toInt(map.get("leftCounts")));
        
        map.put("unionCardId", map.get("clubId") + "-172-1");
        
        Integer status = 0;
        Integer disabled = Convert.toInt(map.get("disabled"));
        Integer leftCounts = Convert.toInt(map.get("leftCounts"));
        Integer expireTime = Convert.toInt(map.get("expireTime"));
        
        //正常使用中 + 未请假 1 , 请假中 2, 已过期3 已禁用10
        if (disabled.equals(0)) {
        	if (leftCounts <=0) {
        		status = 0;
        	} else if (expireTime < DateUtil.timeStamp()) {
        		status = 3;
        	} else {
        		//请假中
        	}
        	
        } else {
        	status = 10;
        }
        map.put("status", status);
	 
    }

}