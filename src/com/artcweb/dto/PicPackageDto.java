
package com.artcweb.dto;

import java.io.Serializable;

import com.artcweb.bean.PicPackage;

public class PicPackageDto extends PicPackage{

	private static final long serialVersionUID = 1L;

	private Integer packageId;

	private String packageName;

	public Integer getPackageId() {

		return packageId;
	}

	public void setPackageId(Integer packageId) {

		this.packageId = packageId;
	}

	public String getPackageName() {

		return packageName;
	}

	public void setPackageName(String packageName) {

		this.packageName = packageName;
	}

}
