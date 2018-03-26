package cn.ggdo.system.framework.dao.mybatis;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 类名：TimeFactory 
 * 版本：1.0.0
 * 用途说明：配置时间的工厂
 * 业务区分(MyBatis框架)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public interface TimeFactory {
    /**
     * Get Calendar object which is used to act as a strategy object 
     * (refering to Strategy pattern of GOF) for calculating time.
     * @return Calendar object (internally, GregorianCalendar is being used)
     */
    public Calendar getCalendarStrategy();
    
    /**
     * Get time millis for right now at GMT TimeZone.
     * @return milliseconds passed since 1970-1-1 00:00:00 GMT
     */
    public long currentGMTTimeMillis();

    /**
     * Get time millis for right now at specified TimeZone.
     * @param timeZone TimeZone object which is used to calculate time milliseconds
     * @return milliseconds passed since 1970-1-1 00:00:00 at specified TimeZone
     */
    public long currentTimeMillis( TimeZone timeZone );
    
    /**
     * Get Date for right now at GMT TimeZone.
     * @return Date object which use data of milliseconds passed since 1970-1-1 00:00:00 GMT
     */
    public Date currentGMTTime();
    
    
    /**
     * Get time for right now at specified TimeZone.
     * @param timeZone TimeZone object which is used to calculate time milliseconds
     * @return Date object which use data of milliseconds passed since 1970-1-1 00:00:00 at specified TimeZone
     */
    public Date currentTime( TimeZone timeZone );
    
    /**
     * Get Calendar object which is prototyped from CalendarStrategy object holding timezone and locale
     * @param timeZone
     * @param locale
     * @return
     */
    public Calendar getCalendarInstance( TimeZone timeZone, Locale locale );
    
    /**
     * Get GMT passed milliseconds which is converted from specified timeMillis at specified TimeZone 
     * @param timeZone the TimeZone at which the timeMillis passed
     * @param timeMillis timeMillis passed since 1970-1-1 00:00:00 at a TimeZone
     * @return timeMillis at GMT TimeZone
     */
    public long getGMTTimeMillis( TimeZone timeZone, long timeMillis );
    
    /**
     * Get GMT Time which is converted from specified time at specified TimeZone 
     * @param timeZone the TimeZone at which the time is
     * @param time the time at a timeZone
     * @return time at GMT TimeZone
     */
    public Date getGMTTime( TimeZone timeZone, Date time );
    
    /**
     * Get passed milliseconds at target TimeZone converted from the passed milliseconds at source TimeZone
     * @param sourceTimeZone source TimeZone which sourceTimeMillis happens
     * @param sourceTimeMillis source milliseconds which passed at sourceTimeZone
     * @param targetTimeZone target TimeZone
     * @return milliseconds passed at target TimeZone
     */
    public long getTimeMillis( TimeZone sourceTimeZone, long sourceTimeMillis, TimeZone targetTimeZone );
    
    /**
     * Get Time at target TimeZone converted from the time at source TimeZone
     * @param sourceTimeZone source TimeZone which sourceTimeMillis happens
     * @param sourceTime source time at sourceTimeZone
     * @param targetTimeZone target TimeZone
     * @return time at target TimeZone
     */
    public Date getTime( TimeZone sourceTimeZone, Date sourceTime, TimeZone targetTimeZone );
    
}
