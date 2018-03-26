package cn.ggdo.system.framework.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名：MethodLog 
 * 版本：1.0.0
 * 用途说明：方法级别的日志
 * 业务区分(Spring)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
	/** * 方法中文名 */
	String name() default "";

	/** * 方法描述 */
	String description() default "";
}
