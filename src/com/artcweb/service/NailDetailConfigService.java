
package com.artcweb.service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailDetailConfig;
import com.artcweb.vo.NailDetailConfigVo;

public interface NailDetailConfigService extends BaseService<NailDetailConfig, Integer>{

	LayUiResult findByPage(NailDetailConfigVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

	

}
