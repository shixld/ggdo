package cn.ggdo.system.framework.adapter.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 类名：Receiver.java 
 * 版本：1.0.0
 * 用途说明：实现及时消息接收
 * 业务区分(MQ消息总线业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class Receiver {
	/**
	 * 方法启动
	 * 
	 * @param args
	 */
	public void ReceiverMessage(BeanActiveMessages beanActiveMessages) {
		// 连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory;
		// JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// 一个发送或接收消息的线程
		Session session;
		// 消息的目的地;消息发送给谁.
		Destination destination;
		// 消费者，消息接收者
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory (
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue(beanActiveMessages.getQueueName());
			consumer = session.createConsumer(destination);
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TextMessage message = (TextMessage) consumer.receive(100000);
				if (null != message) {
					System.out.println("收到消息" + message.getText());
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection){
					connection.close();
				}
			} catch (Throwable ignore) {
			}
		}
	}
}