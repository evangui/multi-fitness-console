package com.stylefeng.guns.modular.club.service;

import com.stylefeng.guns.modular.system.model.StaffSpecial;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 手机端工作人员-会籍前台主管 服务类
 * </p>
 *
 * @author guiyj123
 * @since 2018-06-22
 */
public interface IStaffSpecialService extends IService<StaffSpecial> {

	Page<StaffSpecial> pageList(Page<StaffSpecial> page, Integer clubId, String condition);

}
