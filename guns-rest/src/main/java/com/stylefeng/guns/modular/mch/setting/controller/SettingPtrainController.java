package com.stylefeng.guns.modular.mch.setting.controller;

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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.SettingPtrain;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.setting.service.ISettingPtrainService;

/**
 * 私教课程设置控制器
 *
 * @author guiyj007
 * @Date 2018-06-19 17:19:31
 */
@Controller
@RequestMapping("/mch/setting/settingPtrain")
public class SettingPtrainController extends BaseController {

    private String PREFIX = "/setting/settingPtrain/";

    @Autowired
    private ISettingPtrainService settingPtrainService;

    /**
     * 跳转到私教课程设置首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "settingPtrain.html";
    }

    /**
     * 跳转到添加私教课程设置
     */
    @RequestMapping("/settingPtrain_add")
    public String settingPtrainAdd() {
        return PREFIX + "settingPtrain_add.html";
    }

    /**
     * 跳转到修改私教课程设置
     */
    @RequestMapping("/settingPtrain_update/{settingPtrainId}")
    public String settingPtrainUpdate(@PathVariable Integer settingPtrainId, Model model) {
        SettingPtrain settingPtrain = settingPtrainService.selectById(settingPtrainId);
        model.addAttribute("item",settingPtrain);
        LogObjectHolder.me().set(settingPtrain);
        return PREFIX + "settingPtrain_edit.html";
    }


    /**
     * 新增私教课程设置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SettingPtrain settingPtrain) {
        settingPtrainService.insert(settingPtrain);
        return SUCCESS_TIP;
    }

    /**
     * 删除私教课程设置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer settingPtrainId) {
        settingPtrainService.deleteById(settingPtrainId);
        return SUCCESS_TIP;
    }

    /**
     * 修改设置
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
    	
    	//入库对象
    	mapParams.put("clubId", clubId);
    	mapParams.put("startTime", Convert.toStr(mapParams.get("startTime"), "00:00"));
    	mapParams.put("endTime", Convert.toStr(mapParams.get("endTime"), "23:59"));
    	mapParams.put("delay", Convert.toInt(mapParams.get("delay"), 0));
    	mapParams.put("checkType", Convert.toInt(mapParams.get("checkType"), 0));
    	mapParams.put("coachType", Convert.toInt(mapParams.get("coachType"), 0));
    	mapParams.put("noVipSub", Convert.toInt(mapParams.get("noVipSub"), 0));
    	mapParams.put("allDay", Convert.toInt(mapParams.get("allDay"), 0));
    	mapParams.put("isShowName", Convert.toInt(mapParams.get("isShowName"), 0));
    	
    	SettingPtrain settingPtrain = (SettingPtrain) ToolUtil.convertMap(SettingPtrain.class, mapParams);
     
    	Wrapper<SettingPtrain> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	
    	//验证信息是否存在
    	try {
			if (0 == settingPtrainService.selectCount(ew)) {
				settingPtrainService.insert(settingPtrain);
			} else {
				settingPtrainService.update(settingPtrain, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return SUCCESS_TIP;
    }

    /**
     * 设置详情
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
    	
    	Wrapper<SettingPtrain> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	Map<String, Object> mapSetting = settingPtrainService.selectMap(ew);
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", mapSetting));
    }
}
