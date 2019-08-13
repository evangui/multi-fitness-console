package com.stylefeng.guns.modular.mch.card.controller;

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
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.model.Club;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.SettingCard;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ISettingCardService;

/**
 * 会员卡设置控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:48:19
 */
@Controller
@RequestMapping("/mch/card/settingCard")
public class SettingCardController extends BaseController {

    private String PREFIX = "/card/settingCard/";

    @Autowired
    private ISettingCardService settingCardService;

    /**
     * 跳转到会员卡设置首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "settingCard.html";
    }

    /**
     * 跳转到添加会员卡设置
     */
    @RequestMapping("/settingCard_add")
    public String settingCardAdd() {
        return PREFIX + "settingCard_add.html";
    }

    /**
     * 跳转到修改会员卡设置
     */
    @RequestMapping("/settingCard_update/{settingCardId}")
    public String settingCardUpdate(@PathVariable Integer settingCardId, Model model) {
        SettingCard settingCard = settingCardService.selectById(settingCardId);
        model.addAttribute("item",settingCard);
        LogObjectHolder.me().set(settingCard);
        return PREFIX + "settingCard_edit.html";
    }

    /**
     * 获取会员卡设置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<SettingCard> page = new PageFactory<SettingCard>().defaultPage(null, null);
    	
    	page = settingCardService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }


    /**
     * 修改会员卡设置
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestBody HashMap<String, Object> mapParams) throws Exception {
//    	mapParams.put("deadline", Integer.parseInt((String) mapParams.get("deadline")));
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
    	}
    	
    	//入库对象
    	SettingCard settingCard = new SettingCard();
    	settingCard.setClubId(clubId);
    	settingCard.setLeaveDays(Integer.parseInt((String) mapParams.get("leaveDays")));
    	settingCard.setLessLeaveDays(Integer.parseInt((String) mapParams.get("lessLeaveDays")));
    	settingCard.setDeadline(Integer.parseInt((String) mapParams.get("deadline")));
    	settingCard.setLeftAmountHide(Integer.parseInt((String) mapParams.get("leftAmountHide")));
    	settingCard.setRecord(Integer.parseInt((String) mapParams.get("record")));
    	settingCard.setFrontConsumeSwitch(Integer.parseInt((String) mapParams.get("frontConsumeSwitch")));
    	settingCard.setOnceCardUsedPass((String) mapParams.get("onceCardUsedPass"));
    	settingCard.setCardStartUseTimeSwitch(Integer.parseInt((String) mapParams.get("cardStartUseTimeSwitch")));
    	settingCard.setVipPasswdSwitch(Integer.parseInt((String) mapParams.get("vipPasswdSwitch")));
    	
    	Wrapper<SettingCard> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	
    	//验证信息是否存在
    	try {
			if (0 == settingCardService.selectCount(ew)) {
				settingCardService.insert(settingCard);
			} else {
				settingCardService.update(settingCard, ew);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return SUCCESS_TIP;
    }

    /**
     * 会员卡设置详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	
    	Wrapper<SettingCard> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	Map<String, Object> mapSettingCard = settingCardService.selectMap(ew);
    	
    	return ResponseEntity.ok(new ReturnTip(0, "成功", mapSettingCard));
    }
}
