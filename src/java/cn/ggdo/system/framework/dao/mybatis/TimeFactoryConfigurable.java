package cn.ggdo.system.framework.dao.mybatis;

/**
 * 类名：TimeFactoryConfigurable 
 * 版本：1.0.0
 * 用途说明：配置时间的工厂
 * 业务区分(MyBatis框架)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public interface TimeFactoryConfigurable {
	
	public TimeFactory getTimeFactory();
	
	public void setTimeFactory(TimeFactory TimeFactory);
	
}

