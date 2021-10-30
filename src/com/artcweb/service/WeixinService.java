
package com.artcweb.service;

import com.artcweb.bean.Weixin;

public interface WeixinService extends BaseService<Weixin, Integer>{

	public Weixin saveOpenid(String js_code);
}
