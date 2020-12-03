package ${controllerPackageName};

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ${dtoPackageName}.${table.className?cap_first}Dto;
import ${voPackageName}.${table.className?cap_first}Vo;
import ${servicePackageName}.${table.className?cap_first}Service;
import ${entityCommonPackage}.JsonResult;
import ${entityCommonPackage}.AdminResultByPage;

/**
 * @ClassName: ${table.className?cap_first}Controller
 * @Description: ${description}
 * @author ${author}
 * @date ${dateTime}
 */
@Controller
public class ${table.className?cap_first}Controller {

	@Autowired
	private ${table.className?cap_first}Service ${table.className?uncap_first}Service;

	/**
	 * @Title: save
	 * @Description: 保存
	 * @author ${author}
	 * @date ${dateTime}
	 * @param ${table.className?uncap_first}Vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/${table.className?uncap_first}/save", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult save(${table.className?cap_first}Vo ${table.className?uncap_first}Vo) {
		JsonResult result = new JsonResult();

		// 参数验证
		String errMsg = ${table.className?uncap_first}Service.checkParam(${table.className?uncap_first}Vo);
		if (StringUtils.isNotBlank(errMsg)) {
			result.failure(errMsg);
			return result;
		}

		// 唯一性验证
		errMsg = ${table.className?uncap_first}Service.checkUnique(${table.className?uncap_first}Vo);
		if (StringUtils.isNotBlank(errMsg)) {
			result.failure(errMsg);
			return result;
		}
		// 保存
		boolean save = ${table.className?uncap_first}Service.save${table.className?cap_first}(${table.className?uncap_first}Vo);
		if (save) {
			result.success();
		} else {
			result.failure();
		}

		return result;
	}

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @author ${author}
	 * @date ${dateTime}
	 * @param ${table.className?uncap_first}Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/${table.className?uncap_first}/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult delete(Integer ${table.key_fields[0].java_field_Name}) {
		JsonResult result = new JsonResult();

		// 验证参数
		if (null == ${table.key_fields[0].java_field_Name}) {
			result.failure("${table.key_fields[0].field_comment}不能为空");
			return result;
		}
		// 删除
		boolean delete = ${table.className?uncap_first}Service.delete${table.className?cap_first}(${table.key_fields[0].java_field_Name});
		if (delete) {
			result.success();
		} else {
			result.failure();
		}

		return result;
	}

	/**
	 * @Title: batchDelete
	 * @Description: 批量删除
	 * @author ${author}
	 * @date ${dateTime}
	 * @param ${table.className?uncap_first}IdArr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/${table.className?uncap_first}/batch/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult deleteByIdArr(@RequestParam("${table.key_fields[0].java_field_Name}Arr[]") Integer[] ${table.key_fields[0].java_field_Name}Arr) {
		JsonResult result = new JsonResult();
		// 验证参数
		if (null == ${table.key_fields[0].java_field_Name}Arr || ${table.key_fields[0].java_field_Name}Arr.length < 1) {
			result.failure("请选项要删除的数据");
			return result;
		}
		// 删除
		Integer delete = ${table.className?uncap_first}Service.deleteByBatch(${table.key_fields[0].java_field_Name}Arr);
		if (null != delete && delete > 0) {
			result.success();
		} else {
			result.failure();
		}

		return result;
	}

	/**
	 * @Title: update
	 * @Description: 修改
	 * @author ${author}
	 * @date ${dateTime}
	 * @param ${table.className?uncap_first}Vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/${table.className?uncap_first}/update", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult update(${table.className?cap_first}Vo ${table.className?uncap_first}Vo) {
		JsonResult result = new JsonResult();

		// 验证参数
		Integer ${table.key_fields[0].java_field_Name} = ${table.className?uncap_first}Vo.get${table.key_fields[0].java_field_Name?cap_first}();
		if (null == ${table.key_fields[0].java_field_Name}) {
			result.failure("${table.key_fields[0].field_comment}不能为空");
			return result;
		}
		String errMsg = ${table.className?uncap_first}Service.checkParam(${table.className?uncap_first}Vo);
		if (StringUtils.isNotBlank(errMsg)) {
			result.failure(errMsg);
			return result;
		}

		// 唯一性验证
		errMsg = ${table.className?uncap_first}Service.checkUnique(${table.className?uncap_first}Vo);
		if (StringUtils.isNotBlank(errMsg)) {
			result.failure(errMsg);
			return result;
		}
		// 修改
		boolean update = ${table.className?uncap_first}Service.update${table.className?cap_first}(${table.className?uncap_first}Vo);
		if (update) {
			result.success();
		} else {
			result.failure();
		}

		return result;
	}

	/**
	 * @Title: list
	 * @Description: 分页查找
	 * @author ${author}
	 * @date ${dateTime}
	 * @param ${table.className?uncap_first}Vo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/${table.className?uncap_first}/list", method = { RequestMethod.POST })
	public AdminResultByPage list(${table.className?cap_first}Vo ${table.className?uncap_first}Vo, HttpServletRequest request) {

		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));

		AdminResultByPage jsonResult = new AdminResultByPage(page, limit);

		jsonResult = ${table.className?uncap_first}Service.findByPage(${table.className?uncap_first}Vo, jsonResult);

		return jsonResult;
	}

	/**
	 * @Title: toList
	 * @Description: 列表UI
	 * @author ${author}
	 * @date ${dateTime}
	 * @return
	 */
	@RequestMapping(value = "/admin/${table.className?uncap_first}/list/ui", method = { RequestMethod.GET })
	public String toList() {
		return "/admin/${table.className?uncap_first}/${table.className?uncap_first}_list";
	}

	/**
	 * @Title: edit
	 * @Description: 编辑
	 * @author ${author}
	 * @date ${dateTime}
	 * @param ${table.className?uncap_first}Id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/${table.className?uncap_first}/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public String edit(Integer ${table.key_fields[0].java_field_Name}, HttpServletRequest request) {
		// 编辑,为空新增
		if (null != ${table.key_fields[0].java_field_Name}) {
			${table.className?cap_first}Dto ${table.className?uncap_first}Dto = ${table.className?uncap_first}Service.get${table.className?cap_first}(${table.key_fields[0].java_field_Name});
			request.setAttribute("${table.className?uncap_first}Dto", ${table.className?uncap_first}Dto);
		}
		return "/admin/${table.className?uncap_first}/${table.className?uncap_first}_edit";
	}

}
