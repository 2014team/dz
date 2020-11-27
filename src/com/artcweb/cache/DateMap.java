package com.artcweb.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.baen.NailConfig;
import com.artcweb.baen.NailDetailConfig;

public class DateMap {

	public static Map<String, NailDetailConfig> nailDetailConfigMap = new HashMap<String, NailDetailConfig>();
	public static List<NailConfig> nailConfigList = new ArrayList<NailConfig>();

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

	public static void initNailConfigList(List<NailConfig> list) {
		if (nailConfigList.size() > 0) {
			return;
		}
		if (null != list && list.size() > 0) {
			nailConfigList.addAll(list);
		}
	}

	public static void synChroNailDetailConfigMap(List<NailDetailConfig> list) {
		nailDetailConfigMap.clear();
		if (null != list && list.size() > 0) {
			for (NailDetailConfig nailDetailConfig : list) {
				String rgb = nailDetailConfig.getRgb();
				nailDetailConfigMap.put(rgb, nailDetailConfig);
			}
		}
	}

	public static void synNailConfigList(List<NailConfig> list) {
		nailConfigList.clear();
		if (null != list && list.size() > 0) {
			nailConfigList.addAll(list);
		}
	}
}
