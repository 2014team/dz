package com.artcweb.service;

import com.artcweb.service.BaseService;
import com.artcweb.bean.NailPictureframeStock;
import com.artcweb.vo.NailPictureframeStockHistoryVo;
import com.artcweb.vo.NailPictureframeStockVo;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailPictureframeStockDao
 * @Description: 画框库存
 * @author zhuzq
 * @date 2021年07月30日 09:06:26
 */
public interface NailPictureframeStockService extends BaseService<NailPictureframeStock,Integer>{

	public LayUiResult findByPage(NailPictureframeStockVo entity, LayUiResult result);

	public boolean deleteByBatch(String array);
	
	public Integer saveNailPictureframeStock(NailPictureframeStockHistoryVo entity);

}
