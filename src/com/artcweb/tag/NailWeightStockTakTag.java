
package com.artcweb.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.bean.NailWeightStock;
import com.artcweb.service.NailWeightStockService;
import com.artcweb.util.SpringConfigTool;

public class NailWeightStockTakTag {

	public static NailWeightStockService nailWeightStockService = (NailWeightStockService) SpringConfigTool
			.getBean("nailWeightStockServiceImpl");

	public static List<NailWeightStock> getList(){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<NailWeightStock> list = nailWeightStockService.select(paramMap);
		return list;
	}

}
