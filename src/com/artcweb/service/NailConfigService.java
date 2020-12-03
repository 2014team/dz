
package com.artcweb.service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailConfig;
import com.artcweb.vo.NailConfigVo;

public interface NailConfigService extends BaseService<NailConfig, Integer>{

	LayUiResult findByPage(NailConfigVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

	

}
