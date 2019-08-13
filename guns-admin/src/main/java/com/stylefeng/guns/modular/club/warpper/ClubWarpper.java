package com.stylefeng.guns.modular.club.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author guiyj007
 * @date 2018年6月26日 下午3:47:03
 */
public class ClubWarpper extends BaseControllerWarpper {

    public ClubWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
//        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
//        map.put("roles", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
        map.put("clubStyleName", ConstantFactory.me().getDictsByName("Club-clubStyle", (Integer) map.get("clubStyle")));
        if (Convert.toInt(map.get("startTime")).equals(0)) {
        	map.put("startTime", "");
        } else {
        	map.put("startTime", DateUtil.timeStamp2Date(Convert.toInt(map.get("startTime")), null));
        }
        
        if (Convert.toInt(map.get("expireTime")).equals(0)) {
        	map.put("expireTime", "");
        } else {
        	map.put("expireTime", DateUtil.timeStamp2Date(Convert.toInt(map.get("expireTime")), null));
        }
    }

}
