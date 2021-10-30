package com.artcweb.dao;

import java.util.List;
import java.util.Map;
import com.artcweb.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.artcweb.bean.NailDrawingStock;

import com.artcweb.dto.NailDrawingStockDto;

/**
 * @ClassName: NailDrawingStockDao
 * @Description: 图纸库存
 * @author zhuzq
 * @date 2021年03月09日 11:49:59
 */
@Repository
public interface NailDrawingStockDao extends BaseDao<NailDrawingStock,Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<NailDrawingStockDto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);

}
