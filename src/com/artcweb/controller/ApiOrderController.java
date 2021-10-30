
package com.artcweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.Order;
import com.artcweb.bean.Secret;
import com.artcweb.bean.While;
import com.artcweb.enums.SiteEnum;
import com.artcweb.enums.StatusEnum;
import com.artcweb.service.OrderService;
import com.artcweb.service.SecretService;
import com.artcweb.service.WhileService;
import com.artcweb.util.DateUtil;
import com.artcweb.vo.OrderVo;

@Controller
@RequestMapping("/api/order")
public class ApiOrderController {
	
	private static Logger logger = LoggerFactory.getLogger(ApiOrderController.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private SecretService secretService;
	@Autowired
	private WhileService whileService;

	/**
	 * @Title: list
	 * @Description: 订单列表
	 * @param entity
	 * @param result
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult list(Order entity, LayUiResult result) {

		String errMsg = orderService.checkParamByApi(entity);
		if (StringUtils.isNotBlank(errMsg)) {
			result.failure(errMsg);
			return result;
		}

		// 获取列表
		result = orderService.findPageByApi(entity, result);
		return result;
	}

	/**
	 * @Title: get
	 * @Description: 获取订单详情
	 * @param entity
	 * @param result
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult get(OrderVo entity, LayUiResult result) {
		Integer orderId = entity.getOrderId();
		if (null == orderId) {
			result.failure("参数[orderId]不能为空!");
			return result;
		}

		// 获取订单
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		Order order = orderService.getOrderDetailByApi(paramMap);
		if(null != order){
			order.setStatus(Integer.parseInt(StatusEnum.OK.getDisplayName()));
			result.success(order);
			
			//检查是否白名单
//			Map<String,Object> whileParamMap = new HashMap<String, Object>();
//			whileParamMap.put("mobile",order.getMobile());
//			List<While> whileList = whileService.select(whileParamMap);
//			if(null != whileList && whileList.size() > 0){
//				order.setStatus(1);
//				result.success(order);
//				return result;
//			}
			
			
//			//判断是否2020年8月16以后订单，之前不需要秘钥
//			Date createDate = order.getCreateDate();
//			String dateStr = "2020-09-05 17:00:00";
//			try {
//				Date startDate = DateUtil.parse(dateStr, DateUtil.DATE_YYYY_MM_DD_HH_MM_SS);
//				
//				//时间比较
//				boolean dateCompare = DateUtil.compareToLte(createDate, startDate);
//				//false证明是以前的订单
//				if(!dateCompare){
//					result.success(order);
//					return result;
//				}
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			//判断是否输入过秘钥
//			Integer status = order.getStatus();
//			if(null == status || !status.equals(1)){
//				//秘钥验证
//				String secretKey = entity.getSecretKey();
//				if(StringUtils.isEmpty(secretKey)){
//					result.failure("请输入秘钥");
//					return result;
//				}
//				
//				//验证秘钥是否系统生成
//				paramMap.clear();
//				paramMap.put("secretKey", secretKey);
//				Secret secret = secretService.getByMap(paramMap);
//				if(null == secret){
//					result.failure("秘钥不是系统生成，请输入正确秘钥");
//					return result;
//				}
//				
//				//验证秘钥是否已经被使用
//				if(null != secret.getStatus() && secret.getStatus().equals(1)){
//					result.failure("秘钥已经被使用，请联系工作人员");
//					return result;
//				}
//				
//				//更新秘钥
//				secret.setStatus(1);
//				secret.setOrderId(orderId);
//				secret.setSiteName(String.valueOf(SiteEnum.LINE.getValue()));
//				Integer updateResult = secretService.update(secret);
//				if(null != updateResult && updateResult > 0){
//					order.setStatus(1);
//					result.success(order);
//				}else{
//					result.failure("更新秘钥状态失败");
//				}
//			}else if(null != status && status.equals(1)){
//				//证明已经验证通过
//				result.success(order);
//				return result;
//			}
			
		}else{
			result.failure("订单不存在");
		}
		return result;
	}

	/**
	 * @Title: updateCurrentStep
	 * @Description: 更新当前状态
	 * @param entity
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update/current/step", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult updateCurrentStep(Order entity, LayUiResult result) {

		Integer orderId = entity.getOrderId();
		if (null == orderId) {
			result.failure("参数[orderId]不能为空!");
			return result;
		}

		String currentStep = entity.getCurrentStep();
		if (StringUtils.isBlank(currentStep)) {
			result.failure("参数[currentStep]不能为空!");
			return result;
		}

		// 获取订单
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		paramMap.put("currentStep", currentStep);
		Integer order = orderService.updateCurrentStepByApi(paramMap);
		if (null != order && order > 0) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}

}
