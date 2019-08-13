package com.stylefeng.guns.core.common.constant.factory;

import java.util.HashMap;
import java.util.Map;

public class MapItemFactory {
	public static Map<String, Object> composeMap(String param1, Object val1) {
		Map<String, Object> map = new HashMap<>();
		map.put(param1, val1);
		return map;
	}
	public static Map<String, Object> composeMap(String param1, Object val1, String param2, Object val2) {
		Map<String, Object> map = new HashMap<>();
		map.put(param1, val1);
		map.put(param2, val2);
		return map;
	}
	public static Map<String, Object> composeMap(String param1, Object val1, String param2, Object val2,
			String param3, Object val3) {
		Map<String, Object> map = new HashMap<>();
		map.put(param1, val1);
		map.put(param2, val2);
		map.put(param3, val3);
		return map;
	}
	public static Map<String, Object> composeMap(String param1, Object val1, String param2, Object val2,
			String param3, Object val3, String param4, Object val4) {
		Map<String, Object> map = new HashMap<>();
		map.put(param1, val1);
		map.put(param2, val2);
		map.put(param3, val3);
		map.put(param4, val4);
		return map;
	}
	public static Map<String, Object> composeMap(String param1, Object val1, String param2, Object val2,
			String param3, Object val3, String param4, Object val4, String param5, Object val5) {
		Map<String, Object> map = new HashMap<>();
		map.put(param1, val1);
		map.put(param2, val2);
		map.put(param3, val3);
		map.put(param4, val4);
		map.put(param5, val5);
		return map;
	}
	
	public static Map<String, Object> getStatItem(Integer counts, Integer number,Integer[] userIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("counts", counts);
		map.put("number", number);
		map.put("userIds", userIds);
		return map;
	}
	
	public static Map<String, Object> getCardHeaderItem(Integer count, Integer type, String message) {
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("type", type);
		map.put("message", message);
		return map;
	}
	
	public static Map<String, Object> getCardCouts(Integer counts, Integer usedCounts) {
		Map<String, Object> map = new HashMap<>();
		map.put("counts", counts);
		map.put("usedCounts", usedCounts);
		return map;
	}
}
