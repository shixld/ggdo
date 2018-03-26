package cn.ggdo.system.framework.adapter.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import cn.ggdo.system.framework.adapter.AdapterFactory;
import cn.ggdo.system.framework.adapter.mq.activemq.BeanActiveMessages;

/**
 * 类名：MQAdapter.java 
 * 版本：1.0.0
 * 用途说明：MQ的适配器
 * 业务区分(及时消息业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-27      V1.0.0              孙洪涛     初版
 */
public class MQAdapter extends AdapterFactory {

	/**
	 * 方法名：MQAdapter
	 * 功能：构造函数
	 * 
	 * 参数：无差数
	 * 返回：无返回类型
	 * 
	 * 作者：孙洪涛
	 * 创建时间：2013-02-24
	 * 修改时间 2013-02-24
	 */
	public MQAdapter() {

	}

	/**
	 * 方法名：connction
	 * 功能：连接工厂
	 * 
	 * 返回：能够链接返回true,不能链接返回false
	 * 
	 * 作者：孙洪涛
	 * 创建时间：2013-02-27
	 * 修改时间 2013-02-27
	 */
	@SuppressWarnings("unused")
	public boolean connction() {
		// 连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory;
		// JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// 一个发送或接收消息的线程
		Session session;
		// 消息的目的地;消息发送给谁.
		Destination destination;
		// 消息发送者
		MessageProducer producer;
		// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		return true;
	}

	

	/**
	 * 发送ActiveMQ消息
	 * @param beanActiveMessages
	 * @return
	 */
	public boolean sendAMQMessage(BeanActiveMessages beanActiveMessages) {
		// 定义返回标识
		boolean isFlag = false;
		return isFlag;
	}

	/**
	 * 接收ActiveMQ消息
	 * @return
	 */
	public boolean receiverMessage(BeanActiveMessages beanActiveMessages) {
		// 定义返回标识
		boolean isFlag = false;
		return isFlag;
	}
}