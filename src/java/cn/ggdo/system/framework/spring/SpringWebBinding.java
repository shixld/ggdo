package cn.ggdo.system.framework.spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import cn.ggdo.system.framework.constants.ConstantDateFormatTypes;

/**
 * 类名：SpringWebBinding 
 * 版本：1.0.0
 * 用途说明：spring mvc form 类型转换
 * 业务区分(Spring)
 * 依赖spring.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SpringWebBinding implements WebBindingInitializer {
	/**
	 * form 类型转换
	 */
	public final void initBinder(final WebDataBinder binder, final WebRequest request) {
		// 1. 使用spring自带的CustomDateEditor
		SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantDateFormatTypes.YYYY_MM_DD);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		// 2. 自定义的PropertyEditorSupport
		// binder.registerCustomEditor(Date.class, new DateConvertEditor());
		// 字符串trim
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		// 对具体字段进行转换
	}
}
