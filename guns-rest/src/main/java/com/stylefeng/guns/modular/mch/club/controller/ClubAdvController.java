package com.stylefeng.guns.modular.mch.club.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.ClubAdv;
import com.stylefeng.guns.modular.system.warpper.ClubAdminWarpper;
import com.stylefeng.guns.modular.system.warpper.ClubAdvWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.club.service.IClubAdvService;

/**
 * 教练控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:06:04
 */
@Controller
@RequestMapping("/mch/club/clubAdv")

public class ClubAdvController extends BaseController {

    @Autowired
    private IClubAdvService clubAdvService;


    /**
     * 获取教练列表
     */
    @RequestMapping(value = "/pagelist")
    @ResponseBody
    public Object pagelist() {
//    	coachIds
    	
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	//综合查询条件
     	HashMap<String, Object> mapCondition = new HashMap<>();
     	mapCondition.put("posId", Convert.toStr(request.getParameter("posId"), null));
     	
    	Page<ClubAdv> page = new PageFactory<ClubAdv>().defaultPage("sort", "asc");
    	page = clubAdvService.pageList(page, clubId, mapCondition);
        
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
    	System.err.println(mapParams);
    	mapEntity.put("clubId", clubId);
    	mapEntity.put("id", _id);
    	mapEntity.put("advTitle", Convert.toStr(mapParams.get("title")[0]));
    	mapEntity.put("posId", Convert.toInt(mapParams.get("posId")[0]));
    	mapEntity.put("type", 0);
    	mapEntity.put("content", Convert.toStr(mapParams.get("cover")[0]));
    	mapEntity.put("url", Convert.toStr(mapParams.get("url")[0]));
    	mapEntity.put("sort", Convert.toInt(mapParams.get("sort")[0]));
    	mapEntity.put("advUrlType", Convert.toInt(mapParams.get("urlType")[0]));
    	mapEntity.put("starttime", DateUtil.date2TimeStamp(Convert.toStr(mapParams.get("starttime")[0]), "yyyy-MM-dd"));
    	mapEntity.put("endtime", DateUtil.date2TimeStamp(Convert.toStr(mapParams.get("endtime")[0]), "yyyy-MM-dd"));
    	
    	ClubAdv clubAdv = (ClubAdv) ToolUtil.convertMap(ClubAdv.class, mapEntity);
    	
    	//查询参数
    	Wrapper<ClubAdv> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("id", _id);
    	
    	//验证信息是否存在
    	try {
			if (0 == clubAdvService.selectCount(ew)) {
				clubAdv.setId(null);
				clubAdvService.insert(clubAdv);
			} else {
				clubAdv.setId(_id);
				clubAdvService.update(clubAdv, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
     	Wrapper<ClubAdv> ew = new EntityWrapper<>();
     	ew = ew.eq("id", id);
    	ew = ew.eq("club_id", clubId);
    	try {
    		clubAdvService.delete(ew);
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
    	ClubAdv itemInDb = clubAdvService.selectById(id);
    	
    	//验证合同所属俱乐部
    	if (itemInDb == null || !itemInDb.getClubId().equals(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(501, "访问受限"));
    	}
    	
    	Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(itemInDb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	new ClubAdvWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
}
