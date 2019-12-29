
package com.artcweb.service;

import javax.servlet.http.HttpServletRequest;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.PicPackage;

public interface PicPackageService extends BaseService<PicPackage, Integer> {

	/**
	* @Title: checkSaveParam
	* @Description: 参数验证
	* @param entity
	* @return
	*/
	public String checkSaveParam(PicPackage entity);

	/**
	* @Title: checkUpdateUnique
	* @Description: 更新唯一性验证
	* @param entity
	* @return
	*/
	public String checkUpdateUnique(PicPackage entity);

	/**
	* @Title: checkAddUnique
	* @Description: 保存唯一性验证
	* @param entity
	* @return
	*/
	public String checkAddUnique(PicPackage entity);

	/**
	* @Title: findByPage
	* @Description: 分页查找
	* @param entity
	* @param result
	* @return
	*/
	public LayUiResult findByPage(PicPackage entity, LayUiResult result);

	/**
	* @Title: deleteByBatch
	* @Description: 批量删除
	* @param array
	* @return
	*/
	public int deleteByBatch(String array,HttpServletRequest request);

	/**
	* @Title: deletePicPackage
	* @Description: 删除
	* @param packageId
	* @return
	*/
	public int deletePicPackage(Integer packageId,HttpServletRequest request);


}
