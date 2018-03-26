package cn.ggdo.system.framework.tools.charater;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 类名：CharaterFormat 
 * 版本：1.0.0
 * 用途说明：日期编码
 * 业务区分(字符格式转换)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class CharaterFormat {

    /**
     * 获取昨天日期
     * 
     * @author: steve shi
     */
    public static Date getyesterday() {

        Calendar tempdate = Calendar.getInstance();
        tempdate.setTime(new Date());
        tempdate.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = tempdate.getTime();
        return yesterday;
    }

    /***************************************************************************
     * 获取当前日期
     */
    public static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date Now = new java.util.Date();
        String NDate = formatter.format(Now);
        return NDate;
    }

    /*
     * 截取日期的年月日
     */
    public static String getStrDate(String DateString) {
        return DateString.substring(0, 10);
    }

    /***************************************************************************
     * 将日期的：替换-
     */
    public static String getStrDateTime() {
        return StringTransformUtils.replace(StringTransformUtils.replace(StringTransformUtils.replace(
                getDateTime(), ":", ""), "-", ""), " ", "");
    }

    /***************************************************************************
     * 日期的大小比较
     * 
     * @param last
     * @param now
     * @return boolean
     */
    public static boolean compareTo(String last, String now) {
        try {
            DateFormat formatter = DateFormat.getDateInstance();
            Date temp1 = formatter.parse(last);
            Date temp2 = formatter.parse(now);
            if (temp1.after(temp2))
                return false;
            else if (temp1.before(temp2))
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /***************************************************************************
     * 字符串替换
     * 
     * @param source
     * @param oldString
     * @param newString
     * @return
     */
    public static String Replace(String source, String oldString,
            String newString) {
        StringBuffer output = new StringBuffer();

        int lengthOfSource = source.length(); // 源字符的长度
        int lengthOfOld = oldString.length(); // 老字符串长度

        int posStart = 0;
        int pos;

        while ((pos = source.indexOf(oldString, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));

            output.append(newString);
            posStart = pos + lengthOfOld;
        }

        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }

        return output.toString();
    }

    /***************************************************************************
     * HTML字符的替换
     * 
     * @param str
     * @return
     */
    public static String toHtmlInput(String str) {
        if (str == null)
            return null;
        String html = new String(str);
        html = Replace(html, "&", "&amp;");
        html = Replace(html, "<", "&lt;");
        html = Replace(html, ">", "&gt;");
        return html;
    }

    /***************************************************************************
     * 字符替换HTML标签
     * 
     * @param str
     * @return
     */
    public static String toHtml(String str) {
        if (str == null)
            return null;

        String html = new String(str);

        html = toHtmlInput(html);
        html = Replace(html, "\r\n", "\n");
        html = Replace(html, "\n", "<br>\n");
        html = Replace(html, "\t", "    ");
        html = Replace(html, "  ", " &nbsp;");

        return html;
    }

    /***************************************************************************
     * 字符串的替换
     * 
     * @param str
     * @return
     */
    public static String toSql(String str) {
        String sql = new String(str);
        return Replace(sql, "'", "''");
    }

    /***************************************************************************
     * 日期差
     * 
     * @param date
     * @param date1
     * @return
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /***
     * 获取毫秒
     * @param date
     * @return long
     */
    public static long getMillis(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static void main(String[] args) {
    }

}