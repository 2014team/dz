
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.PicPackage;
import com.artcweb.dao.PicPackageDao;
import com.artcweb.service.PicPackageService;
import com.artcweb.util.FileUtil;
import com.artcweb.util.ImageUtil;

@Service
public class PicPackageServiceImpl extends BaseServiceImpl<PicPackage, Integer> implements PicPackageService {

	private static Logger logger = Logger.getLogger(PicPackageServiceImpl.class);
	@Autowired
	private PicPackageDao picPackageDao;

	/**
	 * @Title: checkSaveParam
	 * @Description: 参数验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkSaveParam(PicPackage entity) {

		String packageName = entity.getPackageName();
		if (StringUtils.isBlank(packageName)) {
			return "图纸名称不能为空!";
		}
		//
		String step = entity.getStep();
		if (StringUtils.isBlank(step)) {
			return "执行步骤不能为空!";
		}
		Integer pins = entity.getPins();
		if (null == pins || pins < 1) {
			return "钉子数量不能为空!";
		}
		return null;
	}

	/**
	 * @Title: checkUpdateUnique
	 * @Description: 更新唯一性验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkUpdateUnique(PicPackage entity) {

		List<PicPackage> list = null;
		// 分类ID与分类名唯一性验证
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("packageId", entity.getPackageId());
		paramMap.put("comeFrom", entity.getComeFrom());
		/* paramMap.put("packageId", entity.getPackageId()); */
		paramMap.put("packageName", entity.getPackageName());
		list = picPackageDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "图纸名称已存在!";
		}

		return null;
	}

	/**
	 * @Title: checkAddUnique
	 * @Description: 保存唯一性验证
	 * @param entity
	 * @return
	 */
	@Override
	public String checkAddUnique(PicPackage entity) {

		List<PicPackage> list = null;
		// 分类ID与分类名唯一性验证
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("comeFrom", entity.getComeFrom());
		paramMap.put("packageName", entity.getPackageName());
		list = picPackageDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "图纸名称已存在!";
		}

		return null;
	}

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(PicPackage entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		int count = findByPageCount(paramMap);
		if (count > 0) {
			List<PicPackage> dataList = findByPage(paramMap);
			// 图片路径处理
			if (null != dataList && dataList.size() > 0) {
				for (PicPackage picPackage : dataList) {
					imageUrlDeal(picPackage);
				}
			}
			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	private void imageUrlDeal(PicPackage entity) {

		if (null == entity) {
			return;
		}
		String imageUrl = ImageUtil.imageUrlDeal(entity.getImageUrl());
		String minImageUrl = ImageUtil.imageUrlDeal(entity.getMinImageUrl());
		entity.setImageUrl(imageUrl);
		entity.setMinImageUrl(minImageUrl);

	}

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	@Override
	public int deleteByBatch(String array,HttpServletRequest request) {
		
		List<PicPackage> list = picPackageDao.selectByBatch(array);
		int result = picPackageDao.deleteByBatch(array);
		if(null != list && list.size() > 0){
			for (PicPackage picPackage : list) {
				String imageUrl = picPackage.getImageUrl();
				String minImageUrl = picPackage.getMinImageUrl();
				//删除原来物理图片
				if(StringUtils.isNotBlank(imageUrl)){
					boolean  deleteResult = FileUtil.deleteFile(imageUrl,request);
					logger.info("物理删除图片结果 = "+deleteResult);
				}
				if(StringUtils.isNotBlank(minImageUrl)){
					boolean  deleteResult = FileUtil.deleteFile(minImageUrl,request);
					logger.info("物理删除图片结果 = "+deleteResult);
				}
			}
		}

		return result;
	}

	
	/**
	* @Title: deletePicPackage
	* @Description: 删除
	* @param packageId
	* @return
	*/
	@Override
	public int deletePicPackage(Integer packageId,HttpServletRequest request) {
		PicPackage picPackage = get(packageId);
		String imageUrl = picPackage.getImageUrl();
		String minImageUrl = picPackage.getMinImageUrl();
		int result = 0;
		if(null != picPackage){
			result = delete(packageId);
			//删除原来物理图片
			if(StringUtils.isNotBlank(imageUrl)){
				boolean  deleteResult = FileUtil.deleteFile(imageUrl,request);
				logger.info("物理删除图片结果 = "+deleteResult);
			}
			if(StringUtils.isNotBlank(minImageUrl)){
				boolean  deleteResult = FileUtil.deleteFile(minImageUrl,request);
				logger.info("物理删除图片结果 = "+deleteResult);
			}
		}
		return result;
	}

}
