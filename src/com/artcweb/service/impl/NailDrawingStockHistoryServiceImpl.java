package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.dao.NailDrawingStockHistoryDao;
import com.artcweb.bean.NailDrawingStockHistory;
import com.artcweb.service.NailDrawingStockHistoryService;
import com.artcweb.service.impl.BaseServiceImpl;
import com.artcweb.vo.NailDrawingStockHistoryVo;
import com.artcweb.dto.NailDrawingStockHistoryDto;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailDrawingStockHistoryServiceImpl
 * @Description: 图纸库存记录
 * @author zhuzq
 * @date 2021年03月10日 10:41:36
 */
@Service
public class NailDrawingStockHistoryServiceImpl extends BaseServiceImpl<NailDrawingStockHistory,Integer>  implements NailDrawingStockHistoryService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailDrawingStockHistoryServiceImpl.class);
	
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
	public LayUiResult findByPage(NailDrawingStockHistoryVo entity, LayUiResult result) {

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
		Integer count = nailDrawingStockHistoryDao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailDrawingStockHistoryDto> dataList = nailDrawingStockHistoryDao.selectByPage(paramMap);
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
		Integer delete = nailDrawingStockHistoryDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}
	
}
