
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

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.Order;
import com.artcweb.bean.User;
import com.artcweb.constant.ComeFromConstant;
import com.artcweb.dao.OrderDao;
import com.artcweb.dao.PicPackageDao;
import com.artcweb.dao.UserDao;
import com.artcweb.service.UserService;
import com.artcweb.util.FileUtil;
import com.artcweb.util.ImageUtil;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private PicPackageDao picPackageDao;

	/**
	 * @Title: checkSaveParam
	 * @Description: 参数验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkSaveParam(User entity) {

		String userName = entity.getUserName();
		if (StringUtils.isBlank(userName)) {
			return "买家名称不能为空!";
		}
		Integer sort = entity.getSort();
		if (null == sort) {
			return "排序不能为空!";
		}
		return null;
	}

	/**
	 * @Title: checkUpdateUnique
	 * @Description: 更新唯一性验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkUpdateUnique(User entity) {

		List<User> list = null;
		// 分类ID与分类名唯一性验证
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", entity.getId());
		paramMap.put("userName", entity.getUserName());
		list = userDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "买家名称已存在!";
		}
		return null;
	}

	/**
	 * @Title: checkAddUnique
	 * @Description: 保存唯一性验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkAddUnique(User entity) {

		List<User> list = null;
		// 分类ID与分类名唯一性验证
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", entity.getUserName());
		list = userDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "买家名称已存在!";
		}
		return null;
	}

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(User entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		int count = findByPageCount(paramMap);
		if (count > 0) {
			List<User> dataList = findByPage(paramMap);

			// 获取订单
			getOrderList(dataList);

			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	/**
	 * @Title: getOrderList
	 * @Description: 获取订单
	 * @param dataList
	 */
	private void getOrderList(List<User> dataList) {

		if (null == dataList || dataList.size() < 1) {
			return;
		}
		for (User user : dataList) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userName", user.getUserName());
			List<Order> orderList = orderDao.select(paramMap);
			if (null != orderList && orderList.size() > 0) {
				for (Order order : orderList) {
					// 图片处理
					imageUrlDeal(order);
				}

				user.setOrderCount(orderList.size());
			}

			user.setOrderList(orderList);
		}
	}

	private void imageUrlDeal(Order order) {

		String imageUrl = ImageUtil.imageUrlDeal(order.getImageUrl());
		String minImageUrl = ImageUtil.imageUrlDeal(order.getMinImageUrl());
		order.setImageUrl(imageUrl);
		order.setMinImageUrl(minImageUrl);
	}

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	@Override
	public boolean deleteByBatch(String array,HttpServletRequest request) {
		List<User> list = userDao.selectByBatch(array);
		if(null != list && list.size() > 0){
			for (User user : list) {
				Integer id = user.getId();
				//删除用户信息
				LayUiResult layUiResult = deleteUser(id, request);
				logger.info("UserServiceImpl.deleteByBatch() code= "+layUiResult.getCode());
			}
			return true;
		}
		return false;
	}

	@Override
	public User getByMap(Map<String, Object> paramMap) {

		return userDao.getByMap(paramMap);
	}

	/**
	 * @Title: deleteUser
	 * @Description: 删除用户信息
	 */
	@Override
	public LayUiResult deleteUser(Integer id,HttpServletRequest request) {

		LayUiResult layUiResult = new LayUiResult();
		User user = userDao.get(id);
		if (null != user) {
			//获取用户订单信息
			String userName = user.getUserName();
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userName", userName);
			List<Order> orderList = orderDao.select(paramMap);
			
			Integer result = userDao.delete(id);
			
			if (null != result && result > 0) {
				if (StringUtils.isNotBlank(userName)) {
					if(null != orderList && orderList.size() > 0){
						for (Order order : orderList) {
							Integer orderId = order.getOrderId();
							//删除订单
							if(null != orderId && orderId > 0){
								Integer delOrder = orderDao.delete(orderId);
								logger.info("UserServiceImpl.deleteUser()删除订单delOrder= "+delOrder);
							}
							
							//删除定制模板
							Integer comeFrom = order.getComeFrom();
							Integer packageId = order.getPackageId();
							if(comeFrom == ComeFromConstant.CUSTOM_MAKE){
								Integer delPicPackage = picPackageDao.delete(packageId);
								if(null != delPicPackage && delPicPackage > 0 ){
									//删除物理图片
									String imageUrl = order.getImageUrl();
									String minImageUrl = order.getMinImageUrl();
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
						
					}
					
				}
				layUiResult.success();
				return layUiResult;

			}
		}

		layUiResult.failure();
		return layUiResult;

	}

	
	/**
	* @Title: updateUser
	* @Description: 修改用户信息
	* @param entity
	* @return
	*/
	@Override
	public Integer updateUser(User entity) {
		return update(entity);
	}

	/**
	* @Title: getPackageName
	* @Description: 获取图纸名称
	* @param id
	* @param user
	* @return
	*/
	@Override
	public String getPackageName(Integer id,User user) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", id);
		Order order = orderDao.getPackageName(paramMap);
		String packageName = null;
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
		}else{
			if(null != user){
				packageName = user.getUserName()+ "-1";;
			}
		}
		return packageName;
	}

}
