package com.artcweb.service;

import com.artcweb.service.BaseService;
import com.artcweb.bean.NailPictureframeStockHistory;
import com.artcweb.vo.NailPictureframeStockHistoryVo;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailPictureframeStockHistoryDao
 * @Description: 画框库存记录
 * @author zhuzq
 * @date 2021年07月30日 11:33:34
 */
public interface NailPictureframeStockHistoryService extends BaseService<NailPictureframeStockHistory,Integer>{

	public LayUiResult findByPage(NailPictureframeStockHistoryVo entity, LayUiResult result);

	public boolean deleteByBatch(String array);



}
