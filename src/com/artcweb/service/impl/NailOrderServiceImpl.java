
package com.artcweb.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailConfig;
import com.artcweb.bean.NailCount;
import com.artcweb.bean.NailDetailConfig;
import com.artcweb.bean.NailImageSize;
import com.artcweb.bean.NailOrder;
import com.artcweb.bean.NailTotalCount;
import com.artcweb.cache.DateMap;
import com.artcweb.constant.NailOrderComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.dao.NailOrderDao;
import com.artcweb.dto.NailOrderDto;
import com.artcweb.enums.NailImageTypeEnum;
import com.artcweb.service.NailOrderService;
import com.artcweb.util.ExportExcelUtil;
import com.artcweb.util.FileUtil;
import com.artcweb.util.GsonUtil;
import com.artcweb.util.ImageUtil;
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
	public boolean deleteByBatch(String array,HttpServletRequest request) {
		List<NailOrder> list = nailOrderDao.getByBatch(array);
		
		Integer delete = nailOrderDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			
			if(null != list && list.size() > 0){
				for (NailOrder n : list) {
					String resultImageUrl =n.getResultImageUrl();
					String sourceImageUrl =n.getImageUrl();
					if(StringUtils.isNotEmpty(sourceImageUrl)){
						// 判断是否有其他数据引用图片
						Map<String,Object> paramMap  = new  HashMap<String, Object>();
						
						paramMap.put("imageUrl", sourceImageUrl);
						List<NailOrder> nailOrder = nailOrderDao.checkExist(paramMap);
						if(null == nailOrder || nailOrder.size() > 0){// 没有引用删除
							
							for (NailOrder nailOrder2 : nailOrder) {
								if(!n.getId().equals(nailOrder2.getId())){
									boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
									logger.info("物理删除图片结果 = "+deleteResult);
									if(deleteResult){
										boolean  sourceImageUrlresult = FileUtil.deleteFile(resultImageUrl,request);
										logger.info("物理删除图片结果 = "+sourceImageUrlresult);
										
									}
								}
							}
						}else{
							boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
							logger.info("物理删除图片结果 = "+deleteResult);
						}
						
					}
				}
			}
			
			
			
			return true;
		}
		return false;
	}

	@Override
	public Integer saveNailOrder(NailOrderVo entity) {
		Integer result = nailOrderDao.save(entity);
		if(null != result && result > 0){
			result = entity.getId();
		}
		return result;
	}

	@Override
	public ConcurrentHashMap<String, Integer> uploadImage(HttpServletRequest request,MultipartFile file, NailOrderVo entity,String uploadDirpath,String comeFrom) {
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
			
			// 画版设置
			BufferedImage bi = null;
			// 画笔
			Graphics2D g2d = null;
			if(StringUtils.isNotEmpty(comeFrom) && !comeFrom.equals(NailOrderComeFromConstant.H5)){
				bi = new BufferedImage(iw*10, ih*10, BufferedImage.TYPE_INT_RGB);
				g2d = bi.createGraphics();
			}
			
		    Ellipse2D.Double circle = null;
			long begin = System.currentTimeMillis();
			for (int y = 0; y < ih; y++) {
				for (int x = 0; x < iw; x++) {
					int pixel = image.getRGB(x, y);
					//int alpha = (pixel >> 24) & 0xFF;
					
					int red = (pixel >> 16) & 0xFF;
					int green = (pixel >> 8) & 0xFF;
					int blue = pixel & 0xFF;
					
					System.out.println(red+"  "+green+" "+blue);
					
					// 画结果图片
					if(StringUtils.isNotEmpty(comeFrom) && !comeFrom.equals(NailOrderComeFromConstant.H5)){
						circle = new Ellipse2D.Double(x*10,y*10,10,10);
				    	g2d.setPaint(new Color(red, green, blue));
				    	g2d.fill(circle);
				    	circle = null;
					}
					
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
			
			
			// 结果图片处理
			if(StringUtils.isNotEmpty(comeFrom) && !comeFrom.equals(NailOrderComeFromConstant.H5)){
				g2d.dispose();
		    	String resultImageUrl = ImageUtil.getUploadPath(request, bi, null, UploadConstant.SAVE_UPLOAD_NAIL_PATH, false);
				if(StringUtils.isNotEmpty(resultImageUrl)){
					entity.setResultImageUrl(resultImageUrl);
				}
			}
			
			// --------获取图片上传路径------------------
			String uploadPath = ImageUtil.getUploadPath(request, image,file, uploadDirpath,false);
			long end = System.currentTimeMillis();
			logger.info("统计钉子像素耗时："+((end-begin)/1000)+" 秒");
			
			
			// 设置高和宽
			entity.setHeight(String.valueOf(ih));
			entity.setWidth(String.valueOf(iw));
			// 设置执行步骤
			
			String step = GsonUtil.toJsonAll(stepList);
			logger.info("step="+step);
			entity.setStep(step);
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
	public ConcurrentHashMap<Integer, NailCount> nailCount(ConcurrentHashMap<String, Integer> nailColorMap, NailOrderVo entity) {
		if(nailColorMap == null || nailColorMap.size() < 1 ){
			logger.error("nailColorMap为空");
			return null;
		}
		
		// 获取数量配置
		Map<String, NailDetailConfig>  nailDetailConfigMap = DateMap.nailDetailConfigMap;
		if(nailDetailConfigMap == null || nailDetailConfigMap.size() < 1 ){
			logger.error("nailDetailConfigMap为空");
			return null;
		}
		
		
		// 数量统计集合
		ConcurrentHashMap<Integer, NailCount> nailCountMap = new ConcurrentHashMap<Integer, NailCount>();
		
		
		// 详细配置,判断大小图钉
		 Map<String,NailConfig> nailConfigMap = DateMap.nailConfigMap;
		 String nailConfigId = entity.getNailConfigId();
		 NailConfig nailConfig = nailConfigMap.get(nailConfigId);
		 if(null == nailConfig){
			 logger.error("没有获取到图片类型");
			 return null;
		 }
		 
		 boolean nailFlag = false;
		 String nailNumber = nailConfig.getNailNumber();
		 BigDecimal nailStandardNumberDecimal = new BigDecimal(nailNumber);
		 logger.info("数量(每公斤颗数)------------------------"+nailStandardNumberDecimal);
		 
		 if(nailConfig.getNailType().equals(NailImageTypeEnum.BIG.getDisplayName())){
			 nailFlag = true;
			 
		 }

		 // 计算开始
		logger.info(" 计算开始------------------------");
		long begin = System.currentTimeMillis();
		
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

			
			// 小钉重量计算公式： 重量计算（数量/3600）=公斤（小数点4位）
			// 大钉重量计算公式： 重量计算（数量/2800）=公斤（小数点4位）
			BigDecimal nailNumberDecimal = new BigDecimal(value);
			BigDecimal requreWeight  = nailNumberDecimal.divide(nailStandardNumberDecimal,4,BigDecimal.ROUND_UP);
			nailCount.setRequreWeight(String.valueOf(requreWeight));
			
			
			// 包计算公式  各颜色重量/各颜色小包公斤数=包数（向上取整）
			String nailBigWeight = nailDetailConfig.getNailBigWeight();
			String nailSmallWeight = nailDetailConfig.getNailSmallWeight();
			BigDecimal nailBigWeightDecimal = new BigDecimal(nailBigWeight);
			if(!nailFlag){
				//小钉计算
				nailBigWeightDecimal = new BigDecimal(nailSmallWeight);
			}
			BigDecimal requrePieces = requreWeight.divide(nailBigWeightDecimal,0, BigDecimal.ROUND_UP);
			nailCount.setRequrePieces(String.valueOf(requrePieces));
		
			
			nailCount.setRgb(key);
			
			nailCountMap.put(Integer.valueOf(nailCount.getIndexId()), nailCount);
			
			 List<Map.Entry<Integer, NailCount>> list = new ArrayList<Map.Entry<Integer, NailCount>>(nailCountMap.entrySet());
		        Collections.sort(list, new Comparator<Map.Entry<Integer, NailCount>>() {
		            public int compare(Map.Entry<Integer, NailCount> o1, Map.Entry<Integer, NailCount> o2) {
		                return o1.getKey().compareTo(o2.getKey());
		            }
		        });
		        
		        

			nailNumberDecimal = null;
			nailBigWeightDecimal = null;
			nailCount = null;
        }
		nailStandardNumberDecimal = null;
		
		
		long end = System.currentTimeMillis();
		logger.info(" 计算结束------------------------");
		logger.info("统计钉子配置详情耗时："+((end-begin)/1000)+" 秒");
		  
		
		
		return nailCountMap;
	}

	@Override
	public NailOrderDto getById(Integer id) {
		return nailOrderDao.getById(id);
	}

	@Override
	public boolean checkExist(Map<String,Object> paramMap,String id) {
		List<NailOrder> nailOrderList = nailOrderDao.checkExist(paramMap);
		if(null != nailOrderList && nailOrderList.size() > 0){
			if(StringUtils.isEmpty(id)){
				return true;
			}
			for (NailOrder nailOrder : nailOrderList) {
				Integer idSource = nailOrder.getId();
				if(!String.valueOf(idSource).equals(id)){
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public boolean checkImageExist(Map<String,Object> paramMap,String id) {
		List<NailOrder> nailOrderList = nailOrderDao.checkExist(paramMap);
		if(null != nailOrderList && nailOrderList.size() > 0){
			for (NailOrder nailOrder : nailOrderList) {
				Integer idSource = nailOrder.getId();
				if(!String.valueOf(idSource).equals(id)){
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void nailTotalCount(ConcurrentHashMap<Integer, NailCount> nailCountMap, NailOrderVo entity) {
		
		NailTotalCount nailTotalCount =  new NailTotalCount();
		
		if(null == nailCountMap || nailCountMap.size() < 1){
			logger.error("nailCountMap为空");
			return;
		}
		
		if(null == entity){
			logger.error("entity为空");
			return;
		}
		
		BigDecimal wDecimal = new BigDecimal(0);
		BigDecimal nDecimal = new BigDecimal(0);
		BigDecimal pDecimal = new BigDecimal(0);
		//BigDecimal bigDecimal = new BigDecimal(0);
		for (Map.Entry<Integer,NailCount> map: nailCountMap.entrySet()) {
			NailCount nailCount = map.getValue();
			BigDecimal requreWeightBigDecimal = new BigDecimal(nailCount.getRequreWeight());
			
			BigDecimal nailNumberBigDecimal = new BigDecimal(nailCount.getNailNumber());
			BigDecimal requrePiecesBigDecimal = new BigDecimal(nailCount.getRequrePieces());
			wDecimal =wDecimal.add(requreWeightBigDecimal);
			nDecimal =nDecimal.add(nailNumberBigDecimal);
			pDecimal =pDecimal.add(requrePiecesBigDecimal);
			requreWeightBigDecimal = null;
			nailNumberBigDecimal = null;
			requrePiecesBigDecimal = null;
		}
		
		// 总重量计算
		nailTotalCount.setTotalWeight(String.valueOf(wDecimal.setScale(2,BigDecimal.ROUND_HALF_DOWN)));
		// 总数量
		nailTotalCount.setTotalNailNumber(String.valueOf(nDecimal));
		// 总包数
		nailTotalCount.setTotalrPieces(String.valueOf(pDecimal));
		// 详细列表
		nailTotalCount.setNailCountDetailMap(nailCountMap);
		
		// 钉子统计详情
		String nailCountDetail = GsonUtil.toJsonAll(nailTotalCount);

		logger.info("------------------------------");
		logger.info(nailCountDetail);
		logger.info("------------------------------");
		entity.setNailCountDetail(nailCountDetail);
	}

	@Override
	public NailOrderDto getNailOrder(Map<String, Object> paramMap) {
		return nailOrderDao.getNailOrder(paramMap);
	}

	@Override
	public List<NailCount> getNailCountList(NailOrderDto entity) {
		
		List<NailCount> nailCountList = null;
		if(null != entity){
			// 钉子统计详情信息
			if(StringUtils.isNotBlank(entity.getNailCountDetail())){
				NailTotalCount nailTotalCount = getNailNailTotalCount(entity);
				
				// 详细列表
				if(null != nailTotalCount && null != nailTotalCount.getNailCountDetailMap() && nailTotalCount.getNailCountDetailMap().size() > 0){
					List<Map.Entry<Integer, NailCount>> list = new ArrayList<Map.Entry<Integer, NailCount>>(nailTotalCount.getNailCountDetailMap().entrySet());
			        Collections.sort(list, new Comparator<Map.Entry<Integer, NailCount>>() {
			            public int compare(Map.Entry<Integer, NailCount> o1, Map.Entry<Integer, NailCount> o2) {
			                return o1.getKey().compareTo(o2.getKey());
			            }
			        });
			        
			        nailCountList = new ArrayList<NailCount>();
			        if(null != list && list.size() > 0){
			        	for (Entry<Integer, NailCount> entry : list) {
			        		nailCountList.add(entry.getValue());
						}
			        }
				}
			}
		}
		return nailCountList;
	}

	@Override
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,NailOrderDto entity) {
		 List<NailCount> nailCountList = getNailCountList( entity);
		 if(null != nailCountList && nailCountList.size() > 0){
			 List<Map<String,Object>> rows = getNailCount(nailCountList,entity);	
			 String[] columnWidth = getColumnWidth();
			 String[][] columnNames = getColumnNames();
			 String excelName = entity.getImageName();
			 ExportExcelUtil.exportExcel(request, response, columnNames, columnWidth, rows, excelName,entity);
		 }
		 
	}
		 

	private List<Map<String, Object>> getNailCount(List<NailCount> nailCountList,NailOrderDto entity) {
		 List<Map<String, Object>>  result = null;
		 if(null  != nailCountList && nailCountList.size() > 0){
			 result = new ArrayList<Map<String, Object>>(); 
			 String imageUrl = entity.getImageUrl();
				 if(StringUtils.isEmpty(imageUrl)){
					logger.error("图片地址为空,imageUrl="+imageUrl);
					 return result;
				 }
				 
				 for (NailCount nailCount : nailCountList) {
					 Map<String, Object> map = new HashMap<String, Object>();
					 map.put("indexId", nailCount.getIndexId());
					 map.put("nailNumber", nailCount.getNailNumber());
					 map.put("requreWeight", nailCount.getRequreWeight());
					 map.put("requrePieces", nailCount.getRequrePieces());
					 
					 result.add(map);
					 map = null;
				}
				 
				// 统计行处理
				 Map<String, Object> map = new HashMap<String, Object>();
				 NailTotalCount nailTotalCount = getNailNailTotalCount(entity);
				 if(null != nailTotalCount){
					 map.put("indexId", "总计");
					 map.put("nailNumber", nailTotalCount.getTotalNailNumber());
					 map.put("requreWeight", nailTotalCount.getTotalWeight());
					 map.put("requrePieces", nailTotalCount.getTotalrPieces());
					 map.put("", "签字：");
					 result.add(map);
					 map = null;
				 }
				 
				 if(result.size() < 25){
					 for(int i = 0; i< (25-result.size()); i++){
						 map = new HashMap<String, Object>();
						 map.put("indexId", "");
						 map.put("nailNumber", "");
						 map.put("requreWeight", "");
						 map.put("requrePieces", "");
						 result.add(map);
						 map = null;
					 }
				 }
				 
				 
				return result; 
		 } 
		return null;
	}

	@Override
	public NailTotalCount getNailNailTotalCount(NailOrderDto entity) {
		NailTotalCount nailTotalCount= null;
		if(null != entity){
			// 钉子统计详情信息
			if(StringUtils.isNotBlank(entity.getNailCountDetail())){
				 nailTotalCount = ( NailTotalCount ) GsonUtil.jsonToBean(entity.getNailCountDetail(),NailTotalCount.class);
			}
		}
		return nailTotalCount;
	}

	
	public String[] getColumnWidth(){
		String [] columnWidth ={"20","12","12","12","12","15"}; 
		return columnWidth;
	}
	
	public String[][] getColumnNames(){
		String[][] columnNames =  new String[][] {
			{" ","编号","数量","重量","包数","确认打钩"}, 
			{"","indexId","nailNumber","requreWeight","requrePieces",""}
			};
		return columnNames;
	}

	@Override
	public String checkNialImageSise(MultipartFile file) {
		BufferedImage image;
		try {
			image = ImageIO.read(file.getInputStream());
			 if (image == null) { //如果image=null 表示上传的不是图片格式
			        return "图片格式不正确.";
			    }
			 String key = image.getWidth()+"x"+image.getHeight();
			 NailImageSize nailImageSize = DateMap.nailImageSizeMap.get(key);
			 if(null == nailImageSize){
				 return "图片不是尺寸配置列表范围,不符合要求，请尺寸配置";
			 }
		}
		catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	    return null;
	}

	@Override
	public List<NailOrderDto> apiSelect(Map<String, Object> paramMap) {
		List<NailOrderDto> list = nailOrderDao.apiSelect(paramMap);
		if(null != list && list.size() > 0){
			for (NailOrderDto nailOrderDto : list) {
				String imageUrl = nailOrderDto.getImageUrl();
				String resultImageUrl = nailOrderDto.getResultImageUrl();
				imageUrl = ImageUtil.imageUrlDeal(imageUrl);
				resultImageUrl = ImageUtil.imageUrlDeal(resultImageUrl);
				nailOrderDto.setImageUrl(imageUrl);	
				nailOrderDto.setResultImageUrl(resultImageUrl);
						
			}
		}
		return list;
	}

	@Override
	public NailOrderDto apiGet(Map<String, Object> paramMap) {
		NailOrderDto nailOrderDto = nailOrderDao.apiGet(paramMap);
		if(null != nailOrderDto){
			String imageUrl = nailOrderDto.getImageUrl();
			String resultImageUrl = nailOrderDto.getResultImageUrl();
			imageUrl = ImageUtil.imageUrlDeal(imageUrl);
			resultImageUrl = ImageUtil.imageUrlDeal(resultImageUrl);
			nailOrderDto.setImageUrl(imageUrl);	
			nailOrderDto.setResultImageUrl(resultImageUrl);
		}
		return nailOrderDto;
	}

	@Override
	public Integer apiUpdateCurrentStep(Map<String, Object> paramMap) {
		return nailOrderDao.apiUpdateCurrentStep(paramMap);
	}

}
