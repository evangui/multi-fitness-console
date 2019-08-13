package com.stylefeng.guns.modular.mch.activity.controller;

import com.stylefeng.guns.core.base.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.modular.system.model.Club;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.common.SimpleObject;
import com.stylefeng.guns.modular.mch.club.service.IClubService;

/**
 * 俱乐部活动控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:15:16
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Autowired
    private IClubService clubService;

    @RequestMapping("")
    public ResponseEntity<ReturnTip> index(@RequestBody SimpleObject simpleObject) {
        System.out.println(simpleObject.getUser());
        
        List<Club> data = clubService.selectList(null);
//        return ResponseEntity.ok(simpleObject);
        
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("key1", 111);
        mapData.put("key2", 222);
        mapData.put("key3", "rrrr");
        
//        return ResponseEntity.ok(new ReturnTip(0, "成功", mapData));
        return ResponseEntity.ok(new ReturnTip(0, "成功", data));
    }

}
