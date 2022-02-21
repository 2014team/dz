
package com.artcweb.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.Analys;
import com.artcweb.bean.AnalysNailConfig;
import com.artcweb.bean.Inventory;
import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailConfig;
import com.artcweb.bean.NailCount;
import com.artcweb.bean.NailDetailConfig;
import com.artcweb.bean.NailDrawingStock;
import com.artcweb.bean.NailImageSize;
import com.artcweb.bean.NailOrder;
import com.artcweb.bean.NailPictureframeStock;
import com.artcweb.bean.NailTotalCount;
import com.artcweb.bean.NailWeightStock;
import com.artcweb.cache.DateMap;
import com.artcweb.constant.NailOrderComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.dao.NailConfigDao;
import com.artcweb.dao.NailDetailConfigDao;
import com.artcweb.dao.NailDrawingStockDao;
import com.artcweb.dao.NailOrderDao;
import com.artcweb.dao.NailPictureFrameDao;
import com.artcweb.dao.NailPictureframeStockDao;
import com.artcweb.dao.NailWeightStockDao;
import com.artcweb.dto.NailOrderDto;
import com.artcweb.enums.CheckoutFlagEnum;
import com.artcweb.enums.NailImageTypeEnum;
import com.artcweb.enums.NailPictureframeEnum;
import com.artcweb.enums.NailTypeEnum;
import com.artcweb.enums.StatusEnum;
import com.artcweb.enums.ThirdFlagEnum;
import com.artcweb.service.NailOrderService;
import com.artcweb.util.DateUtil;
import com.artcweb.util.ExcelUtil;
import com.artcweb.util.ExportExcelUtil;
import com.artcweb.util.FileUtil;
import com.artcweb.util.GsonUtil;
import com.artcweb.util.ImageUtil;
import com.artcweb.util.MapUtil;
import com.artcweb.vo.NailOrderVo;

@Service
public class NailOrderServiceImpl extends BaseServiceImpl<NailOrder, Integer> implements NailOrderService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailOrderServiceImpl.class);
	@Autowired
	private NailOrderDao nailOrderDao;
	@Autowired
	private NailDrawingStockDao nailDrawingStockDao;
	@Autowired
	private NailWeightStockDao nailWeightStockDao;
	@Autowired
	private NailDetailConfigDao nailDetailConfigDao;
	@Autowired
	private NailConfigDao nailConfigDao;
	@Autowired
	private NailPictureframeStockDao nailPictureframeStockDao;
	
	

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailOrderVo entity, LayUiResult result) {
		
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
	public String deleteByBatch(String array,HttpServletRequest request) {
		List<NailOrder> list = nailOrderDao.getByBatch(array);
		
		// 已经出库不可以删除
		if(null != list && list.size() > 0){
			for (NailOrder nailOrder : list) {

				int checkoutFlag = nailOrder.getCheckoutFlag();
				if(String.valueOf(checkoutFlag).equals(CheckoutFlagEnum.OK.getDisplayName())){
					return  "选择数据已经出库，删除失败!";
				}
			}
			
		}
		
		
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
			
			
			
			return null;
		}
		return "删除失败";
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
	public LinkedHashMap<String, Integer> uploadImage(HttpServletRequest request,MultipartFile file, NailOrderVo entity,String uploadDirpath,String comeFrom) {
		// 钉子颜色统计
		LinkedHashMap<String, Integer> nailColorMap = new LinkedHashMap<String, Integer>();
		
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
					 
					 // 验证不是32种颜色中的直接拒接
					 Map<String, NailDetailConfig> nailDetailConfigMap = DateMap.nailDetailConfigMap;
					 if(!nailDetailConfigMap.containsKey(sb.toString())){
						 return null;
					 }
					 
					 
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
	public LinkedHashMap<String, NailCount> nailCount(LinkedHashMap<String, Integer> nailColorMap, NailOrderVo entity) {
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
		LinkedHashMap<Integer, NailCount> nailCountMap = new LinkedHashMap<Integer, NailCount>();
		
		
		// 详细配置,判断大小图钉
		 Map<String,NailConfig> nailConfigMap = DateMap.nailConfigMap;
		 String nailConfigId = entity.getNailConfigId();
		 NailConfig nailConfig = nailConfigMap.get(nailConfigId);
		 if(null == nailConfig){
			 logger.error("没有获取到图片类型");
			 return null;
		 }
		 
		 String nailNumber = nailConfig.getNailNumber();
		 BigDecimal nailStandardNumberDecimal = new BigDecimal(nailNumber);
		 logger.info("数量(每公斤颗数)------------------------"+nailStandardNumberDecimal);
		 
		 
		 //获取图钉类型（大钉、小钉等）
		 String nailType = nailConfig.getNailType();
		 

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
			nailCount.setSort(nailDetailConfig.getSort());

			
			// 小钉重量计算公式： 重量计算（数量/3600）=公斤（小数点4位）
			// 大钉重量计算公式： 重量计算（数量/2800）=公斤（小数点4位）
			BigDecimal nailNumberDecimal = new BigDecimal(value);
			BigDecimal requreWeight  = nailNumberDecimal.divide(nailStandardNumberDecimal,4,BigDecimal.ROUND_UP);
			nailCount.setRequreWeight(String.valueOf(requreWeight));
			
			
			// 包计算公式  各颜色重量/各颜色小包公斤数=包数（向上取整）
			String nailBigWeight = nailDetailConfig.getNailBigWeight();//大钉
			String nailSmallWeight = nailDetailConfig.getNailSmallWeight();//小钉
			String nailMiniWeight = nailDetailConfig.getNailMiniWeight();//迷你
			BigDecimal nailBigWeightDecimal = null;
			
			if (nailType.equals(NailImageTypeEnum.BIG.getDisplayName())) {
				// 大钉计算
				nailBigWeightDecimal = new BigDecimal(nailBigWeight);
			}
			else if (nailType.equals(NailImageTypeEnum.MINI.getDisplayName())) {
				// 迷你计算
				nailBigWeightDecimal = new BigDecimal(nailMiniWeight);
			}
			else {
				// 小钉计算
				nailBigWeightDecimal = new BigDecimal(nailSmallWeight);
			}
			
			BigDecimal requrePieces = requreWeight.divide(nailBigWeightDecimal,0, BigDecimal.ROUND_UP);
			nailCount.setRequrePieces(String.valueOf(requrePieces));
		
			
			nailCount.setRgb(key);
			
			nailCountMap.put(nailCount.getSort(), nailCount);
			
			
		        
		        

			nailNumberDecimal = null;
			nailBigWeightDecimal = null;
			nailCount = null;
        }
		nailStandardNumberDecimal = null;
		
		
		long end = System.currentTimeMillis();
		logger.info(" 计算结束------------------------");
		logger.info("统计钉子配置详情耗时："+((end-begin)/1000)+" 秒");
		  
		
		// 排序
		LinkedHashMap<String, NailCount> result = MapUtil.mapSortForIntegerKey(nailCountMap);
		
		return result;
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
	public void nailTotalCount(LinkedHashMap<String, NailCount> nailCountMap, NailOrderVo entity) {
		
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
		for (Map.Entry<String,NailCount> map: nailCountMap.entrySet()) {
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
//					List<Map.Entry<String, NailCount>> list = new ArrayList<Map.Entry<String, NailCount>>(nailTotalCount.getNailCountDetailMap().entrySet());
//			        Collections.sort(list, new Comparator<Map.Entry<String, NailCount>>() {
//			            public int compare(Map.Entry<String, NailCount> o1, Map.Entry<String, NailCount> o2) {
//			                return o1.getKey().compareTo(o2.getKey());
//			            }
//			        });
//			        
			        nailCountList = new LinkedList<NailCount>();
//			        if(null != list && list.size() > 0){
//			        	for (Entry<String, NailCount> entry : list) {
//			        		nailCountList.add(entry.getValue());
//						}
//			        }
			        LinkedHashMap<String, NailCount> nailCountDetailMap =  MapUtil.mapSortForStringKey(nailTotalCount.getNailCountDetailMap());
			        if(null != nailCountDetailMap){
			        	 for(Entry<String, NailCount> mapping:nailCountDetailMap.entrySet()){ 
			        		 nailCountList.add(mapping.getValue());
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
			 ExcelUtil.exportExcel(request, response, columnNames, columnWidth, rows, excelName,entity);
		 }
		 
	}
		 

		 List<Map<String, Object>>  result = null;
		 private List<Map<String, Object>> getNailCount(List<NailCount> nailCountList,NailOrderDto entity) {
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
				 
				 if(result.size() < 26){
					 for(int i = 0; i< (26-result.size()); i++){
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
				nailOrderDto.setStatus(StatusEnum.OK.getDisplayName());
						
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

	
	/**
	* @Title: updateCheckout
	* @Description: 更新库存
	* @author zhuzq
	* @date  2021年3月11日 下午2:47:59
	* @param array
	* @return
	*/
	@Override
	public String updateCheckout(String array) {
		// 验证订单是否生成、款式是否分类
		List<NailOrder> list = nailOrderDao.getByBatch(array);
		if(null != list && list.size() > 0){
			for (NailOrder nailOrder : list) {
				String comefrom = nailOrder.getComefrom();
				String thirdFlag = nailOrder.getThirdFlag();
				
				
			
				
				
				int checkoutFlag = nailOrder.getCheckoutFlag();
				if(String.valueOf(checkoutFlag).equals(CheckoutFlagEnum.OK.getDisplayName())){
					return "选择数据已经出库，出库失败!";
				}
				
				// 验证有没有款式分类
				String nailDrawingStockId = nailOrder.getNailDrawingStockId();
				if(StringUtils.isEmpty(nailDrawingStockId) || "0".equals(nailDrawingStockId)){
					return "选择数据图纸款式没填，出库失败!";
				}
				
				// 画框颜色验证
				String nailPictureFrameId = nailOrder.getNailPictureFrameId();
				if(StringUtils.isEmpty(nailPictureFrameId) || Integer.valueOf(nailPictureFrameId) < 1){
					return "选择数据画框颜色没填，出库失败!";
				}
				
				NailPictureframeEnum nailPictureframeEnum = NailPictureframeEnum.getByValue(Integer.valueOf(nailPictureFrameId));
				if(null == nailPictureframeEnum){
					return "选择数据中有画框不在系统字典里，请联系开发人员,出库失败!";
				}
				
				
					
				// 画框尺寸
				Integer nailPictureFrameStockId = nailOrder.getNailPictureFrameStockId();
				if(null == nailPictureFrameStockId || nailPictureFrameStockId < 1){
					return "选择数据画框尺寸没填，出库失败!";
				}
				
				
				// 没有清单
				if((StringUtils.isEmpty(thirdFlag) || !ThirdFlagEnum.OK.getDisplayName().equals(comefrom))
						&& 
						(StringUtils.isEmpty(comefrom) || !String.valueOf(NailOrderComeFromConstant.BACKSTAGE).equals(comefrom))
						){
							return "选择数据有清单没有生成，出库失败!";
						}
				
			}
			
			// 检查库存与更新库存
			String ckeckInventory = ckeckInventory(list, array,true);
			if(StringUtils.isNotEmpty(ckeckInventory)){
				return ckeckInventory;
			}
			
			
			
		}
		return null;
	}
	

	private String ckeckInventory(List<NailOrder> list,String array,boolean ckeckOutFlag) {

		Map<String, Inventory> nailInventoryMap = getNailInventory(list,ckeckOutFlag);
		
		// 检查图纸
		Map<String, Inventory> drawInventoryMap = getDrawInventory(list,ckeckOutFlag);
		
		// 画框
		Map<String, Inventory> pictureframeMap = getPictureframe(list,ckeckOutFlag);
		
					
		// 出库验证,退库不需要验证
		if(ckeckOutFlag){
			// 检查图钉
			
			if (null != nailInventoryMap && nailInventoryMap.size() > 0) {
				for (Entry<String, Inventory> mapping : nailInventoryMap.entrySet()) {
//					String key = mapping.getKey();
//					if(key.equals("0.0.0")){
//						System.out.println(11);
//					}
					Inventory inventory = mapping.getValue();
					String newSerialNumber = inventory.getNewSerialNumber();

					String nailConfigId=  inventory.getNailConfigId();
					if(StringUtils.isEmpty(nailConfigId)){
						continue;
					}
					String stock_1 = inventory.getStock_1();
					String stock_2 = inventory.getStock_2();
					String stock_3 = inventory.getStock_3();
					String stock_4 = inventory.getStock_4();
					String stock_5 = inventory.getStock_5();
//					String nailType = "";
//					if (String.valueOf(NailTypeEnum.SMALL.getValue()).equals(nailConfigId)) {
//						stock_1 = inventory.getStock_1();
//						nailType = NailTypeEnum.SMALL.getDisplayName();
//					} else if (String.valueOf(NailTypeEnum.ROSE.getValue()).equals(nailConfigId)) {
//						// 玫瑰库存相加
//						stock = inventory.getStock_2();
//						nailType = NailTypeEnum.ROSE.getDisplayName();
//					} else if (String.valueOf(NailTypeEnum.DIAMOND.getValue()).equals(nailConfigId)) {
//						// 砖石库存相加
//						stock = inventory.getStock_3();
//						nailType = NailTypeEnum.DIAMOND.getDisplayName();
//					} else if (String.valueOf(NailTypeEnum.BIG.getValue()).equals(nailConfigId)) {
//						// 打钉库存相加
//						stock = inventory.getStock_4();
//						nailType = NailTypeEnum.BIG.getDisplayName();
//					}


					BigDecimal stock_1BG = new BigDecimal(stock_1);
					BigDecimal stock_2BG = new BigDecimal(stock_2);
					BigDecimal stock_3BG = new BigDecimal(stock_3);
					BigDecimal stock_4BG = new BigDecimal(stock_4);
					BigDecimal stock_5BG = new BigDecimal(stock_5);

					// 库存<出库
//					if (stock_1BG.compareTo(BigDecimal.ZERO) == -1) {
//						return "小钉新编号【"+newSerialNumber + "】库存不足,出库失败!";
//					}
//					if (stock_2BG.compareTo(BigDecimal.ZERO) == -1) {
//						return "玫瑰钉新编号【"+newSerialNumber + "】库存不足,出库失败!";
//					}
//					if (stock_3BG.compareTo(BigDecimal.ZERO) == -1) {
//						return "钻石钉新编号【"+newSerialNumber + "】库存不足,出库失败!";
//					}
//					if (stock_4BG.compareTo(BigDecimal.ZERO) == -1) {
//						return "大钉新编号【"+newSerialNumber + "】库存不足,出库失败!";
//					}
				}
			}
			
			
			
//			if (null != drawInventoryMap && drawInventoryMap.size() > 0) {
//				for (Entry<String, Inventory> mapping : drawInventoryMap.entrySet()) {
//					Inventory inventory = mapping.getValue();
//
//					String style = inventory.getStyle();
//
//					Integer number = inventory.getNumber();
//
//					BigDecimal numberBG = new BigDecimal(number);
//
//					// 库存<出库
//					if (numberBG.compareTo(BigDecimal.ZERO) == -1) {
//
//						return "图纸【"+style + "】库存不足,出库失败!";
//					}
//				}
//			}
			
			
			
//			if (null != pictureframeMap && pictureframeMap.size() > 0) {
//				for (Entry<String, Inventory> mapping : pictureframeMap.entrySet()) {
//					Inventory inventory = mapping.getValue();
//
//					String colorName = inventory.getColorName();
//					//String nailPictureframeId = inventory.getNailPictureframeId();
//					Integer black= inventory.getBlack();
//					BigDecimal blackBG = new BigDecimal(black);
//					
//					Integer white= inventory.getWhite();;
//					BigDecimal whiteBG = new BigDecimal(white);
//					
//					Integer gold= inventory.getGold();
//					BigDecimal goldBG = new BigDecimal(gold);
//					
//					Integer powder= inventory.getPowder();
//					BigDecimal powderBG = new BigDecimal(powder);
//					
//					Integer blue= inventory.getBlue();
//					BigDecimal blueBG = new BigDecimal(blue);
//					
//					Integer silver= inventory.getSilver();
//					BigDecimal silverBG = new BigDecimal(silver);
//
//					// 库存<出库
//					if (blackBG.compareTo(BigDecimal.ZERO) == -1) {
//						return "画框库存"+colorName+"【黑框】库存不足,出库失败!";
//					}
//					if (whiteBG.compareTo(BigDecimal.ZERO) == -1) {
//						return "画框库存"+colorName+"【白框】库存不足,出库失败!";
//					}
//					if (goldBG.compareTo(BigDecimal.ZERO) == -1) {
//						return "画框库存"+colorName+"【金框】库存不足,出库失败!";
//					}
//					if (powderBG.compareTo(BigDecimal.ZERO) == -1) {
//						return "画框库存"+colorName+"【粉框】库存不足,出库失败!";
//					}
//					if (blueBG.compareTo(BigDecimal.ZERO) == -1) {
//						return "画框库存"+colorName+"【蓝框】库存不足,出库失败!";
//					}
//					if (silverBG.compareTo(BigDecimal.ZERO) == -1) {
//						return "画框库存"+colorName+"【银框】库存不足,出库失败!";
//					}
//				}
//			}
			
			
		}
		
		
		// 更新库存
		Integer updateCheckOut = updateCheckOut(array, nailInventoryMap,drawInventoryMap,pictureframeMap, ckeckOutFlag);
		if(null == updateCheckOut || updateCheckOut < 1){
			return "出库失败！";
		}
					
		return null;
	}
	
	/**
	* @Title: getNailInventory
	* @Description: 统计图钉重量
	* @author zhuzq
	* @date  2021年3月11日 下午1:50:05
	* @param list
	* @return
	*/
	private Map<String,Inventory> getNailInventory(List<NailOrder> list,boolean ckeckOutFlag){

		// 查看库图钉条目
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<NailWeightStock> nailWeightStockList = nailWeightStockDao.select(paramMap);
		
		Map<String,Inventory> rgbMap = new HashMap<String, Inventory>();
		if(null != nailWeightStockList && nailWeightStockList.size() > 0){
			for (NailWeightStock nailWeightStock : nailWeightStockList) {
				Integer id = nailWeightStock.getId();
				String rgb = nailWeightStock.getRgb();
				String newSerialNumber = nailWeightStock.getNewSerialNumber();
				String stock_1 = nailWeightStock.getStock_1();
				String stock_2 = nailWeightStock.getStock_2();
				String stock_3 = nailWeightStock.getStock_3();
				String stock_4 = nailWeightStock.getStock_4();
				String stock_5 = nailWeightStock.getStock_5();
				Inventory inventory = new Inventory(id,rgb, newSerialNumber, stock_1, stock_2, stock_3, stock_4,stock_5);
				rgbMap.put(rgb, inventory);
			}
		}
		
		
		// 统计出库图钉重量
		if(null != list && list.size() > 0){
			for (NailOrder nailOrder : list) {
				
				String nailConfigId = nailOrder.getNailConfigId();
				
				String nailCountDetail = nailOrder.getNailCountDetail();
				// 钉子统计详情信息
				if(StringUtils.isNotBlank(nailCountDetail)){
					NailTotalCount nailTotalCount = ( NailTotalCount ) GsonUtil.jsonToBean(nailCountDetail,NailTotalCount.class);
					
					
					// 详细列表
					if(null != nailTotalCount && null != nailTotalCount.getNailCountDetailMap() && nailTotalCount.getNailCountDetailMap().size() > 0){
				        LinkedHashMap<String, NailCount> nailCountDetailMap =  MapUtil.mapSortForStringKey(nailTotalCount.getNailCountDetailMap());
				        for(Entry<String, NailCount> mapping:nailCountDetailMap.entrySet()){ 
				        	NailCount nailCount = mapping.getValue();
				        	// 重量
				        	
				        	String rgb = nailCount.getRgb();
				        	Inventory inventory = rgbMap.get(rgb);
				        	if(null != inventory){
				        		inventory = inventoryDeal(inventory, nailCount, nailConfigId, ckeckOutFlag);
				        		rgbMap.put(rgb, inventory);
				        	}
			            } 
					}	
				
				}
			}
		}
		return rgbMap;
		
	}
	
	
	public Inventory inventoryDeal(Inventory inventory, NailCount nailCount, String nailConfigId,
			boolean ckeckOutFlag) {
		if (StringUtils.isNotEmpty(nailConfigId)) {

			BigDecimal stockMap = null;
			BigDecimal stock = null;
			String requreWeight = nailCount.getRequreWeight();
			BigDecimal weight = new BigDecimal(requreWeight);
			String inventoryStock = null;
			//小钉库存相加
			if (String.valueOf(NailTypeEnum.SMALL.getValue()).equals(nailConfigId)) {
				inventoryStock = inventory.getStock_1();
				// 玫瑰库存相加
			} else if (String.valueOf(NailTypeEnum.ROSE.getValue()).equals(nailConfigId)) {
				inventoryStock = inventory.getStock_2();
				// 砖石库存相加
			} else if (String.valueOf(NailTypeEnum.DIAMOND.getValue()).equals(nailConfigId)) {
				inventoryStock = inventory.getStock_3();
				// 大钉库存相加
			} else if (String.valueOf(NailTypeEnum.BIG.getValue()).equals(nailConfigId)) {
				inventoryStock = inventory.getStock_4();
				//迷你
			}else if (String.valueOf(NailTypeEnum.MINI.getValue()).equals(nailConfigId)) {
				inventoryStock = inventory.getStock_5();
			}

			
			if(StringUtils.isEmpty(inventoryStock)){
				inventoryStock = "0";
			}
			if (ckeckOutFlag) {
				stockMap = new BigDecimal(inventoryStock);
				stock = stockMap.subtract(weight);
			} else {
				stockMap = new BigDecimal(inventoryStock);
				stock = stockMap.add(weight);
			}
			// 小钉库存相加
			if (String.valueOf(NailTypeEnum.SMALL.getValue()).equals(nailConfigId)) {
				inventory.setStock_1(stock.toString());
				// 玫瑰库存相加
			} else if (String.valueOf(NailTypeEnum.ROSE.getValue()).equals(nailConfigId)) {
				// 砖石库存相加
				inventory.setStock_2(stock.toString());
			} else if (String.valueOf(NailTypeEnum.DIAMOND.getValue()).equals(nailConfigId)) {
				// 大钉库存相加
				inventory.setStock_3(stock.toString());
			} else if (String.valueOf(NailTypeEnum.BIG.getValue()).equals(nailConfigId)) {
				inventory.setStock_4(stock.toString());
				//迷你
			}else if (String.valueOf(NailTypeEnum.MINI.getValue()).equals(nailConfigId)) {
				inventory.setStock_5(stock.toString());
			}

		}
		inventory.setNailConfigId(nailConfigId);
		return inventory;
	}
	
	/**
	* @Title: getDrawInventory
	* @Description: 统计图纸
	* @author zhuzq
	* @date  2021年3月11日 下午3:30:54
	* @param list
	* @return
	*/
	private Map<String, Inventory> getDrawInventory(List<NailOrder> list,boolean ckeckOutFlag) {

		// 查看库图钉条目
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<NailDrawingStock> nailDrawingStockList = nailDrawingStockDao.select(paramMap);

		Map<String, Inventory> rgbMap = new HashMap<String, Inventory>();
		if (null != nailDrawingStockList && nailDrawingStockList.size() > 0) {
			for (NailDrawingStock nailWeightStock : nailDrawingStockList) {
				Integer nailDrawingId = nailWeightStock.getId();
				String style = nailWeightStock.getStyle();
				Integer number = nailWeightStock.getNumber();
				Inventory inventory = new Inventory(nailDrawingId, style, number);
				rgbMap.put(String.valueOf(nailDrawingId), inventory);
			}
		}

		// 统计出库图纸
		if (null != list && list.size() > 0) {
			for (NailOrder nailOrder : list) {
				String nailDrawingStockId = nailOrder.getNailDrawingStockId();
				// 图纸

				Inventory inventory = rgbMap.get(nailDrawingStockId);
				if (null != inventory) {
					//
					String drawCkeckOutNumber = StringUtils.isEmpty(inventory.getDrawCkeckOutNumber()) ? "0"
							: inventory.getDrawCkeckOutNumber();
					if(ckeckOutFlag){
						inventory.setNumber(inventory.getNumber()-1);
						inventory.setDrawCkeckOutNumber(drawCkeckOutNumber+1);
						
					}else{
						inventory.setNumber(inventory.getNumber()+1);
						inventory.setDrawCkeckOutNumber(drawCkeckOutNumber+1);
					}
					rgbMap.put(nailDrawingStockId, inventory);

				}
			}
		}
		return rgbMap;

	}
	
	
	
	
	private Map<String, Inventory> getPictureframe(List<NailOrder> list,boolean ckeckOutFlag) {

		// 查看库图钉条目
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<NailPictureframeStock> nailPictureframeStockList = nailPictureframeStockDao.select(paramMap);

		Map<String, Inventory> rgbMap = new HashMap<String, Inventory>();
		if (null != nailPictureframeStockList && nailPictureframeStockList.size() > 0) {
			for (NailPictureframeStock nailPictureframeStock : nailPictureframeStockList) {
				Integer nailPictureframeStockId = nailPictureframeStock.getId();
				String colorName = nailPictureframeStock.getColorName();
				Integer black = nailPictureframeStock.getBlack();
				Integer white = nailPictureframeStock.getWhite();
				Integer blue = nailPictureframeStock.getBlue();
				Integer powder = nailPictureframeStock.getPowder();
				Integer gold = nailPictureframeStock.getGold();
				Integer silver = nailPictureframeStock.getSilver();
				Integer rose = nailPictureframeStock.getRose();
				Inventory inventory = new Inventory(nailPictureframeStockId+"", colorName ,black,white,blue,powder,gold,silver,rose);
				rgbMap.put(String.valueOf(nailPictureframeStockId), inventory);
			}
		}

		// 统计画框
		if (null != list && list.size() > 0) {
			for (NailOrder nailOrder : list) {
				
				// 画框
				String nailPictureFrameId = nailOrder.getNailPictureFrameId();
				
				// 库存
				Integer nailPictureFrameStockId = nailOrder.getNailPictureFrameStockId();
				
				
				Inventory inventory = rgbMap.get(String.valueOf(nailPictureFrameStockId));
				if (null != inventory) {
					
					if(ckeckOutFlag){
						if(NailPictureframeEnum.BLACK.getValue().toString().equals(nailPictureFrameId)){
							inventory.setBlack(inventory.getBlack()-1);
						}else if(NailPictureframeEnum.WHITE.getValue().toString().equals(nailPictureFrameId)){
							inventory.setWhite(inventory.getWhite()-1);
						}else if(NailPictureframeEnum.BLUE.getValue().toString().equals(nailPictureFrameId)){
							inventory.setBlue(inventory.getBlue()-1);
						}else if(NailPictureframeEnum.GOLD.getValue().toString().equals(nailPictureFrameId)){
							inventory.setGold(inventory.getGold()-1);
						}else if(NailPictureframeEnum.SILVER.getValue().toString().equals(nailPictureFrameId)){
							inventory.setSilver(inventory.getBlack()-1);
						}else if(NailPictureframeEnum.POWDER.getValue().toString().equals(nailPictureFrameId)){
							inventory.setPowder(inventory.getPowder()-1);
						}else if(NailPictureframeEnum.ROSE.getValue().toString().equals(nailPictureFrameId)){
							inventory.setRose(inventory.getRose()-1);
						}
						
					}else{
						if(NailPictureframeEnum.BLACK.getValue().toString().equals(nailPictureFrameId)){
							inventory.setBlack(inventory.getBlack()+1);
						}else if(NailPictureframeEnum.WHITE.getValue().toString().equals(nailPictureFrameId)){
							inventory.setWhite(inventory.getWhite()+1);
						}else if(NailPictureframeEnum.BLUE.getValue().toString().equals(nailPictureFrameId)){
							inventory.setBlue(inventory.getBlue()+1);
						}else if(NailPictureframeEnum.GOLD.getValue().toString().equals(nailPictureFrameId)){
							inventory.setGold(inventory.getGold()+1);
						}else if(NailPictureframeEnum.SILVER.getValue().toString().equals(nailPictureFrameId)){
							inventory.setSilver(inventory.getBlack()+1);
						}else if(NailPictureframeEnum.POWDER.getValue().toString().equals(nailPictureFrameId)){
							inventory.setPowder(inventory.getPowder()+1);
						}else if(NailPictureframeEnum.ROSE.getValue().toString().equals(nailPictureFrameId)){
							inventory.setRose(inventory.getRose()+1);
						}
					}
					rgbMap.put(String.valueOf(nailPictureFrameStockId), inventory);

				}
			}
		}
		return rgbMap;

	}
	
	
	
	

	public Integer updateCheckOut(String array,Map<String, Inventory> nailInventoryMap,Map<String, Inventory> drawInventoryMap,Map<String, Inventory> pictureframeMap,boolean ckeckOutFlag) {
		// 更新出库标识
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		
		paramMap.put("id",array);
		
		if(ckeckOutFlag){
			paramMap.put("checkoutFlag",CheckoutFlagEnum.OK.getDisplayName());
		}else{
			paramMap.put("checkoutFlag",CheckoutFlagEnum.OFF.getDisplayName());
		}
		
		Integer updateCheckoutFlag = nailOrderDao.updateCheckoutFlag(paramMap);
		if(null != updateCheckoutFlag && updateCheckoutFlag > 0){
			//图钉操作
			//Map<String,Inventory> nailInventoryMap = getNailInventory(list);
			 for(Entry<String, Inventory> mapping:nailInventoryMap.entrySet()){ 
				 Inventory inventory = mapping.getValue();
				 String nailConfigId = inventory.getNailConfigId();
				 Integer primaryKey = inventory.getId();
				 NailWeightStock nailWeightStock = nailWeightStockDao.get(primaryKey);
				 if(null != nailWeightStock){
//				 if (String.valueOf(NailTypeEnum.SMALL.getValue()).equals(nailConfigId)) {
					 	nailWeightStock.setStock_1(inventory.getStock_1());
//
//					} else if (String.valueOf(NailTypeEnum.ROSE.getValue()).equals(nailConfigId)) {
//						// 玫瑰库存相加
						nailWeightStock.setStock_2(inventory.getStock_2());
//					} else if (String.valueOf(NailTypeEnum.DIAMOND.getValue()).equals(nailConfigId)) {
//						// 砖石库存相加
						nailWeightStock.setStock_3(inventory.getStock_3());
//					} else if (String.valueOf(NailTypeEnum.BIG.getValue()).equals(nailConfigId)) {
//						// 大钉库存相加
						nailWeightStock.setStock_4(inventory.getStock_4());
						// 迷你
						nailWeightStock.setStock_5(inventory.getStock_5());
//					}
					 Integer update = nailWeightStockDao.update(nailWeightStock);
					 //logger.info("更新库存"+update);
				 }
			 }
			 
			//图纸库存操作
			// Map<String,Inventory> drawInventoryMap = getDrawInventory(list);
			 for(Entry<String, Inventory> mapping:drawInventoryMap.entrySet()){ 
				 Inventory inventory = mapping.getValue();
				 Integer number = inventory.getNumber();
				 Integer primaryKey = inventory.getDrawDrawingId();
				 NailDrawingStock nailDrawingStock = nailDrawingStockDao.get(primaryKey);
				 if(null != nailDrawingStock){
					 nailDrawingStock.setNumber(number);
					 Integer update = nailDrawingStockDao.update(nailDrawingStock);
					 //logger.info("更新库存"+update);
				 }
			 }
			 
			 
			 // 图框颜色
			 for(Entry<String, Inventory> mapping:pictureframeMap.entrySet()){ 
				 Inventory inventory = mapping.getValue();
				 String nailPictureframeId = inventory.getNailPictureframeStockId();
				 NailPictureframeStock nailPictureframeStock = nailPictureframeStockDao.get(Integer.valueOf(nailPictureframeId));
				 if(null != nailPictureframeStock){
					 nailPictureframeStock.setBlack(inventory.getBlack());
					 nailPictureframeStock.setWhite(inventory.getWhite());
					 nailPictureframeStock.setGold(inventory.getGold());
					 nailPictureframeStock.setSilver(inventory.getSilver());
					 nailPictureframeStock.setPowder(inventory.getPowder());
					 nailPictureframeStock.setBlue(inventory.getBlue());
					 nailPictureframeStock.setRose(inventory.getRose());
					 Integer update = nailPictureframeStockDao.update(nailPictureframeStock);
					 //logger.info("更新库存"+update);
				 }
			 }
			 
			 
			 
		}
		return updateCheckoutFlag;
	}

	/**
	* @Title: updateCancelCheckout
	* @Description: 取消库存
	* @author zhuzq
	* @date  2021年3月11日 下午5:30:12
	* @param array
	* @return
	*/
	@Override
	public String updateCancelCheckout(String array) {
		// 验证订单是否生成、款式是否分类
		List<NailOrder> list = nailOrderDao.getByBatch(array);
		if (null != list && list.size() > 0) {
			for (NailOrder nailOrder : list) {
				String comefrom = nailOrder.getComefrom();
				String thirdFlag = nailOrder.getThirdFlag();

				int checkoutFlag = nailOrder.getCheckoutFlag();
				if(String.valueOf(checkoutFlag).equals(CheckoutFlagEnum.OFF.getDisplayName())){
					return "选择数据未出库，退库失败!";
				}

				// 验证有没有款式分类
				String nailDrawingStockId = nailOrder.getNailDrawingStockId();
				if (StringUtils.isEmpty(nailDrawingStockId) || "0".equals(nailDrawingStockId)) {
					return "选择数据图纸款式没填，退库失败!";
				}

				// 没有清单
				if ((StringUtils.isEmpty(thirdFlag) || !ThirdFlagEnum.OK.getDisplayName().equals(comefrom))
						&& (StringUtils.isEmpty(comefrom)
								|| !String.valueOf(NailOrderComeFromConstant.BACKSTAGE).equals(comefrom))) {
					return "选择数据有清单没有生成，退库失败!";
				}

			}

			// 检查库存与更新库存
			String ckeckInventory = cancelCkeckInventory(list, array);
			if (StringUtils.isNotEmpty(ckeckInventory)) {
				return ckeckInventory;
			}

		}
		return null;
	}
	
	private String cancelCkeckInventory(List<NailOrder> list,String array) {
		Map<String, Inventory> nailInventoryMap = getNailInventory(list,false);
		
		Map<String, Inventory> drawInventoryMap = getDrawInventory(list,false);
		
		// 画框
		Map<String, Inventory> pictureframeMap = getPictureframe(list,false);
		// 更新库存
		Integer updateCheckOut = updateCheckOut(array, nailInventoryMap,drawInventoryMap,pictureframeMap,false);
		if(null == updateCheckOut || updateCheckOut < 1){
			return "退库失败！";
		}
					
		return null;
	}

	
	/**
	* @Title: analys
	* @Description: 统计分析
	* @author zhuzq
	* @date  2021年3月12日 下午2:54:50
	* @param entity
	* @return
	*/
	@Override
	public Map<String,Object>  analys(NailOrderVo entity) {
		
		
		Map<String,Object> dataMap = new HashMap<String, Object>();
		
		
		
		String array = entity.getArray();
		
		String createDateStr = entity.getCreateDateStr();
		String nailConfigIde = entity.getNailConfigId();
		int checkoutFlag = entity.getCheckoutFlag();
		
		if(StringUtils.isNotBlank(createDateStr)){
			String[] createDateArr = createDateStr.split("~");
			if(null != createDateArr && createDateArr.length ==2){
				entity.setBeginDate(createDateArr[0]);
				entity.setEndDate(createDateArr[1]);
			}
		}
		
		if(StringUtils.isEmpty(array) && StringUtils.isEmpty(createDateStr) && StringUtils.isEmpty(nailConfigIde) && checkoutFlag == -1){
			String  beginDate = DateUtil.getStartTime();
			String  endDate = DateUtil.getEndTime();
			entity.setCreateDateStr(beginDate);
			
			entity.setBeginDate(beginDate);
			entity.setEndDate(endDate);
		
		}
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("entity", entity);
		// 获取订单
		List<NailOrderDto> nailOrderList = nailOrderDao.selectByMap(paramMap);
		
		
		
		
		
		// 订单统计
		Map<String,AnalysNailConfig> analysNailConfigMap =  getNailConfigMap();
		
		// 图钉统计
		Map<String,Analys>  analysMap = getNailDetailConfigMap();
		
		
		

		if(null != nailOrderList && nailOrderList.size() > 0){
			
			
			
			String totalNailNumber = null;
			String totalrPieces = null;
			String totalWeight = null;
			for (NailOrderDto nailOrder : nailOrderList) {
				
				
				// 订单统计
				String nailConfigId= nailOrder.getNailConfigId();
				if(StringUtils.isNotEmpty(nailConfigId)){
					AnalysNailConfig acf = analysNailConfigMap.get(nailConfigId);
					if(null != acf){
						Integer Id = acf.getId();
						int total = acf.getTotal() +1;
						acf.setTotal(total);
						analysNailConfigMap.put(String.valueOf(Id), acf);
					}
				}
				
				
				
				// 图钉统计
				String nailCountDetail = nailOrder.getNailCountDetail();
				if(StringUtils.isNotEmpty(nailCountDetail)){
					NailTotalCount nailTotalCount = ( NailTotalCount ) GsonUtil.jsonToBean(nailCountDetail,NailTotalCount.class);
					
					if(null != nailTotalCount){
						LinkedHashMap<String, NailCount> nailCountMap = nailTotalCount.getNailCountDetailMap();
						// 排序
						nailCountMap = MapUtil.mapSortForStringKey(nailCountMap);
						if(null != nailCountMap && nailCountMap.size() > 0){
							
							 totalNailNumber = new BigDecimal(StringUtils.isEmpty(totalNailNumber)?"0": totalNailNumber).add(new BigDecimal(StringUtils.isEmpty(nailTotalCount.getTotalNailNumber())?"0" : nailTotalCount.getTotalNailNumber())).toString() ;
							 totalrPieces = new BigDecimal(StringUtils.isEmpty(totalrPieces)?"0":totalrPieces).add(new BigDecimal(StringUtils.isEmpty(nailTotalCount.getTotalrPieces())?"0" : nailTotalCount.getTotalrPieces())).toString() ;
							 totalWeight = new BigDecimal(StringUtils.isEmpty(totalWeight)?"0":totalWeight).add(new BigDecimal(StringUtils.isEmpty(nailTotalCount.getTotalWeight())?"0" : nailTotalCount.getTotalWeight())).toString() ;
							
							
							// 统计各图钉
							for (Entry<String, NailCount> mapping : nailCountMap.entrySet()) {
								String newSerialNumber = mapping.getKey();
								NailCount nailCount = mapping.getValue();
								Analys analys = analysMap.get(newSerialNumber);
								if(null != analys){
									BigDecimal nailNumber = new BigDecimal(StringUtils.isEmpty(analys.getNailNumber())?"0":analys.getNailNumber()).add(new BigDecimal(nailCount.getNailNumber()));
									analys.setNailNumber(nailNumber.toString());
									
									BigDecimal requreWeight = new BigDecimal(StringUtils.isEmpty(analys.getRequreWeight())?"0":analys.getRequreWeight()).add(new BigDecimal(nailCount.getRequreWeight()));
									analys.setRequreWeight(requreWeight.toString());
									
									BigDecimal requrePieces = new BigDecimal(StringUtils.isEmpty(analys.getRequrePieces())?"0":analys.getRequrePieces()).add(new BigDecimal(nailCount.getRequrePieces()));
									analys.setRequrePieces(requrePieces.toString());
									
									
									analys.setTotalNailNumber(totalNailNumber);
									analys.setTotalrPieces(totalrPieces);
									analys.setTotalWeight(totalWeight);
									
									
									
									
									analys.setRgbSize(nailOrderList.size());
									
									analysMap.put(newSerialNumber, analys);
									
								}
								
								
								
								
							}
							
							
						}
						
						
						
					}
				}
			}
		}
		
		
		// 图钉统计处理
		Analys a = getAnalysOne(analysMap);
		if(a !=  null){
			String nailNumber = a.getTotalNailNumber();
			String requreWeight = a.getTotalWeight();
			String requrePieces = a.getTotalrPieces();
			int rgbSize = a.getRgbSize();
			Analys n = new Analys();
			n.setSort(1000000);
			n.setRgb("255,255,255");
			n.setIndexId("总计");
			n.setRgbSize(rgbSize);
			n.setNailNumber(nailNumber);
			n.setRequreWeight(requreWeight);
			n.setRequrePieces(requrePieces);
			analysMap.put("总计", n);
			
			Analys n1 = new Analys();
			n1.setRgbSize(rgbSize);
			n1.setRgb("255,255,255");
			n1.setSort(1000001);
			n1.setIndexId("平均值");
			if(rgbSize > 0){
				if(StringUtils.isNotEmpty(nailNumber)){
					n1.setNailNumber(new BigDecimal(nailNumber).divide(new BigDecimal(rgbSize),2,BigDecimal.ROUND_HALF_UP).toString());
				}
				
				if(StringUtils.isNotEmpty(requreWeight)){
					n1.setRequreWeight(new BigDecimal(requreWeight).divide(new BigDecimal(rgbSize),2,BigDecimal.ROUND_HALF_UP).toString());
				}
				if(StringUtils.isNotEmpty(requrePieces)){
					n1.setRequrePieces(new BigDecimal(requrePieces).divide(new BigDecimal(rgbSize),2,BigDecimal.ROUND_HALF_UP).toString());
				}
			}
			analysMap.put("平均值", n1);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		analysMap = dataDeal(analysMap);
		
		analysNailConfigMap = nailConfigMapDataDeal(analysNailConfigMap);
		dataMap.put("analysNailConfigMap", analysNailConfigMap);
		dataMap.put("analysMap", analysMap);
		
		
		return dataMap;
	}
	
	
	
	private Map<String, AnalysNailConfig> nailConfigMapDataDeal(Map<String, AnalysNailConfig> analysNailConfigMap) {
		if(null != analysNailConfigMap && analysNailConfigMap.size() > 0){
			int  total = 0;
			for (Entry<String, AnalysNailConfig> iterable_element : analysNailConfigMap.entrySet()) {
				AnalysNailConfig analysTotal = iterable_element.getValue();
				if(null != analysTotal){
					total = total + analysTotal.getTotal();
				}
			}
			
			AnalysNailConfig an = new AnalysNailConfig();
			an.setTotal(total);
			an.setNailType("总计");
			analysNailConfigMap.put("总计", an);
		}
		return analysNailConfigMap;
	}

	private Map<String, AnalysNailConfig> getNailConfigMap() {
		Map<String, AnalysNailConfig> analysNailConfig = new LinkedHashMap<String, AnalysNailConfig>();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		List<NailConfig> nailConfigList = nailConfigDao.select(paramMap);
		if(null != nailConfigList && nailConfigList.size() > 0){
			for (NailConfig nailConfig : nailConfigList) {
				Integer id = nailConfig.getId();
				String nailType  = nailConfig.getNailType();
				AnalysNailConfig a = new AnalysNailConfig();
				a.setId(id);
				a.setNailType(nailType);
				analysNailConfig.put(String.valueOf(id), a);
			}
			
		}
		return analysNailConfig;
	}

	
	
	
	//图钉数据处理
	private Map<String,Analys> dataDeal(Map<String,Analys> data){
		
		if(null != data && data.size() > 0){
			
			Analys analysTotal = data.get("总计");
			
			for (Entry<String, Analys> iterable_element : data.entrySet()) {
				Analys analys = iterable_element.getValue();
				int rgbSize = analys.getRgbSize();
				
				// 计算比率
				String totalNailNumber = analysTotal.getNailNumber();
				String totalWeight = analysTotal.getRequreWeight();
				String totalrPieces = analysTotal.getRequrePieces();
				
					if(rgbSize > 0){
						
						// 计算平均值
						if(StringUtils.isNotEmpty(analys.getNailNumber())){
							String nailNumberAvg = new BigDecimal(analys.getNailNumber()).divide(new BigDecimal(rgbSize),4,BigDecimal.ROUND_HALF_UP).toString();
							analys.setNailNumberAvg(nailNumberAvg);
						}
						if(StringUtils.isNotEmpty(analys.getRequrePieces())){
							String requrePiecesAvg = new BigDecimal(analys.getRequrePieces()).divide(new BigDecimal(rgbSize),4,BigDecimal.ROUND_HALF_UP).toString();
							analys.setRequrePiecesAvg(requrePiecesAvg);
							
						}
						if(StringUtils.isNotEmpty(analys.getRequreWeight())){
							String requreWeightAvg = new BigDecimal(analys.getRequreWeight()).divide(new BigDecimal(rgbSize),4,BigDecimal.ROUND_HALF_UP).toString();
							analys.setRequreWeightAvg(requreWeightAvg);
						}
						
						
						
						
						// 计算比率
//						if(StringUtils.isNotEmpty(totalNailNumber)){
//							String nailNumberRatio = new BigDecimal(analys.getNailNumber()).divide(new BigDecimal(totalNailNumber),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).toBigInteger().toString();
//							analys.setNailNumberRatio(nailNumberRatio+"%");
//						}
						if(StringUtils.isNotEmpty(totalNailNumber)){
							String nailNumberRatio = (new BigDecimal(analys.getNailNumber()).divide(new BigDecimal(totalNailNumber),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))).toString();
							analys.setNailNumberRatio(nailNumberRatio+"%");
						}
						
						if(StringUtils.isNotEmpty(totalWeight)){
							String requreWeightRatio = new BigDecimal(analys.getRequreWeight()).divide(new BigDecimal(totalWeight),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).toString();
							analys.setRequreWeightRatio(requreWeightRatio+"%");
						}
						
						if(StringUtils.isNotEmpty(totalrPieces)){
							String requrePiecesRatio = new BigDecimal(analys.getRequrePieces()).divide(new BigDecimal(totalrPieces),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).toString();
							analys.setRequrePiecesRatio(requrePiecesRatio+"%");
						}
				}
					
					
			}
		}
		return data;
		
	}
	
	
	private Map<String,Analys> getNailDetailConfigMap (){
		Map<String,Analys> nailTotalCountMap = new LinkedHashMap<String, Analys>(); 
		// 获取图钉
		List<NailDetailConfig> nailDetailConfigList = nailDetailConfigDao.select(new HashMap<String,Object>());
		if(null != nailDetailConfigList && nailDetailConfigList.size() > 0){
			for (NailDetailConfig nailDetailConfig : nailDetailConfigList) {
				int sort =nailDetailConfig.getSort();
				String rgb = nailDetailConfig.getRgb();
				String newSerialNumber = nailDetailConfig.getNewSerialNumber();
				Analys analys= new Analys(sort,rgb, newSerialNumber);
				nailTotalCountMap.put(newSerialNumber,analys);
			}
		}
		return nailTotalCountMap;
	
	}
	
	
	@Override
	public Analys getAnalysOne(Map<String,Analys> nailDetailConfigMap){
		Analys analys = null; 
		if(null != nailDetailConfigMap && nailDetailConfigMap.size() > 0){
			for (Entry<String, Analys> nailOrder : nailDetailConfigMap.entrySet()) {
				analys = nailOrder.getValue();
	            if (analys != null) {
	                break;
	            }
			}
		}
		return analys;
	}
	
	
	

}
