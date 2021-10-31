
package com.artcweb.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.bean.NailConfig;
import com.artcweb.service.NailConfigService;
import com.artcweb.util.SpringConfigTool;

public class NailConfigTag {

	
	public static NailConfigService nailConfigService = (NailConfigService) SpringConfigTool
			.getBean("nailConfigServiceImpl");

	public static List<NailConfig> getList(){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<NailConfig> list = nailConfigService.select(paramMap);
		return list;
	}

	
	
}
