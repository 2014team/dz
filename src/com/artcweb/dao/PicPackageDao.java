
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.baen.PicPackage;

@Repository
public interface PicPackageDao extends BaseDao<PicPackage, Integer> {

	public List<PicPackage> checkUnique(Map<String, Object> paramMap);

	public int deleteByBatch(String array);
	
	public List<PicPackage> selectByBatch(String array);

	
}