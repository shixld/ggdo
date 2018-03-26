package cn.ggdo.system.framework.lang;

import java.util.Locale;

/**
 * 类名：LocaleUtil 
 * 版本：1.0.0
 * 用途说明：Locale本地化工具类
 * 业务区分(基础工具)
 * 依赖 jodd.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class LocaleUtil {

	/**
	 * getLocale
	 */
	public static Locale getLocale() {
		return jodd.util.LocaleUtil.getLocale("zh", "CN");
	}

	/**
	 * getLocale
	 */
	public static Locale getLocale(final String languageCode) {
		return jodd.util.LocaleUtil.getLocale(languageCode);
	}

	/**
	 * getLocale
	 */
	public static Locale getLocale(final String language, final String country) {
		return jodd.util.LocaleUtil.getLocale(language, country);
	}

	/**
	 * getLocale
	 */
	public static Locale getLocale(final String language, final String country, final String variant) {
		return jodd.util.LocaleUtil.getLocale(language, country, variant);
	}

}
