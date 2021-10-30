package ${servicePackageName}.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${table.package_name_base}.dao.${table.className?cap_first}Dao;
import ${entityPackageName}.${table.className?cap_first};
import ${servicePackageName}.${table.className?cap_first}Service;
import ${serviceImplCommonPackageName}.BaseServiceImpl;
import ${voPackageName}.${table.className?cap_first}Vo;
import ${dtoPackageName}.${table.className?cap_first}Dto;
import ${entityPackageName}.LayUiResult;

/**
 * @ClassName: ${table.className?cap_first}ServiceImpl
 * @Description: ${description}
 * @author ${author}
 * @date ${dateTime}
 */
@Service
public class ${table.className?cap_first}ServiceImpl extends BaseServiceImpl<${table.className?cap_first},Integer>  implements ${table.className?cap_first}Service {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(${table.className?cap_first}ServiceImpl.class);
	
	@Autowired
	private ${table.className?cap_first}Dao ${table.className?uncap_first}Dao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(${table.className?cap_first}Vo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = ${table.className?uncap_first}Dao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<${table.className?cap_first}Dto> dataList = ${table.className?uncap_first}Dao.selectByPage(paramMap);
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
		Integer delete = ${table.className?uncap_first}Dao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}
	
}
