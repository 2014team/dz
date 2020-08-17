
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
import com.artcweb.baen.Secret;
import com.artcweb.dao.SecretDao;
import com.artcweb.dto.SecretDto;
import com.artcweb.service.SecretService;
import com.artcweb.util.SecretUtil;
import com.artcweb.vo.SecretVo;

@Service
public class SecretServiceImpl extends BaseServiceImpl<Secret, Integer> implements SecretService {
	private static Logger logger = Logger.getLogger(SecretServiceImpl.class);
	
	@Autowired
	private SecretDao secretDao;

	@Override
	public LayUiResult findByPage(SecretVo entity, LayUiResult result) {
		
		
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
		Integer count = secretDao.searchByPageCount(paramMap);
		if (null != count && count > 0) {
			List<SecretDto> dataList = secretDao.searchByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	@Override
	public boolean saveSecret(SecretVo secretVo) {
		
		//生成秘钥数
		Integer secretNumber = secretVo.getSecretNumber();
		
		//秘钥长度
		Integer secretDigit = secretVo.getSecretDigit();
		List<Secret> list = new ArrayList<Secret>();
		for (int i = 0; i < secretNumber; i++) {
			String secretKey = getSecretKey(secretDigit);
			Secret secret = new Secret();
			org.springframework.beans.BeanUtils.copyProperties(secretVo, secret);
			secret.setSecretKey(secretKey);
			list.add(secret);
		}
		
		//保存
		Integer result  = secretDao.saveBatch(list);
		if(null != result && result > 0){
			return true;
		}else{
			return false;
		}
		
		
	}
	
	private String getSecretKey(Integer secretDigit){
		Map<String,Object> paramMap  = new HashMap<String, Object>();
		//检查是否已经在数据库存在
		String secretKey  = SecretUtil.getGenerateWord(secretDigit);
		paramMap.put("secretKey", secretKey);
		Integer result =  secretDao.checkExit(paramMap);
		if(null == result || result < 1){
			return secretKey;
		}
		return getSecretKey(secretDigit);
	}

	@Override
	public boolean deleteByBatch(String array) {
		Integer delete  = secretDao.deleteByBatch(array);
		if(null != delete && delete > 0){
			return true;
		}
		return false;
	}

	@Override
	public Secret getByMap(Map<String, Object> paramMap) {
		Secret entity =  secretDao.getByMap(paramMap);
		return entity;
	}

	@Override
	public boolean updateByMap(Map<String, Object> paramMap) {
		Integer result  = secretDao.updateByMap(paramMap);
		if(null == result || result < 1){
			return false;
		}
		return true;
	}



	
	
	
}
