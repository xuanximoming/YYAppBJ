package com.yy.until;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 
	 * 功能描述：获取当前时间返回yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 * 
	 * @author GuXulei
	 *
	 * @since 2014年10月8日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String getSysTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 日期格式转换 Date返回Date
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static Date dateToDate(Date date, String dateFormat) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		ParsePosition pos = new ParsePosition(0);

		Date strtodate = format.parse(format.format(date).toString(), pos);
		return strtodate;

	}

	/**
	 * 日期格式转换 String返回Date
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static Date strToDate(String date, String dateFormat) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = format.parse(date, pos);
		return strtodate;

	}

	/**
	 * 日期格式转换 Date返回String
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateToString(Date date, String dateFormat) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		// ParsePosition pos = new ParsePosition(0);

		return format.format(date).toString();

	}

	/**
	 * 日期格式转换 String返回String
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String strToStr(String date, String dateFormat) {
		if (date == null) {
			return null;
		}
		Date strToDate2 = strToDate(date, "yyyy-MM-dd HH:mm:ss");
		String dateToString = dateToString(strToDate2, dateFormat);
		return dateToString;
	}

}
