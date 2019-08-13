package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 包装类
 *
 * @author guiyj007
 * @date 2018年6月26日 下午3:47:03
 */
public class ClubCoachWarpper extends BaseControllerWarpper {

    public ClubCoachWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
//        map.put("rolesName", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
        map.put("auth", JSON.parseObject((String) map.get("auth"), ArrayList.class));
    }

}
