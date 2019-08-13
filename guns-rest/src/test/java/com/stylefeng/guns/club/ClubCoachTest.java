package com.stylefeng.guns.club;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.card.service.ICardPtraincardService;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;
import com.stylefeng.guns.modular.mch.club.service.IClubCoachService;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;
import com.stylefeng.guns.modular.mch.member.service.impl.UserCommonServiceImpl;
import com.stylefeng.guns.modular.mch.syllabus.service.IPtrainBespeakRecordService;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.rest.common.ReturnTip;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典服务测试
 *
 * @author fengshuonan
 * @date 2017-04-27 17:05
 */
public class ClubCoachTest extends BaseJunit {

	@Autowired
    private IClubCoachService clubCoachService;
    @Autowired
    private IPtrainBespeakRecordService ptrainBespeakRecordService;
    @Autowired
    private ICardSpendLogService cardSpendLogService;
    @Autowired
    private ICardPtraincardService cardPtraincardService;
    
    @Test
    public void homeStatTest() {
    	Integer coachId = 1;
        Integer clubId = 1;

    	//今日消课数
     	Integer newFinishedPtrainNum = cardSpendLogService.countLatestFinishPtrain(clubId, coachId, 86400);
    	//获取某教练新增预约数量（未确认状态）
     	Integer newBespeakNum = ptrainBespeakRecordService.countLatestForCoach(clubId, coachId, 86400);
    	//最近销售的课程数量
     	Integer newPtrainCardNum = cardPtraincardService.countLatestForCoach(clubId, coachId, 86400);
    	
    	Map<String, Integer> mapRet = new HashMap<>();
    	//不显示后台权限数据
    	mapRet.put("newFinishedPtrainNum", newFinishedPtrainNum);
    	mapRet.put("newBespeakNum", newBespeakNum);
    	mapRet.put("newPtrainCardNum", newPtrainCardNum);
        Assert.assertTrue(Convert.toInt(mapRet.get("newFinishedPtrainNum")) >= 0);
    }
    
}
