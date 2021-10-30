package com.artcweb.enums;

/**
 * @ClassName: NailImageTypeEnum
 */
public enum NailImageTypeEnum {
	SMAIL("小钉"),
	FLOWER("玫瑰"),
	MASONRY("钻石"),
	BIG("大钉"), 
	MINI("迷你"), 
	
	
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
