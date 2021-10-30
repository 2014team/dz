package com.artcweb.service;

import com.artcweb.service.BaseService;
import com.artcweb.bean.NailDrawingStockHistory;
import com.artcweb.vo.NailDrawingStockHistoryVo;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailDrawingStockHistoryDao
 * @Description: 图纸库存记录
 * @author zhuzq
 * @date 2021年03月10日 10:41:36
 */
public interface NailDrawingStockHistoryService extends BaseService<NailDrawingStockHistory,Integer>{

	public LayUiResult findByPage(NailDrawingStockHistoryVo entity, LayUiResult result);

	public boolean deleteByBatch(String array);

}
