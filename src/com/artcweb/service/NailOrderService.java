
package com.artcweb.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.NailCount;
import com.artcweb.baen.NailOrder;
import com.artcweb.vo.NailOrderVo;

public interface NailOrderService extends BaseService<NailOrder, Integer>{

	LayUiResult findByPage(NailOrderVo entity, LayUiResult result);

	boolean deleteByBatch(String array,HttpServletRequest request);

	boolean saveNailOrder(NailOrderVo entity);

	ConcurrentHashMap<String, Integer> uploadImage(HttpServletRequest request,MultipartFile file, NailOrderVo entity,String uploadDirpath);

	ConcurrentHashMap<String, NailCount> nailCount(ConcurrentHashMap<String, Integer> nailColorMap, NailOrderVo entity);

	NailOrder getById(Integer id);
	
	boolean checkExist(Map<String, Object> paramMap);

	void nailTotalCount(ConcurrentHashMap<String, NailCount> nailCountMap, NailOrderVo entity);
}
