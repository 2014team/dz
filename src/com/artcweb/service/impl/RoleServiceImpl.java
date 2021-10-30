package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.dao.RoleDao;
import com.artcweb.bean.Role;
import com.artcweb.service.RoleService;
import com.artcweb.service.impl.BaseServiceImpl;
import com.artcweb.vo.RoleVo;
import com.artcweb.dto.RoleDto;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 角色
 * @author zhuzq
 * @date 2021年03月08日 17:57:41
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Integer>  implements RoleService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleDao roleDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(RoleVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = roleDao.selectByPageCount(paramMap);
		if (null != count && count > 0) {
			List<RoleDto> dataList = roleDao.selectByPage(paramMap);
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
		Integer delete = roleDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}
	
}
