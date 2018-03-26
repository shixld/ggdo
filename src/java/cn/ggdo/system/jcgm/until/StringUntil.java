package cn.ggdo.system.jcgm.until;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author liyanqingid
 * 
 */
public class StringUntil {

	/**
	 * 驼峰处理(首字母大写)
	 * @param string 	要处理的字符串
	 * @param sign 		分隔标志
	 * @return 			首字母大写的驼峰字符串
	 */
	public static String toStandardFirstBig(String string, String sign) {
		String[] strings = string.split(sign);
		String finish = "";
		for (String s : strings) {
			String first = s.substring(0, 1);
			String last = s.substring(1, s.length());
			finish = finish + first.toUpperCase() + last;
		}
		return finish;
	}

	/**
	 * 驼峰处理(首字母小写)
	 * @param string	要处理的字符串
	 * @param sign		分隔标志
	 * @return 			首字母小写的驼峰字符串
	 */
	public static String toStandardFirstSmall(String string, String sign) {
		String[] strings = string.split(sign);
		String finish = "";
		for (String s : strings) {
			String first = s.substring(0, 1);
			String last = s.substring(1, s.length());
			finish = finish + first.toUpperCase() + last;
		}
		String finishFirst = finish.substring(0, 1).toLowerCase();
		String finishLast = finish.substring(1, finish.length());
		finish = finishFirst + finishLast;
		return finish;
	}

	/**
	 * 首字母小写
	 * 
	 * @param string 要处理的字符串
	 * @return 首字母小写的字符串
	 */
	public static String toLowerCaseFirst(String string) {
		if(string != null && string.length() > 0){
			String first = string.substring(0, 1);
			String last = string.substring(1, string.length());
			String finish = first.toLowerCase() + last;
			return finish;
		}else{
			return "";
		}
	}

	/**
	 * 批量键值对替换字符串内容 可以使用HashMap
	 * @param string 要处理的字符串
	 * @param map key:替换前的字符串 value:替换后的字符串
	 * @return 替换完成的字符串
	 */
	public static String replaceMap(String string, Map<String, String> map) {
		Iterator<Entry<String, String>> ite = map.entrySet().iterator();
		while (ite.hasNext()) {
			Entry<String, String> entry = ite.next();
			String key = entry.getKey();
			String value = entry.getValue();
			String s = string.replace(key, value);
			string = s;
		}
		return string;
	}
}
