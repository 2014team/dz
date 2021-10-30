
package com.artcweb.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.bean.NailDrawingStock;
import com.artcweb.service.NailDrawingStockService;
import com.artcweb.util.SpringConfigTool;

public class NailDrawingStockTag {

	public static NailDrawingStockService nailDrawingStockService = (NailDrawingStockService) SpringConfigTool
			.getBean("nailDrawingStockServiceImpl");

	public static List<NailDrawingStock> getList(){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<NailDrawingStock> list = nailDrawingStockService.select(paramMap);
		return list;
	}

}
