package cn.ggdo.system.framework.exception;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.ggdo.system.framework.lang.StringUtil;

/**
 * 类名：ExceptionCode 
 * 版本：1.0.0
 * 用途说明：常量异常配置
 * 业务区分(异常)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class ExceptionCode {
	// 网络
	public static final String CODE_0000 = "0000";
	public static final String MSG_0000 = "网络异常！!";
	// 数据库操作
	public static final String CODE_1000 = "1000";
	public static final String MSG_1000 = "数据操作异常!";
	public static final String CODE_1001 = "1001";
	public static final String MSG_1001 = "字段已存在!";
	// 文件操作
	public static final String CODE_2000 = "2000";
	public static final String MSG_2000 = "文件操作异常!";
	// 其它错误
	public static final String CODE_9999 = "9999";
	public static final String MSG_9999 = "其它错误!";

	private static Map<String, String> returnCodeMap = new ConcurrentHashMap<String, String>();

	static {
		// 网络
		returnCodeMap.put(CODE_0000, MSG_0000);
		// 数据库操作
		returnCodeMap.put(CODE_1000, MSG_1000);
		returnCodeMap.put(CODE_1001, MSG_1001);
		// 文件操作
		returnCodeMap.put(CODE_2000, MSG_2000);
		// 其它错误
		returnCodeMap.put(CODE_9999, MSG_9999);
	}

	/**
	 * getMsgByCode
	 */
	public static String getMsgByCode(final String code) {
		if (StringUtil.isNullOrEmpty(code)) {
			return MSG_0000;
		} else {
			String msg = returnCodeMap.get(code);
			if (msg == null) {
				msg = code;
			}
			return msg;
		}
	}
}
