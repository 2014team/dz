package com.artcweb.service;

import com.artcweb.service.BaseService;
import com.artcweb.bean.NailWeightStock;
import com.artcweb.vo.NailWeightStockHistoryVo;
import com.artcweb.vo.NailWeightStockVo;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailWeightStockDao
 * @Description: 图钉重量库存
 * @author zhuzq
 * @date 2021年03月09日 10:04:19
 */
public interface NailWeightStockService extends BaseService<NailWeightStock,Integer>{

	public LayUiResult findByPage(NailWeightStockVo entity, LayUiResult result);

	public boolean deleteByBatch(String array);

	public Integer saveStock(NailWeightStockHistoryVo entity);

}
