
package com.artcweb.service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailWhile;
import com.artcweb.vo.NailWhileVo;

public interface NailWhileService extends BaseService<NailWhile, Integer> {


	LayUiResult findByPage(NailWhileVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

}
