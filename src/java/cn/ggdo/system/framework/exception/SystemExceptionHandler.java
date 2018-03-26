package cn.ggdo.system.framework.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 类名：SystemExceptionHandler 
 * 版本：1.0.0
 * 用途说明：系统异常
 * 业务区分(异常)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SystemExceptionHandler implements HandlerExceptionResolver {  
  
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, 
        Object handler, Exception ex) {  
        Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex);  
        if(ex instanceof BusinessException) {  
            return new ModelAndView("business_error", model);  
        }else if(ex instanceof SystemException) {  
            return new ModelAndView("system_error", model);  
        } else {  
            return new ModelAndView("error", model);  
        }  
    }  
}
