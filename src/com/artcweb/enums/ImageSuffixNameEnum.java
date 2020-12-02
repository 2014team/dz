package com.artcweb.enums;

public enum ImageSuffixNameEnum {
	
	GIF("gif"),
	JPG("jpg"),
	
	;
	// 显示名称
	private String displayName;

	ImageSuffixNameEnum(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
