package cn.ggdo.system.framework.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cn.ggdo.system.framework.io.JsonUtil;
import cn.ggdo.system.function.common.JsonResultBean;

/**
 * 类名：SpringSimpleMappingExceptionHandler 
 * 版本：1.0.0
 * 用途说明：Spring 全局异常处理
 * .自定义SimpleMappingExceptionResolver覆盖spring的SimpleMappingExceptionResolver.复写其doResolveException(HttpServletRequest
 * request, HttpServletResponse response, Object handler, Exception
 * ex)方法，通过修改该方法实现普通异常和ajax异常的处理
 * 业务区分(异常)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SpringSimpleMappingExceptionHandler extends
		SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler,
			final Exception ex) {
		String viewName = determineViewName(ex, request);
		if (viewName != null) { // JSP格式返回
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
					.getHeader("X-Requested-With") != null && request
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else { // JSON格式返回
				try {
					response.setCharacterEncoding("utf-8");
					PrintWriter writer = response.getWriter();
					writer.write(JsonUtil.object2Json(new JsonResultBean(
							JsonResultBean.FAULT, ex.getMessage())));
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		} else {
			return null;
		}
	}
}