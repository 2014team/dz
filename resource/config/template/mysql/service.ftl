package ${servicePackageName};

import ${serviceCommonPackageName}.BaseService;
import ${entityPackageName}.${table.className?cap_first};
import ${voPackageName}.${table.className?cap_first}Vo;
import ${entityPackageName}.LayUiResult;

/**
 * @ClassName: ${table.className?cap_first}Dao
 * @Description: ${description}
 * @author ${author}
 * @date ${dateTime}
 */
public interface ${table.className?cap_first}Service extends BaseService<${table.className?cap_first},Integer>{

	public LayUiResult findByPage(${table.className?cap_first}Vo entity, LayUiResult result);

	public boolean deleteByBatch(String array);

}
