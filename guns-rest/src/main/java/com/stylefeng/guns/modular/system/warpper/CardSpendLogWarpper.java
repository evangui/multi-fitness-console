package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.VipUser;
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
public class CardSpendLogWarpper extends BaseControllerWarpper {

    public CardSpendLogWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
        
        map.put("spendTime", map.get("insertTime"));
        map.put("insertTimeStr", DateUtil.timeStamp2Date((Integer) map.get("insertTime"), null));
        
        map.put("agentCostName", map.get("rcepteionName"));    
        
        HashMap<String, Object> mapRank = new HashMap<String, Object>() {
        	{
        		put("comment", map.get("commentContent"));
        		put("rank", map.get("commentRank"));
        		put("tag", map.get("commentTags"));
        	}
        };
        map.put("userRank", mapRank);
//        map.put("roles", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
//        map.put("insertTime", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
       
        VipUser vipUser = ConstantFactory.me().getVipInfoById(Convert.toInt(map.get("vipId")));
        map.put("vipUser", vipUser);    
        
        HashMap<String, Object> onceCard = ConstantFactory.me().getPtrainCardBasic(Convert.toInt(map.get("cardId")));
        map.put("onceCard", onceCard);    
        
        int logType = Convert.toInt(map.get("logType"));
        switch (logType) {
			case 2:
				map.put("spendValue", map.get("costs") + "次");
				map.put("cardType", "私教卡");
				break;
			case 3:
				map.put("spendValue", map.get("costs") + "次");
				map.put("cardType", "次卡");
				break;		
			case 4:
	        	map.put("spendValue", map.get("costs") + "元");
				map.put("cardType", "储值卡");
				break;	
			default:
				break;
		}
        
    }

}
