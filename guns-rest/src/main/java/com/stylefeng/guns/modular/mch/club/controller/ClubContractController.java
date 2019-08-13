package com.stylefeng.guns.modular.mch.club.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.ClubContract;
import com.stylefeng.guns.modular.system.model.SettingPtrain;
import com.stylefeng.guns.modular.system.warpper.ClubContractWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;

import ch.qos.logback.core.subst.Token.Type;

import com.stylefeng.guns.modular.mch.club.service.IClubContractService;

/**
 * 俱乐部合同控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:05:30
 */
@Controller
@RequestMapping("/mch/club/clubContract")
public class ClubContractController extends BaseController {

    @Autowired
    private IClubContractService clubContractService;

    /**
     * 获取俱乐部合同列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/typeList")
    @ResponseBody
    public Object typeList() {
    	HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	Integer type = Convert.toInt(request.getParameter("type"), 0);
     	Integer id = Convert.toInt(request.getParameter("contractNumber"), 0);
     	
    	Wrapper<ClubContract> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	if (!id.equals(0)) {
    		ew = ew.eq("id", id);
    	}
    	if (!type.equals(0)) {
    		ew = ew.eq("type", type);
    	}
    	
    	List<Map<String, Object>> res =  clubContractService.selectMaps(ew);
    	List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) new ClubContractWarpper(res).warp();
    	//对合同按照类型分组
    	HashMap<String, Object> mapRet = new HashMap<>();
    	 
    	mapRet.put("list", list);
    	mapRet.put("aotuContract", 1);
    	mapRet.put("contractId", 0);
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", mapRet));
    }
    
    /**
     * 获取俱乐部合同列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
    	HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
    	Wrapper<ClubContract> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	
    	List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) new ClubContractWarpper(clubContractService.selectMaps(ew)).warp();
    	//对合同按照类型分组
    	HashMap<String, Object> mapRet = new HashMap<>();
    	ArrayList<Map<String, Object>> timeDataList = new ArrayList<>();
    	ArrayList<Map<String, Object>> personDataList = new ArrayList<>();
    	ArrayList<Map<String, Object>> onceDataList = new ArrayList<>();
    	ArrayList<Map<String, Object>> valueDataList = new ArrayList<>();
    	
    	for (HashMap<String, Object> element : list) {
    		switch (ToolUtil.toInt(element.get("type"))) {
				case 1:
					timeDataList.add(element);
					break;
				case 2:
					personDataList.add(element);
					break;
				case 3:
					onceDataList.add(element);
					break;	
				case 4:
					valueDataList.add(element);
					break;		
				default:
					break;
			}
		}
    	mapRet.put("timeData", timeDataList);
    	mapRet.put("personData", personDataList);
    	mapRet.put("onceData", onceDataList);
    	mapRet.put("valueData", valueDataList);
    	mapRet.put("membership", new ArrayList<>());
    	mapRet.put("coach", new ArrayList<>());
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", mapRet));
    }

    /**
     * 新增俱乐部合同
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestBody HashMap<String, Object> mapParams) throws Exception {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
    	}
    	
    	Integer _id = Convert.toInt(mapParams.get("id"), 0);
    	
    	//入库对象
    	mapParams.put("clubId", clubId);
    	mapParams.put("id", _id);
    	mapParams.put("title", Convert.toStr(mapParams.get("title")));
    	mapParams.put("content", Convert.toStr(mapParams.get("content")));
    	mapParams.put("type", Convert.toInt(mapParams.get("type"), 1));
    	mapParams.put("hidden", Convert.toInt(mapParams.get("hidden"), 0));
    	
    	ClubContract clubContract = (ClubContract) ToolUtil.convertMap(ClubContract.class, mapParams);
     
    	Wrapper<ClubContract> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("id", _id);
    	
    	//验证信息是否存在
    	try {
			if (0 == clubContractService.selectCount(ew)) {
				clubContract.setId(null);
				clubContract.setInsertTime(DateUtil.timeStamp());
				clubContract.setContractNum(IdGenerator.getTimeId());
				clubContractService.insert(clubContract);
			} else {
				clubContract.setId(_id);
				clubContractService.update(clubContract, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return SUCCESS_TIP;
    }

    /**
     * 删除俱乐部合同
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
     	
     	//获取合同内容
     	Integer clubContractId = ToolUtil.toInt(request.getParameter("id"));
     	Wrapper<ClubContract> ew = new EntityWrapper<>();
     	ew = ew.eq("id", clubContractId);
    	ew = ew.eq("club_id", clubId);
    	try {
			clubContractService.delete(ew);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.ok(new ReturnTip(501, "删除失败"));
		}
    	
        return SUCCESS_TIP;
    }

    
    /**
     * 俱乐部合同详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
    	}
    	
    	//获取合同内容
    	Integer clubContractId = ToolUtil.toInt(request.getParameter("id"));
    	ClubContract clubContract = clubContractService.selectById(clubContractId);
    	
    	//验证合同所属俱乐部
    	if (!clubContract.getClubId().equals(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(501, "合同访问受限"));
    	}
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", clubContract));
    }
}
