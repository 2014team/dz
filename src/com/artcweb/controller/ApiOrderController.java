
package com.artcweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.Order;
import com.artcweb.service.OrderService;

@Controller
@RequestMapping("/api/order")
public class ApiOrderController {

	@Autowired
	private OrderService orderService;

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
	public LayUiResult get(Order entity, LayUiResult result) {

		Integer orderId = entity.getOrderId();
		if (null == orderId) {
			result.failure("参数[orderId]不能为空!");
			return result;
		}

		// 获取订单
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		Order order = orderService.getOrderDetailByApi(paramMap);
		result.success(order);
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
