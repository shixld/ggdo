package cn.ggdo.system.framework.lang;

/**
 * 类名：ArrayUtil 
 * 版本：1.0.0
 * 用途说明：数组工具类
 * 业务区分(基础工具)
 * 依赖 jodd.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class ArrayUtil {
	private ArrayUtil() {
	}

	/**
	 * 拼接两个数组
	 */
	public static Object[] join(final Object[] a, final Object[] b) {
		return jodd.util.ArraysUtil.join(a, b);
	}

	/**
	 * Integer数组转int数组
	 */
	public static int[] valuesOf(final Integer[] params) {
		return jodd.util.ArraysUtil.values(params);
	}

	/**
	 * Long数组转long数组
	 */
	public static long[] valuesOf(final Long[] params) {
		return jodd.util.ArraysUtil.values(params);
	}

	/**
	 * int数组转Integer数组
	 */
	public static Integer[] valuesOf(final int[] params) {
		return jodd.util.ArraysUtil.valuesOf(params);
	}

	/**
	 * long数组转Long数组
	 */
	public static Long[] valuesOf(final long[] params) {
		return jodd.util.ArraysUtil.valuesOf(params);
	}

	/**
	 * 判断是否包含
	 */
	public static boolean contains(final int[] array, final int value) {
		return jodd.util.ArraysUtil.contains(array, value);
	}

	/**
	 * 判断是否包含
	 */
	public static boolean contains(final long[] array, final long value) {
		return jodd.util.ArraysUtil.contains(array, value);
	}

	/**
	 * 判断是否包含
	 */
	public static boolean contains(final double[] array, final double value) {
		return jodd.util.ArraysUtil.contains(array, value);
	}

	/**
	 * 判断是否包含
	 */
	public static boolean contains(final String[] array, final String value) {
		return jodd.util.ArraysUtil.contains(array, value);
	}

	/**
	 * 判断是否包含
	 */
	public static boolean contains(final Object[] array, final Object value) {
		return jodd.util.ArraysUtil.contains(array, value);
	}

}
