
package com.artcweb.service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailImageSize;
import com.artcweb.vo.NailImageSizeVo;

public interface NailImageSizeService extends BaseService<NailImageSize, Integer>{

	LayUiResult findByPage(NailImageSizeVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

	

}
