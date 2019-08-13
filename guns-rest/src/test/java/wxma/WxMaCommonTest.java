package wxma;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.core.common.constant.factory.MapItemFactory;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.mch.card.service.ICardPtraincardService;
import com.stylefeng.guns.modular.mch.card.service.ICardSpendLogService;
import com.stylefeng.guns.modular.mch.club.service.IClubCoachService;
import com.stylefeng.guns.modular.mch.member.service.IUserCommonService;
import com.stylefeng.guns.modular.mch.member.service.impl.UserCommonServiceImpl;
import com.stylefeng.guns.modular.mch.syllabus.service.IPtrainBespeakRecordService;
import com.stylefeng.guns.modular.system.model.UserCommon;
import com.stylefeng.guns.modular.wxma.service.IWxMaCommonService;
import com.stylefeng.guns.rest.common.ReturnTip;
import com.stylefeng.guns.rest.common.SimpleObject;

import cn.binarywang.wx.miniapp.api.WxMaService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 字典服务测试
 *
 * @author fengshuonan
 * @date 2017-04-27 17:05
 */
public class WxMaCommonTest extends BaseJunit {
	@Autowired
	private WxMaService wxService;
	@Autowired
	private IWxMaCommonService wxMaCommonService;
    
    @Test
    public void saveFormIdsTest() {
    	String openid =  "a111111";
    	ArrayList list = new ArrayList();
    	Integer currentTime = DateUtil.timeStamp();
    	
    	list.add(MapItemFactory.composeMap("formId", "ffff1", "expire", currentTime+86400));
    	list.add(MapItemFactory.composeMap("formId", "ffff2", "expire", currentTime+86400));
    	list.add(MapItemFactory.composeMap("formId", "ffff3", "expire", currentTime+86400));
    	
    	String json = JSON.toJSONString(list);
		JSONArray formIds = JSON.parseObject(json, JSONArray.class);
		
		boolean res = wxMaCommonService.saveFormIds(openid, formIds);
		
		Assert.assertTrue(res);
    }
    
    @Test
    public void getFormIdTest() {
    	String openid =  "a111111";
    	String formId1 = wxMaCommonService.getFormId(openid);
    	String formId2 = wxMaCommonService.getFormId(openid);
    	Assert.assertTrue(formId1.equals("ffff1"));
    	Assert.assertTrue(formId2.equals("ffff2"));
    }
    
}
