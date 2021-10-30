package com.artcweb.service;

import com.artcweb.service.BaseService;
import com.artcweb.bean.NailH5Strjson;
import com.artcweb.vo.NailH5StrjsonVo;
import com.artcweb.bean.LayUiResult;

/**
 * @ClassName: NailH5StrjsonDao
 * @Description: H5调用
 * @author zhuzq
 * @date 2020年12月07日 16:17:37
 */
public interface NailH5StrjsonService extends BaseService<NailH5Strjson,Integer>{

	public LayUiResult findByPage(NailH5StrjsonVo entity, LayUiResult result);

	public boolean deleteByBatch(String array);

}
