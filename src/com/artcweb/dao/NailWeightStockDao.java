package com.artcweb.dao;

import java.util.List;
import java.util.Map;
import com.artcweb.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.artcweb.bean.NailWeightStock;

import com.artcweb.dto.NailWeightStockDto;

/**
 * @ClassName: NailWeightStockDao
 * @Description: 图钉重量库存
 * @author zhuzq
 * @date 2021年03月09日 10:04:19
 */
@Repository
public interface NailWeightStockDao extends BaseDao<NailWeightStock,Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<NailWeightStockDto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);
	

}
