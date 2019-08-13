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
public class ClubAdminWarpper extends BaseControllerWarpper {

    public ClubAdminWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
        map.put("roles", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
        
        Integer insertTime = Convert.toInt(map.get("insertTime"));
        if (insertTime.equals(0)) {
        	map.put("insertTime", "");
        } else {
        	map.put("insertTime", DateUtil.timeStamp2Date(insertTime, null));
        }
        
        if (Convert.toInt(map.get("lastLoginTime")).equals(0)) {
        	map.put("lastLoginTime", "");
        } else {
        	map.put("lastLoginTime", DateUtil.timeStamp2Date(Convert.toInt(map.get("lastLoginTime")), null));
        }
//        map.put("insertTime", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
    }

}
