package cn.ggdo.system.framework.tools.charater;

/**
 * 类名：CharacterEncoding 
 * 版本：1.0.0
 * 用途说明：字符编码
 * 业务区分(字符格式转换)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class CharacterEncoding {

	public static String toChinese(String n) {
		try {
			if (n == null) {
				return null;
			} else {
				n = new String(n.getBytes("ISO8859_1"), "GBK");
				return CharaterFormat.toHtmlInput(n);
			}
		} catch (Exception e) {
			return n;
		}
	}

	public static String GBKToGb2312(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				s = new String(s.getBytes("GBK"), "gb2312");
				return s;

			}

		} catch (Exception e) {
			return s;

		}
	}
	
	public static String Gb2312ToGBK(String n) {
		try {
			if (n == null) {
				return null;
			} else {
				n = new String(n.getBytes("gb2312"), "GBK");
				return CharaterFormat.toHtmlInput(n);
			}
		} catch (Exception e) {
			return n;
		}
	}
}
