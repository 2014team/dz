
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.NailOrder;
import com.artcweb.dto.NailOrderDto;

@Repository
public interface NailOrderDao extends BaseDao<NailOrder, Integer> {

	List<NailOrderDto> selectByPage(Map<String, Object> paramMap);

	Integer deleteByBatch(String array);
	
	NailOrderDto getById(Integer id);
	
	List<NailOrder> checkExist(Map<String, Object> paramMap);
	
	List<NailOrder> getByBatch(String array);
	
	NailOrderDto getNailOrder(Map<String, Object> paramMap);
	
	List<NailOrderDto> apiSelect(Map<String, Object> paramMap);
	
	NailOrderDto apiGet(Map<String, Object> paramMap);
	Integer apiUpdateCurrentStep(Map<String, Object> paramMap);
	
	/**
	* @Title: updateCheckoutFlag
	* @Description: 更新出库标识
	* @author zhuzq
	* @date  2021年3月11日 下午1:34:21
	* @param paramMap
	* @return
	*/
	Integer updateCheckoutFlag (Map<String, Object> paramMap);
	
	List<NailOrderDto> selectByMap (Map<String, Object> paramMap);

}