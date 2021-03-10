package com.artcweb.service;

import com.artcweb.service.BaseService;
import com.artcweb.bean.NailDrawingStock;
import com.artcweb.vo.NailDrawingStockHistoryVo;
import com.artcweb.vo.NailDrawingStockVo;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailDrawingStockDao
 * @Description: 图纸库存
 * @author zhuzq
 * @date 2021年03月09日 11:50:00
 */
public interface NailDrawingStockService extends BaseService<NailDrawingStock,Integer>{

	public LayUiResult findByPage(NailDrawingStockVo entity, LayUiResult result);

	public boolean deleteByBatch(String array);

	public Integer saveStock(NailDrawingStockHistoryVo entity);

}
