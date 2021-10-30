package com.artcweb.constant;

import java.util.HashMap;
import java.util.Map;

public class SearchConstant {
	
	public static Map<String,String> nailOrderMap = new HashMap<String,String>();
	
	static{
		init();
	}

	public static void init() {
		initNailOrderMap();
		
	}

	public static void initNailOrderMap() {
		if(null != nailOrderMap && nailOrderMap.size() > 0){
			return;
		}
		nailOrderMap.put("1", "username");
		nailOrderMap.put("2", "imageName");
		nailOrderMap.put("3", "mobile");
	}
	public static String getNailOrderMapBySearchKey(Map<String,String> map,String searchKey) {
		if(null == map || map.size() < 1){
			return null;
		}
		return map.get(searchKey);
		
		
	}
	
	

}
