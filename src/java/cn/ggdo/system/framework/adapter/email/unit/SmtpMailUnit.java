package cn.ggdo.system.framework.adapter.email.unit;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.ggdo.system.framework.adapter.email.bean.EmailBean;
import cn.ggdo.system.framework.adapter.email.properties.MailPropertiesFile;

/**
 * 类名：SmtpMailUnit
 * 版本：1.0.0
 * 用途说明：Smtp协议对于邮件的发送和接收
 * 业务区分(Mail业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21              V1.0.0      史晓林      初版
 */
public class SmtpMailUnit {

	private Session session = null;
    
	// 邮件用户名(你通过哪个帐号发送邮件) sunht810423@163.com
	private String mailUser ;
    // 你通过哪个主机发送邮件 smtp.163.com
	private String host ;
    // 你所要通过的帐号的密码是多少，也就是mailUser的密码 =sunht810423!
	private String pwd;
	
	public SmtpMailUnit() {
		host = MailPropertiesFile.MAIL_SMTP_HOST;
		mailUser = MailPropertiesFile.MAIL_USER;
		pwd = MailPropertiesFile.MAIL_PASSWORD;
	}
	
	public SmtpMailUnit(String stmpHost, String user, String password){
		host = stmpHost;
		mailUser = user;
		pwd = password;
	}
	/**
     * 发送电子邮件通用类
     * 
     */
    public void connction() {
    	// 先声明一个配置文件以便存储信息
    	Properties props = new Properties();
    	// 首先说明邮件的传输协议
        props.put("mail.transport.protocol", "smtp");
        // 说明发送邮件的主机地址
        props.put("mail.smtp.host", host);
        // 说明发送邮件是否需要验证，表示自己的主机发送是需要验证的
        props.put("mail.smtp.auth", "true");
        // 说明邮件发送的端口号
        props.put("mail.smpt.port", "25");
        // session认证 getInstance()
        session = javax.mail.Session.getInstance(props,new Authenticator(){
        	public PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(mailUser, pwd);
        	}
        });
        // 这个是跟踪后台消息。打印在控制台
        session.setDebug(true);
     
    }
    
    /**
     * 
     * @param from
     * @param content
     */
    @SuppressWarnings("static-access")
	public boolean send(EmailBean bean) {
    	boolean flag = true;
    	try {
        	
        	Message msg = new MimeMessage(session);
        	// 设置发送者
        	msg.setFrom(new InternetAddress(mailUser));
            // 设置接受者
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(bean.getReceiveEmailByTo()));
            // 设置发送时间
            msg.setSentDate(new Date());
            // 设置内容的基本机制，字体等
            String htmltext = bean.getBodytext();
            msg.setContent(htmltext, "text/html;charset=UTF-8");
            // 设置发送主题
            msg.setSubject(bean.getTitle());
            // 设置邮件内容, 如果以HTML的格式发送邮件那么邮件的内容需要通过setContent来设置并且不能用setText
            msg.setText(htmltext);
//            List<String> list = bean.getSaveAttchPath();
//            for(int i = 0 ; i < list.size() ; i++){
//            	String filePath = list.get(i);
//            	String[] dir = filePath.split("\\\\");      
//            	InputStreamSource attachmentFile = (InputStreamSource) 
//            	msg.addAttachment(dir[dir.length-1], filePath);
//            }
            
            // 得到发送协议
            Transport transport = session.getTransport("smtp");
            // 与发送者的邮箱相连
            transport.connect(host, mailUser, pwd);
            // 发送消息
            transport.send(msg);
        } catch (Exception ee) {
            ee.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
