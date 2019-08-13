package com.stylefeng.guns.modular.mch.member.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;

import com.stylefeng.guns.modular.system.model.ClubAdmin;
import com.stylefeng.guns.modular.system.model.UserPotential;
import com.stylefeng.guns.modular.system.warpper.ClubCoachWarpper;
import com.stylefeng.guns.modular.system.warpper.UserPotentialWarpper;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.modular.mch.member.service.IUserPotentialService;

/**
 * 潜在客户控制器
 *
 * @author fengshuonan
 * @Date 2018-06-22 17:13:08
 */
@Controller
@RequestMapping("/mch/member/userPotential")
public class UserPotentialController extends BaseController {

    @Autowired
    private IUserPotentialService userPotentialService;

    /**
     * 获取VIP用户查询统计表头
     */
    @RequestMapping(value = "/statHeader")
    @ResponseBody
    public Object statHeader() {
    	HttpServletRequest request = this.getHttpServletRequest();
    	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效"));
     	}
     	
     	Map<String, Object> mapRet = new HashMap<>();
     	//全部潜在客户
     	mapRet.put("allmembership", userPotentialService.statCountMap(clubId,"allmembership"));
     	//未绑定会籍
     	mapRet.put("nomembership", userPotentialService.statCountMap(clubId,"nomembership"));
     	//未绑定教练
     	mapRet.put("noCoach", userPotentialService.statCountMap(clubId,"noCoach"));
     	//最近添加
     	mapRet.put("addPotential", userPotentialService.statCountMap(clubId,"addPotential"));
     	//最近未维护
     	mapRet.put("noMaintainPotential", userPotentialService.statCountMap(clubId,"noMaintainPotential"));
     	
        return new ReturnTip(0, "成功", mapRet);
    }
    
     /**
      * 获取VIP用户列表
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
     	mapCondition.put("realname", Convert.toStr(request.getParameter("realname"), null));
     	mapCondition.put("phone", Convert.toStr(request.getParameter("phone"), null));
     	mapCondition.put("nickname", Convert.toStr(request.getParameter("nickname"), null));
     	mapCondition.put("membershipName", Convert.toStr(request.getParameter("mshipname"), null));
     	mapCondition.put("isVipUser", Convert.toInt(request.getParameter("isVipUser")));
     	mapCondition.put("labelLevels", Convert.toInt(request.getParameter("vipUserPotentialUser")));
     	mapCondition.put("isCardOpen", Convert.toStr(request.getParameter("isCardOpen"), null));
     	mapCondition.put("hdSearchKey", Convert.toStr(request.getParameter("hdSearchKey"), null));
     	mapCondition.put("coachName", Convert.toStr(request.getParameter("coachName"), null));
     	mapCondition.put("startDate", Convert.toStr(request.getParameter("startDate"), null));
     	mapCondition.put("endDate", Convert.toStr(request.getParameter("endDate"), null));
     	
     	//查询范围表头
     	String hdSearchKey = Convert.toStr(request.getParameter("hdSearchKey"), "");
     	
     	Page<UserPotential> page = new PageFactory<UserPotential>().defaultPage("id", "desc");
     	page = userPotentialService.pageList(page, clubId,   mapCondition, hdSearchKey);
         
     	Map<String, Object> ret = super.packForPannelTable(page);
     	ret.put("isShowPoint", 1);
     	ret.put("execptDataType", 0);
     	
     	HashMap<String, Object> mapTime = new HashMap<>();
     	mapTime.put("startTime", 1528769716670L);
     	mapTime.put("endTime", 1528769716672L);
     	mapTime.put("endTime2", 1528769717366L);
     	ret.put("time", mapTime);
     	
        return new ReturnTip(0, "成功", ret);
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
     	
     	Integer _id = Convert.toInt(request.getParameter("id"), 0);
     	
     	//入库对象
     	Map<String, Object> mapEntity = new HashMap<>();
     	mapEntity.put("clubId", clubId);
     	mapEntity.put("id", _id);
     	
     	mapEntity.put("realname", Convert.toStr(request.getParameter("name")));
     	mapEntity.put("gender", Convert.toInt(request.getParameter("gender")));
     	mapEntity.put("phone", Convert.toStr(request.getParameter("phone")));
     	mapEntity.put("membershipId", Convert.toInt(request.getParameter("membershipId")));
     	mapEntity.put("remark", Convert.toStr(request.getParameter("remark")));
     	mapEntity.put("coachId", Convert.toInt(request.getParameter("coachId")));
     	mapEntity.put("sourceId", Convert.toInt(request.getParameter("source")));
     	
     	UserPotential vipUser = (UserPotential) ToolUtil.convertMap(UserPotential.class, mapEntity);
      
     	Wrapper<UserPotential> ew = new EntityWrapper<>();
     	ew = ew.eq("club_id", clubId);
     	ew = ew.eq("id", _id);
     	
     	//验证信息是否存在
     	try {
 			if (0 == userPotentialService.selectCount(ew)) {
 				vipUser.setId(null);
 				vipUser.setInsertTime(DateUtil.timeStamp());
 				userPotentialService.insert(vipUser);
 			} else {
 				vipUser.setId(_id);
 				userPotentialService.update(vipUser, ew);
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
      	Wrapper<UserPotential> ew = new EntityWrapper<>();
      	ew = ew.eq("id", id);
     	ew = ew.eq("club_id", clubId);
     	try {
     		userPotentialService.delete(ew);
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
     	UserPotential itemInDb = userPotentialService.selectById(id);
     	
     	//验证所属俱乐部
     	if (!itemInDb.getClubId().equals(clubId)) {
     		return ResponseEntity.ok(new ReturnTip(501, "访问受限"));
     	}
     	
     	Map<String, Object> mapRet = null;
 		try {
 			mapRet = ToolUtil.convertBean(itemInDb);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	new UserPotentialWarpper(null).warpTheMap(mapRet);
     	
     	return new ReturnTip(0, "成功",  mapRet);
     }
     
     /**
      * 与一般用户绑定
      */
     @RequestMapping(value = "/bindUser")
     @ResponseBody
     public Object bindUser() throws Exception {
     	HttpServletRequest request = this.getHttpServletRequest();
     	ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
     	Integer clubId = clubAdmin.getClubId();
     	if (ToolUtil.isEmpty(clubId)) {
     		return new ReturnTip(500, "俱乐部信息失效");
     	}
     	
     	Integer id = ToolUtil.toInt(request.getParameter("id"));
     	Integer userId = ToolUtil.toInt(request.getParameter("userId"));
     	String type = ToolUtil.toStr(request.getParameter("type"));
     	String name = ToolUtil.toStr(request.getParameter("name"));
     	
     	
     	boolean bindingRes = userPotentialService.bindMember(clubId, id, userId, name, type);
     	if (bindingRes) {
     		return new ReturnTip(0, "操作成功");
     	} else {
     		return new ReturnTip(501, "操作失败");
     	}
     	
     }
     
     /**
 	 * 从excel批量导入潜在用户
 	 * 
 	 * @param file
 	 * @param response
 	 * @return
 	 * @throws Exception
 	 */
 	@ResponseBody  
     @RequestMapping(value="uploadInfoExcel",method={RequestMethod.GET,RequestMethod.POST})  
 	public Object uploadInfoExcel(@RequestParam("file") CommonsMultipartFile file, HttpServletResponse response) throws Exception {  
 		HttpServletRequest request = this.getHttpServletRequest();
 		ClubAdmin clubAdmin = (ClubAdmin) request.getAttribute("member");
 		Integer clubId = clubAdmin.getClubId();
 		if (ToolUtil.isEmpty(clubId)) {
 			return ResponseEntity.ok(new ReturnTip(500, "俱乐部信息失效，请重新登录"));
 		}
// 		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
//         MultipartFile file = multipartRequest.getFile("file");  
         
         if(file.isEmpty()){  
         	return ResponseEntity.ok(new ReturnTip(500, "上传的文件无效！"));
         }  
         
         try {
         	InputStream in = file.getInputStream();
         	boolean uploadRes = userPotentialService.uploadPotentialInfoExcel(clubId, in, file);
         	in.close();
         	
         	if (uploadRes) {
         		return ResponseEntity.ok(new ReturnTip(0, "上传成功"));
         	} else {
         		return ResponseEntity.ok(new ReturnTip(503, "上传失败"));
         	}
             
 		} catch (Exception e) {
 			// TODO: handle exception
 			return ResponseEntity.ok(new ReturnTip(502, e.getMessage()));
 		}
     }
}
