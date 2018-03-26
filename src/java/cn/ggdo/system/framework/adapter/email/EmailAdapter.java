package cn.ggdo.system.framework.adapter.email;

import java.util.ArrayList;
import java.util.List;

import cn.ggdo.system.framework.adapter.AdapterFactory;
import cn.ggdo.system.framework.adapter.email.bean.EmailBean;
import cn.ggdo.system.framework.adapter.email.properties.MailPropertiesFile;
import cn.ggdo.system.framework.adapter.email.unit.ImapMailUnit;
import cn.ggdo.system.framework.adapter.email.unit.SmtpMailUnit;

/**
 * 类名：EmailAdapter.java 
 * 版本：1.0.0
 * 用途说明：Email的适配器
 * 业务区分(Mail业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class EmailAdapter extends AdapterFactory {

	private String pophost;
	private String imaphost;
	private int popport;
	private String smtphost;
	private String smtpauth;
	private String usermail;
	private String password;

	/**
	 * 方法名：EmailAdapter 功能：构造函数
	 * 
	 * 参数：无差数 返回：无返回类型
	 * 
	 * 作者：史晓林 创建时间：2013-02-24 修改时间 2013-02-24
	 */
	public EmailAdapter() {
		// 实例化Properties类
		// props = new Properties();
		smtphost = MailPropertiesFile.MAIL_SMTP_HOST;
		smtpauth = MailPropertiesFile.MAIL_AUTH;
		pophost = MailPropertiesFile.MAIL_POP3_HOST;
		popport = Integer.parseInt(MailPropertiesFile.MAIL_POP3_PORT);
		imaphost = MailPropertiesFile.MAIL_IMAP_HOST;
		usermail = MailPropertiesFile.MAIL_USER;
		password = MailPropertiesFile.MAIL_PASSWORD;
		// props.setProperty("mail.smtp.host", smtphost);
		// props.setProperty("mail.smtp.auth", smtpauth);
		// session = Session.getDefaultInstance(props, null);
	}

	/**
	 * 方法名：EmailAdapter 功能：构造函数
	 * 
	 * 参数一：stmpHost STMP的服务信息 参数二：smtpAuth 是否需要权限验证 参数三：pop3Host POP3的服务信息
	 * 参数四：pop3Port POP3端口 参数五：imaphost IMAP的服务信息 参数六：usermail 用户邮箱信息
	 * 参数七：password 用户邮箱密码 返回：无返回类型
	 * 
	 * 作者：史晓林 创建时间：2013-02-24 修改时间 2013-02-24
	 */
	public EmailAdapter(String stmpHost, String smtpAuth, String pop3Host,
			int pop3Port, String imapHost, String userName, String passWord) {
		// 实例化Properties类
		// props = new Properties();
		// STMP的服务信息
		smtphost = stmpHost;
		// 是否需要权限验证
		smtpauth = smtpAuth;
		// POP3的服务信息
		pophost = pop3Host;
		// POP3端口
		popport = pop3Port;
		// IMAP的服务信息
		imaphost = imapHost;
		// 用户邮箱信息
		usermail = userName;
		// 用户邮箱密码
		password = passWord;
		// 属性的设定
		// props.setProperty("mail.smtp.host", smtphost);
		// props.setProperty("mail.smtp.auth", smtpauth);
		// session = Session.getDefaultInstance(props, null);
	}

	// /**
	// * 方法名：connction
	// * 功能：根据用户邮箱，密码链接邮件服务器
	// *
	// * 参数一：user 用户邮箱
	// * 参数二：password 密码
	// * 返回：能够链接返回true,不能链接返回false
	// *
	// * 作者：史晓林
	// * 创建时间：2014-02-24
	// * 修改时间 2014-02-24
	// */
	// public boolean connction(String user, String password){
	// urlname = new URLName("pop3", pophost, popport, null,
	// user, password);
	// try {
	// store = session.getStore(urlname);
	// store.connect();
	// } catch (NoSuchProviderException e) {
	// e.printStackTrace();
	// return false;
	// } catch (MessagingException e) {
	// e.printStackTrace();
	// return false;
	// }
	// return true;
	// }

	/**
	 * 方法名：sendMail 功能：根据邮件内容发送邮件
	 * 
	 * 参数一：EmailBean 发送邮件对象 返回：发送成功返回true,发送成功返回false
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public boolean sendMail(EmailBean bean) {
		SmtpMailUnit smtp = new SmtpMailUnit(smtphost, usermail, password);
		smtp.connction();
		return smtp.send(bean);
	}

	/**
	 * 方法名：receiveMail 功能：接受某一账号的邮件
	 * 
	 * 参数:无差数 返回：List<EmailBean>邮件对象
	 * 
	 * 作者：史晓林 创建时间：2013-02-24 修改时间 2013-02-24
	 */
	public List<EmailBean> receiveMail(String folderName, String saveFilePath) {
		List<EmailBean> list = new ArrayList<EmailBean>();
		ImapMailUnit imap = new ImapMailUnit(imaphost, usermail, password,
				folderName, saveFilePath);
		list = imap.receiveMail();
		for(int i = 0 ; i <list.size() ; i++)
		{ 
		    EmailBean bean = list.get(i);
		    System.out.println(bean.getTitle());
		    System.out.println(bean.getIsseen()); 
		    System.out.println(bean.getBodytext()); 
		 }

		/*
		 * PopMailUnit pop3 = new
		 * PopMailUnit(smtphost,smtpauth,pophost,popport,saveFilePath); List<EmailBean>
		 * list = new ArrayList<EmailBean>(); list =
		 * pop3.receive("shixiaolin886@163.com" , ""); for(int i = 0 ; i <
		 * list.size() ; i++){ EmailBean bean = list.get(i);
		 * System.out.println(bean.getTitle());
		 * System.out.println(bean.getIsseen()); }
		 */
		return list;
	}

	public String getPophost() {
		return pophost;
	}

	public void setPophost(String pophost) {
		this.pophost = pophost;
	}

	public String getImaphost() {
		return imaphost;
	}

	public void setImaphost(String imaphost) {
		this.imaphost = imaphost;
	}

	public int getPopport() {
		return popport;
	}

	public void setPopport(int popport) {
		this.popport = popport;
	}

	public String getSmtphost() {
		return smtphost;
	}

	public void setSmtphost(String smtphost) {
		this.smtphost = smtphost;
	}

	public String getSmtpauth() {
		return smtpauth;
	}

	public void setSmtpauth(String smtpauth) {
		this.smtpauth = smtpauth;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
