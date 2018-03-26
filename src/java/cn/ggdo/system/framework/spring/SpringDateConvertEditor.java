package cn.ggdo.system.framework.spring;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import jodd.util.StringUtil;

import cn.ggdo.system.framework.constants.ConstantDateFormatTypes;
import cn.ggdo.system.framework.lang.DateUtil;

/**
 * 类名：SpringDateConvertEditor 
 * 版本：1.0.0
 * 用途说明：转换日期
 * 业务区分(Spring)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SpringDateConvertEditor extends PropertyEditorSupport {
	private String formatString;

	public SpringDateConvertEditor() {
		this.formatString = ConstantDateFormatTypes.YYYY_MM_DD;
	}

	public SpringDateConvertEditor(final String dateformatStr) {
		this.formatString = dateformatStr;
	}

	/** Date -> String */
	@Override
	public final String getAsText() {
		if (getValue() == null) {
			return "";
		}
		return DateUtil.dateToString((Date) getValue(), formatString);
	}

	/** String -> Date */
	@Override
	public final void setAsText(final String text) {
		if (!StringUtil.isBlank(text)) {
			setValue(null);
		} else {
			setValue(DateUtil.stringToDate(text, formatString));
		}
	}
}
