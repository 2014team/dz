
package com.artcweb.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.bean.NailImageSize;
import com.artcweb.service.NailImageSizeService;
import com.artcweb.util.SpringConfigTool;

public class NailImageSizeTag {

	public static NailImageSizeService nailImageSizeService = (NailImageSizeService) SpringConfigTool
			.getBean("nailImageSizeServiceImpl");

	public static List<NailImageSize> getList(){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<NailImageSize> list = nailImageSizeService.select(paramMap);
		return list;
	}

	
	
}
