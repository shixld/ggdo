package cn.ggdo.system.framework.spring;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import cn.ggdo.system.framework.spring.annotation.MethodLog;

/**
 * 类名：SpringAopLogHelper 
 * 版本：1.0.0
 * 用途说明：LogHelper
 * 业务区分(Spring)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SpringAopLogHelper {
	private final Logger log = LogManager.getLogger();

	/**
	 * 无参无返回值的方法
	 */
	public void log(final JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		MethodLog ann = method.getAnnotation(MethodLog.class);
		if (ann != null) {
			log.info("--位置：{} --方法：{} --描述：{}", joinPoint.getStaticPart(), ann.name(), ann.description());
		}
	}

	/**
	 * 有参无返回值的方法
	 */
	public void logArg(final JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		MethodLog ann = method.getAnnotation(MethodLog.class);
		if (ann != null) {
			log.info("--位置：{} --方法：{} --描述：{} --参数：{}", joinPoint.getStaticPart(), ann.name(), ann.description(), args);
		}
	}

	/**
	 * 有参并有返回值的方法
	 */
	public void logArgAndReturn(final JoinPoint joinPoint, final Object returnObj) {
		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		MethodLog ann = method.getAnnotation(MethodLog.class);
		// if (ann != null) {
		// log.info("--位置：{} --方法：{} --描述：{} --参数：{} --返回结果：{}", joinPoint.getStaticPart(), ann.name(), ann.description(), args, returnObj);
		// }
		if (ann != null) {
			log.info("--位置：{} --方法：{} --描述：{} --参数：{} --返回结果类型：{}", joinPoint.getStaticPart(), ann.name(), ann.description(), args, returnObj != null ? returnObj.getClass() : "null");
		}
	}
}
