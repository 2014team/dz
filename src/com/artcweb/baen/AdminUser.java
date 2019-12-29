
package com.artcweb.baen;

public class AdminUser extends BaseBean {

	private static final long serialVersionUID = 1L;

	// 用户名
	private String userName;

	// 密码
	private String password;

	// 邮箱
	private String email;

	// 有效表识 1:有效 0无效
	private Integer vaild;

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public Integer getVaild() {

		return vaild;
	}

	public void setVaild(Integer vaild) {

		this.vaild = vaild;
	}

}
