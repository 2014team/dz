package com.artcweb.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailWeightStock;
import com.artcweb.dao.NailWeightStockDao;
import com.artcweb.dao.NailWeightStockHistoryDao;
import com.artcweb.dto.NailWeightStockDto;
import com.artcweb.service.NailWeightStockService;
import com.artcweb.vo.NailWeightStockHistoryVo;
import com.artcweb.vo.NailWeightStockVo;

/**
 * @ClassName: NailWeightStockServiceImpl
 * @Description: 图钉重量库存
 * @author zhuzq
 * @date 2021年03月09日 10:04:20
 */
@Service
public class NailWeightStockServiceImpl extends BaseServiceImpl<NailWeightStock,Integer>  implements NailWeightStockService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailWeightStockServiceImpl.class);
	
	@Autowired
	private NailWeightStockDao nailWeightStockDao;
	
	@Autowired
	private NailWeightStockHistoryDao nailWeightStockHistoryDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailWeightStockVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = nailWeightStockDao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailWeightStockDto> dataList = nailWeightStockDao.selectByPage(paramMap);
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
		Integer delete = nailWeightStockDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer saveStock(NailWeightStockHistoryVo entity) {
		Integer id = entity.getNailWeightStockId();
		NailWeightStock nailWeightStock = nailWeightStockDao.get(id);
		
		Integer update = null;
		if(null != nailWeightStock){
			
			// 库存相加
			 BigDecimal stockDB = new BigDecimal(nailWeightStock.getStock());
			 BigDecimal stock = new BigDecimal(entity.getStock());
			 BigDecimal addVal = stockDB.add(stock);
			 nailWeightStock.setStock(addVal.toString());
			 
			 update = nailWeightStockDao.update(nailWeightStock);
			 
			 if(null != update && update > 0){
				 // 库存添加记录
				 Integer addStockHistory = nailWeightStockHistoryDao.save(entity);
				 logger.info("addStockHistory==>"+addStockHistory);
			 }
		}
		return update;
	}
	
}
