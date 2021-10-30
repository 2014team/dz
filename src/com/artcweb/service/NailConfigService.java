
package com.artcweb.service;

import java.util.Map;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailConfig;
import com.artcweb.dto.NailConfigDto;
import com.artcweb.vo.NailConfigVo;

public interface NailConfigService extends BaseService<NailConfig, Integer>{

	LayUiResult findByPage(NailConfigVo entity, LayUiResult result);

	boolean deleteByBatch(String array);
	
	
	NailConfigDto selectByMap(Map<String, Object> paramMap);

	

}
