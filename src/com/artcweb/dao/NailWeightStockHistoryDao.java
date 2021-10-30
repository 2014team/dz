package com.artcweb.dao;

import java.util.List;
import java.util.Map;
import com.artcweb.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.artcweb.bean.NailWeightStockHistory;

import com.artcweb.dto.NailWeightStockHistoryDto;

/**
 * @ClassName: NailWeightStockHistoryDao
 * @Description: 图钉重量库存记录
 * @author zhuzq
 * @date 2021年03月09日 14:16:31
 */
@Repository
public interface NailWeightStockHistoryDao extends BaseDao<NailWeightStockHistory,Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<NailWeightStockHistoryDto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);

}
