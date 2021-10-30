package com.artcweb.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.dao.NailDrawingStockDao;
import com.artcweb.dao.NailDrawingStockHistoryDao;
import com.artcweb.bean.NailDrawingStock;
import com.artcweb.bean.NailWeightStock;
import com.artcweb.service.NailDrawingStockService;
import com.artcweb.service.impl.BaseServiceImpl;
import com.artcweb.vo.NailDrawingStockHistoryVo;
import com.artcweb.vo.NailDrawingStockVo;
import com.artcweb.dto.NailDrawingStockDto;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailDrawingStockServiceImpl
 * @Description: 图纸库存
 * @author zhuzq
 * @date 2021年03月09日 11:50:00
 */
@Service
public class NailDrawingStockServiceImpl extends BaseServiceImpl<NailDrawingStock,Integer>  implements NailDrawingStockService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailDrawingStockServiceImpl.class);
	
	@Autowired
	private NailDrawingStockDao nailDrawingStockDao;
	@Autowired
	private NailDrawingStockHistoryDao nailDrawingStockHistoryDao;
	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailDrawingStockVo entity, LayUiResult result) {
		

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = nailDrawingStockDao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailDrawingStockDto> dataList = nailDrawingStockDao.selectByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}

		return result;
	}

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	@Override
	public boolean deleteByBatch(String array) {
		Integer delete = nailDrawingStockDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer saveStock(NailDrawingStockHistoryVo entity) {
		Integer id = entity.getNailDrawingStockId();
		NailDrawingStock nailDrawingStock = nailDrawingStockDao.get(id);
		
		Integer update = null;
		if(null != nailDrawingStock){
			
			// 库存相加
			 Integer numbeDB  = nailDrawingStock.getNumber();
			 Integer tatalNumbe = numbeDB+Integer.parseInt(entity.getStock());
			 nailDrawingStock.setNumber(tatalNumbe);
			 
			 update = nailDrawingStockDao.update(nailDrawingStock);
			 
			 if(null != update && update > 0){
				 // 库存添加记录
				 Integer addStockHistory = nailDrawingStockHistoryDao.save(entity);
				 logger.info("addStockHistory==>"+addStockHistory);
			 }
		}
		return update;
	}
	
}
