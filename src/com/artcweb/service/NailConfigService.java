
package com.artcweb.service;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.NailConfig;
import com.artcweb.vo.NailConfigVo;

public interface NailConfigService extends BaseService<NailConfig, Integer>{

	LayUiResult findByPage(NailConfigVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

	

}
