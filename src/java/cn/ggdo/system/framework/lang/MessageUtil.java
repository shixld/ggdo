package cn.ggdo.system.framework.lang;

import org.springframework.context.MessageSource;

/**
 * 类名：MessageUtil 
 * 版本：1.0.0
 * 用途说明：LangMessageUtil
 * 业务区分(基础工具)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class MessageUtil {
	private static MessageSource messageSource = null;

	/**
	 * getMessage
	 */
	public static String getMessage(final String code) {
		if (messageSource == null) {
			return null;
		}
		return messageSource.getMessage(code, null, null, LocaleUtil.getLocale());
	}

	/**
	 * getMessage
	 */
	public static String getMessage(final String code, final Object[] args, final String defaultMessage) {
		if (messageSource == null) {
			return null;
		}
		return messageSource.getMessage(code, args, defaultMessage, LocaleUtil.getLocale());
	}
}
