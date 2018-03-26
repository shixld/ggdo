package cn.ggdo.system.framework.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;

/**
 * 类名：DateUtil 
 * 版本：1.0.0
 * 用途说明：日期工具类
 * 业务区分(基础工具)
 * 依赖 Joda-Time.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class DateUtil {
	private DateUtil() {
		
	}

	/**
     * 获取string格式的当前时间
     * @param date
     * @return
     */
    public static String getNowDate(Date date){
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);//time就是当前时间
        return time;
    }
    
    /**
     * 获取json格式的方法参数
     * @param request
     * @return
     */
    public static String getJsonParams(HttpServletRequest request){
        String parameters = "";
        if (request.getParameterMap() != null && request.getParameterMap().size() > 0) {
            JSONArray json = JSONArray.fromObject(request.getParameterMap());
            parameters = json.toString();
        } 
        return parameters;
    }
    
	/**
	 * 构造日期
	 * 
	 * @param instant
	 *            时点
	 */
	public static Date getDate(final long instant) {
		return new DateTime(instant).toDate();
	}

	/**
	 * 构造日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 */
	public static Date getDate(final int year, final int month, final int day) {
		return new DateTime(year, month, day, 0, 0, 0).toDate();
	}

	/**
	 * 构造日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 */
	public static Date getDate(final int year, final int month, final int day, final int hour, final int minute, final int second) {
		return new DateTime(year, month, day, hour, minute, second).toDate();
	}

	/**
	 * 构造日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @param millisecond
	 *            毫秒
	 */
	public static Date getDate(final int year, final int month, final int day, final int hour, final int minute, final int second, final int millisecond) {
		return new DateTime(year, month, day, hour, minute, second, millisecond).toDate();
	}

	/**
	 * 当前日期转化成String
	 * 
	 * @param formatStr
	 *            格式化表达式
	 * @return 格式化后的当前日期字符串
	 */
	public static String currentToString(final String formatStr) {
		return DateTime.now().toString(formatStr);
	}

	/**
	 * 日期转化成String
	 * 
	 * @param date
	 *            需要格式化的日期
	 * @param formatStr
	 *            格式化表达式
	 * 
	 * @return 格式化后的日期字符串
	 */
	public static String dateToString(final Date date, final String formatStr) {
		return new DateTime(date).toString(formatStr);
	}

	/**
	 * String转化成日期
	 * 
	 * @param str
	 *            需要转化为日期的字符串
	 * 
	 * @param formatStr
	 *            格式化表达式
	 * 
	 * @return 转化后日期
	 */
	public static Date stringToDate(final String str, final String formatStr) {
		return DateTime.parse(str, DateTimeFormat.forPattern(formatStr)).toDate();
	}

	/**
	 * String转化成日期
	 * 
	 * @param str
	 *            需要转化为日期的字符串
	 * 
	 * @return 转化后日期
	 */
	public static Date stringToDate(final String str) {
		return DateTime.parse(str).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param years
	 *            年
	 * @param months
	 *            月
	 * @param days
	 *            日
	 * @return 添加后的日期
	 */
	public static Date add(final Date date, final int years, final int months, final int days) {
		return new DateTime(date).plusYears(years).plusMonths(months).plusDays(days).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param years
	 *            年
	 * @param months
	 *            月
	 * @param days
	 *            日
	 * @param hours
	 *            时
	 * @param minutes
	 *            分
	 * @param seconds
	 *            秒
	 * @param milliseconds
	 *            毫秒
	 * @return 添加后的日期
	 */
	public static Date add(final Date date, final int years, final int months, final int days, final int hours, final int minutes, final int seconds, final int milliseconds) {
		return new DateTime(date).plusYears(years).plusMonths(months).plusDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(milliseconds).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param years
	 *            年
	 * @return 添加后的日期
	 */
	public static Date addYears(final Date date, final int years) {
		return new DateTime(date).plusYears(years).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param months
	 *            月
	 * @return 添加后的日期
	 */
	public static Date addMonths(final Date date, final int months) {
		return new DateTime(date).plusMonths(months).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param days
	 *            日
	 * @return 添加后的日期
	 */
	public static Date addDays(final Date date, final int days) {
		return new DateTime(date).plusDays(days).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param hours
	 *            小时
	 * @return 添加后的日期
	 */
	public static Date addHours(final Date date, final int hours) {
		return new DateTime(date).plusHours(hours).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param minutes
	 *            分钟
	 * @return 添加后的日期
	 */
	public static Date addMinutes(final Date date, final int minutes) {
		return new DateTime(date).plusMinutes(minutes).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param seconds
	 *            秒
	 * @return 添加后的日期
	 */
	public static Date addSeconds(final Date date, final int seconds) {
		return new DateTime(date).plusSeconds(seconds).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param milliseconds
	 *            毫秒
	 * @return 添加后的日期
	 */
	public static Date addMilliseconds(final Date date, final int milliseconds) {
		return new DateTime(date).plusMillis(milliseconds).toDate();
	}

	/**
	 * 添加
	 * 
	 * @param date
	 *            添加前的日期
	 * @param weeks
	 *            周
	 * @return 添加后的日期
	 */
	public static Date addWeeks(final Date date, final int weeks) {
		return new DateTime(date).plusWeeks(weeks).toDate();
	}

	/**
	 * 比较两个日期
	 * 
	 * @param date1
	 *            时间1
	 * 
	 * @param date2
	 *            时间2
	 * @return 时间1大于时间2返回1，时间1小于时间2返回-1
	 */
	public static int compareTo(final Date date1, final Date date2) {
		return new DateTime(date1).compareTo(new DateTime(date2));
	}

	/**
	 * 得到当前年
	 * 
	 * @return 得到当前年
	 */
	public static int getCurrentYear() {
		return new DateTime().getYear();
	}

	/**
	 * 得到当前月
	 * 
	 * @return 得到当前月
	 */
	public static int getCurrentMonthOfYear() {
		return new DateTime().getMonthOfYear();
	}

	/**
	 * 得到当前日
	 * 
	 * @return 得到当前日
	 */
	public static int getCurrentDayOfMonth() {
		return new DateTime().getDayOfMonth();
	}

	/**
	 * 得到当前星期
	 * 
	 * @return 得到当前星期
	 */
	public static int getCurrentDayOfWeek() {
		return new DateTime().getDayOfWeek();
	}

	/**
	 * 得到年
	 * 
	 * @return 得到年
	 */
	public static int getYear(final Date date) {
		return new DateTime(date).getYear();
	}

	/**
	 * 得到月
	 * 
	 * @return 得到月
	 */
	public static int getMonthOfYear(final Date date) {
		return new DateTime(date).getMonthOfYear();
	}

	/**
	 * 得到日
	 * 
	 * @return 得到日
	 */
	public static int getDayOfMonth(final Date date) {
		return new DateTime(date).getDayOfMonth();
	}

	/**
	 * 得到星期
	 * 
	 * @return 得到星期
	 */
	public static int getDayOfWeek(final Date date) {
		return new DateTime(date).getDayOfWeek();
	}

	/**
	 * 比较两个日期直接的天数
	 * 
	 * @param startDate
	 *            开始的时间
	 * @param endDate
	 *            结束的时间
	 * @return 结束时间减去开始时间的天数
	 */
	public static int dayBetween(final Date startDate, final Date endDate) {
		return Days.daysBetween(new DateTime(startDate), new DateTime(endDate)).getDays();
	}

	/**
	 * 比较两个日期直接的秒数
	 * 
	 * @param startDate
	 *            开始的时间
	 * @param endDate
	 *            结束的时间
	 * @return 结束时间减去开始时间的秒数
	 */
	public static int secondsBetween(final Date startDate, final Date endDate) {
		return Seconds.secondsBetween(new DateTime(startDate), new DateTime(endDate)).getSeconds();
	}

}
