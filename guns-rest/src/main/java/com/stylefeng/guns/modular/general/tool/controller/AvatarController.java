package com.stylefeng.guns.modular.general.tool.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.cache.CacheKit;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.model.Club;
import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.system.warpper.UserCommonWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.modular.mch.club.service.IClubService;
import com.stylefeng.guns.modular.mch.member.service.IUserClubService;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;

/**
 * 用户头像控制器
 *
 * @author guiyj007
 * @Date 2018-06-22 17:45:38
 */
@Controller("general_avatarController")
@RequestMapping("/general/tool/avatar")
public class AvatarController extends BaseController {
    @Autowired
    private IUserCommonService userCommonService;
     

    /**
     * 俱乐部管理员详情
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
	@GetMapping(value = "")
    @ResponseBody
    public void detail(HttpServletResponse response) throws IOException {
    	HttpServletRequest request = this.getHttpServletRequest();
		Integer userId = Convert.toInt(request.getParameter("id"), 0);
		String defaultAvatar = "http://xysmch.whbws.cn/assets/panel/images/avatar_default.png";
		
		response.reset();
    	if (ToolUtil.isEmpty(userId)) {
    		response.sendRedirect(defaultAvatar);
    	}
    	
    	UserCommon itemInDb = null;
    	try {
    		//获取内容
        	itemInDb = ConstantFactory.me().getUserCommonById(userId);
        	
		} catch (NullPointerException e) {
			// TODO: handle exception
			response.sendRedirect(defaultAvatar);
			return;
		}
    	
    	if (null == itemInDb) {
    		response.sendRedirect(defaultAvatar);
    	} else {
    		response.sendRedirect(itemInDb.getAvatar());
    	}
    	
    }
}
