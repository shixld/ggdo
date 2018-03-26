package cn.ggdo.system.framework.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jodd.props.Props;

/**
 * 类名：PropsUtil 
 * 版本：1.0.0
 * 用途说明：properties 配置文件操作工具类
 * 业务区分(IO处理工具)
 * 依赖 fastJson.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class PropsUtil {
	private PropsUtil() {
		throw new Error("工具类不能实例化！");
	}

	private static Map<String, Props> props = new HashMap<String, Props>();

	/**
	 * 读取properties文件参数
	 * 
	 * @param key
	 * @param propFilePath
	 *            propFile文件名 如system.properties
	 * @return 值
	 */
	public static String getValue(final String key, final String propFilePath) {
		Props prop = null;
		if (props.get(propFilePath) != null) {
			prop = props.get(propFilePath);
		} else {
			prop = new Props();
			try {
				prop.load(PropsUtil.class.getResourceAsStream("/" + propFilePath));
				props.put(propFilePath, prop);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop.getValue(key);
	}

	/**
	 * 设置properties文件参数
	 * 
	 * @param key
	 * @param propFile
	 *            propFile文件名 如system.properties
	 */
	public static void setValue(final String key, final String value, final String propFile) {
		Props prop = null;
		if (props.get(propFile) != null) {
			prop = props.get(propFile);
		} else {
			prop = new Props();
			try {
				prop.load(new File(propFile));
				props.put(propFile, prop);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		prop.setValue(key, value);
	}
}
