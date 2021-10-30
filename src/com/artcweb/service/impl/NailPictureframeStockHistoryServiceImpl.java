package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.dao.NailPictureframeStockHistoryDao;
import com.artcweb.bean.NailPictureframeStockHistory;
import com.artcweb.service.NailPictureframeStockHistoryService;
import com.artcweb.service.impl.BaseServiceImpl;
import com.artcweb.vo.NailPictureframeStockHistoryVo;
import com.artcweb.dto.NailPictureframeStockHistoryDto;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailPictureframeStockHistoryServiceImpl
 * @Description: 画框库存记录
 * @author zhuzq
 * @date 2021年07月30日 11:33:34
 */
@Service
public class NailPictureframeStockHistoryServiceImpl extends BaseServiceImpl<NailPictureframeStockHistory,Integer>  implements NailPictureframeStockHistoryService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailPictureframeStockHistoryServiceImpl.class);
	
	@Autowired
	private NailPictureframeStockHistoryDao nailPictureframeStockHistoryDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailPictureframeStockHistoryVo entity, LayUiResult result) {

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
		Integer count = nailPictureframeStockHistoryDao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailPictureframeStockHistoryDto> dataList = nailPictureframeStockHistoryDao.selectByPage(paramMap);
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
		Integer delete = nailPictureframeStockHistoryDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}
	
}
