package com.stylefeng.guns.modular.member.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;

import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.member.service.IVipUserService;

/**
 * VIP用户控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 16:45:03
 */
@Controller
@RequestMapping("/vipUser")
public class VipUserController extends BaseController {

    private String PREFIX = "/member/vipUser/";

    @Autowired
    private IVipUserService vipUserService;

    /**
     * 跳转到VIP用户首页
     */
    @RequestMapping("")
    public String index(Integer clubId, Model model) {
    	if (ToolUtil.isEmpty(clubId)) {
    		clubId = 0;
    	}
    	
    	model.addAttribute("clubId", clubId);
        return PREFIX + "vipUser.html";
    }

    /**
     * 跳转到添加VIP用户
     */
    @RequestMapping("/vipUser_add")
    public String vipUserAdd() {
        return PREFIX + "vipUser_add.html";
    }

    /**
     * 跳转到修改VIP用户
     */
    @RequestMapping("/vipUser_update/{vipUserId}")
    public String vipUserUpdate(@PathVariable Integer vipUserId, Model model) {
        VipUser vipUser = vipUserService.selectById(vipUserId);
        model.addAttribute("item",vipUser);
        LogObjectHolder.me().set(vipUser);
        return PREFIX + "vipUser_edit.html";
    }

    /**
     * 获取VIP用户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer clubId) {
    	Page<VipUser> page = new PageFactory<VipUser>().defaultPage();
    	
    	page = vipUserService.pageList(page, clubId, condition);
        return super.packForBT(page);
    }

    /**
     * 新增VIP用户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(VipUser vipUser) {
        vipUserService.insert(vipUser);
        return SUCCESS_TIP;
    }

    /**
     * 删除VIP用户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer vipUserId) {
        vipUserService.deleteById(vipUserId);
        return SUCCESS_TIP;
    }

    /**
     * 修改VIP用户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(VipUser vipUser) {
        vipUserService.updateById(vipUser);
        return SUCCESS_TIP;
    }

    /**
     * VIP用户详情
     */
    @RequestMapping(value = "/detail/{vipUserId}")
    @ResponseBody
    public Object detail(@PathVariable("vipUserId") Integer vipUserId) {
        return vipUserService.selectById(vipUserId);
    }
}
