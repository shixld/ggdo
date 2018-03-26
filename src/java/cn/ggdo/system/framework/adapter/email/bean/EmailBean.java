package cn.ggdo.system.framework.adapter.email.bean;

import java.util.List;

/**
 * 类名：EmailBean 
 * 版本：1.0.0
 * 用途说明：Email的属性对象实体
 * 业务区分(Mail业务)
 * 履历：
 * 序号       日期               修改版本       更新者      内容
 *   1      2013-02-26          V1.0.0       史晓林     初版
 */
public class EmailBean {
	// UUID邮件的唯一标识
	private String UUID;
	// 发送邮件人员
	private String sendMan;
	// 发送邮件
	private String sendEmail;
	// 接收邮件者
	private String receiveEmailByTo;
	// 抄送人员mail
	private String receiveEmailByCc;
	// 密送人员mail
	private String receiveEmailByBcc;
	// 邮件标题
	private String title;
	// 邮件标题
	private String folder;
	// 发送日期
	private String sendDate;
	// 附件地址
	private List<String> saveAttchPath;
	// 邮件体
	private String bodytext;
	// 是否已读
	private boolean isseen;
	// 邮件优先级
	private String Priority;
	// 邮件是否需要回复
	private boolean isReplySign;
	/**
	 * 获取发送的邮件人员
	 * @return
	 */
	public String getSendEmail() {
		return sendEmail;
	}
	
	/**
	 * 设定发送的邮件人员
	 * @param sendEmail
	 */
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	/**
	 * 获取接收邮件人员
	 * @return
	 */
	public String getReceiveEmailByTo() {
		return receiveEmailByTo;
	}

	/**
	 * 设定接收邮件人员
	 * @param receiveEmailByTo
	 */
	public void setReceiveEmailByTo(String receiveEmailByTo) {
		this.receiveEmailByTo = receiveEmailByTo;
	}

	/**
	 * 获取接收邮件抄送人员
	 * @return
	 */
	public String getReceiveEmailByCc() {
		return receiveEmailByCc;
	}

	/**
	 * 设定接收邮件抄送人员
	 * @param receiveEmailByCc
	 */
	public void setReceiveEmailByCc(String receiveEmailByCc) {
		this.receiveEmailByCc = receiveEmailByCc;
	}

	/**
	 * 获取接收邮件密送人员
	 * @return
	 */
	public String getReceiveEmailByBcc() {
		return receiveEmailByBcc;
	}

	/**
	 * 设定接收邮件密送人员
	 * @param receiveEmailByBcc
	 */
	public void setReceiveEmailByBcc(String receiveEmailByBcc) {
		this.receiveEmailByBcc = receiveEmailByBcc;
	}

	/**
	 * 获取邮件标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设定邮件标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取邮件发送日期
	 * @return
	 */
	public String getSendDate() {
		return sendDate;
	}

	/**
	 *  设定邮件发送日期
	 * @param sendDate
	 */
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * 获取邮件附件的路径
	 * @return
	 */
	public List<String> getSaveAttchPath() {
		return saveAttchPath;
	}

	/**
	 * 设定邮件附件路径
	 * @param saveAttchPath
	 */
	public void setSaveAttchPath(List<String> saveAttchPath) {
		this.saveAttchPath = saveAttchPath;
	}

	/**
	 * 获取邮件体
	 * @return
	 */
	public String getBodytext() {
		return bodytext;
	}

	/**
	 * 设定邮件体
	 * @param bodytext
	 */
	public void setBodytext(String bodytext) {
		this.bodytext = bodytext;
	}

	/**
	 * 获取UUID
	 * @return
	 */
	public String getUUID() {
		return UUID;
	}

	/**
	 * 设定UUID
	 * @param uUID
	 */
	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getSendMan() {
		return sendMan;
	}

	public void setSendMan(String sendMan) {
		this.sendMan = sendMan;
	}

	public boolean getIsseen() {
		return isseen;
	}

	public void setIsseen(boolean isseen) {
		this.isseen = isseen;
	}

	public String getPriority() {
		return Priority;
	}

	public void setPriority(String priority) {
		Priority = priority;
	}

	public boolean isReplySign() {
		return isReplySign;
	}

	public void setReplySign(boolean isReplySign) {
		this.isReplySign = isReplySign;
	}
}