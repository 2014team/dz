package ${controllerPackageName};

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ${entityPackageName}.LayUiResult;
import ${entityPackageName}.${table.className?cap_first};
import ${servicePackageName}.${table.className?cap_first}Service;
import ${voPackageName}.${table.className?cap_first}Vo;


/**
 * @ClassName: ${table.className?cap_first}Controller
 * @Description: ${description}
 * @author ${author}
 * @date ${dateTime}
 */
@Controller
@RequestMapping("/admin/center/${table.className?uncap_first}")
public class ${table.className?cap_first}Controller {

	@Autowired
	private ${table.className?cap_first}Service ${table.className?uncap_first}Service;

	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/${table.className?uncap_first}/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		return "/${table.className?uncap_first}/edit";
	}
	
	/**
	 * @Title: toEdit
	 * @Description: 到编辑UI
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String toEdit(@PathVariable Integer id, HttpServletRequest request) {
		${table.className?cap_first} entity = ${table.className?uncap_first}Service.get(id);
		request.setAttribute("entity", entity);
		return "/${table.className?uncap_first}/edit";
	}

	/**
	 * @Title: save
	 * @Description: 保存
	 * @param entity
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "/save")
	public LayUiResult save(${table.className?cap_first}Vo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
		<#list table.common_fields as field>
		<#if (field.java_type == 'String' && field.java_type == 'String')>
	    String ${field.java_field_Name} = entity.get${field.java_field_Name?cap_first}();
		if (StringUtils.isBlank(${field.java_field_Name})) {
			layUiResult.failure("${field.field_comment}不能为空");
			return layUiResult;
		}
		<#elseif (field.java_type == 'Integer' && field.java_type == 'Integer')>
		Integer ${field.java_field_Name} = entity.get${field.java_field_Name?cap_first}();
		if (null == ${field.java_field_Name}) {
			layUiResult.failure("${field.field_comment}不能为空");
			return layUiResult;
		}
		<#else>
		</#if>
		</#list>
		

		// 保存
		Integer result = ${table.className?uncap_first}Service.saveOrUpdate(entity);
		if (null != result && result > 0) {
			layUiResult.success();
		}
		else {
			layUiResult.failure();
		}
		return layUiResult;
	}

	/**
	 * @Title: list
	 * @Description: 列表
	 * @param adminCate
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult list(${table.className?cap_first}Vo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = ${table.className?uncap_first}Service.findByPage(entity, result);
		return result;
	}

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @param delete
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult delete(${table.className?cap_first} entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = ${table.className?uncap_first}Service.delete(id);
		if (null != delResult && delResult > 0) {
			result.success();
			return result;
		}

		result.failure();
		return result;
	}

	/**
	 * @Title: deleteBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/batch", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult deleteBatch(String array, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		if (StringUtils.isBlank(array)) {
			result.failure();
			return result;
		}

		array = array.replace("[", "").replace("]", "");

		boolean deleteResult = ${table.className?uncap_first}Service.deleteByBatch(array);
		if (deleteResult) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}
	
	/**
	* @Title: get
	* @Description: 获取单个对象
	* @param id
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "/get", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult get(Integer id) {
		
		LayUiResult result = new LayUiResult();
		if (null == id || id < 1) {
			result.failure("id不能为空");
			return result;
		}
		${table.className?cap_first} entity = ${table.className?uncap_first}Service.get(id);
		result.success(entity);
		return result;
	}
	
	

}
