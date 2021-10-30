package com.artcweb.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.artcweb.bean.NailCount;

public class MapUtil {

	/**
	* @Title: mapSortForIntegerKey
	* @Description: map排序，当key为Integer
	* @author zhuzq
	* @date  2021年2月2日 上午9:08:39
	* @param nailCountMap
	* @return
	*/
	public static LinkedHashMap<String, NailCount> mapSortForIntegerKey(LinkedHashMap<Integer, NailCount> nailCountMap) {
		LinkedHashMap<String, NailCount> result = new LinkedHashMap<String, NailCount>();
		if(null == nailCountMap || nailCountMap.size() < 1){
			return result;
		}
		List<Map.Entry<Integer, NailCount>> list = new ArrayList<Map.Entry<Integer, NailCount>>(
				nailCountMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, NailCount>>() {
			public int compare(Map.Entry<Integer, NailCount> o1, Map.Entry<Integer, NailCount> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});

		
		for (Entry<Integer, NailCount> mapping : list) {
			result.put(mapping.getValue().getIndexId() + "", mapping.getValue());
		}

		return result;
	}
	
	
	/**
	* @Title: mapSortForStringKey
	* @Description: map排序，当key为String
	* @author zhuzq
	* @date  2021年2月2日 上午9:09:10
	* @param nailCountMap
	* @return
	*/
	public static LinkedHashMap<String, NailCount> mapSortForStringKey(LinkedHashMap<String, NailCount> nailCountMap) {
		
		LinkedHashMap<String, NailCount> result = new LinkedHashMap<String, NailCount>();
		if(null == nailCountMap || nailCountMap.size() < 1){
			return result;
		}
		LinkedHashMap<Integer, NailCount> IntegerResult = new LinkedHashMap<Integer, NailCount>();
		for (Entry<String, NailCount> mapping : nailCountMap.entrySet()) {
			IntegerResult.put(mapping.getValue().getSort(), mapping.getValue());
		}
	
		result = mapSortForIntegerKey(IntegerResult);
		return result;
	}
}
