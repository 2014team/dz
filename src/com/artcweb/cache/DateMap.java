package com.artcweb.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.baen.NailConfig;
import com.artcweb.baen.NailDetailConfig;

public class DateMap {

	// 化数量配置
	public static Map<String, NailDetailConfig> nailDetailConfigMap = new HashMap<String, NailDetailConfig>();
	
	// 详细配置
	public static Map<String,NailConfig> nailConfigMap = new HashMap<String,NailConfig>();

	/**
	* @Title: initNailDetailConfigMap
	* @Description: 初始化数量配置
	* @param list
	*/
	public static void initNailDetailConfigMap(List<NailDetailConfig> list) {
		if (nailDetailConfigMap.size() > 0) {
			return;
		}
		if (null != list && list.size() > 0) {
			for (NailDetailConfig nailDetailConfig : list) {
				String rgb = nailDetailConfig.getRgb();
				nailDetailConfigMap.put(rgb, nailDetailConfig);
			}
		}
	}

	/**
	* @Title: initNailConfigList
	* @Description: 详细配置
	* @param list
	*/
	public static void initNailConfigList(List<NailConfig> list) {
		if (nailConfigMap.size() > 0) {
			return;
		}
		if (null != list && list.size() > 0) {
			for (NailConfig nailConfig : list) {
				Integer rgb = nailConfig.getId();
				nailConfigMap.put(String.valueOf(rgb), nailConfig);
			}
		}
	}

	/**
	* @Title: synChroNailDetailConfigMap
	* @Description: 修改操作后，数量配置同步缓存
	* @param list
	*/
	public static void synChroNailDetailConfigMap(List<NailDetailConfig> list) {
		nailDetailConfigMap.clear();
		if (null != list && list.size() > 0) {
			for (NailDetailConfig nailDetailConfig : list) {
				String rgb = nailDetailConfig.getRgb();
				nailDetailConfigMap.put(rgb, nailDetailConfig);
			}
		}
	}

	/**
	* @Title: synNailConfigList
	* @Description: 修改操作后，详细配置同步缓存
	* @param list
	*/
	public static void synNailConfigList(List<NailConfig> list) {
		nailConfigMap.clear();
		if (null != list && list.size() > 0) {
			for (NailConfig nailConfig : list) {
				Integer rgb = nailConfig.getId();
				nailConfigMap.put(String.valueOf(rgb), nailConfig);
			}
		}
	}
}
