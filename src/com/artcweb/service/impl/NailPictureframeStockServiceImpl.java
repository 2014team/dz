package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.dao.NailPictureFrameDao;
import com.artcweb.dao.NailPictureframeStockDao;
import com.artcweb.dao.NailPictureframeStockHistoryDao;
import com.artcweb.bean.NailPictureframeStock;
import com.artcweb.bean.NailPictureframeStockHistory;
import com.artcweb.service.NailPictureframeStockService;
import com.artcweb.service.impl.BaseServiceImpl;
import com.artcweb.vo.NailDrawingStockHistoryVo;
import com.artcweb.vo.NailPictureframeStockHistoryVo;
import com.artcweb.vo.NailPictureframeStockVo;
import com.artcweb.dto.NailPictureframeStockDto;
import com.artcweb.enums.NailPictureframeEnum;
import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailDrawingStock;
import com.artcweb.bean.NailPictureFrame;

/**
 * @ClassName: NailPictureframeStockServiceImpl
 * @Description: 画框库存
 * @author zhuzq
 * @date 2021年07月30日 09:06:26
 */
@Service
public class NailPictureframeStockServiceImpl extends BaseServiceImpl<NailPictureframeStock,Integer>  implements NailPictureframeStockService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailPictureframeStockServiceImpl.class);
	
	@Autowired
	private NailPictureframeStockDao nailPictureframeStockDao;
	@Autowired
	private NailPictureframeStockHistoryDao nailPictureframeStockHistoryDao;
	
	@Autowired
	private NailPictureFrameDao nailPictureFrameDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailPictureframeStockVo entity, LayUiResult result) {
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
		Integer count = nailPictureframeStockDao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailPictureframeStockDto> dataList = nailPictureframeStockDao.selectByPage(paramMap);
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
		Integer delete = nailPictureframeStockDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Integer saveNailPictureframeStock(NailPictureframeStockHistoryVo entity) {
		Integer id = entity.getNailPictureframeId();
		NailPictureframeStock nailPictureframeStock = nailPictureframeStockDao.get(id);
		
		Integer update = null;
		if(null != nailPictureframeStock){
			
			
			Integer nailPictureFrameIds = entity.getFrameId();
			
			// 库存相加
			Integer stockNumber = entity.getStockNumber();
			if(NailPictureframeEnum.BLACK.getValue() == nailPictureFrameIds){
				nailPictureframeStock.setBlack(nailPictureframeStock.getBlack()+stockNumber);
			}else if(NailPictureframeEnum.BLUE.getValue() == nailPictureFrameIds){
				nailPictureframeStock.setBlue(nailPictureframeStock.getBlue()+stockNumber);
			}else if(NailPictureframeEnum.GOLD.getValue() == nailPictureFrameIds){
				 nailPictureframeStock.setGold(nailPictureframeStock.getGold()+stockNumber);
			}else if(NailPictureframeEnum.SILVER.getValue() == nailPictureFrameIds){
				nailPictureframeStock.setSilver(nailPictureframeStock.getSilver()+stockNumber);
			}else if(NailPictureframeEnum.WHITE.getValue() == nailPictureFrameIds){
				nailPictureframeStock.setWhite(nailPictureframeStock.getWhite()+stockNumber);
			}else if(NailPictureframeEnum.POWDER.getValue() == nailPictureFrameIds){
				 nailPictureframeStock.setPowder(nailPictureframeStock.getPowder()+stockNumber);
			}
			 update = nailPictureframeStockDao.update(nailPictureframeStock);
			 
			 if(null != update && update > 0){
				 // 库存添加记录
				 Integer addStockHistory = nailPictureframeStockHistoryDao.save(entity);
				 logger.info("addStockHistory==>"+addStockHistory);
			 }
		}
		return update;
	}
	
}
