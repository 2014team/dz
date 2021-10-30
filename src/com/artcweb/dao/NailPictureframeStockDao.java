package com.artcweb.dao;

import java.util.List;
import java.util.Map;
import com.artcweb.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.artcweb.bean.NailPictureframeStock;

import com.artcweb.dto.NailPictureframeStockDto;

/**
 * @ClassName: NailPictureframeStockDao
 * @Description: 画框库存
 * @author zhuzq
 * @date 2021年07月30日 09:06:25
 */
@Repository
public interface NailPictureframeStockDao extends BaseDao<NailPictureframeStock,Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<NailPictureframeStockDto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);

}
