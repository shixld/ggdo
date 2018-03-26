package cn.ggdo.system.framework.adapter.mq.activemq;

/**
 * 类名：BeanActiveMessages.java 
 * 版本：1.0.0
 * 用途说明：实体数据对象
 * 业务区分(MQ消息总线业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class BeanActiveMessages {
	// Queue名称
	private String queueName;
	
	// 发送消息数
	private int sendNumber;
	
	// 消息内容
	private String messageContext;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public int getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(int sendNumber) {
		this.sendNumber = sendNumber;
	}

	public String getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(String messageContext) {
		this.messageContext = messageContext;
	}
}
