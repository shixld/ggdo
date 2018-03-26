package cn.ggdo.system.framework.adapter;

/**
 * 类名：AdapterFactory 
 * 版本：1.0.0
 * 用途说明：适配器工厂
 * 业务区分(服务业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              孙洪涛     初版
 */
public abstract class AdapterFactory {

	/**
	 * 连接Mail
	 * @param user  用户名
	 * @param password  密码
	 * @return 返回true 成功  false 失败
	 */
	 //public abstract boolean connctionMail(String user, String password);
	 
	 /**
	  * 发送Mail
	  * @param bean EmailBean对象
	  * @return 返回true 成功  false 失败
	  */
	 //public abstract boolean sendMail(EmailBean bean);
	 
	 /**
	  * 获取Mail
	  * @return EmailBean对象List
	  */
	 //public abstract List<EmailBean> receiveMail();

}
