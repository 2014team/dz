package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: Role
 * @Description: 角色
 * @author zhuzq
 * @date 2021年03月08日 17:57:40
 */ 
public class Role extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 描述
	 */
	private String description;
 
	public String getRoleName(){
		return this.roleName;
	}
	
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
}