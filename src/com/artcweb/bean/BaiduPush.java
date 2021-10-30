package com.artcweb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: BaiduPush
 * @Description: 参考 ： https://ziyuan.baidu.com/linksubmit/index?site=https://dz.artcweb.com/
 */
public class BaiduPush implements Serializable {
	private static final long serialVersionUID = 1L;
	// 成功推送的url条数
	private String success;
	// 当天剩余的可推送url条数
	private String remain;
	// 由于不是本站url而未处理的url列表
	private List<String> not_same_site;
	// 不合法的url列表
	private List<String> not_valid;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getRemain() {
		return remain;
	}

	public void setRemain(String remain) {
		this.remain = remain;
	}

	public List<String> getNot_same_site() {
		return not_same_site;
	}

	public void setNot_same_site(List<String> not_same_site) {
		this.not_same_site = not_same_site;
	}

	public List<String> getNot_valid() {
		return not_valid;
	}

	public void setNot_valid(List<String> not_valid) {
		this.not_valid = not_valid;
	}

}
