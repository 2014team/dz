
package com.artcweb.service;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.While;
import com.artcweb.vo.WhileVo;

public interface WhileService extends BaseService<While, Integer> {


	LayUiResult findByPage(WhileVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

}
