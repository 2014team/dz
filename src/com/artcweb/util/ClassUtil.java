package com.artcweb.util;

import java.lang.reflect.Field;

import org.slf4j.LoggerFactory;

public class ClassUtil {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	/**
	 * 根据属性名获取属性值
	 *
	 * @param fieldName
	 * @param object
	 * @return
	 */
	public static String getFieldValueByFieldName(String fieldName, Object object) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			// 设置对象的访问权限，保证对private的属性的访问
			field.setAccessible(true);
			return (String) field.get(object);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 根据属性名设置属性值
	 *
	 * @param fieldName
	 * @param object
	 * @return
	 */
	public static void setFieldValueByFieldName(String fieldName, Object object, String value) {
		try {
			// 获取obj类的字节文件对象
			Class c = object.getClass();
			// 获取该类的成员变量
			Field f = c.getDeclaredField(fieldName);
			// 取消语言访问检查
			f.setAccessible(true);
			// 给变量赋值
			f.set(object, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
