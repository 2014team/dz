package com.artcweb.dao;

import java.util.List;
import java.util.Map;
import com.artcweb.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.artcweb.bean.Role;

import com.artcweb.dto.RoleDto;

/**
 * @ClassName: RoleDao
 * @Description: 角色
 * @author zhuzq
 * @date 2021年03月08日 17:57:39
 */
@Repository
public interface RoleDao extends BaseDao<Role,Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<RoleDto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);

}
