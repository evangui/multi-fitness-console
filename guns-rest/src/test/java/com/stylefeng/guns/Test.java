package com.stylefeng.guns;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.BeanMap;
import org.junit.experimental.theories.Theories;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.SimpleObject;
import com.stylefeng.guns.rest.modular.auth.converter.BaseTransferEntity;

/**
 * json测试
 *
 * @author fengshuonan
 * @date 2017-08-25 16:11
 */


public class Test {

    
	public static void main(String[] args) {
//    	Test.testCollection();
//    	Test.testJson();
//		testArray();
		testFloat();
    }
	
    @SuppressWarnings("unchecked")
    public static void testCollection() {
    	List<Object> layer1List = new ArrayList<Object>();
    	List<Object> layer2List = new ArrayList<Object>();
    	Map layer1Obj = new HashMap<>();
    	Map layer2Obj = new HashMap<>();
    	
    	layer2Obj.put("title", "系统设置");
    	layer2Obj.put("type", "sysSetting");
    	layer2Obj.put("jumpUrl", "/panel/club/1390/setting/index/");
    	layer2Obj.put("icon", "iconfont2");
    	layer2Obj.put("iconText", "&#xe617;");
    	layer2Obj.put("iconType", "1");
    	layer2List.add(0, layer2Obj);
    	
    	layer2Obj.put("title", "工作人员");
    	layer2Obj.put("type", "switchingClient");
    	layer2Obj.put("jumpUrl", "/panel/club/1390/setting/index/");
    	layer2Obj.put("icon", "iconfont2");
    	layer2Obj.put("iconText", "&#xe617;");
    	layer2Obj.put("iconType", "1");
    	layer2List.add(1, layer2Obj);
    	System.out.println("111");
    	System.out.println(layer2List);
    	layer1Obj.put("list", layer2List);
    	layer1Obj.put("title", "会员管理");
    	layer1Obj.put("type", "vipUserModel");
    	layer1List.add(layer1Obj);
    	layer1List.add(layer1Obj);

        System.out.println(JSON.toJSONString(layer1List));
    }
    
    public static void testJson() {
    	SimpleObject simpleObject = new SimpleObject();
        simpleObject.setUser("fsn");
        simpleObject.setAge(32);
        simpleObject.setTips("rrrrR");

        Map<String, Object> map = new HashMap<>();
        map.put("user", "aaa");
        map.put("age", 32);
        map.put("tips", "rrdsfs");
        @SuppressWarnings({ "unchecked", "deprecation" })
        
		Map<String, Object> map2 = new BeanMap(simpleObject);
        
        System.out.println(map2.toString());
        String json = JSON.toJSONString(simpleObject);
        String json2 = JSON.toJSONString(map);
        String json3 = JSON.toJSONString(map2);

        //Object转成的json字符串 转对象，与取属性值
        JSONObject obj11 = JSON.parseObject(json);
        SimpleObject obj12 = JSON.parseObject(json, SimpleObject.class);
//        System.out.println(obj11.get("user"));
//        System.out.println(obj12.getUser());
        
        //HashMap转成的json字符串 转对象，与取属性值
        JSONObject obj21 = (JSONObject) JSON.parse(json2);
        SimpleObject obj22 = JSON.parseObject(json2, SimpleObject.class);
        System.out.println(obj21.get("user"));
        System.out.println(obj22.getUser());
        
        //Object转成HashMap，再转成的json字符串 转对象，与取属性值
        JSONObject obj31 = JSON.parseObject(json3);
        SimpleObject obj32 = JSON.parseObject(json3, SimpleObject.class);
        System.out.println(obj31.get("user"));
        System.out.println(obj32.getUser());
    }
    
    public static void testArray() {
    	//1 声明数组
    	String[] aStrings;
    	String aString[];
    	int[] aInts;
    	int aInt[];
    	
    	String[] aStrings2 = new String[5];    	
    	String aString2[] = new String[5];
    	int[] aInts2 = new int[3];
    	int aInt2[] = new int[3];
    	
    	//2.初始化数组
    	//静态初始化
    	int arr21[] = new int[]{1,2,3,4,5};
    	int arr22[] = {1,2,3,4,5};
    	String s11[] = new String[]{"黄渤","张艺兴","孙红雷","小猪","牙哥","黄磊"};
    	String[] s12 = new String[]{"黄渤","张艺兴","孙红雷","小猪","牙哥","黄磊"};
    	String s21[] = {"黄渤","张艺兴","孙红雷","小猪","牙哥","黄磊"};
    	String[] s22 = {"黄渤","张艺兴","孙红雷","小猪","牙哥","黄磊"};
    	
    	//动态初始化
    	int scores[] = new int[3];
    	for(int i=0; i < scores.length; i++) {
    		scores[i] = i+1;
    	}
    	
//    	数组中是否包含某一个值；
    	String a="马超";
    	String[] array1={"马超","马云","关羽","刘备","张飞"};
    	if (Arrays.asList(array1).contains(a)) {
    	    System.out.println("马超在这里");
    	}
    	
    	
//    	8.将数组转成set集合；
    	String[] array2=new String[]{"黄渤","张艺兴","孙红雷","小猪","牙哥","黄磊"};
        Set<String> set=new HashSet<String>(Arrays.asList(array2));
        System.out.println(set);
    	
        
        ArrayList<Integer> list2 = new ArrayList<>(20);
        for (int i = 0; i<10;i++) {
        	list2.add(i);
        }
        ArrayList<Integer> list3 = new ArrayList<>(list2);
        System.out.println(list2);
        System.out.println(list3);
        
    }
    
    public static void testFloat() {
    	double d1 = 100.0234;
        double d2 = 12.0652;
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        
        System.out.println(d1-d2);
        System.out.println(b1.subtract(b2).floatValue());
        
        //小数点取2位
        String we = b1.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
        System.out.println(we);
    }
    
    
}