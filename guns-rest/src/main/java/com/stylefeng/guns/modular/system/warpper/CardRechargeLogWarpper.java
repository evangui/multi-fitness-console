package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.alibaba.druid.util.MapComparator;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 包装类
 *
 * @author guiyj007
 * @date 2018年6月26日 下午3:47:03
 */
public class CardRechargeLogWarpper extends BaseControllerWarpper {

    public CardRechargeLogWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
//        map.put("roles", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
//        map.put("insertTime", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
       
        map.put("sourceName", ConstantFactory.me().getDictsByName("sales-source", (Integer) map.get("sourceId")));
        map.put("expires", Convert.toInt(map.get("expireDays"), 0) * 86400);
        map.put("expireTimeStr", DateUtil.timeStamp2Date((Integer) map.get("expireTime"), "yyyy-MM-dd"));
        map.put("InsertTimeStr", DateUtil.timeStamp2Date((Integer) map.get("InsertTime"), "yyyy-MM-dd"));
        
        map.put("opeUserName", map.get("opeUsername"));
        map.put("rechargeTime", map.get("insertTime"));
        map.put("rechargeValue", map.get("counts"));
        map.put("cardType", map.get("cardTypeName"));
        map.put("isEdit", Convert.toBool(map.get("banksure")) ? 0 : 1);
        map.put("bankStation", Convert.toBool(map.get("banksure")) ? "已确认" : "未确认");
        
        
        Map<String, Object> mapUser = null;
        if (ToolUtil.isNotEmpty(map.get("vipId")) && !map.get("vipId").equals(0) && ToolUtil.isEmpty(map.get("vipUser"))) {
        	VipUser vipUser = ConstantFactory.me().getVipInfoById(Convert.toInt(map.get("vipId")));
    		if (ToolUtil.isNotEmpty(vipUser)) {
    			try {
        			mapUser = ToolUtil.convertBean(vipUser);
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    		}
//            new VipUserWarpper(null).warpTheMap(mapUser);
            map.put("vipUser", mapUser);
        }
        
        mapUser = (Map<String, Object>) map.get("vipUser");
        map.put("cardNumber", mapUser == null ? "" : mapUser.get("cardNumber"));
        int logType = Convert.toInt(map.get("logType"));
        
        if (logType == 1) {
        	map.put("logTypeStr", "时间卡");
        } else if (logType == 2) {
        	map.put("logTypeStr", "私教卡");
        } else if (logType == 3) {
        	map.put("logTypeStr", "次卡");
        } else if (logType == 4) {
        	map.put("logTypeStr", "储值卡");
        }
        
    }

}
