package com.artcweb.dao;

import java.util.List;
import java.util.Map;
import com.artcweb.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.artcweb.bean.NailDrawingStockHistory;

import com.artcweb.dto.NailDrawingStockHistoryDto;

/**
 * @ClassName: NailDrawingStockHistoryDao
 * @Description: 图纸库存记录
 * @author zhuzq
 * @date 2021年03月10日 10:41:36
 */
@Repository
public interface NailDrawingStockHistoryDao extends BaseDao<NailDrawingStockHistory,Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<NailDrawingStockHistoryDto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);

}
