package com.artcweb.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.steadystate.css.ParseException;

public class DateUtil {
	
	/**
	 * 存放不同的日期模板格式的sdf的Map
	 */
	public static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
	
	/**
	 * 锁对象
	 */
	private static final Object lockObj = new Object();

	public static final String DATE_YYYY_MM = "yyyy-MM";
	public static final Integer DATE_YYYY_MM_LENGTH = 7;

	public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";
	public static final Integer DATE_YYYY_MM_DD_LENGTH = 10;

	public static final String DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final Integer DATE_YYYY_MM_DD_HH_MM_LENGTH = 16;

	public static final String DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final Integer DATE_YYYY_MM_DD_HH_MM_SS_LENGTH = 19;
	
	public static final String DATEYYYYMMDDHHMMSS = "yyyyMMddHHmmss";
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
	
	

	/**
	 * @Title: compareToLte
	 * @Description: 日期比较
	 * @param end
	 * @param start
	 * @return true:大于等于
	 */
	public static boolean compareToLte(Date end, Date start) {
		if (null == start || null == end) {
			return false;
		}
		int result = end.compareTo(start);
		if (result != -1) {
			return true;
		}

		return false;
	}

	/**
	 * @Title: compareToGt
	 * @Description: 日期比较
	 * @param end
	 * @param start
	 * @return true:大于
	 */
	public static boolean compareToGt(Date end, Date start) {
		if (null == start || null == end) {
			return false;
		}
		int result = end.compareTo(start);
		if (result == 1) {
			return true;
		}

		return false;
	}
	
	/**
	 * @Title: parse
	 * @Description: 字符串转日期
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException 
	 */
	public static Date parse(String dateStr, String pattern) throws Exception {
		return getSdf(pattern).parse(dateStr);
	}
	
	
	public static String format(Date date, String pattern) {
		return getSdf(pattern).format(date);
	}
	
	/**
	 * @Title: getSdf
	 * @Description: 高并发使用
	 * @param pattern
	 * @return
	 */
	private static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (tl == null) {
			synchronized (lockObj) {
				tl = sdfMap.get(pattern);
				if (tl == null) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected SimpleDateFormat initialValue() {
							return new SimpleDateFormat(pattern);
						}
					};
					sdfMap.put(pattern, tl);
				}
			}
		}
		return tl.get();
	}
	

	public static Date addDay(Date date, int num) {
		return add(date, Calendar.DAY_OF_MONTH, num);
	}

	public static Date addMonth(Date date, int num) {
		return add(date, Calendar.MONTH, num);
	}

	public static Date addYear(Date date, int num) {
		return add(date, Calendar.YEAR, num);
	}
	
	private static Date add(Date date,int type,int num){
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
		cal.add(type, num);
		
		return cal.getTime();
	}
	
	public static String getStartTime(){
		return format(new Date(), DATE_YYYY_MM_DD);
	}
	public static String getEndTime(){
		Date d = addDay(new Date(),1);
		return format(d, DATE_YYYY_MM_DD);
	}
	
	private static Date getTime(int hour,int minute,int second ,int millisecond ) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, hour);
		todayStart.set(Calendar.MINUTE, minute);
		todayStart.set(Calendar.SECOND, second);
		todayStart.set(Calendar.MILLISECOND, millisecond);
		return todayStart.getTime();
	}
 

}
