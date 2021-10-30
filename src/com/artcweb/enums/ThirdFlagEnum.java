package com.artcweb.enums;

public enum ThirdFlagEnum {

	OFF("0"), OK("1"),;
	// 显示名称
	private String displayName;

	ThirdFlagEnum(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
