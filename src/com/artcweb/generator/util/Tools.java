package com.artcweb.generator.util;

public class Tools {

	public static String fieldDeal(String fieldName) {
		if (null != fieldName && fieldName.length() > 0 && fieldName.indexOf("_") != -1) {
			String[] tableNameArr = fieldName.split("_");
			StringBuffer sb = new StringBuffer();
			if (null != tableNameArr && tableNameArr.length > 0) {
				for (int i = 0; i < tableNameArr.length; i++) {
					if (i == 0) {
						sb.append(tableNameArr[i]);
					} else {
						sb.append(capitalFirstChar(tableNameArr[i]));// 首字母大写
					}

				}
				fieldName = sb.toString();
			}
		}

		return fieldName;

	}

	public static String capitalFirstChar(String str) {
		if (str == null || str.trim().equals(""))
			return str;
		else {
			char[] charArray = str.toCharArray();
			if (charArray[0] >= 'a' && charArray[0] <= 'z') {
				charArray[0] = (char) (charArray[0] - 32);
				return String.valueOf(charArray);
			} else
				return str;
		}
	}
}
