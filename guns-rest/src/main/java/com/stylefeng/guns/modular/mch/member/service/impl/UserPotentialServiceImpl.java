package com.stylefeng.guns.modular.mch.member.service.impl;

import com.stylefeng.guns.modular.system.model.ClubCoach;
import com.stylefeng.guns.modular.system.model.StaffSpecial;
import com.stylefeng.guns.modular.system.model.UserPotential;
import com.stylefeng.guns.modular.system.model.VipUser;
import com.stylefeng.guns.modular.system.warpper.UserPotentialWarpper;
import com.stylefeng.guns.modular.system.dao.UserPotentialMapper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.common.constant.factory.MapItemFactory;
import com.stylefeng.guns.core.support.ExcelUtils;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.club.service.IClubCoachService;
import com.stylefeng.guns.modular.mch.club.service.IStaffSpecialService;
import com.stylefeng.guns.modular.mch.member.service.IUserPotentialService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 潜在客户表 服务实现类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
@Service
public class UserPotentialServiceImpl extends ServiceImpl<UserPotentialMapper, UserPotential> implements IUserPotentialService {
	@Autowired
	IClubCoachService clubCoachService;
	@Autowired
	IStaffSpecialService staffSpecialService;

	/**
     * <p>
     * 分页列表查询
     * </p>
     *
     * @param page 分页查询条件
     * @param clubId   俱乐部id
     * @param condition   其他模糊查询条件
     * @param hdSearchKey   查询范围表头
     * @return List<>
     */
	@SuppressWarnings("unchecked")
	@Override
	public Page<UserPotential> pageList(Page<UserPotential> page, Integer clubId, HashMap<String, Object> condition, String hdSearchKey) {
    	Wrapper<UserPotential> ew = new EntityWrapper<>();
    	
    	if (ToolUtil.isNotEmpty(condition.get("id"))) {
    		ew = ew.eq("id", (Integer) condition.get("id"));
    	}
    	if (ToolUtil.isNotEmpty(clubId) && !clubId.equals(0)) {
    		ew = ew.eq("club_id", clubId);
    	}
    	if (ToolUtil.isNotEmpty(condition.get("isVipUser"))) {
    		ew = ew.eq("is_vip_user", ToolUtil.toInt(condition.get("isVipUser")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("phone"))) {
    		ew = ew.eq("phone", (String) condition.get("phone"));
    	}
//    	维护跟进的关注度：0未设置 1普通 2高关注 3不维护
    	if (ToolUtil.isNotEmpty(condition.get("labelLevels")) && !condition.get("labelLevels").equals(0)) {
    		ew = ew.eq("label_levels", ToolUtil.toInt(condition.get("labelLevels")));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("realname"))) {
    		ew = ew.like("realname", (String) condition.get("realname"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("nickname"))) {
    		ew = ew.like("nickname", (String) condition.get("nickname"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("membershipName"))) {
    		ew = ew.like("membership_name", (String) condition.get("membershipName"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("coachName"))) {
    		ew = ew.like("coach_name", (String) condition.get("coachName"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("startDate"))) {
    		ew = ew.ge("insert_time", DateUtil.date2TimeStamp((String) condition.get("startDate"), "yyyy-MM-dd"));
    	}
    	if (ToolUtil.isNotEmpty(condition.get("endDate"))) {
    		ew = ew.le("insert_time", DateUtil.date2TimeStamp((String) condition.get("endDate"), "yyyy-MM-dd"));
    	}
    	
    	
    	//查询范围表头
    	switch (hdSearchKey) {
		case "allmembership":
			break;
		case "nomembership":
			//未绑定会籍
			ew = ew.eq("membership_id", 0);
			break;	
		case "noCoach":
			//未绑定教练
			ew = ew.eq("coach_id", 0);
			break;			
		case "addPotential":
			//最近添加
			ew = ew.gt("insert_time", DateUtil.timeStamp() - 10*86400);
			break;	
		case "noMaintainPotential":
			//最近未维护
			ew = ew.lt("last_maintain_time", DateUtil.timeStamp() - 30*86400);
			break;		
		default:
			break;
		}
    	
    	List<Map<String, Object>> result = baseMapper.selectMapsPage(page, ew);
        return page.setRecords( (List<UserPotential>) new UserPotentialWarpper(result).warp());
	}
	
	
	/**
     * <p>
     * 查询某项具体表头信息
     * </p>
     *
     * @param hdSearchKey   查询范围表头
     * @return List<>
     */
	@Override
	public Map<String, Object> statCountMap(Integer clubId, String hdSearchKey) {
    	Wrapper<UserPotential> ew = new EntityWrapper<>();
    	ew = ew.eq("club_id", clubId);
    	
    	//查询范围表头
    	switch (hdSearchKey) {
		case "allMember":
			break;
		case "privateVip":
			//私教会员
			ew = ew.gt("personal_trainer_card_counts", 0);
			break;
		case "noExpireMember":
			//未过期会员
			ew = ew.gt("expire_time", DateUtil.timeStamp());
			break;
		case "insertMember":
			//新增会员
			ew = ew.gt("insert_time", DateUtil.timeStamp() - 10*86400);
			break;
		case "noneExpire":
			//即将到期
			ew = ew.gt("expire_time", DateUtil.timeStamp());
			ew = ew.lt("expire_time", DateUtil.timeStamp() + 30*86400);
			break;
		case "noneDue":
			//最近已过期
			ew = ew.lt("expire_time", DateUtil.timeStamp());
			ew = ew.gt("expire_time", DateUtil.timeStamp() - 30*86400);
			break;
		case "birthday":
			//最近生日
			// @TODO 
			ew = ew.gt("birthday", DateUtil.timeStamp());
			ew = ew.lt("birthday", DateUtil.timeStamp() + 30*86400);
		case "noBindmembership":
			//未绑定会籍
			ew = ew.eq("membership_id", 0);
			break;	
		case "noBindCoach":
			//未绑定教练
			ew = ew.eq("coach_id", 0);
			break;		
		case "noMaintain":
			//最近未维护
			ew = ew.lt("last_maintain_time", DateUtil.timeStamp() - 30*86400);
			break;	
		case "noSign":
			//最近未签到
			ew = ew.lt("last_sign_time", DateUtil.timeStamp() - 30*86400);
			break;	
		default:
			break;
		}
    	
    	Integer cnt = baseMapper.selectCount(ew);
    	return MapItemFactory.getStatItem(cnt, 0, null);
	}
	
	/**
	 * <p>
	 * 将潜在用户 与特定用户将星绑定
	 * </p>
	 * @param clubId
	 * @param vipId
	 * @param bindingId
	 * @param type ： user coach membership
	 * 
	 * @return
	 */
	@Override
	public boolean bindMember(int clubId, int potentialId, int bindingId, String name, String type) {
    	
		UserPotential entity = new UserPotential();
		
		switch (type) {
		case "coach":
			entity.setCoachId(bindingId);
			//名称为空，则根据id获取名称
			if (ToolUtil.isEmpty(name)) {
				name = ConstantFactory.me().getCoachNameById(bindingId);
			}
			entity.setCoachName(name);
			break;
		case "membership":
			entity.setMembershipId(bindingId);
			//名称为空，则根据id获取名称
			if (ToolUtil.isEmpty(name)) {
				name = ConstantFactory.me().getStaffSpecialNameById(bindingId);
			}
			entity.setMembershipName(name);
			break;
		default:
			return false;
		}
		 
		Wrapper<UserPotential> ew = new EntityWrapper<>();
		ew = ew.eq("id", potentialId);
		ew = ew.eq("club_id", clubId);
		
     	try {
     		this.update(entity, ew);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			return false;
 		}
     	
     	return true;
	}
	
	@Override
	public boolean uploadPotentialInfoExcel(Integer clubId, InputStream in, MultipartFile file) throws Exception {
		List listErr = new ArrayList();	//行处理错误记录列表

		/**
		 * 1. 获取excel表的行列数据二维列表
		 */
		List<List<Object>> listob = ExcelUtils.getBankListByExcel(in, file.getOriginalFilename(), null);
//		List<VipUser> vipUserInfoList = new ArrayList<VipUser>();

		String sFirstColumVal = null;
		String sBirthdayVal = null;
		String sBirthdayFormat = null;
		String sCoachName = null;
		String sMembershipName = null;
		int newVipId = 0;
		int currentTime = DateUtil.timeStamp();

		for (int i = 0; i < listob.size(); i++) {

			List<Object> ob = listob.get(i);

			// 如果是模板中的表头数据，则当前行忽略
			sFirstColumVal = Convert.toStr(ob.get(0)).trim();
			if (sFirstColumVal.startsWith("姓名") || sFirstColumVal.startsWith("必填")) {
				continue;
			}

			/**
			 * 2. 按行添加用户信息
			 */
			UserPotential userPotentialBean = new UserPotential();
			userPotentialBean.setClubId(clubId);
			userPotentialBean.setInsertTime(currentTime);
			// 1 姓名
			userPotentialBean.setRealname(String.valueOf(ob.get(0)));
			// 2 手机号
			userPotentialBean.setPhone(String.valueOf(ob.get(1)));
			// 3 关注度标签（ 0未设置 1普通 2高关注 3不维护）
			Integer strLabelLevels = new Integer(0);
			if (String.valueOf(ob.get(2)).equals("不维护")) {
				strLabelLevels = 3;
			} else if (String.valueOf(ob.get(2)).equals("高关注")) {
				strLabelLevels = 2;
			} else if (String.valueOf(ob.get(2)).equals("普通")) {
				strLabelLevels = 1;
			}
			userPotentialBean.setLabelLevels(strLabelLevels);
			 
			// 4  教练，根据姓名查找教练
			sCoachName = String.valueOf(ob.get(3));
			try {
				ClubCoach clubCoach = clubCoachService.getByRealname(clubId, sCoachName);
				userPotentialBean.setCoachId(clubCoach.getId());
				userPotentialBean.setCoachName(clubCoach.getRealname());
			} catch (Exception e) {
				// TODO: handle exception
				userPotentialBean.setCoachId(0);
				userPotentialBean.setCoachName(sCoachName);
			}
			// 5 会籍，根据姓名查找会籍
			sMembershipName = String.valueOf(ob.get(4));
			try {
				StaffSpecial staffSpecial = staffSpecialService.getByRealname(clubId, sMembershipName, 0);
				userPotentialBean.setMembershipId(staffSpecial.getId());
				userPotentialBean.setMembershipName(staffSpecial.getRealname());
			} catch (Exception e) {
				// TODO: handle exception
				userPotentialBean.setMembershipId(0);
				userPotentialBean.setMembershipName(sMembershipName);
			}
			
			// 6 会员备注
			userPotentialBean.setRemark(String.valueOf(ob.get(5)));

			// 插入会员数据
			try {
				if (!this.insert(userPotentialBean)) {
					listErr.add("第" + i + "行(" + userPotentialBean.getRealname() + ") 导出出错");
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				listErr.add("第" + i + "行(" + userPotentialBean.getRealname() + ") 导出出错");
				continue;
			}

		}
		
		if (listErr.size() == 0) {
			return true;
		} else {
			throw new Exception(listErr.toString());
		}
	}
}
