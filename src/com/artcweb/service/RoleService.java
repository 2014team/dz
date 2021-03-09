package com.artcweb.service;

import com.artcweb.service.BaseService;
import com.artcweb.bean.Role;
import com.artcweb.vo.RoleVo;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: RoleDao
 * @Description: 角色
 * @author zhuzq
 * @date 2021年03月08日 17:57:41
 */
public interface RoleService extends BaseService<Role,Integer>{

	public LayUiResult findByPage(RoleVo entity, LayUiResult result);

	public boolean deleteByBatch(String array);

}
