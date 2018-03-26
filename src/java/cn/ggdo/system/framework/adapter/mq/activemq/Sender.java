package cn.ggdo.system.framework.adapter.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 类名：Sender.java 
 * 版本：1.0.0
 * 用途说明：实现及时消息发送
 * 业务区分(MQ消息总线业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class Sender {
	/**
	 * 方法启动
	 * 
	 * @param args
	 */
	public void SendMessage(BeanActiveMessages beanActiveMessages) {
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
				ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://localhost:61616"
				);
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue(beanActiveMessages.getQueueName());
			// 得到消息生成者【发送者】
			producer = session.createProducer(destination);
			// 设置不持久化，此处学习，实际根据项目决定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// 构造消息，此处写死，项目就是参数，或者方法获取 
			sendMessage(session, producer, beanActiveMessages);
			session.commit();
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
	
	/**
	 * 发送消息
	 * @param session
	 * @param producer
	 * @throws Exception
	 */
	public static void sendMessage(Session session, MessageProducer producer, BeanActiveMessages beanActiveMessages) throws Exception {
		for (int i = 1; i <= beanActiveMessages.getSendNumber(); i++) {
			TextMessage message = session.createTextMessage(beanActiveMessages.getMessageContext());
			// 发送消息到目的地方
			System.out.println("发送消息：" + "ActiveMq 发送的消息" + i);
			// 消息发送
			producer.send(message);
		}
	}
}