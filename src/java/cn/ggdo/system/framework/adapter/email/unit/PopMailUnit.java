package cn.ggdo.system.framework.adapter.email.unit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import cn.ggdo.system.framework.adapter.email.bean.EmailBean;
import cn.ggdo.system.framework.adapter.email.properties.MailPropertiesFile;

/**
 * 类名：PopMailUnit
 * 版本：1.0.0
 * 用途说明：pop协议对于邮件的发送和接收
 * 业务区分(Mail业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21              V1.0.0      史晓林      初版
 */
public class PopMailUnit {
	private Logger logger = Logger.getLogger(getClass());
	private Store store;

	private String pophost;
	private int popport;
	private String smtphost;
	private String smtpauth;
	private String filepath;

	/**
	 * 方法名：EmailAdapter
	 * 功能：构造函数
	 * 
	 * 参数：无差数
	 * 返回：无返回类型
	 * 
	 * 作者：史晓林
	 * 创建时间：2013-02-24
	 * 修改时间 2013-02-24
	 */
	public PopMailUnit() {
		smtphost = MailPropertiesFile.MAIL_SMTP_HOST;
		smtpauth = MailPropertiesFile.MAIL_AUTH;
		pophost = MailPropertiesFile.MAIL_POP3_HOST;
		popport = Integer.parseInt(MailPropertiesFile.MAIL_POP3_PORT);
		filepath = MailPropertiesFile.MAIL_ATTACHMENT_PATH_DIR;

	}

	/**
	 * 方法名：EmailAdapter
	 * 功能：构造函数
	 * 
	 * 参数一：stmpHost   STMP的服务信息
	 * 参数二：smtpAuth   	是否需要权限验证
	 * 参数三：pop3Host   	POP3的服务信息
	 * 参数四：pop3Port   POP3端口
	 * 返回：无返回类型
	 * 
	 * 作者：史晓林
	 * 创建时间：2013-02-24
	 * 修改时间 2013-02-24
	 */
	public PopMailUnit(String stmpHost, String smtpAuth, String pop3Host,
			int pop3Port, String filePath) {

		// STMP的服务信息
		smtphost = stmpHost;
		// 是否需要权限验证
		smtpauth = smtpAuth;
		// POP3的服务信息
		pophost = pop3Host;
		// POP3端口
		popport = pop3Port;
		// 属性的设定
		filepath = filePath;

	}

	/**
	 * 接收邮件
	 */
	public List<EmailBean> receive(String user, String password)
			throws Exception {
		List<EmailBean> list = new ArrayList<EmailBean>();
		// 准备连接服务器的会话信息
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtphost);
		props.setProperty("mail.smtp.auth", smtpauth);
		Session session = Session.getDefaultInstance(props, null);

		URLName urlname = new URLName("pop3", pophost, popport, null, user,
				password);

		store = session.getStore(urlname);
		store.connect();

		// 获得收件箱 
		Folder folder = store.getFolder("INBOX");
		/* Folder.READ_ONLY：只读权限
		 * Folder.READ_WRITE：可读可写（可以修改邮件的状态）
		 */
		folder.open(Folder.READ_WRITE); //打开收件箱 
		//一个邮箱地址对应多个邮件的 UID，以后在收取每封邮件的时候判断该邮件的UID是否已经在本地保存
		FetchProfile profile = new FetchProfile();
		profile.add(UIDFolder.FetchProfileItem.UID);

		// 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数 
		//System.out.println("未读邮件数: " + folder.getUnreadMessageCount()); 

		// 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0 
		//System.out.println("删除邮件数: " + folder.getDeletedMessageCount()); 
		//System.out.println("新邮件: " + folder.getNewMessageCount()); 

		// 获得收件箱中的邮件总数 
		// System.out.println("邮件总数: " + folder.getMessageCount()); 

		// 得到收件箱中的所有邮件,并解析 
		Message[] messages = folder.getMessages();
		folder.fetch(messages, profile);

		list = parseMessage(messages);

		//释放资源 
		folder.close(true);
		store.close();

		return list;
	}

	/**
	 * 解析邮件
	 * @param messages 要解析的邮件列表
	 */
	public List<EmailBean> parseMessage(Message... messages)
			throws MessagingException, IOException {
		List<EmailBean> list = new ArrayList<EmailBean>();
		if (messages == null || messages.length < 1)
			throw new MessagingException("未找到要解析的邮件!");

		// 解析所有邮件 
		for (int i = 0, count = messages.length; i < count; i++) {
			MimeMessage msg = (MimeMessage) messages[i];
			EmailBean bean = new EmailBean();
			logger.info("---解析第" + msg.getMessageNumber() + "封邮件--- ");
			logger.info("邮件大小：" + msg.getSize() * 1024 + "kb");
			//主题
			bean.setTitle(getSubject(msg));
			//发件人
			bean.setSendEmail(getFrom(msg));
			//收件人
			bean.setReceiveEmailByTo(getReceiveAddress(msg, null));
			// 发送时间
			bean.setSendDate(getSentDate(msg, null));
			//判断是否已读
			bean.setIsseen(isSeen(msg));
			//邮件优先级
			bean.setPriority(getPriority(msg));
			//是否需要回执
			bean.setReplySign(isReplySign(msg));

			boolean isContainerAttachment = isContainAttachment(msg);
			logger.info("是否包含附件：" + isContainerAttachment);
			if (isContainerAttachment) {
				String uploadDir = filepath;
				File fUploadDir = new File(uploadDir);
				if (!fUploadDir.exists()) {
					if (!fUploadDir.mkdirs()) {

					}
				}
				List<String> filePath = new ArrayList<String>();
				filePath = saveAttachment(msg, uploadDir, filePath); //保存附件 
				bean.setSaveAttchPath(filePath);
			}

			StringBuffer content = new StringBuffer(30);
			getMailTextContent(msg, content);
			//邮件正文

			bean.setBodytext(content.length() > 100 ? content.substring(0, 100)
					+ "..." : content.toString());
			list.add(bean);
			
		}
		return list;
	}

	/**
	 * 获得邮件主题
	 * @param msg 邮件内容
	 * @return 解码后的邮件主题
	 */
	public static String getSubject(MimeMessage msg)
			throws UnsupportedEncodingException, MessagingException {
		return MimeUtility.decodeText(msg.getSubject());
	}

	/**
	 * 获得邮件发件人
	 * @param msg 邮件内容
	 * @return 姓名 <Email地址>
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public static String getFrom(MimeMessage msg) throws MessagingException,
			UnsupportedEncodingException {
		String from = "";
		Address[] froms = msg.getFrom();
		if (froms.length < 1)
			throw new MessagingException("没有发件人!");

		InternetAddress address = (InternetAddress) froms[0];
		String person = address.getPersonal();
		if (person != null) {
			person = MimeUtility.decodeText(person) + " ";
		} else {
			person = "";
		}
		from = person + "<" + address.getAddress() + ">";

		return from;
	}

	/**
	 * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
	 * <p>Message.RecipientType.TO  收件人</p>
	 * <p>Message.RecipientType.CC  抄送</p>
	 * <p>Message.RecipientType.BCC 密送</p>
	 * @param msg 邮件内容
	 * @param type 收件人类型
	 * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
	 * @throws MessagingException
	 */
	public static String getReceiveAddress(MimeMessage msg,
			Message.RecipientType type) throws MessagingException {
		StringBuffer receiveAddress = new StringBuffer();
		Address[] addresss = null;
		if (type == null) {
			addresss = msg.getAllRecipients();
		} else {
			addresss = msg.getRecipients(type);
		}

		if (addresss == null || addresss.length < 1)
			throw new MessagingException("没有收件人!");
		for (Address address : addresss) {
			InternetAddress internetAddress = (InternetAddress) address;
			receiveAddress.append(internetAddress.toUnicodeString())
					.append(",");
		}

		receiveAddress.deleteCharAt(receiveAddress.length() - 1); //删除最后一个逗号 

		return receiveAddress.toString();
	}

	/**
	 * 获得邮件发送时间
	 * @param msg 邮件内容
	 * @return yyyy年mm月dd日 星期X HH:mm
	 * @throws MessagingException
	 */
	public static String getSentDate(MimeMessage msg, String pattern)
			throws MessagingException {
		Date receivedDate = msg.getSentDate();
		if (receivedDate == null)
			return "";

		if (pattern == null || "".equals(pattern))
			pattern = "yyyy年MM月dd日 E HH:mm ";

		return new SimpleDateFormat(pattern).format(receivedDate);
	}

	/**
	 * 判断邮件中是否包含附件
	 * @param msg 邮件内容
	 * @return 邮件中存在附件返回true，不存在返回false
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static boolean isContainAttachment(Part part)
			throws MessagingException, IOException {
		boolean flag = false;
		if (part.isMimeType("multipart/*")) {
			MimeMultipart multipart = (MimeMultipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if (disp != null
						&& (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp
								.equalsIgnoreCase(Part.INLINE))) {
					flag = true;
				} else if (bodyPart.isMimeType("multipart/*")) {
					flag = isContainAttachment(bodyPart);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("application") != -1) {
						flag = true;
					}

					if (contentType.indexOf("name") != -1) {
						flag = true;
					}
				}

				if (flag)
					break;
			}
		} else if (part.isMimeType("message/rfc822")) {
			flag = isContainAttachment((Part) part.getContent());
		}
		return flag;
	}

	/** 
	 * 判断邮件是否已读  www.2cto.com
	 * @param msg 邮件内容 
	 * @return 如果邮件已读返回true,否则返回false 
	 * @throws MessagingException  
	 */
	public static boolean isSeen(MimeMessage msg) throws MessagingException {
		return msg.getFlags().contains(Flags.Flag.SEEN);
	}

	/**
	 * 判断邮件是否需要阅读回执
	 * @param msg 邮件内容
	 * @return 需要回执返回true,否则返回false
	 * @throws MessagingException
	 */
	public static boolean isReplySign(MimeMessage msg)
			throws MessagingException {
		boolean replySign = false;
		String[] headers = msg.getHeader("Disposition-Notification-To");
		if (headers != null)
			replySign = true;
		return replySign;
	}

	/**
	 * 获得邮件的优先级
	 * @param msg 邮件内容
	 * @return 1(High):紧急  3:普通(Normal)  5:低(Low)
	 * @throws MessagingException 
	 */
	public static String getPriority(MimeMessage msg) throws MessagingException {
		String priority = "普通";
		String[] headers = msg.getHeader("X-Priority");
		if (headers != null) {
			String headerPriority = headers[0];
			if (headerPriority.indexOf("1") != -1
					|| headerPriority.indexOf("High") != -1)
				priority = "紧急";
			else if (headerPriority.indexOf("5") != -1
					|| headerPriority.indexOf("Low") != -1)
				priority = "低";
			else
				priority = "普通";
		}
		return priority;
	}

	/**
	 * 获得邮件文本内容
	 * @param part 邮件体
	 * @param content 存储邮件文本内容的字符串
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void getMailTextContent(Part part, StringBuffer content)
			throws MessagingException, IOException {
		//如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断 
		boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
		if (part.isMimeType("text/*") && !isContainTextAttach) {
			content.append(part.getContent().toString());
		} else if (part.isMimeType("message/rfc822")) {
			getMailTextContent((Part) part.getContent(), content);
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				getMailTextContent(bodyPart, content);
			}
		}
	}

	/** 
	 * 保存附件 
	 * @param part 邮件中多个组合体中的其中一个组合体 
	 * @param destDir  附件保存目录 
	 * @throws UnsupportedEncodingException 
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public List<String> saveAttachment(Part part, String destDir,
			List<String> saveAttchPath) throws UnsupportedEncodingException,
			MessagingException, FileNotFoundException, IOException {
		if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent(); //复杂体邮件 
			//复杂体邮件包含多个邮件体 
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				//获得复杂体邮件中其中一个邮件体 
				BodyPart bodyPart = multipart.getBodyPart(i);
				//某一个邮件体也有可能是由多个邮件体组成的复杂体 
				String disp = bodyPart.getDisposition();
				if (disp != null
						&& (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp
								.equalsIgnoreCase(Part.INLINE))) {
					InputStream is = bodyPart.getInputStream();
					saveFile(is, destDir, decodeText(bodyPart.getFileName()));
					saveAttchPath.add(destDir + "/" + bodyPart.getFileName());
				} else if (bodyPart.isMimeType("multipart/*")) {
					saveAttchPath = this.saveAttachment(bodyPart, destDir,
							saveAttchPath);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("name") != -1
							|| contentType.indexOf("application") != -1) {
						saveFile(bodyPart.getInputStream(), destDir,
								decodeText(bodyPart.getFileName()));
						saveAttchPath.add(destDir + "/"
								+ bodyPart.getFileName());
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttchPath = saveAttachment((Part) part.getContent(), destDir,
					saveAttchPath);
		}
		return saveAttchPath;
	}

	/** 
	 * 读取输入流中的数据保存至指定目录 
	 * @param is 输入流 
	 * @param fileName 文件名 
	 * @param destDir 文件存储目录 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	private static void saveFile(InputStream is, String destDir, String fileName)
			throws FileNotFoundException, IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(new File(destDir + fileName)));
		int len = -1;
		while ((len = bis.read()) != -1) {
			bos.write(len);
			bos.flush();
		}
		bos.close();
		bis.close();
	}

	/**
	 * 文本解码
	 * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
	 * @return 解码后的文本
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeText(String encodeText)
			throws UnsupportedEncodingException {
		if (encodeText == null || "".equals(encodeText)) {
			return "";
		} else {
			return MimeUtility.decodeText(encodeText);
		}
	}
}
