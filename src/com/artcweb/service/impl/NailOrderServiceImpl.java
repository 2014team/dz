
package com.artcweb.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.NailCount;
import com.artcweb.baen.NailDetailConfig;
import com.artcweb.baen.NailOrder;
import com.artcweb.cache.DateMap;
import com.artcweb.dao.NailDetailConfigDao;
import com.artcweb.dao.NailOrderDao;
import com.artcweb.dto.NailOrderDto;
import com.artcweb.service.NailOrderService;
import com.artcweb.util.GsonUtil;
import com.artcweb.util.ImageUtil;
import com.artcweb.util.UploadUtil;
import com.artcweb.vo.NailOrderVo;

@Service
public class NailOrderServiceImpl extends BaseServiceImpl<NailOrder, Integer> implements NailOrderService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailOrderServiceImpl.class);
	@Autowired
	private NailOrderDao nailOrderDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailOrderVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = nailOrderDao.findByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailOrderDto> dataList = nailOrderDao.selectByPage(paramMap);
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
		Integer delete = nailOrderDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveNailOrder(NailOrderVo entity) {
		Integer result = nailOrderDao.save(entity);
		if(null != result && result > 0){
			return true;
		}
		return false;
	}

	@Override
	public ConcurrentHashMap<String, Integer> uploadImage(HttpServletRequest request,MultipartFile file, NailOrderVo entity,String uploadDirpath) {
		// 钉子颜色统计
		ConcurrentHashMap<String, Integer> nailColorMap = new ConcurrentHashMap<String, Integer>();
		
		BufferedImage image = null;
		InputStream input = null;
		List<String> stepList = null;
		try {
			input = file.getInputStream();
			image = ImageIO.read(input);
			int ih = image.getHeight();
			int iw = image.getWidth();
			logger.info("高="+ih +" 宽="+iw);
			
			stepList = new Vector<String>();
			for (int y = 0; y < ih; y++) {
				for (int x = 0; x < iw; x++) {
					int pixel = image.getRGB(x, y);
					int alpha = (pixel >> 24) & 0xFF;
					
					int red = (pixel >> 16) & 0xFF;
					int green = (pixel >> 8) & 0xFF;
					int blue = pixel & 0xFF;
					
					 StringBuffer sb = new StringBuffer();
					 sb.append(red);
					 sb.append(",");
					 sb.append(green);
					 sb.append(",");
					 sb.append(blue);
					 stepList.add(sb.toString());
					 String colorKsy = sb.toString();
					 Integer count = nailColorMap.get(colorKsy);
					 // 判断是否第一次
					 if(null != count){
						 nailColorMap.put(colorKsy, count+1);
					 }else{
						 nailColorMap.put(colorKsy, 1);
					 }
					 image.setRGB(x, y, pixel);
					 sb = null;
					logger.info("红绿蓝="+colorKsy);
				}
			}
			
			
			// --------获取图片上传路径------------------
			String uploadPath = ImageUtil.getUploadPath(request, image,file, uploadDirpath);
			
			// 设置高和宽
			entity.setHeight(String.valueOf(ih));
			entity.setWidth(String.valueOf(iw));
			// 设置执行步骤
			entity.setStep(GsonUtil.toJsonAll(stepList));
			// 设置上传图片
			entity.setImageUrl(uploadPath);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != input){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(stepList != null){
				stepList = null;
			}
		}
		return nailColorMap;

	}

	@Override
	public void nailCount(ConcurrentHashMap<String, Integer> nailColorMap, NailOrderVo entity) {
		if(nailColorMap == null || nailColorMap.size() < 1 ){
			logger.error("nailColorMap为空");
			return ;
		}
		
		
		Map<String, NailDetailConfig>  nailDetailConfigMap = DateMap.nailDetailConfigMap;
		if(nailDetailConfigMap == null || nailDetailConfigMap.size() < 1 ){
			logger.error("nailDetailConfigMap为空");
			return ;
		}
		
		
		
		// 数量统计集合
		ConcurrentHashMap<String, NailCount> nailCountMap = new ConcurrentHashMap<String, NailCount>();
		
		
		// 计算开始
		logger.info(" 计算开始------------------------");
		for (Map.Entry<String, Integer> entry : nailColorMap.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			NailDetailConfig nailDetailConfig = nailDetailConfigMap.get(key);
			if(null == nailDetailConfig){
				logger.error(key+"没有查到详细相关配置");
				continue;
			}
			
			NailCount nailCount = new NailCount();
			nailCount.setIndexId(nailDetailConfig.getNewSerialNumber());
			nailCount.setNailNumber(String.valueOf(value));
			nailCountMap.put(key, nailCount);
			
			nailCount = null;
			
			
        }
		logger.info(" 计算结束------------------------");
		
		// 钉子统计详情
		String nailCountDetail = GsonUtil.toJsonAll(nailCountMap);
		entity.setNailCountDetail(nailCountDetail);
	}

	@Override
	public NailOrder getById(Integer id) {
		return nailOrderDao.getById(id);
	}
	
	
}
