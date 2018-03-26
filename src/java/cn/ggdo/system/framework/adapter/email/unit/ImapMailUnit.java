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

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import cn.ggdo.system.framework.adapter.email.bean.EmailBean;
import cn.ggdo.system.framework.adapter.email.properties.MailPropertiesFile;

/**
 * 类名：ImapMailUnit
 * 版本：1.0.0
 * 用途说明：Imap协议对于邮件的发送和接收
 * 业务区分(Mail业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21              V1.0.0      史晓林      初版
 */
public class ImapMailUnit {

	//private Logger logger = Logger.getLogger(getClass());
	private String dateformat="yyyy-MM-dd HH:mm"; // 默认的日前显示格式
	private StringBuffer bodytext=new StringBuffer();// 存放邮件内容
	
	private Store store;
	private Folder folder;
	private Message message;
	
	private String imaphost;
	private String foldername;
	private String filepath;
	private String user;
	private String password;
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
	public ImapMailUnit(){
		imaphost = MailPropertiesFile.MAIL_IMAP_HOST;
		foldername = MailPropertiesFile.MAIL_FOLDER;
		filepath = MailPropertiesFile.MAIL_ATTACHMENT_PATH_DIR;
		user = MailPropertiesFile.MAIL_USER;
		password = MailPropertiesFile.MAIL_PASSWORD;
	}
	
	/**
	 * 方法名：EmailAdapter
	 * 功能：构造函数
	 * 
	 * 参数：imapHost   IMAP的服务信息
	 * 返回：无返回类型
	 * 
	 * 作者：史晓林
	 * 创建时间：2013-02-24
	 * 修改时间 2013-02-24
	 */
	public ImapMailUnit(String imapHost , String userName , String passWord , String folderName , String filePath ){
		// IMAP的服务信息
		imaphost = imapHost;
		foldername = folderName;
		filepath = filePath;
		user = userName;
		password = passWord;
	}
	/**
	 * 方法名：receiveMail
	 * 功能：接受某一账号的邮件
	 * 
	 * 参数:无差数
	 * 返回：List<EmailBean>邮件对象
	 * 
	 * 作者：史晓林
	 * 创建时间：2013-02-24
	 * 修改时间 2013-02-24
	 */
	public List<EmailBean> receiveMail(){
		List<EmailBean> list = new ArrayList<EmailBean>();
		try{
			// 获取默认会话
			Properties props=System.getProperties();
			Session session=Session.getDefaultInstance(props,null);
			// 使用POP3/IMAP等会话机制，连接服务器
			store=session.getStore("imap");
			store.connect(imaphost,user,password);
			
			// 获取默认文件夹
			folder=store.getDefaultFolder();
			//if(folder==null)
			//logger.error("没有缺省的folder");
			// 设置邮件夹类型
			folder=folder.getFolder(foldername);
			//if(folder==null)
			//logger.error("No imap "+foldername);
			// 使用只读方式打开收件箱
			//folder.open(Folder.READ_ONLY);
			/* Folder.READ_ONLY：只读权限
			 * Folder.READ_WRITE：可读可写（可以修改邮件的状态）
			 */
			folder.open(Folder.READ_WRITE); //打开收件箱 
			// 得到文件夹信息，获取邮件列表
			Message[] msgs=folder.getMessages();
			//logger.info(new StringBuffer(128).append(user).append(" ").append(foldername).append(" ").append("共：").append(msgs.length).append("封邮件"));
			for(int msgNum=0;msgNum<msgs.length;msgNum++){
				//logger.info(new StringBuffer(128).append("开始收取").append(user).append(" ").append(foldername).append(" ").append("第：").append(msgNum+1).append("：封邮件"));
				this.setMessage(msgs[msgNum]);
				EmailBean bean = this.getMailMessage(foldername);
				list.add(bean);
//				if(!bean.getIsseen()){
//					list.add(bean);
//				}
				
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			//logger.error(new StringBuffer(128).append("receiveError:").append(e));
		}finally{
			// 释放资源
			try{
				if(folder!=null)
					folder.close(false);
				if(store!=null)
					store.close();
			}catch(Exception e){
				//logger.error(new StringBuffer(128).append("receiveCloseError:").append(e));
			}
		}
		return list;
	}

	/**
	 * 获取邮件信息
	 * @param folder
	 */
	public EmailBean getMailMessage(String folder){
		EmailBean bean = new EmailBean();
		try{
			// 主题
			bean.setTitle(this.replaceD(this.getSubject()));
			// 发送日期
			bean.setSendDate(this.replaceD(this.getSendDate()));
			
			String info=null;
			String[] buffInfo=null;
			// 发件人
			try{
				info=this.replaceD(this.getFrom());
				if(info.indexOf("|")>0){
					buffInfo = info.split("\\|");
					// 发件人
					bean.setSendMan(buffInfo[0]);
					// 发件人地址
					bean.setSendEmail(buffInfo[1]);
				}else{
					bean.setSendMan("");
					bean.setSendEmail(info);
				}
			}catch(Exception e){
				//logger.error(new StringBuffer(128).append("doFromError:").append(e));
			}
			// 收件人
			try{
				bean.setReceiveEmailByTo(this.replaceD(this.getMailAddress("to")));
			}catch(Exception e){
				//logger.error(new StringBuffer(128).append("doFromError:").append(e));
			}
			// 抄送人
			bean.setReceiveEmailByCc(this.replaceD(this.getMailAddress("cc")));
			// 密抄送人
			bean.setReceiveEmailByBcc(this.replaceD(this.getMailAddress("bcc")));
			// 内容
			//this.getMailContent((Part)this.getMessage());
			//bean.setBodytext(this.replaceD(this.getBodyText()));
			MimeMessage msg = (MimeMessage) this.getMessage(); 
			// 内容
			StringBuffer content = new StringBuffer(30);
			getMailTextContent(msg, content);
			//邮件正文

			bean.setBodytext(content.length() > 100 ? content.substring(0, 100)
					+ "..." : content.toString());
			//判断是否已读
			bean.setIsseen(isSeen(msg));
			//邮件优先级
			bean.setPriority(getPriority(msg));
			//是否需要回执
			bean.setReplySign(isReplySign(msg));
			
            boolean isContainerAttachment = isContainAttachment(msg); 
            //logger.info("是否包含附件：" + isContainerAttachment); 
            if (isContainerAttachment) {
            	String uploadDir = filepath;
            	File fUploadDir = new File(uploadDir);
				if (!fUploadDir.exists()) {
					if (!fUploadDir.mkdirs()) {
						
					}
				}
				List<String> filePath = new ArrayList<String>();
				filePath = saveAttachment(msg, uploadDir , filePath); //保存附件 
				bean.setSaveAttchPath(filePath);
            }  
			
		}catch(Exception e){
			//logger.error(new StringBuffer(128).append("getMailMessageError:").append(e));
		}
		return bean;
	}
	
	/**
	 * 将所有的单引号替换成空
	 */
	public String replaceD(String str){
		if(str!=null&&!str.equals("")){
			str=str.replaceAll("'","");
		}
		return str;
	}
	
	/**
	 * 获取邮件主题
	 * @return String
	 */
	public String getSubject(){
		String subject="";
		try{
			subject=message.getSubject();
			String header=((MimeMessage)message).getHeader("SUBJECT")[0];
			if((header.toLowerCase().indexOf("=?"))>0)
				subject=new String((subject.getBytes("iso-8859-1")),"gb2312");
			if(subject==null)
				subject="";
		}catch(Exception e){
			//logger.error(new StringBuffer(128).append("getSubjectError:").append(e));
		}
		return subject;
	}

	/**
	 * 获取邮件发送日期
	 */
	public String getSendDate() throws Exception{
		Date sentdate=message.getSentDate();
		SimpleDateFormat format=new SimpleDateFormat(dateformat);
		return format.format(sentdate);
	}

	/**
	 * 获得发件人的地址和姓名
	 */
	public String getFrom() throws Exception{
		String from=null;
		InternetAddress address[]=null;
		String personal=null;
		String fromaddr=null;
		try{
			address=(InternetAddress[])message.getFrom();
			if(address!=null){
				from=address[0].getAddress();
				if(from==null)
					from="";
				personal=address[0].getPersonal(); // 发件人的姓名
				if(personal==null)
					personal="";
				fromaddr=personal+"|"+from; // 发件人的地址
			}else{
				fromaddr="null|null";
			}
		}catch(Exception e){
			//logger.error(new StringBuffer(128).append("getFromError:").append(e));
		}
		return fromaddr;
	}

	/**
	 * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同<br>
	 * "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
	 */
	public String getMailAddress(String type){
		StringBuffer sbMailaddr=new StringBuffer(128);
		String mailaddr="";
		String addtype=type.toUpperCase();
		InternetAddress[] address=null;
		try{
			if(addtype.equals("TO")||addtype.equals("CC")||addtype.equals("BCC")){
				if(addtype.equals("TO"))
					address=(InternetAddress[])message.getRecipients(Message.RecipientType.TO);
				else if(addtype.equals("CC"))
					address=(InternetAddress[])message.getRecipients(Message.RecipientType.CC);
				else
					address=(InternetAddress[])message.getRecipients(Message.RecipientType.BCC);
				if(address!=null){
					for(int i=0;i<address.length;i++){
						String email=address[i].getAddress();
						if(email==null)
							email="";
						else
							email=MimeUtility.decodeText(email);
						String personal=address[i].getPersonal();
						if(personal==null)
							personal="";
						else
							personal=MimeUtility.decodeText(personal);
						String compositeto=personal+"|"+email;
						// sbMailaddr.append(",").append(compositeto);
						sbMailaddr.append(compositeto);
					}
					mailaddr=sbMailaddr.substring(0);
				}
			}
		}catch(Exception e){
			//logger.error(new StringBuffer(128).append("getMailAddressError:").append(e));
		}
		return mailaddr;
	}

	/**
	 * 获得邮件正文内容
	 */
	//public String getBodyText(){
		//return bodytext.toString();
	//}
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
	 * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中<br>
	 * 解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
	 * @param part
	 */
	public void getMailContent(Part part) throws Exception{
		String contenttype=part.getContentType();
		int nameindex=contenttype.indexOf("name");
		boolean conname=false;
		if(nameindex!=-1)
			conname=true;
		if(part.isMimeType("text/plain")&&!conname){
			bodytext.append((String)part.getContent());
		}else if(part.isMimeType("text/html")&&!conname){
			bodytext.append((String)part.getContent());
		}else if(part.isMimeType("multipart/*")){
			Multipart multipart=(Multipart)part.getContent();
			int counts=multipart.getCount();
			for(int i=0;i<counts;i++){
				getMailContent(multipart.getBodyPart(i));
			}
		}else if(part.isMimeType("message/rfc822")){
			getMailContent((Part)part.getContent());
		}else{
		}
	}
	
	/**
     * 判断邮件中是否包含附件
     * @param msg 邮件内容
     * @return 邮件中存在附件返回true，不存在返回false
     * @throws MessagingException
     * @throws IOException
     */ 
    public static boolean isContainAttachment(Part part) throws MessagingException, IOException { 
        boolean flag = false; 
        if (part.isMimeType("multipart/*")) { 
            MimeMultipart multipart = (MimeMultipart) part.getContent(); 
            int partCount = multipart.getCount(); 
            for (int i = 0; i < partCount; i++) { 
                BodyPart bodyPart = multipart.getBodyPart(i); 
                String disp = bodyPart.getDisposition(); 
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) { 
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
                 
                if (flag) break; 
            } 
        } else if (part.isMimeType("message/rfc822")) { 
            flag = isContainAttachment((Part)part.getContent()); 
        } 
        return flag; 
    } 
     
    /** 
     * 判断邮件是否已读 
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
    public static boolean isReplySign(MimeMessage msg) throws MessagingException { 
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
            if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1) 
                priority = "紧急"; 
            else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1) 
                priority = "低"; 
            else 
                priority = "普通"; 
        } 
        return priority; 
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
    public List<String> saveAttachment(Part part, String destDir , List<String> saveAttchPath) throws UnsupportedEncodingException, MessagingException, 
            FileNotFoundException, IOException { 
        if (part.isMimeType("multipart/*")) { 
            Multipart multipart = (Multipart) part.getContent();    //复杂体邮件 
            //复杂体邮件包含多个邮件体 
            int partCount = multipart.getCount(); 
            for (int i = 0; i < partCount; i++) { 
                //获得复杂体邮件中其中一个邮件体 
                BodyPart bodyPart = multipart.getBodyPart(i); 
                //某一个邮件体也有可能是由多个邮件体组成的复杂体 
                String disp = bodyPart.getDisposition(); 
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) { 
                    InputStream is = bodyPart.getInputStream(); 
                    saveFile(is, destDir, decodeText(bodyPart.getFileName()));
                    saveAttchPath.add(destDir + "/" + bodyPart.getFileName());
                } else if (bodyPart.isMimeType("multipart/*")) { 
                	saveAttchPath =this.saveAttachment(bodyPart , destDir , saveAttchPath); 
                } else { 
                    String contentType = bodyPart.getContentType(); 
                    if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) { 
                        saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName())); 
                        saveAttchPath.add(destDir + "/" + bodyPart.getFileName());
                    } 
                } 
            } 
        } else if (part.isMimeType("message/rfc822")) { 
        	saveAttchPath = saveAttachment((Part) part.getContent(),destDir , saveAttchPath); 
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
    public static String decodeText(String encodeText) throws UnsupportedEncodingException { 
        if (encodeText == null || "".equals(encodeText)) { 
            return ""; 
        } else { 
            return MimeUtility.decodeText(encodeText); 
        } 
    } 
    
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
