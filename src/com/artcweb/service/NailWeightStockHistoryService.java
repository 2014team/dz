package com.artcweb.service;

import com.artcweb.service.BaseService;
import com.artcweb.bean.NailWeightStockHistory;
import com.artcweb.vo.NailWeightStockHistoryVo;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailWeightStockHistoryDao
 * @Description: 图钉重量库存记录
 * @author zhuzq
 * @date 2021年03月09日 14:16:31
 */
public interface NailWeightStockHistoryService extends BaseService<NailWeightStockHistory,Integer>{

	public LayUiResult findByPage(NailWeightStockHistoryVo entity, LayUiResult result);

	public boolean deleteByBatch(String array);

}
