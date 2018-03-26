package cn.ggdo.system.framework.io;

import jodd.util.HtmlDecoder;

/**
 * 类名：HtmlUtil 
 * 版本：1.0.0
 * 用途说明：html工具类
 * 业务区分(IO处理工具)
 * 依赖 jodd.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class HtmlUtil {

	private HtmlUtil() {
		throw new Error("工具类不能实例化");
	}

	/**
	 * decode
	 * 
	 * @param html
	 *            html
	 * @return String
	 */
	public static String decode(final String html) {
		return HtmlDecoder.decode(html);
	}

	// ////////////其他 HtmlEncoder
}
