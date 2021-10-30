package com.artcweb.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.bean.NailConfig;
import com.artcweb.bean.NailDetailConfig;
import com.artcweb.bean.NailImageSize;

public class DateMap {

	// 颜色配置
	public static Map<String, NailDetailConfig> nailDetailConfigMap = new HashMap<String, NailDetailConfig>();
	
	// 图钉类型配置
	public static Map<String,NailConfig> nailConfigMap = new HashMap<String,NailConfig>();
	
	// 图片尺寸大小
	public static Map<String,NailImageSize> nailImageSizeMap = new HashMap<String,NailImageSize>();

	/**
	* @Title: initNailDetailConfigMap
	* @Description: 初始化颜色配置
	* @param list
	*/
	public static void initNailDetailConfigMap(List<NailDetailConfig> list) {
		if (nailDetailConfigMap.size() > 0) {
			return;
		}
		if (null != list && list.size() > 0) {
			for (NailDetailConfig entity : list) {
				String rgb = entity.getRgb();
				nailDetailConfigMap.put(rgb, entity);
			}
		}
	}

	/**
	* @Title: initNailConfigMap
	* @Description: 图钉类型
	* @param list
	*/
	public static void initNailConfigMap(List<NailConfig> list) {
		if (nailConfigMap.size() > 0) {
			return;
		}
		if (null != list && list.size() > 0) {
			for (NailConfig entity : list) {
				Integer  id= entity.getId();
				nailConfigMap.put(String.valueOf(id), entity);
			}
		}
	}
	
	/**
	* @Title: initNailImageSizeMap
	* @Description: 尺寸大小
	* @param list
	*/
	public static void initNailImageSizeMap(List<NailImageSize> list) {
		if (nailImageSizeMap.size() > 0) {
			return;
		}
		if (null != list && list.size() > 0) {
			for (NailImageSize entity : list) {
				String key = entity.getWidth()+"x"+entity.getHeight();
				nailImageSizeMap.put(key, entity);
			}
		}
	}

	/**
	* @Title: synChroNailDetailConfigMap
	* @Description: 修改操作后，颜色同步缓存
	* @param list
	*/
	public static void synChroNailDetailConfigMap(List<NailDetailConfig> list) {
		nailDetailConfigMap.clear();
		if (null != list && list.size() > 0) {
			for (NailDetailConfig entity : list) {
				String rgb = entity.getRgb();
				nailDetailConfigMap.put(rgb, entity);
			}
		}
	}

	/**
	* @Title: synNailConfigList
	* @Description: 修改操作后，图钉类型同步缓存
	* @param list
	*/
	public static void synNailConfigMap(List<NailConfig> list) {
		nailConfigMap.clear();
		if (null != list && list.size() > 0) {
			for (NailConfig entity : list) {
				Integer rgb = entity.getId();
				nailConfigMap.put(String.valueOf(rgb), entity);
			}
		}
	}
	
	/**
	* @Title: synNailImageSizeMap
	* @Description: 修改操作后，尺寸大小同步缓存
	* @param list
	*/
	public static void synNailImageSizeMap(List<NailImageSize> list) {
		nailImageSizeMap.clear();
		if (null != list && list.size() > 0) {
			for (NailImageSize entity : list) {
				String key = entity.getWidth()+"x"+entity.getHeight();
				nailImageSizeMap.put(key, entity);
			}
		}
	}
}
