
package com.artcweb.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.bean.NailPictureframeStock;
import com.artcweb.service.NailPictureframeStockService;
import com.artcweb.util.SpringConfigTool;

public class NailPictureframeStockTag {

	public static NailPictureframeStockService nailPictureframeStock = (NailPictureframeStockService) SpringConfigTool
			.getBean("nailPictureframeStockServiceImpl");

	public static List<NailPictureframeStock> getList(){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<NailPictureframeStock> list = nailPictureframeStock.select(paramMap);
		return list;
	}

	
	
}
