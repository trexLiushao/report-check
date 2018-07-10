package util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * 
 * @author chentx
 */
public final class DateUtils {

	private DateUtils() {

	}

	/**
	 * yyyy-MM-dd
	 */
	public static final String FORMAT1 = "yyyy-MM-dd";

	/**
	 * yyyy.MM.dd
	 */
	public static final String FORMAT2 = "yyyy.MM.dd";

	/**
	 * yyyy/MM/dd
	 */
	public static final String FORMAT3 = "yyyy/MM/dd";

	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String FORMAT4 = "yyyy-MM-dd HH:mm";

	/**
	 * yyyy.MM.dd HH:mm
	 */
	public static final String FORMAT5 = "yyyy.MM.dd HH:mm";

	/**
	 * yyyy/MM/dd HH:mm
	 */
	public static final String FORMAT6 = "yyyy/MM/dd HH:mm";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String FORMAT7 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy.MM.dd HH:mm:ss
	 */
	public static final String FORMAT8 = "yyyy.MM.dd HH:mm:ss";

	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	public static final String FORMAT9 = "yyyy/MM/dd HH:mm:ss";

	/**
	 * yyyy_MM_dd_HH_mm_ss
	 */
	public static final String FORMAT10 = "yyyy_MM_dd_HH_mm_ss";

	/**
	 * yy-MM-dd
	 */
	public static final String FORMAT11 = "yy-MM-dd";

	/**
	 * yyyyMMdd
	 */
	public static final String FORMAT12 = "yyyyMMdd";

	/**
	 * yyyyMMddHHmmss
	 */
	public static final String FORMAT13 = "yyyyMMddHHmmss";

	/**
	 * yyyyMM
	 */
	public static final String FORMAT14 = "yyyyMM";

	/**
	 * YYYY-MM-dd HH-mm-ss
	 */
	public static final String FORMAT15 = "YYYY-MM-dd HH-mm-ss";

	/**
	 * yyyy年MM月dd日 E HH:mm:ss
	 */
	public static final String FORMAT16 = "yyyy年MM月dd日 EEEE HH:mm:ss";

	/**
	 * yyyy-MM
	 */
	public static final String FORMAT17 = "yyyy-MM";
	
	public static final String FORMAT18 = "YYYY-MM-dd HH:mm:ss.SSS";
	
	public static final String FORMAT19 = "HH:mm";
	
	public static final String FORMAT20 = "yyyy年MM月dd日";
	
	public static final String FORMAT21 = "yyyy年MM月dd日 HH:mm:ss";
	
	/**
	 * yyyyMMddHHmmss
	 */
	public static final String FORMAT22 = "yyyyMMddHHmmssSSS";

	public static Date getCurrentDate() {

		return new Date(System.currentTimeMillis());
	}

	public static Date getYesterDay() {

		return addDay(new Date(System.currentTimeMillis()), -1);
	}

	public static String getYesterDayString() {

		return parseDateToString(addDay(new Date(System.currentTimeMillis()), -1), FORMAT1);
	}

	/**
	 * 得到年月日的路径
	 * 
	 * @return
	 */
	public static String getThisYearMonthDay(String dateString) {

		Date date = parseStringToDate(dateString, FORMAT12);

		return DateUtils.getYear(date) + "/" + DateUtils.getMonth(date) + "/" + DateUtils.getDay(date) + "/";
	}

	/**
	 * 返回当前日期或时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {

		if (StringUtils.isBlank(format)) {
			format = FORMAT1;
		}

		Date date = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat(format);

		String currentTime = formatter.format(date);

		return currentTime;
	}

	/**
	 * 返回当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {

		String datetime = getCurrentDate(FORMAT7);
		System.out.println(datetime);
		String time = datetime.substring(datetime.indexOf(" ") + 1);

		return time;
	}

	/**
	 * 返回当前时间加随机数
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeRandom() {

		String datetime = getCurrentDate(DateUtils.FORMAT10) + "_" + Math.random();

		return datetime;
	}

	/**
	 * 返回当前时间和日期
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeString() {

		return getCurrentDate(DateUtils.FORMAT7);

	}

	/**
	 * 返回当前日期
	 * 
	 * @return
	 */
	public static Date getCurrentDateTime() {

		String datetime = getCurrentDate(FORMAT7);

		return parseStringToDate(datetime, "");
	}

	public static Timestamp getCurrentTimestamp() {

		String datetime = getCurrentDate(FORMAT7);

		return parseStringToTimestamp(datetime, "");
	}

	public static Timestamp getCurrentTimestamp(String format) {

		String datetime = getCurrentDate(format);

		return parseStringToTimestamp(datetime, "");
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 返回字符型日期
	 */
	public static String parseDateToString(Date date, String format) {

		String result = "";
		DateFormat formatter = null;
		try {
			if (date != null) {
				if (StringUtils.isBlank(format)) {
					formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
				} else {
					formatter = new SimpleDateFormat(format, Locale.CHINA);
				}
				result = formatter.format(date);
			}
		} catch (Exception e) {
		}

		return result;
	}

	/**
	 * 时间1-时间2的毫秒
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static long between(Timestamp t1, Timestamp t2) {

		if ((t1 != null) && (t2 != null)) {
			return t1.getTime() - t2.getTime();
		}

		return 0;
	}

	/**
	 * 两个日期date1-date2相减，相差的天数
	 * 
	 * @param date1
	 *            日期
	 * @param date2
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static int betweenTwoDates(Date date1, Date date2) {

		return (int) ((getMillis(date1) - getMillis(date2)) / (24 * 3600 * 1000));
	}

	/**
	 * 两个日期date1-date2相减，相差的小时数
	 * 
	 * @param date1
	 *            日期
	 * @param date2
	 *            日期
	 * @return 返回相减后的小时数
	 */
	public static double betweenTwoDatesHours(Date date1, Date date2) {

		return (double) ((getMillis(date1) - getMillis(date2)) / (3600 * 1000));
	}

	/**
	 * 两个日期date1-date2相减，相差的分钟
	 * 
	 * @param date1
	 *            日期
	 * @param date2
	 *            日期
	 * @return 返回相减后的小时数
	 */
	public static double betweenTwoDatesMinute(Date date1, Date date2) {

		return (double) ((getMillis(date1) - getMillis(date2)) / (60 * 1000));
	}

	/**
	 * 将字符串转换为日期
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringToDate(String str, String format) {

		DateFormat formatter = null;
		Date date = null;
		if (StringUtils.isNotBlank(str)) {

			if (StringUtils.isBlank(format)) {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				formatter = new SimpleDateFormat(format);
			}

			try {
				date = formatter.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}

	/**
	 * 返回2007到今年的年份
	 * 
	 * @return
	 */
	public static List<Integer> get2007ToThisYear() {

		// 当前时间
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		// 返回的年份
		List<Integer> the2007ToThisYearList = new ArrayList<Integer>();
		// 当前年
		int thisYear = c.get(Calendar.YEAR);

		for (int i = thisYear; i >= 2007; i--) {
			the2007ToThisYearList.add(i);
		}

		return the2007ToThisYearList;
	}

	/**
	 * 获取当前月的前几个月份的时间
	 * 
	 * @param months
	 * @return
	 */
	public static Date getDateBeforeMonths(int months) {

		// 当前时间
		Calendar c = Calendar.getInstance();

		c.add(Calendar.MONTH, -months);

		return c.getTime();
	}

	/**
	 * 获取当前日期的前几天的时间
	 * 
	 * @param days
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getDateBeforeDays(int days) {
		Date date=new Date();//取时间  
		Calendar calendar = new GregorianCalendar();  
		calendar.setTime(date);  
		calendar.add(calendar.DATE, -days);//把日期往后增加一天.整数往后推,负数往前移动  
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果   
		return date;
	}

	/**
	 * 返回1-12月份
	 * 
	 * @return
	 */
	public static List<String> getAllMonth() {

		List<String> theMonthList = new ArrayList<String>();

		for (int i = 1; i < 13; i++) {
			if (i < 10) {
				theMonthList.add("0" + i);
			} else {
				theMonthList.add("" + i);
			}
		}

		return theMonthList;
	}

	/**
	 * 返回日期中的年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.YEAR);
	}

	/**
	 * 返回日期中的月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回日期中的日
	 * 
	 * @param date
	 *            日期
	 * @return 返回日
	 */
	public static int getDay(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回日期中的小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回日期中的分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.MINUTE);
	}

	/**
	 * 返回日期中的秒钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.SECOND);
	}

	/**
	 * 返回日期代表的毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.getTimeInMillis();
	}

	/**
	 * 返回当前日期代表的毫秒
	 * 
	 * @return
	 */
	public static long getCurrentTimeMillis() {

		return System.currentTimeMillis();
	}

	@SuppressWarnings("static-access")
	public static Date addMonth(Date date, int month) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(c.MONTH, month);

		return c.getTime();
	}

	/**
	 * @描述 时间加上分钟
	 * @创建时间 2016年10月12日16:43:24
	 * 
	 */
	@SuppressWarnings("static-access")
	public static Date addMinute(Date date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(c.MINUTE, minute);
		return c.getTime();
	}

	/**
	 * @描述 把传进来的"HH:mm"格式的字符串日期转换为当前时间
	 * @创建时间 2016年10月12日16:54:09
	 * 
	 */
	public static Date chkStringToDate(String chkDate) throws Exception {
		DateFormat format = new SimpleDateFormat(FORMAT1);
		DateFormat format2 = new SimpleDateFormat(FORMAT7);
		Date date = new Date();
		String date2 = format.format(date);
		String returnDate2 = date2 + " " + chkDate + ":00";
		Date returnDate = format2.parse(returnDate2);
		return returnDate;
	}
	
	/**
	 * @描述 得到从当前时间往后的某一天的零点
	 * @创建时间 2017年1月17日11:06:01
	 * @param date :当前时间
	 * @param day :几天
	 * */
	public static String getNextZeroDateToString(Date date,Integer day){
		DateFormat format = new SimpleDateFormat(DateUtils.FORMAT7);
		Calendar day2 = Calendar.getInstance();
		day2.setTime(date);
		day2.set(Calendar.HOUR_OF_DAY,24*day);
		day2.set(Calendar.MINUTE,0);
		day2.set(Calendar.SECOND,0);
		day2.set(Calendar.MILLISECOND,0);
		return format.format(day2.getTime());
	}

	/**
	 * 日期加天数,可以向前加，向后加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static Date addDay(Date date, int day) {

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);

		return c.getTime();
	}

	/**
	 * 日期加秒数,可以向前加，向后加
	 * 
	 * @param date
	 *            日期
	 * @param second
	 *            秒数
	 * @return 返回相加后的日期
	 */
	public static Date addSecond(Date date, long second) {

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + second * 1000);

		return c.getTime();
	}

	/**
	 * 根据一个日期，返回是星期几
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeekByDate(String sdate) {

		// 再转换为时间
		Date date = parseStringToDate(sdate, "");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		// day中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return day + "";
	}

	/**
	 * 根据一个日期，返回是星期几 1=星期日 7=星期六，其他类推
	 * 
	 * @param sdate
	 * @return
	 */
	public static int getWeekByDate(Date date) {

		// 再转换为时间
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// day中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 日期为一年中的第几周
	 * 
	 * @return
	 */
	public static String getWeekNum(Date date) {

		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(date);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));

		return week;

	}

	public static java.sql.Date parseUtilDateToSqlDate(Date date) {

		if (date != null) {
			return new java.sql.Date(date.getTime());
		} else {
			return null;
		}
	}

	public static java.sql.Date parseStringToSqlDate(String dateStr, String format) {

		Date date = null;
		if (StringUtils.isBlank(format)) {
			date = parseStringToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
		} else {
			date = parseStringToDate(dateStr, format);
		}

		return parseUtilDateToSqlDate(date);
	}

	public static Timestamp parseUtilDateToTimestamp(Date date, String format) {

		return parseStringToTimestamp(parseDateToString(date, format), format);
	}

	public static Date parseTimestampToUtilDate(Timestamp date, String format) {

		return parseStringToDate(parseDateToString(date, format), format);
	}

	public static Timestamp parseStringToTimestamp(String dateStr, String format) {

		if (StringUtils.isBlank(dateStr)) {
			return null;
		}

		Date date = null;
		if (StringUtils.isBlank(format)) {
			date = parseStringToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
		} else {
			date = parseStringToDate(dateStr, format);
		}

		if (date != null) {
			long t = date.getTime();
			return new Timestamp(t);
		} else {
			return null;
		}
	}
	
	public static Timestamp parseDateToTimestamp(Date date, String format) {
		String dateStr = "";
		Date date1 = null;
		if (StringUtils.isBlank(format)) {
			dateStr = parseDateToString(date, "yyyy-MM-dd HH:mm:ss");
			date1 = parseStringToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
		} else {
			dateStr = parseDateToString(date, format);
			date1 = parseStringToDate(dateStr, format);
		}

		if (date1 != null) {
			long t = date1.getTime();
			return new Timestamp(t);
		} else {
			return null;
		}
	}

	/**
	 * 获取时间2099-12-31，23:59:59
	 * 
	 * @return
	 */
	public static Timestamp getMaxTimestamp() {

		return DateUtils.parseStringToTimestamp("2099-12-31 23:59:59", DateUtils.FORMAT7);
	}

	/**
	 * 返回日期中的年月日，格式化成yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonthDay(Date date) {

		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT12);

		String currentTime = formatter.format(date);

		return currentTime;
	}

	/**
	 * 取得指定月份的第一天
	 *
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthBegin(String strdate) {

		return parseDateToString(parseStringToDate(strdate, "yyyy-MM"), "");
	}

	/**
	 * 取得指定月份的最后一天
	 *
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthEnd(String strdate) {

		Date date = parseStringToDate(getMonthBegin(strdate), "");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		return parseDateToString(calendar.getTime(), "");
	}

	public static String getPreviousMonthBegin() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		Date date = new Date(cal.getTimeInMillis());

		return parseDateToString(parseStringToDate(parseDateToString(date, FORMAT1), "yyyy-MM"), FORMAT1);
	}

	public static String getPreviousMonthEnd() {

		Date date = parseStringToDate(getPreviousMonthBegin(), FORMAT1);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		return parseDateToString(calendar.getTime(), FORMAT1);
	}

	/**
	 * 上个月
	 * 
	 * @return
	 */
	public static String getPreviousMonth() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		Date date = new Date(cal.getTimeInMillis());

		return parseDateToString(parseStringToDate(parseDateToString(date, FORMAT1), "yyyy-MM"), FORMAT14);
	}

	/**
	 * 上两个月
	 * 
	 * @return
	 */
	public static String getPreviousTwoMonth() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 2);
		Date date = new Date(cal.getTimeInMillis());

		return parseDateToString(parseStringToDate(parseDateToString(date, FORMAT1), "yyyy-MM"), FORMAT14);
	}

	/**
	 * 判断两个日期是否在同一周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		} else if ((1 == subYear) && (11 == cal2.get(Calendar.MONTH))) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		} else if ((-1 == subYear) && (11 == cal1.get(Calendar.MONTH))) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		}
		return false;
	}

	public static String addOneDay(String stopTime) {
		String finishTime = stopTime;
		Date finishDate = null;
		if (stopTime != null && !"".equals(stopTime)) {
			if (stopTime.length() < 19) {
				finishTime = stopTime.substring(0, 10) + " 00:00:00";
			}
			finishDate = DateUtils.parseStringToDate(finishTime, DateUtils.FORMAT7);
			finishDate = DateUtils.addDay(finishDate, 1);
			return DateUtils.parseDateToString(finishDate, DateUtils.FORMAT7);
		} else {
			return null;
		}
	}

	/**
	 * 给date加1天
	 * 
	 * @param date
	 * @return
	 */
	public static Date addOneDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 获取年月的int值 可用作比较
	 * 
	 * @param date
	 * @return
	 */
	public static int getYMForInt(Date date) {// 传入日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);// 设置时间
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH);// 获取月份
		return year * 100 + month;// 返回年份乘以100加上月份的值，因为月份最多2位数，所以年份乘以100可以获取一个唯一的年月数值
	}

	/**
	 * 获取年月日的int值 可用作比较
	 * 
	 * @param date
	 * @return
	 */
	public static int getYMDForInt(Date date) {// 传入日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);// 设置时间
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH);// 获取月份
		int day = cal.get(Calendar.DATE);// 获取日期
		return year * 10000 + month*100+day;// 返回年份乘以100加上月份的值，因为月份最多2位数，所以年份乘以100可以获取一个唯一的年月数值
	}
	
	/**
	 * 获取要查询时间范围内的日期
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(DateUtils.parseStringToDate(DateUtils.parseDateToString(dBegin, DateUtils.FORMAT1), DateUtils.FORMAT1));
		calEnd.setTime(DateUtils.parseStringToDate(DateUtils.parseDateToString(dEnd, DateUtils.FORMAT1), DateUtils.FORMAT1));
		// 测试此日期是否在指定日期之后
		while (calEnd.compareTo(calBegin) > 0) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_YEAR, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}
	
	/**
	 * 获取当前时间之前 几天内所有的日期
	 * 
	 * @param dayCount
	 * @return
	 */
	public static List<Map<String, Object>> findDates(int dayCount) {
		List<Map<String, Object>> findAllDates = new ArrayList<Map<String, Object>>();
		Date dBegin = getDateBeforeDays(dayCount);
		Date dEnd = getDateBeforeDays(0);
		List<Date> findDates = findDates(dBegin, dEnd);
		for (Date date : findDates) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("d", parseDateToString(date, FORMAT1));
			int i = getWeekByDate(date);
			//星期采用与Mysql数据库一致的方式 0:星期一 1:星期二2:星期三 3:星期四 4:星期五 5:星期六 6:星期日 
			map.put("w", i == 1 ? 6 : i - 2);
			findAllDates.add(map);
		}
		return findAllDates;
	}
	
	/**
	 * 获取时间范围内几天内所有的日期
	 * 
	 * @param dayCount
	 * @return
	 */
	public static List<Map<String, Object>> findDates(String begin, String end) {
		List<Map<String, Object>> findAllDates = new ArrayList<Map<String, Object>>();
		Date dBegin = parseStringToDate(begin, FORMAT1);
		Date dEnd = parseStringToDate(end, FORMAT1);
		List<Date> findDates = findDates(dBegin, dEnd);
		for (Date date : findDates) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("d", parseDateToString(date, FORMAT1));
			int i = getWeekByDate(date);
			//星期采用与Mysql数据库一致的方式 0:星期一 1:星期二2:星期三 3:星期四 4:星期五 5:星期六 6:星期日 
			map.put("w", i == 1 ? 6 : i - 2);
			findAllDates.add(map);
		}
		return findAllDates;
	}
	
	//通过传入日期，获取星期几
	public static String getWeekDayByDate(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
			case 1:
				return "星期日";
			case 2:
				return "星期一";
			case 3:
				return "星期二";
			case 4:
				return "星期三";
			case 5:
				return "星期四";
			case 6:
				return "星期五";
			case 7:
				return "星期六";
		}
		return null;
	}

	/**
	 * 检查日期有效
	 * @param s
	 * @return
	 */
	public static boolean isValidDate(String strDate, String format) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			dateFormat.setLenient(false);
			dateFormat.parse(strDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// @SuppressWarnings("unused")
	// public static void main(String[] args) {
	// }
}
