
package com.artcweb.service;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.PicPackage;
import com.artcweb.vo.WeixinVo;

public interface ApiService{

	LayUiResult findByPage(PicPackage entity, LayUiResult result);

	/**
	* @Title: bind
	* @Description: 绑定账号
	* @param weixinVo
	* @return
	*/
	LayUiResult bind(WeixinVo weixinVo);

}
