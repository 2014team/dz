
package com.artcweb.service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailPictureFrame;
import com.artcweb.vo.NailPictureFrameVo;

public interface NailPictureFrameService extends BaseService<NailPictureFrame, Integer>{

	LayUiResult findByPage(NailPictureFrameVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

	

}
