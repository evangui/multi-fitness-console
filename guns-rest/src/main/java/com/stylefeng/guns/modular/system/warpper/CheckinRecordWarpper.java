package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.MapItemFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;

/**
 * 包装类
 *
 * @author guiyj007
 * @date 2018年6月26日 下午3:47:03
 */
public class CheckinRecordWarpper extends BaseControllerWarpper {
    public CheckinRecordWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void warpTheMap(Map<String, Object> map) {
    	Integer clubId = Convert.toInt(map.get("clubId"));
        map.put("clubName", ConstantFactory.me().getClubNameById(clubId));
        
        map.put("admissionRecordId", map.get("id"));
        map.put("agentSignInfo", MapItemFactory.composeMap("opeUserId", map.get("opeUserId"), "opeUserName", map.get("opeUsername")));
        
        Map<String, Object> mapUser = null;
        if (ToolUtil.isNotEmpty(map.get("vipId")) && ToolUtil.isEmpty(map.get("vipUserInfo"))) {
        	VipUser vipUser = ConstantFactory.me().getVipInfoById(Convert.toInt(map.get("vipId")));
    		try {
    			mapUser = ToolUtil.convertBean(vipUser);
    			new VipUserWarpper(null).warpTheMap(mapUser);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		map.put("vipUserInfo", mapUser);
        }
        
//        if (clubRingService == null) {
//        	clubRingService = new ClubRingServiceImpl();
//        }
        
        map.put("ringItem", map.get("ringNum").equals("") ? null : ConstantFactory.me().getClubRing(clubId, (String) map.get("ringNum")) );
        
        ArrayList<Object> ringBorrowedItems = new ArrayList<>();
        if (ToolUtil.isNotEmpty(map.get("ringItem"))) {
        	ringBorrowedItems.add(map.get("ringItem"));
        }
        map.put("ringBorrowedItems", ringBorrowedItems);
        
        
        ArrayList<HashMap<String, Object>> ringList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> ringListItem = new HashMap<>();
        ringListItem.put("ringNum", map.get("ringNum"));
        ringListItem.put("ringStatus", map.get("ringStatus"));
        ringList.add(ringListItem);
        map.put("ringList", ringList);
        
      //是否有可用次卡
        map.put("isOnceCard", 0);	
        mapUser = (Map<String, Object>) map.get("vipUserInfo");
        if (mapUser != null) {
        	if ( (Convert.toInt(mapUser.get("onceCardCounts")) - Convert.toInt(mapUser.get("onceCardUsedCounts"))) > 0) {
        		map.put("isOnceCard", 1);	//是否有可用次卡
            }
        }
        
//        todaySign
    }

}
