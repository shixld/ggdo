package cn.ggdo.system.framework.lang;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 类名：PinyinUtil 
 * 版本：1.0.0
 * 用途说明：汉语拼音工具类
 * 业务区分(基础工具)
 * 依赖 pingyin4j.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class PinyinUtil {

	private PinyinUtil() {
	}

	/**
	 * 转换单个汉字为第一个拼音
	 * 
	 * @param c
	 */
	public static String hanzi2Pinyin(final char c, final HanyuPinyinCaseType caseType, final HanyuPinyinToneType toneType, final HanyuPinyinVCharType vCharType, final HanyuPinyinOutputFormat format) {
		HanyuPinyinOutputFormat format1;
		if (format == null) {
			HanyuPinyinCaseType caseType1 = caseType == null ? HanyuPinyinCaseType.LOWERCASE : caseType;
			HanyuPinyinToneType toneType1 = toneType == null ? HanyuPinyinToneType.WITHOUT_TONE : toneType;
			HanyuPinyinVCharType vCharType1 = vCharType == null ? HanyuPinyinVCharType.WITH_V : vCharType;
			format1 = new HanyuPinyinOutputFormat();
			format1.setCaseType(caseType1);
			format1.setToneType(toneType1);
			format1.setVCharType(vCharType1);
		} else {
			format1 = format;
		}
		String[] pinyin = null;
		try {
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format1);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		if (pinyin == null) {
			return null; // 如果c不是汉字，toHanyuPinyinStringArray会返回null
		}
		return pinyin[0]; // 只取一个发音，如果是多音字，仅取第一个发音
	}

	/**
	 * 转换单个汉字的多个拼音
	 * 
	 * @param c
	 */
	public static String[] hanzi2Pinyins(final char c, final HanyuPinyinCaseType caseType, final HanyuPinyinToneType toneType, final HanyuPinyinVCharType vCharType) {
		HanyuPinyinOutputFormat format1 = new HanyuPinyinOutputFormat();
		HanyuPinyinCaseType caseType1 = caseType == null ? HanyuPinyinCaseType.LOWERCASE : caseType;
		HanyuPinyinToneType toneType1 = toneType == null ? HanyuPinyinToneType.WITHOUT_TONE : toneType;
		HanyuPinyinVCharType vCharType1 = vCharType == null ? HanyuPinyinVCharType.WITH_V : vCharType;
		format1 = new HanyuPinyinOutputFormat();
		format1.setCaseType(caseType1);
		format1.setToneType(toneType1);
		format1.setVCharType(vCharType1);
		String[] pinyin = null;
		try {
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format1);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		if (pinyin == null) {
			return null; // 如果c不是汉字，toHanyuPinyinStringArray会返回null
		}
		return pinyin;
	}

	/**
	 * 转换字符串为拼音串
	 * 
	 * @param str
	 * @param caseType
	 * @param toneType
	 */
	public static String string2Pinyin(final String str, final HanyuPinyinCaseType caseType, final HanyuPinyinToneType toneType, final HanyuPinyinVCharType vCharType, final String split) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(caseType);
		format.setToneType(toneType);
		format.setVCharType(vCharType);
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0, size = str.length(); i < size; i++) {
			tempPinyin = hanzi2Pinyin(str.charAt(i), caseType, toneType, vCharType, format);
			if (tempPinyin == null) {
				sb.append(str.charAt(i)).append(split); // 如果str.charAt(i)非汉字，则保持原样
			} else {
				sb.append(tempPinyin).append(split);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取汉字串拼音首字母
	 */
	public static String string2PinyinShort(final String str, final HanyuPinyinCaseType caseType) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(caseType);
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0, size = str.length(); i < size; i++) {
			tempPinyin = hanzi2Pinyin(str.charAt(i), caseType, null, null, format);
			if (tempPinyin == null) {
				sb.append(str.charAt(i)); // 如果str.charAt(i)非汉字，则保持原样
			} else {
				sb.append(tempPinyin.charAt(0));
			}
		}
		return sb.toString();
	}

	// public static void main(String[] args) throws UnsupportedEncodingException {
	// String x = "你好啊中国，哈哈, 绿色";
	// // System.out.println(cn2Spell(x, HanyuPinyinToneType.WITH_TONE_MARK));
	// System.out.println(hanzi2Pinyins('单', HanyuPinyinCaseType.LOWERCASE, HanyuPinyinToneType.WITH_TONE_NUMBER, null)[1]);
	// System.out.println(string2PinyinShort(x, HanyuPinyinCaseType.LOWERCASE));
	// System.out.println(string2Pinyin(x, HanyuPinyinCaseType.LOWERCASE, HanyuPinyinToneType.WITHOUT_TONE, HanyuPinyinVCharType.WITH_U_UNICODE, " "));
	// }

}
