package ${daoPackageName};

import java.util.List;
import java.util.Map;
import ${daoCommonPackageName}.BaseDao;
import org.springframework.stereotype.Repository;
import ${entityPackageName}.${table.className?cap_first};

import ${dtoPackageName}.${table.className?cap_first}Dto;

/**
 * @ClassName: ${table.className?cap_first}Dao
 * @Description: ${description}
 * @author ${author}
 * @date ${dateTime}
 */
@Repository
public interface ${table.className?cap_first}Dao extends BaseDao<${table.className?cap_first},Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<${table.className?cap_first}Dto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);

}
