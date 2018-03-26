package cn.ggdo.system.framework.tools.basePath;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 类名：BasePathUtil 
 * 版本：1.0.0
 * 用途说明：获取项目路径
 * 业务区分(项目路径)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class BasePathUtil {
	
	/**
	 * 获取项目路径
	 * @auther steve shi
	 * @date 2016-12-4 下午02:53:25
	 * @return
	 */
	public static  String getBasePath(){
		 HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest(); 
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath;
	}
	/**
	 * 获取项目路径  http://localhost:8080
	 * @auther steve shi
	 * @date 2016-12-4 下午02:53:25
	 * @return
	 */
	public static  String getBasePathNotProject(){
		 HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest(); 
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		return basePath;
	}
}
