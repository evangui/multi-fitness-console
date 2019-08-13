package com.stylefeng.guns.modular.mch.member.controller;

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
import com.stylefeng.guns.modular.mch.member.service.IUserClubService;

/**
 * 注册用户控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:45:38
 */
@Controller
@RequestMapping("/mch/member/userClub")
public class UserClubController extends BaseController {


    @Autowired
    private IUserClubService userClubService;

    /**
     * 获取注册用户列表
     */
    @RequestMapping(value = "/pagelist")
    @ResponseBody
    public Object pagelist() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//综合查询条件
     	HashMap<String, Object> mapCondition = new HashMap<>();
     	mapCondition.put("realname", Convert.toStr(request.getParameter("realname"), null));
     	mapCondition.put("phone", Convert.toStr(request.getParameter("phone"), null));
     	mapCondition.put("nickname", Convert.toStr(request.getParameter("nickname"), null));
     	mapCondition.put("virtual", Convert.toStr(request.getParameter("virtual"), null));
     	
    	Page<UserClub> page = new PageFactory<UserClub>().defaultPage("id", "desc");
    	page = userClubService.pageList(page, clubId, mapCondition);
        
        return new ReturnTip(0, "成功", super.packForPannelTable(page));
    }

    /**
     * 新增+更新
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save() throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return new ReturnTip(500, "俱乐部信息失效");
    	}
    	
    	Map<String, String[]> mapParams = request.getParameterMap();
    	Integer _id = Convert.toInt(mapParams.get("id")[0], 0);
    	//入库对象
    	Map<String, Object> mapEntity = new HashMap<>();
    	mapEntity.put("clubId", clubId);
    	mapEntity.put("id", _id);
    	mapEntity.put("type", Convert.toInt(mapParams.get("type")[0]));
    	mapEntity.put("realname", Convert.toStr(mapParams.get("realname")[0]));
    	mapEntity.put("nickname", Convert.toStr(mapParams.get("realname")[0]));
    	mapEntity.put("avatar", Convert.toStr(mapParams.get("avatar")[0]));
    	mapEntity.put("userId", Convert.toInt(mapParams.get("userId")[0]));
    	mapEntity.put("auth", JSON.toJSONString(mapParams.get("authArray[]")));
    	
    	UserClub userClub = (UserClub) ToolUtil.convertMap(UserClub.class, mapEntity);
     
    	Wrapper<UserClub> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("id", _id);
    	
    	//验证信息是否存在
    	try {
			if (0 == userClubService.selectCount(ew)) {
				userClub.setId(null);
				userClub.setInsertTime(DateUtil.timeStamp());
				userClubService.insert(userClub);
			} else {
				userClub.setId(_id);
				userClubService.update(userClub, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ReturnTip(0, "成功");
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
