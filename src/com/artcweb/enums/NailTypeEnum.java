package com.artcweb.enums;

public enum NailTypeEnum {
	SMALL(1,"小钉"),
	ROSE(2,"玫瑰钉"),
	DIAMOND(3,"钻石钉"),
	BIG(4,"大钉"),
	;
	private Integer value;
	// 显示名称
	private String displayName;

	NailTypeEnum(Integer value,String displayName) {
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
