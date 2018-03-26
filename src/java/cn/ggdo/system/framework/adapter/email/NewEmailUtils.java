package cn.ggdo.system.framework.adapter.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 类名：NewEmailUtils
 * 版本：1.0.0
 * 用途说明：Email发送HTML网页内容
 * 业务区分(Mail业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class NewEmailUtils {
	private static JavaMailSenderImpl senderImpl;

	public JavaMailSenderImpl getSenderImpl() {
		return senderImpl;
	}

	public void setSenderImpl(JavaMailSenderImpl senderImpl) {
		NewEmailUtils.senderImpl = senderImpl;
	}

	public static boolean sendHtmlMail(final String[] toMail, final String title, final String context) {
		boolean yesNo = false;
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(mailMessage, true, "GBK");
			messageHelper.setTo(toMail);
			messageHelper.setFrom(senderImpl.getUsername());
			messageHelper.setSubject(title);
			messageHelper.setText("<html><head></head><body><h2>" + context + "</h2></body></html>", true);
			senderImpl.send(mailMessage);
			yesNo = true;
		} catch (MessagingException e) {
			yesNo = false;
			e.printStackTrace();
		}
		return yesNo;

	}
}
