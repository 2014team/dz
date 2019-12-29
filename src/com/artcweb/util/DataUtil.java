package com.artcweb.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtil {

	/**
	* @Title: getYear
	* @Description: 获取年
	* @param date
	* @return
	*/
	public static int getYear(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(Calendar.YEAR);
	}

	/**
	* @Title: getMonth
	* @Description: 获取月
	* @param date
	* @return
	*/
	public static int getMonth(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(Calendar.MONTH) + 1;
	}

}
