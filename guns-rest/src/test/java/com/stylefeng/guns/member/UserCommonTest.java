package com.stylefeng.guns.member;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;
import com.stylefeng.guns.modular.mch.member.service.impl.UserCommonServiceImpl;
import com.stylefeng.guns.modular.system.model.UserCommon;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.HashMap;

/**
 * 字典服务测试
 *
 * @author fengshuonan
 * @date 2017-04-27 17:05
 */
public class UserCommonTest extends BaseJunit {

    @Resource
    IUserCommonService userCommonService;


    public void addTest() {
    	UserCommon entity = new UserCommon();
    	entity.setRealname("guiyajun");
    	entity.setAvatar("https://wx.qlogo.cn/mmhead/Q3auHgzwzM72VYc33bquZYaXaCu1ZJrHHdGMCKicTIGjkcupFvTmGeA/0");
    	entity.setGender(1);
    	entity.setPasswd("123456");
    	entity.setPhone("18571592113");
        boolean res = userCommonService.insert(entity);
        Assert.assertTrue(res);
    }

    @Test
    public void pageListTest() {
    	Integer clubId = 0;
    	
    	HashMap<String, Object> mapCondition = new HashMap<>();
     	mapCondition.put("realname", "guiyajun");
     	mapCondition.put("phone", null);
     	mapCondition.put("nickname", null);
     	
    	Page<UserCommon> page = new PageFactory<UserCommon>().defaultPage("id", "desc");
    	page = userCommonService.pageList(page, clubId, mapCondition);
        
    	System.out.println(page);
        Assert.assertTrue(page.getRecords().size() > 0);
    }
    
    @Test
    public void beTrialVipTest() {
		HashMap<String, Object> mapUserCommnon = new HashMap<>();
		UserCommon entity = userCommonService.selectById(6);
		try {
			mapUserCommnon = (HashMap<String, Object>) ToolUtil.convertBean(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean res = userCommonService.beTrialVip(mapUserCommnon, 7);
        Assert.assertTrue(res);
    }
}
