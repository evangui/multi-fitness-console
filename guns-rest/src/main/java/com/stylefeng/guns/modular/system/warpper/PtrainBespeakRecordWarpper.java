package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
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
public class PtrainBespeakRecordWarpper extends BaseControllerWarpper {

    public PtrainBespeakRecordWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("clubName", ConstantFactory.me().getClubNameById((Integer) map.get("clubId")));
        
        map.put("fromTimeStr", DateUtil.timeStamp2Date((Integer) map.get("fromTime"), "HH:mm"));
		map.put("toTimeStr", DateUtil.timeStamp2Date((Integer) map.get("toTime"), "HH:mm"));
//        map.put("roles", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
//        map.put("insertTime", ConstantFactory.me().getDictsByName("ClubAdmin-roles", (Integer) map.get("roles")));
    }

}
