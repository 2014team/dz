
package com.artcweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.PicPackage;
import com.artcweb.constant.ComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.service.ImageService;
import com.artcweb.service.PicPackageService;
import com.artcweb.util.FileUtil;
import com.artcweb.util.ImageUtil;

@Controller
@RequestMapping("/admin/center/package")
public class PicPackageController {
	
	private static Logger logger = Logger.getLogger(PicPackageController.class);

	@Autowired
	private ImageService imageService;

	@Autowired
	private PicPackageService picPackageService;

	/**
	 * @Title: toList
	 * @Description: 列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/package/package";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd() {

		return "/package/package_edit";
	}

	/**
	 * @Title: toEdit
	 * @Description: 到编辑UI
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{packageId}")
	public String toEdit(@PathVariable Integer packageId, HttpServletRequest request) {

		PicPackage entity = picPackageService.get(packageId);
		if (null != entity) {
			entity.setImageUrl(ImageUtil.imageUrlDeal(entity.getImageUrl()));
		}

		request.setAttribute("entity", entity);
		return "/package/package_edit";
	}

	/**
	 * @Title: save
	 * @Description: 保存
	 * @param adminCate
	 * @param operator
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public LayUiResult save(PicPackage entity, MultipartFile file, HttpServletRequest request) {

		entity.setComeFrom(ComeFromConstant.TEMPLATE);
		
		
		LayUiResult result = new LayUiResult();

		// 参数验证
		String checkResult = picPackageService.checkSaveParam(entity);
		if (StringUtils.isNotBlank(checkResult)) {
			result.failure(checkResult);
			return result;
		}

		Integer operator = null;
		String imageUrl = null;
		String minImageUrl = null;
		Integer packageId = entity.getPackageId();

		// 修改
		if (null != packageId && packageId >0 ) {

			String checkUpdateUnique = null;
			if (file != null) {
				// 图片验证
				String errorMsg = imageService.checkImage(file);
				if (StringUtils.isNotBlank(errorMsg)) {
					result.failure(errorMsg);
					return result;
				}
				// 验证唯一性
				checkUpdateUnique = picPackageService.checkUpdateUnique(entity);
				if (StringUtils.isNotBlank(checkUpdateUnique)) {
					result.failure(checkUpdateUnique);
					return result;
				}

				// 上传图片
				imageUrl = imageService.uploadImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
				minImageUrl = imageService.uploadMinImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
				
				//删除原来图片
				PicPackage picPackage = picPackageService.get(packageId);
				if(null != picPackage){
					String sourceImageUrl = picPackage.getImageUrl();
					String sourceMinImageUrl = picPackage.getMinImageUrl();
						//删除原来物理图片
						if(StringUtils.isNotBlank(sourceImageUrl)){
							boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
							logger.info("物理删除图片结果 = "+deleteResult);
						}
						if(StringUtils.isNotBlank(sourceMinImageUrl)){
							boolean  deleteResult = FileUtil.deleteFile(sourceMinImageUrl,request);
							logger.info("物理删除图片结果 = "+deleteResult);
						}
					
				}
				entity.setMinImageUrl(minImageUrl);
				entity.setImageUrl(imageUrl);
				operator = picPackageService.update(entity);

			}
			else {

				// 验证唯一性
				checkUpdateUnique = picPackageService.checkUpdateUnique(entity);
				if (StringUtils.isNotBlank(checkUpdateUnique)) {
					result.failure(checkUpdateUnique);
					return result;
				}

				PicPackage picPackage = picPackageService.get(Integer.valueOf(entity.getPackageId()));
				if (null != picPackage) {
					picPackage.setPackageName(entity.getPackageName());
					picPackage.setStep(entity.getStep());
					picPackage.setPins(entity.getPins());
				}

				operator = picPackageService.update(picPackage);
			}

		}
		else {// 保存

			if (null == file) {
				result.failure("图片不能为空!");
				return result;
			}

			// 图片验证
			String errorMsg = imageService.checkImage(file);
			if (StringUtils.isNotBlank(errorMsg)) {
				result.failure(errorMsg);
				return result;
			}

			// 验证唯一性
			String checkAddUnique = picPackageService.checkAddUnique(entity);
			if (StringUtils.isNotBlank(checkAddUnique)) {
				result.failure(checkAddUnique);
				return result;
			}
			// 上传图片
			imageUrl = imageService.uploadImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
			minImageUrl = imageService.uploadMinImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
			entity.setImageUrl(imageUrl);
			entity.setMinImageUrl(minImageUrl);
			entity.setComeFrom(ComeFromConstant.TEMPLATE);
			operator = picPackageService.save(entity);
		}

		if (null != operator && operator > 0) {
			result.success();
			return result;
		}

		result.failure();
		return result;
	}

	/**
	 * @Title: list
	 * @Description: 列表
	 * @param adminCate
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult list(PicPackage entity, HttpServletRequest request) {

		entity.setComeFrom(ComeFromConstant.TEMPLATE);
		
		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = picPackageService.findByPage(entity, result);
		return result;
	}

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @param adminCate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult delete(PicPackage entity,HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer packageId = entity.getId();
		if (null == packageId) {
			result.failure("参数[packageId]不能为空!");
			return result;
		}
		int delResult = picPackageService.deletePicPackage(packageId,request);
		if (delResult > 0) {
			result.success();
			return result;
		}

		result.failure();
		return result;
	}

	/**
	 * @Title: deleteBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/batch", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult deleteBatch(String array,HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		if (StringUtils.isBlank(array)) {
			result.failure();
			return result;
		}

		array = array.replace("[", "").replace("]", "");

		int deleteResult = picPackageService.deleteByBatch(array,request);
		if (deleteResult > 0) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}

}
