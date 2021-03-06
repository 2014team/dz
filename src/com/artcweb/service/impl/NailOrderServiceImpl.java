
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
import com.artcweb.bean.NailTotalCount;
import com.artcweb.bean.NailWeightStock;
import com.artcweb.cache.DateMap;
import com.artcweb.constant.NailOrderComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.dao.NailConfigDao;
import com.artcweb.dao.NailDetailConfigDao;
import com.artcweb.dao.NailDrawingStockDao;
import com.artcweb.dao.NailOrderDao;
import com.artcweb.dao.NailWeightStockDao;
import com.artcweb.dto.NailOrderDto;
import com.artcweb.enums.CheckoutFlagEnum;
import com.artcweb.enums.NailImageTypeEnum;
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
	
	

	/**
	 * @Title: findByPage
	 * @Description: ????????????
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailOrderVo entity, LayUiResult result) {
		
		//??????????????????
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
	 * @Description: ????????????
	 * @param array
	 * @return
	 */
	@Override
	public String deleteByBatch(String array,HttpServletRequest request) {
		List<NailOrder> list = nailOrderDao.getByBatch(array);
		
		// ???????????????????????????
		if(null != list && list.size() > 0){
			for (NailOrder nailOrder : list) {

				int checkoutFlag = nailOrder.getCheckoutFlag();
				if(String.valueOf(checkoutFlag).equals(CheckoutFlagEnum.OK.getDisplayName())){
					return  "???????????????????????????????????????!";
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
						// ???????????????????????????????????????
						Map<String,Object> paramMap  = new  HashMap<String, Object>();
						
						paramMap.put("imageUrl", sourceImageUrl);
						List<NailOrder> nailOrder = nailOrderDao.checkExist(paramMap);
						if(null == nailOrder || nailOrder.size() > 0){// ??????????????????
							
							for (NailOrder nailOrder2 : nailOrder) {
								if(!n.getId().equals(nailOrder2.getId())){
									boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
									logger.info("???????????????????????? = "+deleteResult);
									if(deleteResult){
										boolean  sourceImageUrlresult = FileUtil.deleteFile(resultImageUrl,request);
										logger.info("???????????????????????? = "+sourceImageUrlresult);
										
									}
								}
							}
						}else{
							boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
							logger.info("???????????????????????? = "+deleteResult);
						}
						
					}
				}
			}
			
			
			
			return null;
		}
		return "????????????";
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
		// ??????????????????
		LinkedHashMap<String, Integer> nailColorMap = new LinkedHashMap<String, Integer>();
		
		BufferedImage image = null;
		InputStream input = null;
		List<String> stepList = null;
		try {
			input = file.getInputStream();
			image = ImageIO.read(input);
			int ih = image.getHeight();
			int iw = image.getWidth();
			logger.info("???="+ih +" ???="+iw);
			
			stepList = new Vector<String>();
			
			// ????????????
			BufferedImage bi = null;
			// ??????
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
					
					// ???????????????
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
					 // ?????????????????????
					 if(null != count){
						 nailColorMap.put(colorKsy, count+1);
					 }else{
						 nailColorMap.put(colorKsy, 1);
					 }
					 image.setRGB(x, y, pixel);
					 sb = null;
					logger.info("?????????="+colorKsy);
				}
			}
			
			
			// ??????????????????
			if(StringUtils.isNotEmpty(comeFrom) && !comeFrom.equals(NailOrderComeFromConstant.H5)){
				g2d.dispose();
		    	String resultImageUrl = ImageUtil.getUploadPath(request, bi, null, UploadConstant.SAVE_UPLOAD_NAIL_PATH, false);
				if(StringUtils.isNotEmpty(resultImageUrl)){
					entity.setResultImageUrl(resultImageUrl);
				}
			}
			
			// --------????????????????????????------------------
			String uploadPath = ImageUtil.getUploadPath(request, image,file, uploadDirpath,false);
			long end = System.currentTimeMillis();
			logger.info("???????????????????????????"+((end-begin)/1000)+" ???");
			
			
			// ???????????????
			entity.setHeight(String.valueOf(ih));
			entity.setWidth(String.valueOf(iw));
			// ??????????????????
			
			String step = GsonUtil.toJsonAll(stepList);
			logger.info("step="+step);
			entity.setStep(step);
			// ??????????????????
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
			logger.error("nailColorMap??????");
			return null;
		}
		
		// ??????????????????
		Map<String, NailDetailConfig>  nailDetailConfigMap = DateMap.nailDetailConfigMap;
		if(nailDetailConfigMap == null || nailDetailConfigMap.size() < 1 ){
			logger.error("nailDetailConfigMap??????");
			return null;
		}
		
		
		// ??????????????????
		LinkedHashMap<Integer, NailCount> nailCountMap = new LinkedHashMap<Integer, NailCount>();
		
		
		// ????????????,??????????????????
		 Map<String,NailConfig> nailConfigMap = DateMap.nailConfigMap;
		 String nailConfigId = entity.getNailConfigId();
		 NailConfig nailConfig = nailConfigMap.get(nailConfigId);
		 if(null == nailConfig){
			 logger.error("???????????????????????????");
			 return null;
		 }
		 
		 boolean nailFlag = false;
		 String nailNumber = nailConfig.getNailNumber();
		 BigDecimal nailStandardNumberDecimal = new BigDecimal(nailNumber);
		 logger.info("??????(???????????????)------------------------"+nailStandardNumberDecimal);
		 
		 if(nailConfig.getNailType().equals(NailImageTypeEnum.BIG.getDisplayName())){
			 nailFlag = true;
			 
		 }

		 // ????????????
		logger.info(" ????????????------------------------");
		long begin = System.currentTimeMillis();
		
		for (Map.Entry<String, Integer> entry : nailColorMap.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			NailDetailConfig nailDetailConfig = nailDetailConfigMap.get(key);
			if(null == nailDetailConfig){
				logger.error(key+"??????????????????????????????");
				continue;
			}
			
			NailCount nailCount = new NailCount();
			nailCount.setIndexId(nailDetailConfig.getNewSerialNumber());
			nailCount.setNailNumber(String.valueOf(value));
			nailCount.setSort(nailDetailConfig.getSort());

			
			// ??????????????????????????? ?????????????????????/3600???=??????????????????4??????
			// ??????????????????????????? ?????????????????????/2800???=??????????????????4??????
			BigDecimal nailNumberDecimal = new BigDecimal(value);
			BigDecimal requreWeight  = nailNumberDecimal.divide(nailStandardNumberDecimal,4,BigDecimal.ROUND_UP);
			nailCount.setRequreWeight(String.valueOf(requreWeight));
			
			
			// ???????????????  ???????????????/????????????????????????=????????????????????????
			String nailBigWeight = nailDetailConfig.getNailBigWeight();
			String nailSmallWeight = nailDetailConfig.getNailSmallWeight();
			BigDecimal nailBigWeightDecimal = new BigDecimal(nailBigWeight);
			if(!nailFlag){
				//????????????
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
		logger.info(" ????????????------------------------");
		logger.info("?????????????????????????????????"+((end-begin)/1000)+" ???");
		  
		
		// ??????
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
			logger.error("nailCountMap??????");
			return;
		}
		
		if(null == entity){
			logger.error("entity??????");
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
		
		// ???????????????
		nailTotalCount.setTotalWeight(String.valueOf(wDecimal.setScale(2,BigDecimal.ROUND_HALF_DOWN)));
		// ?????????
		nailTotalCount.setTotalNailNumber(String.valueOf(nDecimal));
		// ?????????
		nailTotalCount.setTotalrPieces(String.valueOf(pDecimal));
		// ????????????
		nailTotalCount.setNailCountDetailMap(nailCountMap);
		
		// ??????????????????
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
			// ????????????????????????
			if(StringUtils.isNotBlank(entity.getNailCountDetail())){
				NailTotalCount nailTotalCount = getNailNailTotalCount(entity);
				
				// ????????????
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
		 

	private List<Map<String, Object>> getNailCount(List<NailCount> nailCountList,NailOrderDto entity) {
		 List<Map<String, Object>>  result = null;
		 if(null  != nailCountList && nailCountList.size() > 0){
			 result = new ArrayList<Map<String, Object>>(); 
			 String imageUrl = entity.getImageUrl();
				 if(StringUtils.isEmpty(imageUrl)){
					logger.error("??????????????????,imageUrl="+imageUrl);
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
				 
				// ???????????????
				 Map<String, Object> map = new HashMap<String, Object>();
				 NailTotalCount nailTotalCount = getNailNailTotalCount(entity);
				 if(null != nailTotalCount){
					 map.put("indexId", "??????");
					 map.put("nailNumber", nailTotalCount.getTotalNailNumber());
					 map.put("requreWeight", nailTotalCount.getTotalWeight());
					 map.put("requrePieces", nailTotalCount.getTotalrPieces());
					 map.put("", "?????????");
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
			// ????????????????????????
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
			{" ","??????","??????","??????","??????","????????????"}, 
			{"","indexId","nailNumber","requreWeight","requrePieces",""}
			};
		return columnNames;
	}

	@Override
	public String checkNialImageSise(MultipartFile file) {
		BufferedImage image;
		try {
			image = ImageIO.read(file.getInputStream());
			 if (image == null) { //??????image=null ?????????????????????????????????
			        return "?????????????????????.";
			    }
			 String key = image.getWidth()+"x"+image.getHeight();
			 NailImageSize nailImageSize = DateMap.nailImageSizeMap.get(key);
			 if(null == nailImageSize){
				 return "????????????????????????????????????,?????????????????????????????????";
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
	* @Description: ????????????
	* @author zhuzq
	* @date  2021???3???11??? ??????2:47:59
	* @param array
	* @return
	*/
	@Override
	public String updateCheckout(String array) {
		// ?????????????????????????????????????????????
		List<NailOrder> list = nailOrderDao.getByBatch(array);
		if(null != list && list.size() > 0){
			for (NailOrder nailOrder : list) {
				String comefrom = nailOrder.getComefrom();
				String thirdFlag = nailOrder.getThirdFlag();
				
				int checkoutFlag = nailOrder.getCheckoutFlag();
				if(String.valueOf(checkoutFlag).equals(CheckoutFlagEnum.OK.getDisplayName())){
					return "???????????????????????????????????????!";
				}
				
				// ???????????????????????????
				String nailDrawingStockId = nailOrder.getNailDrawingStockId();
				if(StringUtils.isEmpty(nailDrawingStockId) || "0".equals(nailDrawingStockId)){
					return "?????????????????????????????????????????????!";
				}
				
				// ????????????
				if((StringUtils.isEmpty(thirdFlag) || !ThirdFlagEnum.OK.getDisplayName().equals(comefrom))
						&& 
						(StringUtils.isEmpty(comefrom) || !String.valueOf(NailOrderComeFromConstant.BACKSTAGE).equals(comefrom))
						){
							return "????????????????????????????????????????????????!";
						}
				
			}
			
			// ???????????????????????????
			String ckeckInventory = ckeckInventory(list, array,true);
			if(StringUtils.isNotEmpty(ckeckInventory)){
				return ckeckInventory;
			}
			
			
			
		}
		return null;
	}
	

	private String ckeckInventory(List<NailOrder> list,String array,boolean ckeckOutFlag) {

		Map<String, Inventory> nailInventoryMap = getNailInventory(list,ckeckOutFlag);
		
		// ????????????
		Map<String, Inventory> drawInventoryMap = getDrawInventory(list,ckeckOutFlag);
					
		// ????????????,?????????????????????
		if(ckeckOutFlag){
			// ????????????
			
			if (null != nailInventoryMap && nailInventoryMap.size() > 0) {
				for (Entry<String, Inventory> mapping : nailInventoryMap.entrySet()) {
					String key = mapping.getKey();
					if(key.equals("0.0.0")){
						System.out.println(11);
					}
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
//					String nailType = "";
//					if (String.valueOf(NailTypeEnum.SMALL.getValue()).equals(nailConfigId)) {
//						stock_1 = inventory.getStock_1();
//						nailType = NailTypeEnum.SMALL.getDisplayName();
//					} else if (String.valueOf(NailTypeEnum.ROSE.getValue()).equals(nailConfigId)) {
//						// ??????????????????
//						stock = inventory.getStock_2();
//						nailType = NailTypeEnum.ROSE.getDisplayName();
//					} else if (String.valueOf(NailTypeEnum.DIAMOND.getValue()).equals(nailConfigId)) {
//						// ??????????????????
//						stock = inventory.getStock_3();
//						nailType = NailTypeEnum.DIAMOND.getDisplayName();
//					} else if (String.valueOf(NailTypeEnum.BIG.getValue()).equals(nailConfigId)) {
//						// ??????????????????
//						stock = inventory.getStock_4();
//						nailType = NailTypeEnum.BIG.getDisplayName();
//					}


					BigDecimal stock_1BG = new BigDecimal(stock_1);
					BigDecimal stock_2BG = new BigDecimal(stock_2);
					BigDecimal stock_3BG = new BigDecimal(stock_3);
					BigDecimal stock_4BG = new BigDecimal(stock_4);

					// ??????<??????
					if (stock_1BG.compareTo(BigDecimal.ZERO) == -1) {
						return "??????????????????"+newSerialNumber + "???????????????,????????????!";
					}
					if (stock_2BG.compareTo(BigDecimal.ZERO) == -1) {
						return "?????????????????????"+newSerialNumber + "???????????????,????????????!";
					}
					if (stock_3BG.compareTo(BigDecimal.ZERO) == -1) {
						return "?????????????????????"+newSerialNumber + "???????????????,????????????!";
					}
					if (stock_4BG.compareTo(BigDecimal.ZERO) == -1) {
						return "??????????????????"+newSerialNumber + "???????????????,????????????!";
					}
				}
			}
			
			
			if (null != drawInventoryMap && drawInventoryMap.size() > 0) {
				for (Entry<String, Inventory> mapping : drawInventoryMap.entrySet()) {
					Inventory inventory = mapping.getValue();

					String style = inventory.getStyle();

					Integer number = inventory.getNumber();

					BigDecimal numberBG = new BigDecimal(number);

					// ??????<??????
					if (numberBG.compareTo(BigDecimal.ZERO) == -1) {

						return "?????????"+style + "???????????????,????????????!";
					}
				}
			}
			
		}
		
		
		// ????????????
		Integer updateCheckOut = updateCheckOut(array, nailInventoryMap,drawInventoryMap, ckeckOutFlag);
		if(null == updateCheckOut || updateCheckOut < 1){
			return "???????????????";
		}
					
		return null;
	}
	
	/**
	* @Title: getNailInventory
	* @Description: ??????????????????
	* @author zhuzq
	* @date  2021???3???11??? ??????1:50:05
	* @param list
	* @return
	*/
	private Map<String,Inventory> getNailInventory(List<NailOrder> list,boolean ckeckOutFlag){

		// ?????????????????????
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
				Inventory inventory = new Inventory(id,rgb, newSerialNumber, stock_1, stock_2, stock_3, stock_4);
				rgbMap.put(rgb, inventory);
			}
		}
		
		
		// ????????????????????????
		if(null != list && list.size() > 0){
			for (NailOrder nailOrder : list) {
				
				String nailConfigId = nailOrder.getNailConfigId();
				
				String nailCountDetail = nailOrder.getNailCountDetail();
				// ????????????????????????
				if(StringUtils.isNotBlank(nailCountDetail)){
					NailTotalCount nailTotalCount = ( NailTotalCount ) GsonUtil.jsonToBean(nailCountDetail,NailTotalCount.class);
					
					
					// ????????????
					if(null != nailTotalCount && null != nailTotalCount.getNailCountDetailMap() && nailTotalCount.getNailCountDetailMap().size() > 0){
				        LinkedHashMap<String, NailCount> nailCountDetailMap =  MapUtil.mapSortForStringKey(nailTotalCount.getNailCountDetailMap());
				        for(Entry<String, NailCount> mapping:nailCountDetailMap.entrySet()){ 
				        	NailCount nailCount = mapping.getValue();
				        	// ??????
				        	
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
			if (String.valueOf(NailTypeEnum.SMALL.getValue()).equals(nailConfigId)) {
				inventoryStock = inventory.getStock_1();

			} else if (String.valueOf(NailTypeEnum.ROSE.getValue()).equals(nailConfigId)) {
				// ??????????????????
				inventoryStock = inventory.getStock_2();
			} else if (String.valueOf(NailTypeEnum.DIAMOND.getValue()).equals(nailConfigId)) {
				// ??????????????????
				inventoryStock = inventory.getStock_3();
			} else if (String.valueOf(NailTypeEnum.BIG.getValue()).equals(nailConfigId)) {
				// ??????????????????
				inventoryStock = inventory.getStock_4();
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

			if (String.valueOf(NailTypeEnum.SMALL.getValue()).equals(nailConfigId)) {
				inventory.setStock_1(stock.toString());

			} else if (String.valueOf(NailTypeEnum.ROSE.getValue()).equals(nailConfigId)) {
				// ??????????????????
				inventory.setStock_2(stock.toString());
			} else if (String.valueOf(NailTypeEnum.DIAMOND.getValue()).equals(nailConfigId)) {
				// ??????????????????
				inventory.setStock_3(stock.toString());
			} else if (String.valueOf(NailTypeEnum.BIG.getValue()).equals(nailConfigId)) {
				// ??????????????????
				inventory.setStock_4(stock.toString());
			}

		}
		inventory.setNailConfigId(nailConfigId);
		return inventory;
	}
	
	/**
	* @Title: getDrawInventory
	* @Description: ????????????
	* @author zhuzq
	* @date  2021???3???11??? ??????3:30:54
	* @param list
	* @return
	*/
	private Map<String, Inventory> getDrawInventory(List<NailOrder> list,boolean ckeckOutFlag) {

		// ?????????????????????
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

		// ??????????????????
		if (null != list && list.size() > 0) {
			for (NailOrder nailOrder : list) {
				String nailDrawingStockId = nailOrder.getNailDrawingStockId();
				// ??????

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

	public Integer updateCheckOut(String array,Map<String, Inventory> nailInventoryMap,Map<String, Inventory> drawInventoryMap,boolean ckeckOutFlag) {
		// ??????????????????
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		
		paramMap.put("id",array);
		
		if(ckeckOutFlag){
			paramMap.put("checkoutFlag",CheckoutFlagEnum.OK.getDisplayName());
		}else{
			paramMap.put("checkoutFlag",CheckoutFlagEnum.OFF.getDisplayName());
		}
		
		Integer updateCheckoutFlag = nailOrderDao.updateCheckoutFlag(paramMap);
		if(null != updateCheckoutFlag && updateCheckoutFlag > 0){
			//????????????
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
//						// ??????????????????
						nailWeightStock.setStock_2(inventory.getStock_2());
//					} else if (String.valueOf(NailTypeEnum.DIAMOND.getValue()).equals(nailConfigId)) {
//						// ??????????????????
						nailWeightStock.setStock_3(inventory.getStock_3());
//					} else if (String.valueOf(NailTypeEnum.BIG.getValue()).equals(nailConfigId)) {
//						// ??????????????????
						nailWeightStock.setStock_4(inventory.getStock_4());
//					}
					 Integer update = nailWeightStockDao.update(nailWeightStock);
					 //logger.info("????????????"+update);
				 }
			 }
			 
			//??????????????????
			// Map<String,Inventory> drawInventoryMap = getDrawInventory(list);
			 for(Entry<String, Inventory> mapping:drawInventoryMap.entrySet()){ 
				 Inventory inventory = mapping.getValue();
				 Integer number = inventory.getNumber();
				 Integer primaryKey = inventory.getDrawDrawingId();
				 NailDrawingStock nailDrawingStock = nailDrawingStockDao.get(primaryKey);
				 if(null != nailDrawingStock){
					 nailDrawingStock.setNumber(number);
					 Integer update = nailDrawingStockDao.update(nailDrawingStock);
					 //logger.info("????????????"+update);
				 }
			 }
			 
			 
		}
		return updateCheckoutFlag;
	}

	/**
	* @Title: updateCancelCheckout
	* @Description: ????????????
	* @author zhuzq
	* @date  2021???3???11??? ??????5:30:12
	* @param array
	* @return
	*/
	@Override
	public String updateCancelCheckout(String array) {
		// ?????????????????????????????????????????????
		List<NailOrder> list = nailOrderDao.getByBatch(array);
		if (null != list && list.size() > 0) {
			for (NailOrder nailOrder : list) {
				String comefrom = nailOrder.getComefrom();
				String thirdFlag = nailOrder.getThirdFlag();

				int checkoutFlag = nailOrder.getCheckoutFlag();
				if(String.valueOf(checkoutFlag).equals(CheckoutFlagEnum.OFF.getDisplayName())){
					return "????????????????????????????????????!";
				}

				// ???????????????????????????
				String nailDrawingStockId = nailOrder.getNailDrawingStockId();
				if (StringUtils.isEmpty(nailDrawingStockId) || "0".equals(nailDrawingStockId)) {
					return "?????????????????????????????????????????????!";
				}

				// ????????????
				if ((StringUtils.isEmpty(thirdFlag) || !ThirdFlagEnum.OK.getDisplayName().equals(comefrom))
						&& (StringUtils.isEmpty(comefrom)
								|| !String.valueOf(NailOrderComeFromConstant.BACKSTAGE).equals(comefrom))) {
					return "????????????????????????????????????????????????!";
				}

			}

			// ???????????????????????????
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
		// ????????????
		Integer updateCheckOut = updateCheckOut(array, nailInventoryMap,drawInventoryMap,false);
		if(null == updateCheckOut || updateCheckOut < 1){
			return "???????????????";
		}
					
		return null;
	}

	
	/**
	* @Title: analys
	* @Description: ????????????
	* @author zhuzq
	* @date  2021???3???12??? ??????2:54:50
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
		// ????????????
		List<NailOrderDto> nailOrderList = nailOrderDao.selectByMap(paramMap);
		
		
		
		
		
		// ????????????
		Map<String,AnalysNailConfig> analysNailConfigMap =  getNailConfigMap();
		
		// ????????????
		Map<String,Analys>  analysMap = getNailDetailConfigMap();
		
		
		

		if(null != nailOrderList && nailOrderList.size() > 0){
			
			
			
			String totalNailNumber = null;
			String totalrPieces = null;
			String totalWeight = null;
			for (NailOrderDto nailOrder : nailOrderList) {
				
				
				// ????????????
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
				
				
				
				// ????????????
				String nailCountDetail = nailOrder.getNailCountDetail();
				if(StringUtils.isNotEmpty(nailCountDetail)){
					NailTotalCount nailTotalCount = ( NailTotalCount ) GsonUtil.jsonToBean(nailCountDetail,NailTotalCount.class);
					
					if(null != nailTotalCount){
						LinkedHashMap<String, NailCount> nailCountMap = nailTotalCount.getNailCountDetailMap();
						// ??????
						nailCountMap = MapUtil.mapSortForStringKey(nailCountMap);
						if(null != nailCountMap && nailCountMap.size() > 0){
							
							 totalNailNumber = new BigDecimal(StringUtils.isEmpty(totalNailNumber)?"0": totalNailNumber).add(new BigDecimal(StringUtils.isEmpty(nailTotalCount.getTotalNailNumber())?"0" : nailTotalCount.getTotalNailNumber())).toString() ;
							 totalrPieces = new BigDecimal(StringUtils.isEmpty(totalrPieces)?"0":totalrPieces).add(new BigDecimal(StringUtils.isEmpty(nailTotalCount.getTotalrPieces())?"0" : nailTotalCount.getTotalrPieces())).toString() ;
							 totalWeight = new BigDecimal(StringUtils.isEmpty(totalWeight)?"0":totalWeight).add(new BigDecimal(StringUtils.isEmpty(nailTotalCount.getTotalWeight())?"0" : nailTotalCount.getTotalWeight())).toString() ;
							
							
							// ???????????????
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
		
		
		// ??????????????????
		Analys a = getAnalysOne(analysMap);
		if(a !=  null){
			String nailNumber = a.getTotalNailNumber();
			String requreWeight = a.getTotalWeight();
			String requrePieces = a.getTotalrPieces();
			int rgbSize = a.getRgbSize();
			Analys n = new Analys();
			n.setSort(1000000);
			n.setRgb("255,255,255");
			n.setIndexId("??????");
			n.setRgbSize(rgbSize);
			n.setNailNumber(nailNumber);
			n.setRequreWeight(requreWeight);
			n.setRequrePieces(requrePieces);
			analysMap.put("??????", n);
			
			Analys n1 = new Analys();
			n1.setRgbSize(rgbSize);
			n1.setRgb("255,255,255");
			n1.setSort(1000001);
			n1.setIndexId("?????????");
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
			analysMap.put("?????????", n1);
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
			an.setNailType("??????");
			analysNailConfigMap.put("??????", an);
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

	
	
	
	//??????????????????
	private Map<String,Analys> dataDeal(Map<String,Analys> data){
		
		if(null != data && data.size() > 0){
			
			Analys analysTotal = data.get("??????");
			
			for (Entry<String, Analys> iterable_element : data.entrySet()) {
				Analys analys = iterable_element.getValue();
				int rgbSize = analys.getRgbSize();
				
				// ????????????
				String totalNailNumber = analysTotal.getNailNumber();
				String totalWeight = analysTotal.getRequreWeight();
				String totalrPieces = analysTotal.getRequrePieces();
				
					if(rgbSize > 0){
						
						// ???????????????
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
						
						
						
						
						// ????????????
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
		// ????????????
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
