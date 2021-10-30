package com.artcweb.generator;

public class MyField {
	private String field_name;
	private String java_field_Name;
	private String sql_type;
	private String java_type;
	private String field_comment;
	private boolean is_primary;

	public String getField_name() {
		return field_name;
	}

	public String getSql_type() {
		return sql_type;
	}

	public String getJava_type() {
		return java_type;
	}

	public String getField_comment() {
		return field_comment;
	}

	public boolean isIs_primary() {
		return is_primary;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public void setSql_type(String sql_type) {
		this.sql_type = sql_type;
	}

	public void setJava_type(String java_type) {
		this.java_type = java_type;
	}

	public void setField_comment(String field_comment) {
		this.field_comment = field_comment;
	}

	public void setIs_primary(boolean is_primary) {
		this.is_primary = is_primary;
	}

	public String getJava_field_Name() {
		return java_field_Name;
	}

	public void setJava_field_Name(String java_field_Name) {
		this.java_field_Name = java_field_Name;
	}
	

}
