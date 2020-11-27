
package com.artcweb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.NailSecret;
import com.artcweb.dao.NailSecretDao;
import com.artcweb.dto.NailSecretDto;
import com.artcweb.service.NailSecretService;
import com.artcweb.util.SecretUtil;
import com.artcweb.vo.NailSecretVo;

@Service
public class NailSecretServiceImpl extends BaseServiceImpl<NailSecret, Integer> implements NailSecretService {
	private static Logger logger = Logger.getLogger(NailSecretServiceImpl.class);
	
	@Autowired
	private NailSecretDao nailSecretDao;

	@Override
	public LayUiResult findByPage(NailSecretVo entity, LayUiResult result) {
		
		
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
		Integer count = nailSecretDao.searchByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailSecretDto> dataList = nailSecretDao.searchByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	@Override
	public boolean saveSecret(NailSecretVo NailSecretVo) {
		
		//生成秘钥数
		Integer secretNumber = NailSecretVo.getSecretNumber();
		
		//秘钥长度
		Integer secretDigit = NailSecretVo.getSecretDigit();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < secretNumber; i++) {
			String secretKey = getSecretKey(list,secretDigit);
			list.add(secretKey);
		}
		
		//保存
		Integer result  = nailSecretDao.saveBatch(list);
		if(null != result && result > 0){
			return true;
		}else{
			return false;
		}
		
		
	}
	
	private String getSecretKey(List<String> list,Integer secretDigit){
		Map<String,Object> paramMap  = new HashMap<String, Object>();
		//检查是否已经在数据库存在
		String secretKey  = SecretUtil.getGenerateWord(secretDigit);
		paramMap.put("secretKey", secretKey);
		Integer result =  nailSecretDao.checkExit(paramMap);
		if(null == result || result < 1){
			if(!list.contains(secretKey)){
				return secretKey;
			}
		}
		return getSecretKey(list,secretDigit);
	}

	@Override
	public boolean deleteByBatch(String array) {
		Integer delete  = nailSecretDao.deleteByBatch(array);
		if(null != delete && delete > 0){
			return true;
		}
		return false;
	}

	@Override
	public NailSecret getByMap(Map<String, Object> paramMap) {
		NailSecret entity =  nailSecretDao.getByMap(paramMap);
		return entity;
	}

	@Override
	public boolean updateByMap(Map<String, Object> paramMap) {
		Integer result  = nailSecretDao.updateByMap(paramMap);
		if(null == result || result < 1){
			return false;
		}
		return true;
	}



	
	
	
}
