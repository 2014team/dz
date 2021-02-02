
package com.artcweb.service;

import java.util.Map;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailDetailConfig;
import com.artcweb.dto.NailDetailConfigDto;
import com.artcweb.vo.NailDetailConfigVo;

public interface NailDetailConfigService extends BaseService<NailDetailConfig, Integer>{

	LayUiResult findByPage(NailDetailConfigVo entity, LayUiResult result);

	boolean deleteByBatch(String array);
	
	NailDetailConfigDto selectByMap(Map<String, Object> paramMap);

	

}
