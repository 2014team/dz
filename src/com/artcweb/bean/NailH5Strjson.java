package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: NailH5Strjson
 * @Description: H5调用
 * @author zhuzq
 * @date 2020年12月07日 16:17:37
 */ 
public class NailH5Strjson extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * json内容
	 */
	private String strJson;
 
	public String getStrJson(){
		return this.strJson;
	}
	
	public void setStrJson(String strJson){
		this.strJson = strJson;
	}
}