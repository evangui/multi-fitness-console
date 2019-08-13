package com.stylefeng.guns.modular.mch.club.controller;

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
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.system.warpper.ClubAdminWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.club.service.IClubAdminService;

/**
 * 俱乐部管理员控制器
 *
 * @author guiyj007
 * @Date 2018-06-25 10:24:28
 */
@Controller
@RequestMapping("/mch/club/clubAdmin")
public class ClubAdminController extends BaseController {

    @Autowired
    private IClubAdminService clubAdminService;

    /**
     * 获取俱乐部管理员列表
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
     	
    	Page<ClubAdmin> page = new PageFactory<ClubAdmin>().defaultPage("id", "asc");
    	page = clubAdminService.pageList(page, clubId, null);
        
        return new ReturnTip(0, "成功", super.packForPannelTable(page));
    }


    /**
     * 新增
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
    	String passwd = Convert.toStr(request.getParameter("password"));
    	
    	//入库对象
    	Map<String, Object> mapEntity = new HashMap<>();
    	mapEntity.put("clubId", clubId);
    	mapEntity.put("id", _id);
    	mapEntity.put("realname", Convert.toStr(mapParams.get("realname")[0]));
    	mapEntity.put("username", Convert.toStr(mapParams.get("username")[0]));
    	mapEntity.put("authority", JSON.toJSONString(mapParams.get("authority[]")));
    	
    	ClubAdmin admin = (ClubAdmin) ToolUtil.convertMap(ClubAdmin.class, mapEntity);
     
    	Wrapper<ClubAdmin> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("id", _id);
    	
    	ClubAdmin adminInDb = clubAdminService.selectOne(ew);
    	//验证信息是否存在
    	try {
			if (adminInDb == null) {
				//新增。验证密码
				if (ToolUtil.isEmpty(passwd)) {
					return new ReturnTip(502, "新增管理员密码不能为空");
				}
				
				admin.setId(null);
				admin.setInsertTime(DateUtil.timeStamp());
				admin.setSalt(IdGenerator.getTimeId());
		    	//设置加密密码
		    	admin.setPassword(MD5Util.encBySalt(passwd, admin.getSalt()));
				
				clubAdminService.insert(admin);
			} else {
				admin.setId(_id);
				if (ToolUtil.isEmpty(passwd)) {
					admin.setPassword(null);
				} else {
					admin.setPassword(MD5Util.encBySalt(passwd, adminInDb.getSalt()));
				}
				
				clubAdminService.update(admin, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ReturnTip(0, "成功");
    }
    
    /**
     * 修改密码
     */
    @RequestMapping(value = "/updatePassWord")
    @ResponseBody
    public Object updatePassWord() throws Exception {
    	/**
    	 * 验证token信息
    	 */
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return new ReturnTip(500, "俱乐部信息失效");
    	}
    	
    	String oldpasswd = Convert.toStr(request.getParameter("oldpasswd"));
    	String newpasswd = Convert.toStr(request.getParameter("newpasswd"));
    	String surepasswd = Convert.toStr(request.getParameter("surepasswd"));
    	String salt = clubAdmin.getSalt();
    	
    	/**
    	 * 验证输入密码信息
    	 */
    	if (!newpasswd.equals(surepasswd)) {
    		return new ReturnTip(501, "两次密码不一致");
    	}
    	//验证原密码是否正确
    	if (!MD5Util.encBySalt(oldpasswd, salt).equals(clubAdmin.getPassword())) {
    		return new ReturnTip(501, "原密码不正确");
		}
    	
    	//设置新密码
    	ClubAdmin newAdmin = new ClubAdmin();
    	newAdmin.setPassword(MD5Util.encBySalt(newpasswd, salt));
    	newAdmin.setId(clubAdmin.getId());
     
    	//验证信息是否存在
    	try {
    		clubAdminService.updateById(newAdmin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ReturnTip(502, "修改失败");
		}
    	return new ReturnTip(0, "成功");
    }

    

    /**
     * 删除俱乐部管理员
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
     	Wrapper<ClubAdmin> ew = new EntityWrapper<>();
     	ew = ew.eq("id", id);
    	ew = ew.eq("club_id", clubId);
    	try {
    		clubAdminService.delete(ew);
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
    	
    	//获取内容
    	Integer id = ToolUtil.toInt(request.getParameter("id"));
    	ClubAdmin itemInDb = clubAdminService.selectById(id);
    	
    	//验证合同所属俱乐部
    	if (!itemInDb.getClubId().equals(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(501, "合同访问受限"));
    	}
    	
    	Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(itemInDb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	new ClubAdminWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
}
