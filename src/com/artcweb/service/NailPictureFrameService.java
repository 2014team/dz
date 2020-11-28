
package com.artcweb.service;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.NailPictureFrame;
import com.artcweb.vo.NailPictureFrameVo;

public interface NailPictureFrameService extends BaseService<NailPictureFrame, Integer>{

	LayUiResult findByPage(NailPictureFrameVo entity, LayUiResult result);

	boolean deleteByBatch(String array);

	

}
