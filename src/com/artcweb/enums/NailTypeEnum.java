package com.artcweb.enums;


public enum NailTypeEnum {
	SMALL(1,"小钉"),
	ROSE(2,"玫瑰钉"),
	DIAMOND(3,"钻石钉"),
	BIG(4,"大钉"),
	MINI(5,"迷你"),
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
	
	
	/**
	 * 根据value获取到枚举类名称
	 * 
	 * @param value
	 * @return
	 */
	public static String getNameByValue(Integer value) {
		NailTypeEnum type = getByValue(value);
		return null == type ? "" : type.name();
	}

	/**
	 * 根据value获取到枚举类显示名称
	 * 
	 * @param value
	 * @return
	 */
	public static String getDisplayNameByValue(Integer value) {
		NailTypeEnum type = getByValue(value);
		return null == type ? "" : type.getDisplayName();
	}

	/**
	 * 根据value获取枚举类型
	 * 
	 * @param value
	 * @return
	 */
	public static NailTypeEnum getByValue(Integer value) {
		if (null != value) {
			for (NailTypeEnum type : NailTypeEnum.values()) {
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
			NailTypeEnum type = NailTypeEnum.valueOf(name);
			return null == type ? 0 : type.value;
		}
		return 0;
	}
	
}
