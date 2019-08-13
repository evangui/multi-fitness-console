package com.stylefeng.guns.core.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.baomidou.mybatisplus.toolkit.IdWorker;

/**
 * 唯一id生成器
 *
 * @author fengshuonan
 * @date 2017-08-23 11:10
 */
public class IdGenerator {

    public static String getId() {
        return String.valueOf(IdWorker.getId());
    }

    public static long getIdLong() {
        return IdWorker.getId();
    }
    
    public static  String getTimeId() {
//    	return DateUtil.getAllTime() + new Random().nextInt(1000);
    	return DateFormatUtils.format(new Date(), "yyMMddHHmmss") + Math.round(Math.random()*(9999-1000)+1000);
    }
    
    
}
