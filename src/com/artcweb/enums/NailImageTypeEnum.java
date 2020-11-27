package com.artcweb.enums;

/**
 * @ClassName: NailImageTypeEnum
 */
public enum NailImageTypeEnum {
	SMAIL("小图钉"),
	BIG("大图钉"), 
	
	;
	// 显示名称
	private String displayName;

	NailImageTypeEnum(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
