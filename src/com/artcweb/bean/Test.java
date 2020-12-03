package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: Test
 * @Description: 测试
 * @author zhuzq
 * @date 2020年12月03日 18:06:43
 */ 
public class Test extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String name;
 
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}