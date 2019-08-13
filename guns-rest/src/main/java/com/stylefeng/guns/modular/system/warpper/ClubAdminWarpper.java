package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.DateUtil;
import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.ArrayList;
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
        map.put("rolesName", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
        map.put("authority", JSON.parseObject((String) map.get("authority"), ArrayList.class));
    }

}
