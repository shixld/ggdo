package cn.ggdo.system.framework.spring;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * 类名：SpringUTF8StringBeanPostProcessor 
 * 版本：1.0.0
 * 用途说明：解决sping mvc 返回json中文乱码
 * 业务区分(Spring)
 * 依赖spring.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SpringUTF8StringBeanPostProcessor implements BeanPostProcessor {
	@Override
	public final Object postProcessAfterInitialization(final Object bean, final String beanName) {
		if (bean instanceof StringHttpMessageConverter) {
			List<MediaType> types = new ArrayList<MediaType>();
			types.add(new MediaType("text", "plain", Charset.forName("UTF-8")));
			((StringHttpMessageConverter) bean).setSupportedMediaTypes(types);
		}
		return bean;
	}

	@Override
	public final Object postProcessBeforeInitialization(final Object bean, final String beanName) {
		return bean;
	}
}