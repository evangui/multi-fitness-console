package com.stylefeng.guns.modular.usr.member.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.UserClub;
import com.stylefeng.guns.modular.system.warpper.UserClubWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.common.exception.BizException;
import com.stylefeng.guns.rest.common.exception.BizExistException;
import com.stylefeng.guns.modular.mch.member.service.IUserClubService;

/**
 * 注册用户控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:45:38
 */
@Controller("usr_userClubController")
@RequestMapping("/usr/member/userClub")
public class UserClubController extends BaseController {


    @Autowired
    private IUserClubService userClubService;

    /**
     * 加入俱乐部
     */
    @RequestMapping(value = "/joinClub")
    @ResponseBody
    public Object joinClub() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	HashMap<String, Object> mapMember = (HashMap<String, Object>) request.getAttribute("mapMember");
    	Integer userId = Convert.toInt(mapMember.get("id"), 0);
    	if (userId.equals(0)) {
    		return new ReturnTip(500, "会员信息失效");
    	}
    	
    	//获取post数据
    	HashMap<String, String> mapParams = JSON.parseObject(getStringFromStream(), HashMap.class);
    	Integer clubId = Convert.toInt(mapParams.get("clubId"), 0);
    	if (clubId.equals(0)) {
    		return new ReturnTip(500, "俱乐部不能为空");
    	}
    	
    	//验证信息是否存在
    	UserClub userClub = null;
    	try {
    		userClub = userClubService.addFromCommonUser(mapMember, clubId);
    		return new ReturnTip(0, "成功", userClub);
		} catch (BizExistException e) {
			// TODO Auto-generated catch block
			return new ReturnTip(200, "成功");
		} catch (BizException e) {
			// TODO Auto-generated catch block
			return new ReturnTip(501, e.getMessage());
		}
    	
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	Integer id = ToolUtil.toInt(request.getParameter("id"));
     	Integer type = ToolUtil.toInt(request.getParameter("type"));
     	Wrapper<UserClub> ew = new EntityWrapper<>();
     	ew = ew.eq("id", id);
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("type", type);
    	try {
    		userClubService.delete(ew);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.ok(new ReturnTip(501, "删除失败"));
		}
    	
    	return new ReturnTip(0, "操作成功");
    }

    /**
     * 俱乐部管理员详情
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
    	}
    	
    	Integer id = Convert.toInt(request.getParameter("id"), 0);
    	Integer userId = Convert.toInt(request.getParameter("userId"), 0);
    	
    	UserClub itemInDb = null;
    	try {
    		//获取内容
    		if (id > 0) {
    			itemInDb = userClubService.selectById(id);
    		} else {
    			Wrapper<UserClub> ew = new EntityWrapper<>();
             	ew = ew.eq("user_id", userId);
             	ew = ew.eq("club_id", clubId);
             	itemInDb = userClubService.selectOne(ew);
    		}
		} catch (NullPointerException e) {
			// TODO: handle exception
			return ResponseEntity.ok(new ReturnTip(501, "实体不存在"));
		}
    	
    	
    	Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(itemInDb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ReturnTip(0, e.getMessage());
		}
    	new UserClubWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
}
