package com.stylefeng.guns.modular.general.tool.controller;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.MapItemFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.ToolUtil;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.rest.common.ReturnTip;

/**
 * 俱乐部管理员控制器
 *
 * @author guiyj007
 * @Date 2018-06-25 10:24:28
 */
@Controller
@RequestMapping("/general/tool/id")
public class IdController extends BaseController {
    /**
     * 服务端生成卡号（确保卡号唯一性，暂时用时间错+随机数代替）
     */
    @RequestMapping(value = "/getCardNumber")
    @ResponseBody
    public Object getCardNumber() {
    	String cardNumber = IdGenerator.getTimeId();
    	return new ReturnTip(0, "成功", MapItemFactory.composeMap("cardNumber", cardNumber));
    }
    
}
