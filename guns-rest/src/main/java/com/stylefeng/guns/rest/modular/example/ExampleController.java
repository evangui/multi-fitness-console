package com.stylefeng.guns.rest.modular.example;

import com.stylefeng.guns.modular.mch.club.service.IClubService;
import com.stylefeng.guns.modular.system.model.Club;
import com.stylefeng.guns.rest.common.SimpleObject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 常规控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
//@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST }, origins = "*")
@Controller
@RequestMapping(value="/hello", method = RequestMethod.POST)
public class ExampleController {

	@Autowired
    private IClubService clubService;
	
    @RequestMapping("")
    public ResponseEntity<List<Club>> hello(@RequestBody SimpleObject simpleObject) {
        System.out.println(simpleObject.getUser());
        
        
//        return ResponseEntity.ok(simpleObject);
        return ResponseEntity.ok(clubService.selectList(null));
    }
}
