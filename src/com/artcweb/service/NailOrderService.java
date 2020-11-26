
package com.artcweb.service;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.NailCount;
import com.artcweb.baen.NailOrder;
import com.artcweb.vo.NailOrderVo;

public interface NailOrderService extends BaseService<NailOrder, Integer>{

	LayUiResult findByPage(NailOrderVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

	boolean saveNailOrder(NailOrderVo entity);

	ConcurrentHashMap<String, Integer> uploadImage(HttpServletRequest request,MultipartFile file, NailOrderVo entity,String uploadDirpath);

	void nailCount(ConcurrentHashMap<String, Integer> nailColorMap, NailOrderVo entity);

	

}
