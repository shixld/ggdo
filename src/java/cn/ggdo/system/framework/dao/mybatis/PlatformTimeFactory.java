package cn.ggdo.system.framework.dao.mybatis;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * 类名：PlatformTimeFactory 
 * 版本：1.0.0
 * 用途说明：配置时间的工厂
 * 业务区分(MyBatis框架)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class PlatformTimeFactory implements TimeFactory {
    public static final String DEFAULT_TIMEZONE = "GMT";
    
    public static final Locale DEFAULT_LOCALE = Locale.CHINA;
    
    private static PlatformTimeFactory _instance;
    
    private static Logger logger = Logger.getLogger(PlatformTimeFactory.class);
	
    
    private Calendar calendarStrategy;
    private TimeZone gmtTimeZone;
    private TimeZone systemTimeZone;
    private Locale systemLocale;
    
    
    /**
     * Hide default constructor and make the class be a singleton
     */
    private PlatformTimeFactory() {
        systemLocale = DEFAULT_LOCALE;
        systemTimeZone = TimeZone.getDefault();
        gmtTimeZone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);
        calendarStrategy = Calendar.getInstance(gmtTimeZone, systemLocale);
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format(
                    "SimpleTimeFactory is using system Locale [ {0} ] and system TimeZone [ {1} ]", systemLocale,
                    systemTimeZone.getID()));
        }
    }
    
    /**
     * Singleton Create method.
     * @return life-cycle-controlled instance.
     */
    public static synchronized PlatformTimeFactory i(){
      if(_instance==null){
        _instance = new PlatformTimeFactory();
      }
      return _instance;
    }

    @SuppressWarnings("static-access")
    public Calendar getCalendarInstance(TimeZone timeZone, Locale locale) {
        return calendarStrategy.getInstance(timeZone, locale);
    }
    
    public Date currentGMTTime() {
        long timeMillis = currentGMTTimeMillis();
        Calendar gmtCalendar = getCalendarInstance(gmtTimeZone, systemLocale);
        gmtCalendar.setTimeInMillis(timeMillis);
        Date retDate = gmtCalendar.getTime();
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("Current GMT time is [{0}]", retDate));
        }
        return retDate;
    }
    
    public long currentGMTTimeMillis() {
        Calendar localCalender = getCalendarInstance(systemTimeZone, systemLocale);
        long timeMillis = localCalender.getTimeInMillis();
        int offset = systemTimeZone.getOffset(timeMillis);
        timeMillis = timeMillis - offset; // Get GMT time milliseconds
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("Current GMT time is [{0}]", timeMillis));
        }
        return timeMillis;
    }
    
    public Date currentTime(TimeZone timeZone) {
        long timeMillis = currentTimeMillis(timeZone);
        Calendar cal = getCalendarInstance(timeZone, systemLocale);
        cal.setTimeInMillis(timeMillis);
        Date retDate = cal.getTime();
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("Current {0} time is [{1}]", timeZone.getID(), retDate));
        }
        return retDate;
    }
    
    public long currentTimeMillis(TimeZone timeZone) {
        long timeMillis = currentGMTTimeMillis();
        int offset = timeZone.getOffset(timeMillis);
        timeMillis = timeMillis + offset; // Get the timeZone's time
                                            // milliseconds
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("Current {0} time is [{1}]", timeZone.getID(), timeMillis));
        }
        
        return timeMillis;
    }
    
    public Calendar getCalendarStrategy() {
        return calendarStrategy;
    }
    
    @SuppressWarnings("static-access")
    public Date getGMTTime(TimeZone timeZone, Date time) {
        long timeMillis = getGMTTimeMillis(timeZone, time.getTime());
        Calendar cal = getCalendarInstance(timeZone, systemLocale);
        cal.setTimeInMillis(timeMillis);
        Date retDate = cal.getTime();
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("GMT time [{0}] is converted from {1} time [{2}]", retDate, timeZone
                    .getID(), time));
        }
        return retDate;
    }
    
    @SuppressWarnings("static-access")
    public long getGMTTimeMillis(TimeZone timeZone, long timeMillis) {
        int offset = systemTimeZone.getOffset(timeMillis);
        long gmtTimeMillis = timeMillis - offset; // Get GMT time milliseconds
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("GMT time [{0}] is converted from {1} time [{2}]", gmtTimeMillis,
                    timeZone.getID(), timeMillis));
        }
        return gmtTimeMillis;
    }
    
    @SuppressWarnings("static-access")
    public Date getTime(TimeZone sourceTimeZone, Date sourceTime, TimeZone targetTimeZone) {
        int sourceOffset = sourceTimeZone.getOffset(sourceTime.getTime());
        int targetOffset = targetTimeZone.getOffset(sourceTime.getTime());
        int diffToTargetTZ = targetOffset - sourceOffset;
        long timeMillis = sourceTime.getTime() + diffToTargetTZ;
        Calendar cal = getCalendarInstance(targetTimeZone, systemLocale);
        cal.setTimeInMillis(timeMillis);
        Date retDate = cal.getTime();
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("{0} time [{1}] is converted from {2} time [{3}]",
                    targetTimeZone.getID(), retDate, sourceTimeZone.getID(), sourceTime));
        }
        return retDate;
    }
    
    public long getTimeMillis(TimeZone sourceTimeZone, long sourceTimeMillis, TimeZone targetTimeZone) {
        int sourceOffset = sourceTimeZone.getOffset(sourceTimeMillis);
        int targetOffset = targetTimeZone.getOffset(sourceTimeMillis);
        int diffToTargetTZ = targetOffset - sourceOffset;
        long timeMillis = sourceTimeMillis + diffToTargetTZ;
        
        if(logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("{0} time [{1}] is converted from {2} time [{3}]",
                    targetTimeZone.getID(), timeMillis, sourceTimeZone.getID(), sourceTimeMillis));
        }
        return timeMillis;
    }
    
    /**
     * Set calendarStrategy from outside (DI)
     * @param calendarStrategy the calendarStrategy to set
     */
    public void setCalendarStrategy(Calendar calendarStrategy) {
        this.calendarStrategy = calendarStrategy;
    }
    
}
