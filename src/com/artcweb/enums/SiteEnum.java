package com.artcweb.enums;

public enum SiteEnum {
	
	LINE(1,"绕线画"),
	NAIL(2,"图钉画"),
	;
	private Integer value;
	// 显示名称
	private String displayName;

	SiteEnum(Integer value,String displayName) {
		this.value = value;
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
