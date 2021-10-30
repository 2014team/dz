package com.artcweb.generator;

import java.util.List;

public class MyTable {

	// 表名
	private String tableName;
	// 普通字段集合，一个字段包括了：字段名，SQL类型，Java类型，注释，是否主键标志
	private List<MyField> common_fields;
	// 主键字段集合
	private List<MyField> key_fields;
	// 字段中是否有日期型的，如果有，那么在生成实体类时，就需要导java.util.Date包了
	public boolean date_flag = false;
	// 字段中是否有numeric和decimal类型的，如果有，那么，在生成实体类时就需要导java.math.BigDecimal包了
	public boolean math_flag = false;
	// 生成实体类，mapper接口等等时的基名
	public String package_name_base;
	// 类名
	private String className;

	public String getTableName() {
		return tableName;
	}

	public List<MyField> getCommon_fields() {
		return common_fields;
	}

	public List<MyField> getKey_fields() {
		return key_fields;
	}

	public boolean isDate_flag() {
		return date_flag;
	}

	public boolean isMath_flag() {
		return math_flag;
	}

	public String getPackage_name_base() {
		return package_name_base;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setCommon_fields(List<MyField> common_fields) {
		this.common_fields = common_fields;
	}

	public void setKey_fields(List<MyField> key_fields) {
		this.key_fields = key_fields;
	}

	public void setDate_flag(boolean date_flag) {
		this.date_flag = date_flag;
	}

	public void setMath_flag(boolean math_flag) {
		this.math_flag = math_flag;
	}

	public void setPackage_name_base(String package_name_base) {
		this.package_name_base = package_name_base;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
