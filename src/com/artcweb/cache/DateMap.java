package com.artcweb.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.baen.NailDetailConfig;

public class DateMap {
	
	public static Map<String, NailDetailConfig>  nailDetailConfigMap = new HashMap<String, NailDetailConfig>();
	
	
	public static void initNailDetailConfigMap(List<NailDetailConfig> nailDetailConfigList){
		if(nailDetailConfigMap.size() > 0){
			return;
		}
		if(null != nailDetailConfigList && nailDetailConfigList.size() > 0){
			for (NailDetailConfig nailDetailConfig : nailDetailConfigList) {
				String rgb = nailDetailConfig.getRgb();
				nailDetailConfigMap.put(rgb, nailDetailConfig);
			}
		}
	}
}
