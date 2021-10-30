
package com.artcweb.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.Order;
import com.artcweb.bean.PicPackage;
import com.artcweb.bean.User;
import com.artcweb.constant.ComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.dao.OrderDao;
import com.artcweb.dao.PicPackageDao;
import com.artcweb.dao.UserDao;
import com.artcweb.enums.StatusEnum;
import com.artcweb.service.ImageService;
import com.artcweb.service.OrderService;
import com.artcweb.util.FileUtil;
import com.artcweb.util.ImageUtil;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Integer> implements OrderService {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private PicPackageDao picPackageDao;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserDao userDao;

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	@Override
	public boolean deleteByBatch(String array,HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idStr", array);
		
		//获取订单
		List<Order> orderList = orderDao.select(paramMap);
		int delResult = 0;
		if(null != orderList && orderList.size() > 0){
			// 删除订单
			delResult = orderDao.deleteByBatch(array);
			if(delResult > 0){
					StringBuffer sb = new StringBuffer();
					for (Order order : orderList) {
						sb.append(order.getPackageId());
						sb.append(",");
					}
					
					String picPackageStr = sb.toString();
					if(picPackageStr.lastIndexOf(",") != -1){
						picPackageStr = picPackageStr.substring(0,picPackageStr.length()-1);
					}
				//删除模本
				Integer picPackageRersult = picPackageDao.deleteByBatch(picPackageStr);
				if(null != picPackageRersult && picPackageRersult > 0){
					//删除图片
					for (Order order : orderList) {
						Integer comeFrom = order.getComeFrom();
						if(comeFrom == ComeFromConstant.CUSTOM_MAKE){
							String imageUrl = order.getImageUrl(); 
							String minImageUrl = order.getMinImageUrl();//删除原来物理图片
								if(StringUtils.isNotBlank(imageUrl)){
									boolean  deleteResult = FileUtil.deleteFile(imageUrl,request);
									logger.info("物理删除图片结果 = "+deleteResult);
								}
								if(StringUtils.isNotBlank(minImageUrl)){
									boolean  deleteResult = FileUtil.deleteFile(minImageUrl,request);
									logger.info("物理删除图片结果 = "+deleteResult);
								}
								
						}
					}
					
				}
				
				return true;	
					
			}
		}
		return false;
	}

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(Order entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		int count = findByPageCount(paramMap);
		if (count > 0) {
			List<Order> dataList = findByPage(paramMap);

			// 图片路径处理
			if (null != dataList && dataList.size() > 0) {
				for (Order order : dataList) {
					imageUrlDeal(order);
				}
			}

			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	private void imageUrlDeal(Order entity) {

		if (null == entity) {
			return;
		}
		String imageUrl = ImageUtil.imageUrlDeal(entity.getImageUrl());
		entity.setImageUrl(imageUrl);
		entity.setStatus(Integer.parseInt(StatusEnum.OK.getDisplayName()));

	}

	/**
	 * @Title: checkParamByApi
	 * @Description: 参数验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkParamByApi(Order entity) {

		String mobile = entity.getMobile();
		if (StringUtils.isBlank(mobile)) {
			return "手机号码不能为空!";

		}
		return null;
	}

	/**
	 * @Title: getOrderDetailByApi
	 * @Description: 获取订单详情
	 * @param paramMap
	 * @return
	 */
	@Override
	public Order getOrderDetailByApi(Map<String, Object> paramMap) {
		Order order = orderDao.getOrderDetail(paramMap);
		if(null != order){
			imageUrlDeal(order);
			order.setCurrentStep(StringUtils.defaultIfBlank(order.getCurrentStep(), "1"));
		}
		return order;
	}

	/**
	 * @Title: findPageByApi
	 * @Description: 分页查找
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findPageByApi(Order entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		int count = orderDao.findPageByApiCount(paramMap);
		if (count > 0) {
			List<Order> dataList = orderDao.findPageByApi(paramMap);

			// 图片路径处理
			if (null != dataList && dataList.size() > 0) {
				for (Order order : dataList) {
					imageUrlDeal(order);
				}
			}

			result.success(dataList);
			result.setCount(count);
		}
		return result;
	}

	/**
	 * @Title: updateCurrentStep
	 * @Description: 更新当前步骤
	 * @param paramMap
	 * @return
	 */
	@Override
	public Integer updateCurrentStepByApi(Map<String, Object> paramMap) {

		return orderDao.updateCurrentStep(paramMap);
	}

	/**
	 * @Title: checkSaveParam
	 * @Description: 新增参数验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkSaveParam(Order entity) {

		String mobile = entity.getMobile();
		if (StringUtils.isBlank(mobile)) {
			return "手机号码不能为空!";
		}
		
		Integer packageId = entity.getPackageId();
		if (null == packageId) {
			return "图纸名称不能为空!";
		}
		
		Integer sort = entity.getSort();
		if (null == sort) {
			return "排序号不能为空!";
		}
		return null;
	}

	

	/**
	 * @Title: defaultValueDeal
	 * @Description: 默认值处理
	 * @param entity
	 */
	@Override
	public void defaultValueDeal(Order entity) {

		entity.setCurrentStep(StringUtils.defaultIfBlank(entity.getCurrentStep(), ""));

	}

	/**
	 * @Title: getById
	 * @Description: 通过id获取订单
	 * @param paramMap
	 * @return
	 */
	@Override
	public Order getByMap(Map<String, Object> paramMap) {
		Order order = orderDao.getByMap(paramMap);
		return order;
	}

	/**
	 * @Title: checkUpdateUnique
	 * @Description: 修改唯一性验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkUpdateUnique(Order entity) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer comeFrom =entity.getComeFrom();
		if(comeFrom == ComeFromConstant.TEMPLATE){
			paramMap.put("packageId", entity.getPackageId());
		}else{
			paramMap.put("packageName", entity.getPackageName());
		}
		paramMap.put("orderId", entity.getOrderId());
		paramMap.put("userId", entity.getUserId());
		Integer result = orderDao.checkExistOreder(paramMap);
		if (null != result && result > 0) {
			return "买家已存在该订单!";
		}
		return null;
	}

	/**
	 * @Title: saveChooseTemplate
	 * @Description: 选择模本保存
	 * @param entity
	 * @return
	 */
	@Override
	public LayUiResult saveChooseTemplate(Order entity,HttpServletRequest request) {

		LayUiResult result = new LayUiResult();

		// 参数验证
		String checkResult = checkSaveParam(entity);
		if (StringUtils.isNotBlank(checkResult)) {
			result.failure(checkResult);
			return result;
		}
		//
		Integer operator = null;
		Integer orderId = entity.getOrderId();

		// 修改
		if (null != orderId) {
			// 验证唯一性
			String checkUpdateUnique = checkUpdateUnique(entity);
			if (StringUtils.isNotBlank(checkUpdateUnique)) {
				result.failure(checkUpdateUnique);
				return result;
			}

			Order order = orderDao.get(orderId);
			if (null != order) {
				order.setMobile(entity.getMobile());
				order.setPackageId(entity.getPackageId());
				order.setSort(entity.getSort());
				operator = orderDao.update(order);
			}

		}
		else {	// 保存
			
			
			//点击增加买家操作，创建用户
			String idParam = request.getParameter("idParam");
			Integer apiFlag = entity.getApiFlag();
			if(StringUtils.isNotBlank(idParam) && "-1".equals(idParam) ||(null != apiFlag && apiFlag ==1)){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("userName", entity.getUserName());
				User user = userDao.getByMap(paramMap);
				if(null == user){
					user = new User();
					user.setSort(1);
					user.setUserName(entity.getUserName());
					Integer save = userDao.save(user);
					if(null != save && save > 0){
						entity.setUserId(String.valueOf(user.getId()));
					}
				}else{
					//packageName处理
					paramMap.clear();
					paramMap.put("userId", user.getId());
					Order order = orderDao.getPackageName(paramMap);
					String packageName = user.getUserName()+"-1";
					entity.setUserId(user.getId()+"");
					String[] packageNameArr;
					if(null != order ){
						packageName = order.getPackageName();
						if(StringUtils.isNotBlank(packageName) && (packageName.lastIndexOf("-")!= -1)){
							packageNameArr =  packageName.split("-");
							if(null != packageName && packageName.length() > 1){
								packageName = packageNameArr[0] + "-"+(Integer.valueOf(packageNameArr[1])+1);
							}
							
						}else{
							packageName = order.getUserName() + "-1";
						}
					
						entity.setUserId(order.getId()+"");
					}
					entity.setPackageName(packageName);
				
				}
			}
			
			// 验证唯一性
			String checkAddUnique = checkAddUnique(entity);
			if (StringUtils.isNotBlank(checkAddUnique)) {
				result.failure(checkAddUnique);
				return result;
			}
			// 默认值处理
			defaultValueDeal(entity);
			operator = orderDao.save(entity);
		}

		if (null != operator && operator > 0) {
			result.success();
			return result;
		}

		result.failure();
		return result;

	}

	/**
	 * @Title: saveNewTemplate
	 * @Description: 新建模板保存
	 * @param entity
	 * @param file
	 * @param request
	 * @return
	 */
	@Override
	public LayUiResult saveNewTemplate(Order entity, MultipartFile file, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();

		// 参数验证
		String checkResult = chekNewTemplateParam(entity);
		if (StringUtils.isNotBlank(checkResult)) {
			result.failure(checkResult);
			return result;
		}
		

		Integer operator = null;
		String imageUrl = null;
		String minImageUrl = null;
		Integer packageId = entity.getPackageId();
		Integer orderId = entity.getOrderId();
		if (null != packageId || (null != orderId && orderId > 0)) {// 修改
			
			// 验证唯一性
			String checkUpdateUnique = checkUpdateUnique(entity);
			if (StringUtils.isNotBlank(checkUpdateUnique)) {
				result.failure(checkUpdateUnique);
				return result;
			}
			
			//图片有修改情况
			if (file != null) {
				// 图片验证
				String errorMsg = imageService.checkImage(file);
				if (StringUtils.isNotBlank(errorMsg)) {
					result.failure(errorMsg);
					return result;
				}
				// 上传图片
				imageUrl = imageService.uploadImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
				minImageUrl = imageService.uploadMinImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);

			}
			
			// 修改订单信息
			Order order = orderDao.get(orderId);
			if(null == order){
				result.failure("订单已经不存在!");
				return result;
			}
			
			//获取模本信息
			PicPackage picPackage = picPackageDao.get(packageId);
			if(null == picPackage){
				result.failure("模板信息已被删出!");
				return result;
			}
			
			
			
			entity.setId(orderId);
			order.setMobile(entity.getMobile());
			order.setPackageId(picPackage.getPackageId());
			order.setSort(entity.getSort());
			
			
			//更新订单
			operator = update(order);
			
			if(null != operator && operator > 0){
				String sourceImageUrl = picPackage.getImageUrl();
				String sourceMinImageUrl = picPackage.getMinImageUrl();
				//修改模本信息
				if(file != null){//图片修改情况
					picPackage.setImageUrl(imageUrl);
					picPackage.setMinImageUrl(minImageUrl);
				}
				picPackage.setPackageName(entity.getPackageName());
				picPackage.setPins(entity.getPins());
				picPackage.setStep(entity.getStep());
				if(StringUtils.isNotBlank(entity.getStepName())){
					picPackage.setStepName(entity.getStepName());
				}
				//更新模本
				Integer picPackageResult = picPackageDao.update(picPackage);
				
				if(null != picPackageResult && picPackageResult > 0){
					//删除图片
					if(null != file ){
						if(null != operator && operator > 0){
							if(StringUtils.isNotBlank(sourceImageUrl)){
								boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
								logger.info("物理删除图片结果 = "+deleteResult);
							}
							if(StringUtils.isNotBlank(sourceMinImageUrl)){
								boolean  deleteResult = FileUtil.deleteFile(sourceMinImageUrl,request);
								logger.info("物理删除图片结果 = "+deleteResult);
							}
						}
					}
				}
				
			}

		}// 保存
		else {
			
			//点击增加买家操作，创建用户
			String idParam = request.getParameter("idParam");
			if(StringUtils.isNotBlank(idParam) && "-1".equals(idParam)){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("userName", entity.getUserName());
				User user = userDao.getByMap(paramMap);
				if(null == user){
					user = new User();
					user.setSort(1);
					user.setUserName(entity.getUserName());
					Integer save = userDao.save(user);
					if(null != save && save > 0){
						entity.setUserId(String.valueOf(user.getId()));
					}
				}else{
					//packageName处理
					paramMap.clear();
					paramMap.put("userId", user.getId());
					Order order = orderDao.getPackageName(paramMap);
					String packageName = user.getUserName() + "-1";
					entity.setUserId(user.getId()+"");
					String[] packageNameArr;
					if(null != order ){
						packageName = order.getPackageName();
						if(StringUtils.isNotBlank(packageName) && (packageName.lastIndexOf("-")!= -1)){
							packageNameArr =  packageName.split("-");
							String newPackageName = null;
							try {
								newPackageName = (new BigDecimal(packageNameArr[packageNameArr.length-1]).add(new BigDecimal(1)))+"";
								if(packageNameArr.length > 2){
									packageName = packageName.replace(packageNameArr[packageNameArr.length-1], newPackageName);
								}else{
									packageName = packageNameArr[0] + "-"+newPackageName;
								}
							} catch (Exception e) {
								e.printStackTrace();
								newPackageName = new BigDecimal(System.currentTimeMillis()).toString();
								packageName = packageName + "-"+newPackageName;
							}
							
						}else{
							packageName = order.getUserName() + "-1";
						}
					
					}
					entity.setUserId(user.getId()+"");
					entity.setPackageName(packageName);
				
				}
			}

			// 验证唯一性
			String checkAddUnique = checkAddUnique(entity);
			if (StringUtils.isNotBlank(checkAddUnique)) {
				result.failure(checkAddUnique);
				return result;
			}

			
			if(null != file){
				// 图片验证
				String errorMsg = imageService.checkImage(file);
				if (StringUtils.isNotBlank(errorMsg)) {
					result.failure(errorMsg);
					return result;
				}
				
				// 上传图片
				imageUrl = imageService.uploadImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
				minImageUrl = imageService.uploadMinImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
				
				
				entity.setMinImageUrl(minImageUrl);
				entity.setImageUrl(imageUrl);
			}
			
			

			// 保存套餐信息
			PicPackage picPackage = new PicPackage();
			picPackage.setPackageName(entity.getPackageName());
			picPackage.setMinImageUrl(entity.getMinImageUrl());
			picPackage.setImageUrl(entity.getImageUrl());
			picPackage.setStep(entity.getStep());
			picPackage.setStepName(entity.getStepName());
			picPackage.setPins(entity.getPins());
			picPackage.setComeFrom(entity.getComeFrom());
			operator = picPackageDao.save(picPackage);
			if (null != operator && operator > 0) {
				// 保存订单信息
				defaultValueDeal(entity);// 默认值处理
				entity.setPackageId(picPackage.getPackageId());
				operator = orderDao.save(entity);
			}


		}
		if (null != operator && operator > 0) {
			result.success();
			return result;
		}
		result.failure();
		return result;

	}

	private String chekNewTemplateParam(Order entity) {

		String mobile = entity.getMobile();
		if (StringUtils.isBlank(mobile)) {
			return "手机号码不能为空!";
		}
		
		String packageName = entity.getPackageName();
		if (StringUtils.isBlank(packageName)) {
			return "图纸名称不能为空!";
		}
		Integer pins = entity.getPins();
		if (null == pins || pins < 1) {
			return "钉子数量不能为空!";
		}
		String step = entity.getStep();
		if (StringUtils.isBlank(step)) {
			return "执行步骤不能为空!";
		}
		Integer sort = entity.getSort();
		if (null == sort) {
			return "排序号不能为空!";
		}
		return null;
	}

	
	
	/**
	 * @Title: checkAddUnique
	 * @Description: 唯一性验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkAddUnique(Order entity) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer comeFrom =entity.getComeFrom();
		if(comeFrom == ComeFromConstant.TEMPLATE){
			paramMap.put("packageId", entity.getPackageId());
		}else{
			paramMap.put("packageName", entity.getPackageName());
		}
		paramMap.put("userId", entity.getUserId());
		Integer result = orderDao.checkExistOreder(paramMap);
		if (null != result && result > 0) {
			return "用户已存在该订单!";
		}
		return null;
	}

	
	/**
	* @Title: deleteOrder
	* @Description: 删除订单
	* @param id
	* @param request
	* @return
	*/
	@Override
	public boolean deleteOrder(Integer id ,HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		Order order = orderDao.getByMap(paramMap);
		if(null != order){
			String imageUrl = order.getImageUrl(); 
			String minImageUrl = order.getMinImageUrl();
			Integer packageId = order.getPackageId();
			Integer comeFrom = order.getComeFrom();
			
			//删除订单
			Integer delOrder = orderDao.delete(id);
			if(null != delOrder && delOrder > 0){
				if(comeFrom == ComeFromConstant.CUSTOM_MAKE){
					//删除定制模板
					Integer delPicPackage = picPackageDao.delete(packageId);
					logger.info("删除定制模板结果 = "+delPicPackage);
					
					//删除原来物理图片
					if(null != delPicPackage && delPicPackage > 0){
						if(StringUtils.isNotBlank(imageUrl)){
							boolean  deleteResult = FileUtil.deleteFile(imageUrl,request);
							logger.info("物理删除图片结果 = "+deleteResult);
						}
						if(StringUtils.isNotBlank(minImageUrl)){
							boolean  deleteResult = FileUtil.deleteFile(minImageUrl,request);
							logger.info("物理删除图片结果 = "+deleteResult);
						}
						
					}
				}
				return true;
			}
			
		}
		return false;
	}

	@Override
	public void stepFileDeal(Order entity, MultipartFile stepFile) {

		if(null != stepFile){
			try {
				String step = new String(stepFile.getBytes(),"UTF-8");
				String originalFilename = stepFile.getOriginalFilename();
				entity.setStep(step);
				entity.setStepName(originalFilename);
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error("OrderServiceImpl.stepFileDeal()步骤附件处理异常"+e.getMessage());
			}
			
		}
		
	}

	@Override
	public String getPackageName(String userName) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);
		User user = userDao.getByMap(paramMap);
		if(null == user){
			return userName + "-1";
		}else{
			//packageName处理
			paramMap.clear();
			paramMap.put("userId", user.getId());
			Order order = orderDao.getPackageName(paramMap);
			String packageName = null;
			String[] packageNameArr;
			if(null != order ){
				packageName = order.getPackageName();
				if(StringUtils.isNotBlank(packageName) && (packageName.lastIndexOf("-")!= -1)){
					packageNameArr =  packageName.split("-");
					if(null != packageName && packageName.length() > 1){
						String newPackageName = null;
						try {
							newPackageName = (new BigDecimal(packageNameArr[1]).add(new BigDecimal(1)))+"";
							packageName = packageNameArr[0] + "-"+newPackageName;
						} catch (Exception e) {
							e.printStackTrace();
							newPackageName = new BigDecimal(System.currentTimeMillis()).toString();
							packageName = packageName + "-"+newPackageName;
						}
						
					}
					
				}else{
					packageName = order.getUserName() + "-1";
				}
			}
			return packageName;
		
		}
	}

	@Override
	public LayUiResult saveApiNewTemplate(Order entity, MultipartFile file, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 参数验证
		String checkResult = chekNewTemplateParam(entity);
		if (StringUtils.isNotBlank(checkResult)) {
			result.failure(checkResult);
			return result;
		}

		Integer operator = null;
		String imageUrl = null;
		String minImageUrl = null;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", entity.getUserName());
		User user = userDao.getByMap(paramMap);
		if (null == user) {
			user = new User();
			user.setSort(1);
			user.setUserName(entity.getUserName());
			Integer save = userDao.save(user);
			if (null != save && save > 0) {
				entity.setUserId(String.valueOf(user.getId()));
			}
		}
		else {
			// packageName处理
			paramMap.clear();
			paramMap.put("userId", user.getId());
			Order order = orderDao.getPackageName(paramMap);
			String packageName = user.getUserName()+"-1";
			String[] packageNameArr;
			if (null != order) {
				packageName = order.getPackageName();
				if (StringUtils.isNotBlank(packageName) && (packageName.lastIndexOf("-") != -1)) {
					packageNameArr = packageName.split("-");
					if (null != packageName && packageName.length() > 1) {
						packageName = packageNameArr[0] + "-" + (Integer.valueOf(packageNameArr[1]) + 1);
					}

				}
				else {
					packageName = order.getUserName() + "-1";
				}

			}
			entity.setUserId(user.getId() + "");
			entity.setPackageName(packageName);

		}

		// 验证唯一性
		String checkAddUnique = checkAddUnique(entity);
		if (StringUtils.isNotBlank(checkAddUnique)) {
			result.failure(checkAddUnique);
			return result;
		}

		if (null != file && !file.isEmpty()) {
			// 图片验证
			String errorMsg = imageService.checkImage(file);
			if (StringUtils.isNotBlank(errorMsg)) {
				result.failure(errorMsg);
				return result;
			}

			// 上传图片
			imageUrl = imageService.uploadImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
			minImageUrl = imageService.uploadMinImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);

			entity.setMinImageUrl(minImageUrl);
			entity.setImageUrl(imageUrl);
		}

		// 保存套餐信息
		PicPackage picPackage = new PicPackage();
		picPackage.setPackageName(entity.getPackageName());
		picPackage.setMinImageUrl(entity.getMinImageUrl());
		picPackage.setImageUrl(entity.getImageUrl());
		picPackage.setStep(entity.getStep());
		picPackage.setStepName(entity.getStepName());
		picPackage.setPins(entity.getPins());
		picPackage.setComeFrom(entity.getComeFrom());
		operator = picPackageDao.save(picPackage);
		if (null != operator && operator > 0) {
			// 保存订单信息
			defaultValueDeal(entity);// 默认值处理
			entity.setPackageId(picPackage.getPackageId());
			operator = orderDao.save(entity);
		}

		if (null != operator && operator > 0) {
			result.success();
			return result;
		}
		result.failure();
		return result;

	}

	@Override
	public LayUiResult saveApiChooseTemplate(Order entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();

		// 参数验证
		String checkResult = checkSaveParam(entity);
		if (StringUtils.isNotBlank(checkResult)) {
			result.failure(checkResult);
			return result;
		}
		//
		Integer operator = null;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", entity.getUserName());
		User user = userDao.getByMap(paramMap);
		if (null == user) {
			user = new User();
			user.setSort(1);
			user.setUserName(entity.getUserName());
			Integer save = userDao.save(user);
			if (null != save && save > 0) {
				entity.setUserId(String.valueOf(user.getId()));
			}
		}
		else {
			// packageName处理
			paramMap.clear();
			paramMap.put("userId", user.getId());
			Order order = orderDao.getPackageName(paramMap);
			String packageName = user.getUserName() + "-1";
			entity.setUserId(user.getId() + "");
			String[] packageNameArr;
			if (null != order) {
				packageName = order.getPackageName();
				if (StringUtils.isNotBlank(packageName) && (packageName.lastIndexOf("-") != -1)) {
					packageNameArr = packageName.split("-");
					if (null != packageName && packageName.length() > 1) {
						packageName = packageNameArr[0] + "-" + (Integer.valueOf(packageNameArr[1]) + 1);
					}

				}
				else {
					packageName = order.getUserName() + "-1";
				}

				entity.setUserId(user.getId() + "");
			}
			entity.setPackageName(packageName);

		}

		// 验证唯一性
		String checkAddUnique = checkAddUnique(entity);
		if (StringUtils.isNotBlank(checkAddUnique)) {
			result.failure(checkAddUnique);
			return result;
		}
		// 默认值处理
		defaultValueDeal(entity);
		operator = orderDao.save(entity);

		if (null != operator && operator > 0) {
			result.success();
			return result;
		}

		result.failure();
		return result;
	}
	
}
