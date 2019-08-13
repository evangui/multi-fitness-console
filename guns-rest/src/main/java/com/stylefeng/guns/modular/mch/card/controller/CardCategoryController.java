package com.stylefeng.guns.modular.mch.card.controller;

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
import com.stylefeng.guns.modular.system.model.CardCategory;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.warpper.CardCategoryWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.card.service.ICardCategoryService;

/**
 * 卡种控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 16:45:03
 */
@Controller
@RequestMapping("/mch/card/cardCategory")
public class CardCategoryController extends BaseController {

    @Autowired
    private ICardCategoryService cardCategoryService;
 
    /**
     * 获取获取分页列表
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
    	mapCondition.put("title", Convert.toStr(request.getParameter("title")));
    	mapCondition.put("disabled", Convert.toStr(request.getParameter("disabled")));
    	
    	Integer cardType = Convert.toInt(request.getParameter("cardType"), 0);
    	
    	Page<CardCategory> page = new PageFactory<CardCategory>().defaultPage("id", "desc");
    	page = cardCategoryService.pageList(page, clubId, mapCondition, cardType);
    	Map<String, Object> ret = super.packForPannelTable(page);
    	
       return new ReturnTip(0, "成功", ret);
    }
    
    /**
     * 新增+更新 教练
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save() throws Exception {
	   	/**
	   	 * 验证token中的俱乐部信息
	   	 */
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
    	Integer clubId = clubAdmin.getClubId();
    	if (ToolUtil.isEmpty(clubId)) {
    		return new ReturnTip(500, "俱乐部信息失效");
    	}
    	
    	/**
    	 * 入库对象构建
    	 */
    	Map<String, String[]> mapParams = request.getParameterMap();
    	Map<String, Object> mapEntity = new HashMap<>();
    	Integer _id = Convert.toInt(request.getParameter("cardId"), 0);
    	String title = Convert.toStr(request.getParameter("cardTitle"), "");
    	if (title.equals("")) {
    		return new ReturnTip(501, "名称不能为空");
    	}
    	
    	try {
        	//如果没有传有效的card_type 则默认为0
        	Integer cardType = Convert.toInt(request.getParameter("cardType"), 0);
        	if (cardType.equals(0)) {
        		cardType = 0;
        	}
        	
        	mapEntity.put("clubId", clubId);
        	mapEntity.put("id", _id);
        	mapEntity.put("cardType", cardType);
        	mapEntity.put("title", title);
        	mapEntity.put("value", Convert.toInt(request.getParameter("cardValue")));
        	mapEntity.put("leaveMaxDay", Convert.toInt(request.getParameter("leaveMaxDay"), 0));
        	mapEntity.put("expireDay", Convert.toInt(request.getParameter("cardExpireDay"), 0));
        	mapEntity.put("expireTime", DateUtil.date2TimeStamp(request.getParameter("cardExpireTime"), "yyyy-MM-d"));
        	mapEntity.put("shareRestrict", Convert.toInt(request.getParameter("shareRestrict")));
        	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ReturnTip(501, "必要参数不能为空");
		}
    	
    	
    	CardCategory cardCategory = (CardCategory) ToolUtil.convertMap(CardCategory.class, mapEntity);
    	Wrapper<CardCategory> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	ew = ew.eq("id", _id);
    	
    	//验证信息是否存在
    	try {
			if (0 == cardCategoryService.selectCount(ew)) {
				cardCategory.setId(null);
				cardCategoryService.insert(cardCategory);
			} else {
				cardCategory.setId(_id);
				cardCategoryService.update(cardCategory, ew);
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
     	Wrapper<CardCategory> ew = new EntityWrapper<>();
     	ew = ew.eq("id", id);
    	ew = ew.eq("club_id", clubId);
    	try {
    		cardCategoryService.delete(ew);
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
    	CardCategory itemInDb = cardCategoryService.selectById(id);
    	
    	//验证合同所属俱乐部
    	if (!itemInDb.getClubId().equals(clubId)) {
    		return ResponseEntity.ok(new ReturnTip(501, "合同访问受限"));
    	}
    	
    	Map<String, Object> mapRet = null;
		try {
			mapRet = ToolUtil.convertBean(itemInDb);
			mapRet.put("expireTime", DateUtil.timeStamp2Date((Integer) mapRet.get("expireTime"), "yyyy-MM-dd"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	new CardCategoryWarpper(null).warpTheMap(mapRet);
    	
    	return new ReturnTip(0, "成功",  mapRet);
    }
}
