package com.artcweb.generator.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.artcweb.generator.MyField;
import com.artcweb.generator.MyTable;


public class DataBaseUtil {
	
	public static String jdbc_url ;
	public static String jdbc_driver ;
	public static String jdbc_user ;
	public static String jdbc_password ;
	private static Integer jdbc_poolsize;	
 
	private static List<Connection> connectionPool;
	
	/**
	 * 初始化,用于读取db.properties，初始化和数据库连接相关的信息
	 */
	public static void init(){

//		Properties properties = new Properties();
		InputStream input = null;
//		String path = "db.properties";
//		input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		try {
//			properties.load(input);
//			jdbc_driver = properties.getProperty("jdbc_driver");
//			jdbc_url = properties.getProperty("jdbc_url");
//			jdbc_user = properties.getProperty("jdbc_user");
//			jdbc_password = properties.getProperty("jdbc_password");
//			jdbc_poolsize = Integer.parseInt(properties.getProperty("jdbc_poolsize"));
			
//			jdbc_driver = "com.mysql.jdbc.Driver";
//			jdbc_url = "jdbc:mysql://47.96.100.62:3306/temple?useUnicode=true&characterEncoding=UTF-8";
//			jdbc_user = "root";
//			jdbc_password = "root";
			jdbc_poolsize = 3;
			
			connectionPool = new ArrayList<Connection>(jdbc_poolsize);			
			Class.forName(jdbc_driver);
			int i;
			for(i = 0 ; i < jdbc_poolsize ; i++){
				Connection connection = DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_password);
				connectionPool.add(connection);
			}			
		} catch (ClassNotFoundException e) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("加载数据库驱动出错，请检查：\r\n");
			buffer.append("（1）db.properties中的jdbc_driver是否写错？\r\n");
			buffer.append("（2）数据库驱动JAR包是否已导入？");
			System.out.println(buffer.toString());
			System.exit(-1);
		} catch (SQLException e) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Oh，My God! 数据库连接失败，请检查：\r\n");
			buffer.append("（1）数据库服务是否已开启？\r\n");
			buffer.append("（2）db.properties中的jdbc_url是否写错？\r\n");
			buffer.append("（3）db.properties中的jdbc_user是否写错？\r\n");
			buffer.append("（4）db.properties中的jdbc_password是否写错？\r\n");
			System.out.println(buffer.toString());
			System.exit(-1);
		} finally{
			try {
				if(input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	
	/**
	 * @return 获取数据库连接，可能返回null
	 */
	public static Connection getConnection(){
		if(connectionPool != null){
			if(connectionPool.size() > 0){
				return connectionPool.remove(connectionPool.size() - 1);
			}else{
				Connection connection = null;
				try {
					Class.forName(jdbc_driver);
					connection = DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_password);
					return connection;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return connection;
				} catch (SQLException e) {
					e.printStackTrace();
					return connection;
				}
			}
		}else
			return null;
	}
	
	
	/**
	 * 
	 * @param sqlType
	 * @return SQL类型得到java类型
	 */
	public static String getJavaTypeFromSQLType(String sqlType){
		String javaType = null;
		int index = sqlType.indexOf("(");
		if(index != -1)
			sqlType = sqlType.substring(0, index);
		
		if(sqlType.equalsIgnoreCase("VARCHAR")||sqlType.equalsIgnoreCase("CHAR")||sqlType.contains("TEXT") ||sqlType.equalsIgnoreCase("text") ||sqlType.equalsIgnoreCase("nchar") ||sqlType.equalsIgnoreCase("nvarchar"))
			javaType = "String";
		else if(sqlType.equalsIgnoreCase("NUMERIC")||sqlType.equalsIgnoreCase("DECIMAL") ||sqlType.equalsIgnoreCase("money") ||sqlType.equalsIgnoreCase("smallmoney"))
			javaType = "BigDecimal";
		else if(sqlType.equalsIgnoreCase("BIT"))
			javaType = "boolean";
		else if(sqlType.equalsIgnoreCase("TINYINT"))
			javaType = "Integer";
		else if(sqlType.equalsIgnoreCase("SAMLLINT"))
			javaType = "short";
		else if(sqlType.equalsIgnoreCase("INTEGER")||sqlType.equalsIgnoreCase("int")||sqlType.equalsIgnoreCase("mediumint"))
			javaType = "Integer";
		else if(sqlType.equalsIgnoreCase("BIGINT") ||sqlType.equalsIgnoreCase("bigint"))
			javaType = "Long";
		else if(sqlType.equalsIgnoreCase("REAL"))
			javaType = "float";
		else if(sqlType.equalsIgnoreCase("FLOAT")||sqlType.equalsIgnoreCase("double"))
			javaType = "Double";
		else if(sqlType.equalsIgnoreCase("binary")||sqlType.equalsIgnoreCase("varbinary")||sqlType.equalsIgnoreCase("longvarbinary"))
			javaType = "byte[]";
		else if(sqlType.equalsIgnoreCase("date"))
			javaType = "Date";
		else if(sqlType.equalsIgnoreCase("time"))
			javaType = "Date";
		else if(sqlType.equalsIgnoreCase("datetime")||sqlType.equalsIgnoreCase("timestamp") || sqlType.equalsIgnoreCase("smalldatetime"))
			javaType = "Date";
		return javaType;
	}
		
	/**
	 * 
	 * @param str
	 * @return 把一个字符串首字母大写
	 */
	public static String capitalFirstChar(String str){
		if(str == null || str.trim().equals(""))
			return str;
		else{
			char[] charArray = str.toCharArray();
			if(charArray[0] >= 'a' && charArray[0] <= 'z'){
				charArray[0] = (char) (charArray[0] - 32);
				return String.valueOf(charArray);				
			}
			else
				return str;
		}
	}
	
	/**
	* @Title: lowerFirstCapse
	* @Description: T把一个字符串首字母小写
	* @param str
	* @return
	*/
	public static String lowerFirstCapse(String str){
		 
		char[]chars = str.toCharArray();
		 
		chars[0] += 32;
		 
		return String.valueOf(chars);
		}
	
	
	/**
	 * 
	 * @param packageName
	 * @param tableName
	 * @return 生成实体类，返回一个包含实体类内容的StringBuffer，只需要把这个StringBuffer写到一个.java文件即可
	 */
//	public static StringBuffer generateEntity(String packageName, String tableName){		
//		MyTable myTable = getTable(tableName);				
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("package ").append(packageName).append(";\r\n");
//		
//		if(myTable.math_flag)
//			buffer.append("import java.math.*;\r\n");
//		if(myTable.date_flag)
//			buffer.append("import java.util.Date;\r\n");
//		
//		buffer.append("\r\n");
//		buffer.append("public class ").append(capitalFirstChar(tableName)).append(" {\r\n");
//		StringBuffer contentBuffer = generateSetAndGet(myTable);
//		buffer.append(contentBuffer);
//		buffer.append("\r\n}");
//		
//		return buffer;
//		
//	}
	
	/**
	 * 
	 * @param myTable
	 * @return 生成实体类的主体内容，包括属性声明，以及get和set方法
	 */
	private static StringBuffer generateSetAndGet(MyTable myTable){
		StringBuffer buffer = new StringBuffer();
		//思想：先生成属性声明，再生成相应的get和set方法
		
		//1.生成字段属性声明
		myTable.getCommon_fields().addAll(myTable.getCommon_fields());
		for(MyField field : myTable.getCommon_fields()){
			String field_name = field.getField_name();
			String field_type = field.getJava_type();
			buffer.append("\tprivate " + field_type + " " + field_name + ";\r\n");
		}
		/*//2.生成普通字段属性声明
		for(MyField field : myTable.getCommon_fields()){
			String field_name = field.getField_name();
			String field_type = field.getJava_type();
			buffer.append("\tprivate " + field_type + " " + field_name + ";\r\n");
		}
		//3.生成主键字段属性的getter和setter
		for(MyField field : myTable.getKey_fields()){
			String field_name = field.getField_name();
			String field_type = field.getField_type();
			//生成set方法
			buffer.append("\tpublic void set" + capitalFirstChar(field_name) + "(" + field_type + " " + field_name + "){\r\n");
			buffer.append("\t\tthis.").append(field_name).append(" = ").append(field_name).append(";\r\n");
			buffer.append("\t}").append("\r\n");
			//生成get方法
			buffer.append("\tpublic ").append(field_type).append(" get").append(capitalFirstChar(field_name)).append("(){\r\n");
			buffer.append("\t\treturn this.").append(field_name).append(";\r\n");
			buffer.append("\t}").append("\r\n");			
		}*/
		//4.生成普通字段属性的getter和setter
		for(MyField field : myTable.getCommon_fields()){
			String field_name = field.getField_name();
			String field_type = field.getJava_type();
			//生成set方法
			buffer.append("\tpublic void set" + capitalFirstChar(field_name) + "(" + field_type + " " + field_name + "){\r\n");
			buffer.append("\t\tthis.").append(field_name).append(" = ").append(field_name).append(";\r\n");
			buffer.append("\t}").append("\r\n");
			//生成get方法
			buffer.append("\tpublic ").append(field_type).append(" get").append(capitalFirstChar(field_name)).append("(){\r\n");
			buffer.append("\t\treturn this.").append(field_name).append(";\r\n");
			buffer.append("\t}").append("\r\n");			
		}
		return buffer;
	}
	
	/**
	 * 
	 * @param myTable
	 * @return 主键作为参数时，返回的参数列表:如果主键有多个，那么参数列表就是一个实体类，否则，就是主键所对应的属性字段为参数
	 */
	private static StringBuffer generatePrimaryKeyParam(MyTable myTable){
		StringBuffer buffer = new StringBuffer();
		List<MyField> key_fields = myTable.getKey_fields();
		if(key_fields.size() >= 2){
			String tableName = myTable.getTableName();
			String entityName = capitalFirstChar(tableName);
			buffer.append(entityName).append(" ").append(tableName);
		}else{
			MyField key_field = key_fields.get(0);
			String field_type = key_field.getJava_type();
			String field_name = key_field.getField_name();
			buffer.append(field_type).append(" ").append(field_name);
		}
		return buffer;
	}
			
	
	public static String getClassName(String tableName) {

		String[] tableNameArr = tableName.split("_");
		StringBuffer sb = new StringBuffer();
		if (null != tableNameArr && tableNameArr.length > 0) {
			for (int i = 0; i < tableNameArr.length; i++) {
				if (i == 0 && tableNameArr[i].equals("t")) {
					continue;
				} else if (i == 1 && (tableNameArr[i].equals("admin") || tableNameArr[i].equals("web"))) {
					continue;
				}
				sb.append(capitalFirstChar(tableNameArr[i]));// 首字母大写
			}
			tableName = sb.toString();

		}
		return tableName;
	}

	/**
	 * 获取数据库类型
	 * @return
	 */
	public static String getDbType(){
		if(null != jdbc_driver){
			if(jdbc_driver.toLowerCase().contains("mysql")){
				return "mysql";
				
			}else if(jdbc_driver.toLowerCase().contains("sqlserver")){
				return "sqlserver";
				
			}
		}
		return "";
	}
}

