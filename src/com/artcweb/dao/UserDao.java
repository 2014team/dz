
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.baen.User;

@Repository
public interface UserDao extends BaseDao<User, Integer> {

	public List<User> checkUnique(Map<String, Object> paramMap);

	public User getByMap(Map<String, Object> paramMap);

	public int deleteByBatch(String array);
	
	public List<User>  selectByBatch(String array);
	
	
}