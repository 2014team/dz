package com.artcweb.enums;

public enum ImagePrefixNameEnum {
	
	NAIL("nail_")
	;
	// 显示名称
	private String displayName;

	ImagePrefixNameEnum(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
