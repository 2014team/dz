
package com.artcweb.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.Analys;
import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailCount;
import com.artcweb.bean.NailOrder;
import com.artcweb.bean.NailTotalCount;
import com.artcweb.dto.NailOrderDto;
import com.artcweb.vo.NailOrderVo;

public interface NailOrderService extends BaseService<NailOrder, Integer>{

	LayUiResult findByPage(NailOrderVo entity, LayUiResult result);

	String deleteByBatch(String array,HttpServletRequest request);

	Integer saveNailOrder(NailOrderVo entity);

	LinkedHashMap<String, Integer> uploadImage(HttpServletRequest request,MultipartFile file, NailOrderVo entity,String uploadDirpath,String comeFrom);

	LinkedHashMap<String, NailCount> nailCount(LinkedHashMap<String, Integer> nailColorMap, NailOrderVo entity);

	NailOrderDto getById(Integer id);
	
	boolean checkExist(Map<String,Object> paramMap,String id);
	boolean checkImageExist(Map<String,Object> paramMap,String id);

	void nailTotalCount(LinkedHashMap<String, NailCount> nailCountMap, NailOrderVo entity);
	
	NailOrderDto getNailOrder(Map<String, Object> paramMap);

	List<NailCount> getNailCountList(NailOrderDto entity);
	NailTotalCount getNailNailTotalCount(NailOrderDto entity);

	void exportExcel(HttpServletRequest request,HttpServletResponse response,NailOrderDto entity);

	String checkNialImageSise(MultipartFile file);
	
	
	List<NailOrderDto> apiSelect(Map<String, Object> paramMap);
	
	NailOrderDto apiGet(Map<String, Object> paramMap);
	
	Integer apiUpdateCurrentStep(Map<String, Object> paramMap);


	/**
	* @Title: updateCheckout
	* @Description: 更新库存
	* @author zhuzq
	* @date  2021年3月11日 下午2:47:59
	* @param array
	* @return
	*/
	String updateCheckout(String array);

	/**
	* @Title: updateCancelCheckout
	* @Description: 取消库存
	* @author zhuzq
	* @date  2021年3月11日 下午5:30:12
	* @param array
	* @return
	*/
	String updateCancelCheckout(String array);

	/**
	* @Title: analys
	* @Description: 统计分析
	* @author zhuzq
	* @date  2021年3月12日 下午2:54:50
	* @param entity
	* @return
	*/
	Map<String,Object>  analys(NailOrderVo entity);
	
	public Analys getAnalysOne(Map<String,Analys> nailDetailConfigMapMap);



}
