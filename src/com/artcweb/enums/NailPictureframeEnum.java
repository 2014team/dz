package com.artcweb.enums;


public enum NailPictureframeEnum {
	BLACK(1,"黑框"),
	WHITE(2,"白框"),
	BLUE(3,"蓝框"),
	POWDER(4,"粉框"),
	GOLD(5,"金框"),
	SILVER(6,"银框"),
	ROSE(7,"玫瑰金"),
	;
	private Integer value;
	// 显示名称
	private String displayName;

	NailPictureframeEnum(Integer value,String displayName) {
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
	
	
	/**
	 * 根据value获取到枚举类名称
	 * 
	 * @param value
	 * @return
	 */
	public static String getNameByValue(Integer value) {
		NailPictureframeEnum type = getByValue(value);
		return null == type ? "" : type.name();
	}

	/**
	 * 根据value获取到枚举类显示名称
	 * 
	 * @param value
	 * @return
	 */
	public static String getDisplayNameByValue(Integer value) {
		NailPictureframeEnum type = getByValue(value);
		return null == type ? "" : type.getDisplayName();
	}

	/**
	 * 根据value获取枚举类型
	 * 
	 * @param value
	 * @return
	 */
	public static NailPictureframeEnum getByValue(Integer value) {
		if (null != value) {
			for (NailPictureframeEnum type : NailPictureframeEnum.values()) {
				if (type.getValue() == value) {
					return type;
				}
			}
		}
		return null;
	}

	/**
	 * 根据value获取枚举name
	 * @param name
	 * @return
	 */
	public static int getValueByName(String name) {
		if (null != name && "".equals(name.trim())) {
			NailPictureframeEnum type = NailPictureframeEnum.valueOf(name);
			return null == type ? 0 : type.value;
		}
		return 0;
	}
	
}
