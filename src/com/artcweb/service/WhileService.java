
package com.artcweb.service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.While;
import com.artcweb.vo.WhileVo;

public interface WhileService extends BaseService<While, Integer> {


	LayUiResult findByPage(WhileVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

}
