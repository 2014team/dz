package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.dao.NailH5StrjsonDao;
import com.artcweb.bean.NailH5Strjson;
import com.artcweb.service.NailH5StrjsonService;
import com.artcweb.service.impl.BaseServiceImpl;
import com.artcweb.vo.NailH5StrjsonVo;
import com.artcweb.dto.NailH5StrjsonDto;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailH5StrjsonServiceImpl
 * @Description: H5调用
 * @author zhuzq
 * @date 2020年12月07日 16:17:37
 */
@Service
public class NailH5StrjsonServiceImpl extends BaseServiceImpl<NailH5Strjson,Integer>  implements NailH5StrjsonService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailH5StrjsonServiceImpl.class);
	
	@Autowired
	private NailH5StrjsonDao nailH5StrjsonDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailH5StrjsonVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = nailH5StrjsonDao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailH5StrjsonDto> dataList = nailH5StrjsonDao.selectByPage(paramMap);
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
		Integer delete = nailH5StrjsonDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}
	
}
