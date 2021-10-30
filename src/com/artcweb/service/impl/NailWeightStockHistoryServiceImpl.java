package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.dao.NailWeightStockHistoryDao;
import com.artcweb.bean.NailWeightStockHistory;
import com.artcweb.service.NailWeightStockHistoryService;
import com.artcweb.service.impl.BaseServiceImpl;
import com.artcweb.vo.NailWeightStockHistoryVo;
import com.artcweb.dto.NailWeightStockHistoryDto;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailWeightStockHistoryServiceImpl
 * @Description: 图钉重量库存记录
 * @author zhuzq
 * @date 2021年03月09日 14:16:31
 */
@Service
public class NailWeightStockHistoryServiceImpl extends BaseServiceImpl<NailWeightStockHistory,Integer>  implements NailWeightStockHistoryService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailWeightStockHistoryServiceImpl.class);
	
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
	public LayUiResult findByPage(NailWeightStockHistoryVo entity, LayUiResult result) {
		
		//创建日期处理
		String createDateStr = entity.getCreateDateStr();
		if(StringUtils.isNotBlank(createDateStr)){
			String[] createDateArr = createDateStr.split("~");
			if(null != createDateArr && createDateArr.length ==2){
				entity.setBeginDate(createDateArr[0]);
				entity.setEndDate(createDateArr[1]);
			}
		}
				

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = nailWeightStockHistoryDao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailWeightStockHistoryDto> dataList = nailWeightStockHistoryDao.selectByPage(paramMap);
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
		Integer delete = nailWeightStockHistoryDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}
	
}
