package com.artcweb.dao;

import java.util.List;
import java.util.Map;
import com.artcweb.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.artcweb.bean.NailPictureframeStockHistory;

import com.artcweb.dto.NailPictureframeStockHistoryDto;

/**
 * @ClassName: NailPictureframeStockHistoryDao
 * @Description: 画框库存记录
 * @author zhuzq
 * @date 2021年07月30日 11:33:33
 */
@Repository
public interface NailPictureframeStockHistoryDao extends BaseDao<NailPictureframeStockHistory,Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<NailPictureframeStockHistoryDto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);

}
