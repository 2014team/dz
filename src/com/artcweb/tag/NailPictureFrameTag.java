
package com.artcweb.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artcweb.bean.NailImageSize;
import com.artcweb.bean.NailPictureFrame;
import com.artcweb.service.NailPictureFrameService;
import com.artcweb.util.SpringConfigTool;

public class NailPictureFrameTag {

	public static NailPictureFrameService nailPictureFrameService = (NailPictureFrameService) SpringConfigTool
			.getBean("nailPictureFrameServiceImpl");

	public static List<NailPictureFrame> getList(){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<NailPictureFrame> list = nailPictureFrameService.select(paramMap);
		return list;
	}

	
	
}
