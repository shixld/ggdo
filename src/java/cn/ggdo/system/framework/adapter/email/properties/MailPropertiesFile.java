
package cn.ggdo.system.framework.adapter.email.properties;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import cn.ggdo.system.framework.readconfig.ResourceUtil;

/**
 * 类名：MailPropertiesFile
 * 版本：1.0.0
 * 用途说明：Email的属性文件
 * 业务区分(Mail业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21              V1.0.0      史晓林      初版
 */
public class MailPropertiesFile {
	// 定义SMTP
	public static final String MAIL_SMTP_HOST;
	// 定义POP3
	public static final String MAIL_POP3_HOST;
	// 定义IMAP
	public static final String MAIL_IMAP_HOST;
	// 定义邮件权限
	public static final String MAIL_AUTH;
	// 定义邮件POP3端口
	public static final String MAIL_POP3_PORT;
	// 定义邮件附件路径
	public static final String MAIL_ATTACHMENT_PATH_DIR;
	// 定义邮件用户
	public static final String MAIL_USER;
	// 定义邮件密码
	public static final String MAIL_PASSWORD;
	// 定义邮件超时
	public static final String MAIL_TIMEOUT;
	// 定义邮件文件夹
	public static final String MAIL_FOLDER;

	static {
		// 获取指定的资源文件
		ResourceUtil config = new ResourceUtil(
				"/adapterProperties/mail.properties");
		// 取得SMTP
		MAIL_SMTP_HOST = config.getProPerties("mail.smtp.host");
		// 取得POP3
		MAIL_POP3_HOST = config.getProPerties("mail.pop3.host");
		// 取得IMAP
		MAIL_IMAP_HOST = config.getProPerties("mail.imap.host");
		// 取得权限
		MAIL_AUTH = config.getProPerties("mail.auth");
		// 取得POP3的端口号
		MAIL_POP3_PORT = config.getProPerties("mail.pop3.port");
		// 取得邮件的附件地址路径
		MAIL_ATTACHMENT_PATH_DIR = config.getProPerties("mail.attachment.path");
		// 取得邮件的用户名
		MAIL_USER = config.getProPerties("mail.username");
		// 取得邮件的密码
		MAIL_PASSWORD = config.getProPerties("mail.password");
		// 取得邮件的超时时间
		MAIL_TIMEOUT = config.getProPerties("mail.timeout");
		// 定义邮件文件夹
		MAIL_FOLDER = config.getProPerties("mail.folder");
	}

	/***
	 * 获取配置文件的信息
	 * @param fileName 文件名称
	 * @return
	 */
	public static String getFileData(String fileName) {
		try {
			File inputFile = new File(fileName);
			FileReader in = new FileReader(inputFile);
			char[] fileData = new char[(int) inputFile.length()];
			in.read(fileData);
			return String.copyValueOf(fileData);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/***
	 * 修改配置文件的信息
	 * @param fileName
	 * @param fileData
	 */
	public static void setFileData(String fileName, String fileData) {
		try {
			File outFile = new File(fileName);
			outFile = new File(outFile.getParent());
			outFile.mkdirs();
			outFile = new File(fileName);
			FileWriter out = new FileWriter(outFile);
			out.write(fileData);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获取文件名中的扩展名，没有扩展名的文件会返回空串
	 * @param filename
	 * @return 返回文件的扩展名，如.doc、.xls、.jpg等
	 */
	public static String getFileExpandedName(String filename) {
		// 定义扩展名
		String ext = "";
		if (filename != null) {
			int index = filename.lastIndexOf(".");
			if (index > -1) {
				return filename.substring(index);
			}
		}
		return ext;
	}
}