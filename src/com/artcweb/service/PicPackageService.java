
package com.artcweb.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.PicPackage;
import com.artcweb.dto.PicPackageDto;

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

	/**
	* @Title: stepFileDeal
	* @Description: 步骤附件处理
	* @param entity
	* @param stepFile
	*/
	public void stepFileDeal(PicPackage entity, MultipartFile stepFile);
	
	public List<PicPackage> selectByMap(Map<String, Object> paramMap);
	
	/**
	* @Title: selectByApiIndex
	* @Description: 小程序-首页
	* @param paramMap
	* @return
	*/
	public List<PicPackageDto> selectByApiIndex(Map<String, Object> paramMap);


}
