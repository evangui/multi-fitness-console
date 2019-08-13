package com.stylefeng.guns.modular.club.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.IdGenerator;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.dao.ClubMapper;
import com.stylefeng.guns.modular.system.model.Club;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.stylefeng.guns.modular.club.service.IClubService;

/**
 * 俱乐部主体信息控制器
 *
 * @author guiyajun
 * @Date 2018-06-22 16:01:23
 */
@Controller
@RequestMapping("/club")
public class ClubController extends BaseController {

    private String PREFIX = "/club/club/";

    @Resource
    private ClubMapper clubMapper;
    
    @Autowired
    private IClubService clubService;

    /**
     * 跳转到俱乐部主体信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "club.html";
    }

    /**
     * 跳转到添加俱乐部主体信息
     */
    @RequestMapping("/club_add")
    public String clubAdd() {
        return PREFIX + "club_add.html";
    }

    /**
     * 跳转到修改俱乐部主体信息
     */
    @RequestMapping("/club_update/{clubId}")
    public String clubUpdate(@PathVariable Integer clubId, Model model) {
        Club club = clubService.selectById(clubId);
        
        
        model.addAttribute("item",club);
        model.addAttribute("expireTime", DateUtil.timeStamp2Date(club.getExpireTime(), null));
        model.addAttribute("startTime", DateUtil.timeStamp2Date(club.getStartTime(), null));
        model.addAttribute("applyTime", DateUtil.timeStamp2Date(club.getApplyTime(), null));
        LogObjectHolder.me().set(club);
        return PREFIX + "club_edit.html";
    }

    /**
     * 获取俱乐部主体信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	Page<Club> page = new PageFactory<Club>().defaultPage("id", "desc");
    	page = clubService.pageList(page, condition);
        return super.packForBT(page);
    }

    /**
     * 新增俱乐部主体信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Club club) {
    	if (ToolUtil.isEmpty(club.getStartTime())) {
    		club.setStartTime(null);
    	}
    	if (ToolUtil.isEmpty(club.getExpireTime())) {
    		club.setExpireTime(null);
    	}
    	//默认logo
        if (ToolUtil.isEmpty(club.getLogo())) {
        	club.setLogo("https://xysmch.whbws.cn/assets/panel/images/logo_default.jpg");
        }
    	
        clubService.insert(club);
        
//    	认证码不能为空
        if (ToolUtil.isNotEmpty(club.getId())) {
        	String authCode = "xys-" + club.getId();
        	club.setAuthCode(authCode);
        	clubService.updateById(club);
        }
        return SUCCESS_TIP;
    }

    /**
     * 删除俱乐部主体信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer clubId) {
        clubService.deleteById(clubId);
        return SUCCESS_TIP;
    }

    /**
     * 修改俱乐部主体信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Club club) {
    	if (ToolUtil.isEmpty(club.getStartTime())) {
    		club.setStartTime(null);
    	}
    	if (ToolUtil.isEmpty(club.getExpireTime())) {
    		club.setExpireTime(null);
    	}
    	if (ToolUtil.isEmpty(club.getApplyTime())) {
    		club.setApplyTime(null);
    	}
    	if (ToolUtil.isEmpty(club.getLogo())) {
        	club.setLogo("https://xysmch.whbws.cn/assets/panel/images/logo_default.jpg");
        }
        clubService.updateById(club);
        return SUCCESS_TIP;
    }

    /**
     * 俱乐部主体信息详情
     */
    @RequestMapping(value = "/detail/{clubId}")
    @ResponseBody
    public Object detail(@PathVariable("clubId") Integer clubId) {
        Club club = clubService.selectById(clubId);
        return club;
    }
    
    /**
     * json格式返回 俱乐部列表信息查询 
     */
    @ApiOperation(value="俱乐部列表信息查询", notes="描述", tags="tags1", response=Club.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name="condition", value="请求条件",required=false, dataType="String", paramType="query")
    })
    @RequestMapping(value = "/searchList", method = RequestMethod.GET)
    @ResponseBody
    public Object searchList( String condition) {
        return clubService.searchList(condition);
    }
}
