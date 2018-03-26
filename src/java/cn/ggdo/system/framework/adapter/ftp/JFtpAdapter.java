package cn.ggdo.system.framework.adapter.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import net.sf.jftp.net.BasicConnection;
import net.sf.jftp.net.ConnectionListener;
import net.sf.jftp.net.FtpConnection;
import net.sf.jftp.net.FtpConstants;

import cn.ggdo.system.framework.adapter.AdapterFactory;
import cn.ggdo.system.framework.adapter.ftp.properties.FTPPropertiesFile;
import cn.ggdo.system.framework.lang.StringUtil;

/**
 * 类名：JFtpAdapter
 * 版本：1.0.0
 * 用途说明：FTP文件处理
 * 业务区分(FTP文件处理)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class JFtpAdapter extends AdapterFactory implements ConnectionListener {

	private boolean isThere = false;

	private FtpConnection ftpConn = null;
	private String ip;
	private String username;
	private String password;

	public JFtpAdapter(String ip) {
		this.ip = ip;
	}

	public JFtpAdapter() {
		this.ip = FTPPropertiesFile.FTP_HOST;
	}

	/**
	 * 方法名：connction 功能：根据用户名，密码链接FTP服务器
	 * 
	 * 参数一：user 用户名 参数二：password 密码 返回：能够链接返回true,不能链接返回false
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public boolean connction(String userName, String passWord) {
		this.username = userName;
		this.password = passWord;

		try {
			ftpConn = new FtpConnection(ip);

			ftpConn.addConnectionListener(this);

			ftpConn.login(username, password);

			ftpConn.binary();

			while (!isThere) {
				try {
					Thread.sleep(10);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			// logger.info("login success");
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	/**
	 * 方法名：exitConnction 功能：退出FTP服务器
	 * 
	 * 参数:无 返回：退出 返回CODE
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public void exitConnction() {
		try {
			if (ftpConn.isConnected()) {
				ftpConn.disconnect();
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 实现文件的FTP上传
	 * 
	 * @param vsUpLoadPath
	 *            //上传的路径
	 * @param vsLocalPathAndFileName
	 *            //本地路径和文件名
	 * @param vsaFileName
	 *            //上传的文件名
	 * @return String //返回实际采用的上传文件名
	 * @author yyh 2013-2-22
	 * @throws Exception
	 */
	public String upload(String vsUpLoadPath, String vsLocalPathAndFileName,
			String vsaFileName, boolean reName) throws Exception {
		String sUpLoadFileName = null;

		try {
			// 文件上传
			sUpLoadFileName = this.uploadfile(vsUpLoadPath,
					vsLocalPathAndFileName, vsaFileName, reName);
		} catch (IOException ex) {
			// logger.error(ex);
			throw ex;
		}
		return sUpLoadFileName;
	}

	/**
	 * 实现文件的FTP上传
	 * 
	 * @param vsUpLoadPath
	 *            //上传的路径
	 * @param is
	 *            //上传文件流
	 * @param vsaFileName
	 *            //上传的文件名
	 * @author yyh 2013-2-22
	 * @return
	 * @throws Exception
	 */
	public String upload(String vsUpLoadPath, InputStream is,
			String vsaFileName, boolean reName) throws Exception {
		String sUpLoadFileName = null;

		try {
			// 文件上传
			sUpLoadFileName = this.uploadfile(vsUpLoadPath, is, vsaFileName,
					reName);
		} catch (IOException ex) {
			// logger.error(ex);
			throw ex;
		}
		return sUpLoadFileName;
	}

	/**
	 * 实现文件上传的部分，不包括创建/释放连接的功能
	 * 
	 * @param vsUpLoadPath
	 *            //上传的路径
	 * @param vsLocalPathAndFileName
	 *            //本地路径和文件名
	 * @param vsaFileName
	 *            //上传的文件名
	 * @return String //返回实际采用的上传文件名
	 * @throws IOException
	 *             异常处理
	 * @author yyh 2013-2-22
	 */
	private String uploadfile(String vsUpLoadPath,
			String vsLocalPathAndFileName, String vsaFileName, boolean reName)
			throws IOException {
		java.io.File file_in = new java.io.File(vsLocalPathAndFileName); // 本地的路径

		InputStream is = new FileInputStream(file_in);
		return uploadfile(vsUpLoadPath, is, vsaFileName, reName);
	}

	/**
	 * 实现文件上传的部分，不包括创建/释放连接的功能
	 * 
	 * @param vsUpLoadPath
	 *            //上传的路径
	 * @param is
	 *            //上传文件流
	 * @param vsaFileName
	 *            //上传的文件名
	 * @param reName
	 *            是否从命名
	 * @return String //返回实际采用的上传文件名
	 * @throws IOException
	 *             异常处理
	 * @author yyh 2013-2-22
	 * @throws IOException
	 */
	public String uploadfile(String vsUpLoadPath, InputStream is,
			String vsaFileName, boolean reName) throws IOException {

		String sUpLoadFileName = "";
		// 创建目录
		try {
			makeWorkingDirectory(vsUpLoadPath);

			if (reName) {
				// 判断文件是否存在
				if (!ftpConn.rename(vsaFileName, vsaFileName)) {
					// logger.debug("File is not found! -- " + vsaFileName);
				} else {
					sUpLoadFileName = "Copy of ";
					// logger.debug("File is found !!!!!!!!!! -- " + vsaFileName);
				}
			}
			// 上传到文件服务器上的名称
			sUpLoadFileName += vsaFileName;
			// 上传文件
			// logger.info("start to upload file !!!!!!!!!!!!");
			ftpConn.binary();
			ftpConn.upload(sUpLoadFileName, is);
			is.close();
		} catch (IOException e) {
			// logger.error("upload file error!" + e);
			throw e;
		}
		return sUpLoadFileName;
	}

	/**
	 * 
	 * @param is
	 * @param vsaFileName
	 * @param reName
	 * @return
	 * @throws IOException
	 */
	public String uploadfile(InputStream is, String vsaFileName, boolean reName)
			throws IOException {

		String sUpLoadFileName = "";

		try {

			if (reName) {
				// 判断文件是否存在
				if (!ftpConn.rename(vsaFileName, vsaFileName)) {

					// logger.debug("File is not found! -- " + vsaFileName);
				} else {
					sUpLoadFileName = "Copy of ";

					// logger.debug("File is found !!!!!!!!!! -- " +
					// vsaFileName);
				}
			}
			// 上传到文件服务器上的名称
			sUpLoadFileName += vsaFileName;

			// 上传文件
			ftpConn.binary();
			ftpConn.upload(sUpLoadFileName, is);
			is.close();
		} catch (IOException e) {
			throw e;

		}
		return sUpLoadFileName;
	}

	/**
	 * 创建目录
	 * 
	 * @param vsUpLoadPath
	 * @throws IOException
	 */
	public void makeWorkingDirectory(String vsUpLoadPath) throws IOException {
		String path[] = convertUpLoadPath(vsUpLoadPath);
		for (int i = 0; i < path.length; i++) {
			String currentDir = path[i];
			if (!currentDir.equals("")) {
				// 判断目录是否存在

				// 判断路径是否创建成功

				if (!ftpConn.chdir(currentDir)) {
					// logger.debug("Not Found Directory!!!!");

					// 创建目录
					boolean createDir = ftpConn.mkdir(currentDir);
					if (createDir) {
						boolean setWorkingDir = ftpConn.chdir(currentDir);
						if (setWorkingDir) {
						}
					}
				} else {
				}

			}
		}

	}

	public void removeFileOrDir(String dir) throws IOException {
		String path[] = convertUpLoadPath(dir);
		for (int i = 0; i < path.length; i++) {
			String currentDir = path[i];
			if (!currentDir.equals("")) {
				// 判断目录是否存在
				// 判断路径是否创建成功
				if (!ftpConn.chdir(currentDir)) {
					if (currentDir.equals(path[path.length - 1])) {
						ftpConn.removeFileOrDir(currentDir);
						// int num = ftpConn.removeFileOrDir(currentDir);
					}

				} else {
					// logger.debug("Found Directory!!!!");
				}

			}

		}

	}

	// ----------------------------下载--------------------------------------
	/**
	 * 下载时首先进入到文件所在目录
	 * 
	 * @author liuqing
	 * @param vsUpLoadPath
	 *            //文件所在路径
	 */
	private void changeWorkingDirectory(String vsUpLoadPath) throws IOException {
		String path[] = convertUpLoadPath(vsUpLoadPath);
		// 重置路径

		for (int i = 0; i < path.length; i++) {
			String currentDir = path[i];
			if (!currentDir.equals("")) {
				// 判断目录是否存在

				if (!ftpConn.chdir(currentDir)) {
					// 目录不存在
					// logger.debug("Not Found Directory!!!!");
					return;
				} else {
					// logger.debug("Found Directory!!!!");

				}

			}

		}

	}

	/**
	 * 下载服务,向输出流写从ftp服务器获取的流默认不指定下载文件名，
	 * 
	 * @param vsUpLoadPath
	 * @param vsaFileName
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public boolean download(String vsUpLoadPath, String vsaFileName,
			HttpServletResponse response) throws IOException {

		return download(vsUpLoadPath, vsaFileName, null, response);

	}

	/**
	 * 下载服务,向输出流写从ftp服务器获取的流
	 * 
	 * @param vsUpLoadPath
	 *            //文件所在路径
	 * @param vsaFileName
	 *            //文件名
	 * @param saveAsFileName
	 *            //下载时指定的文件名
	 * @param response
	 * @return
	 * @author yyh
	 * @throws IOException
	 */
	public boolean download(String vsUpLoadPath, String vsaFileName,
			String saveAsFileName, HttpServletResponse response)
			throws IOException {
		boolean result = false;
		try {

			// 连接服务器
			ftpConn.binary();
			changeWorkingDirectory(vsUpLoadPath);
			// 判断当前目录文件是否存在
			if (!ftpConn.rename(vsaFileName, vsaFileName)) {
				// 文件不存在
				// logger.debug("File is not found! -- " + vsaFileName);

			} else {
				String isoFilename = "";
				response.setContentType("application/x-msdownload");
				// 当需要指定下载文件名称时使用
				if (!StringUtil.isBlank(saveAsFileName)) {

					isoFilename = saveAsFileName;
				} else {
					isoFilename = vsaFileName;
				}

				response.setHeader("Content-Disposition",
						"attachment; filename=\""
								+ StringUtil.getISO(isoFilename) + "\"");

				response.setHeader("Accept-ranges", "bytes");

				OutputStream os = ((HttpServletResponse) response)
						.getOutputStream();
				InputStream is = ftpConn.getDownloadInputStream(vsaFileName);
				// FileOutputStream fos=new FileOutputStream("c:\\xx.doc");

				byte[] bytes = new byte[1024];
				int c;
				while ((c = is.read(bytes)) != -1) {
					os.write(bytes, 0, c);
					// fos.write(bytes, 0, c);
				}
				is.close();
				os.close();
				// fos.close();
			}
		} catch (IOException e) {
			// logger.error("下载失败！", e);
		}
		return result;

	}

	/*
	 * 从指定的ftp服务器上传下文件的输入流，以上传到另一的ftp上
	 */

	/**
	 * 删除服务器上的文件
	 * 
	 * @author liuqing 2005-11-02
	 * @param vsUpLoadPath
	 * @param vsaFileName
	 * @return
	 */
	public boolean deleteFile(String vsUpLoadPath, String vsaFileName) {
		boolean result = false;
		try {
			ftpConn.binary();
			changeWorkingDirectory(vsUpLoadPath);
			// 判断当前目录文件是否存在
			if (!ftpConn.rename(vsaFileName, vsaFileName)) {
				// 文件不存在
				// logger.debug("File is not found! -- " + vsaFileName);

			} else {

				result = FtpConstants.REMOVE_SUCCESSFUL == ftpConn
						.removeFileOrDir(vsaFileName);

			}
		} catch (IOException e) {
			// logger.error("删除附件错误！", e);
		}
		return result;
	}

	/**
	 * 处理上传路径
	 * 
	 * @param vsUpLoadPath
	 * @return 返回路径数组
	 */
	private String[] convertUpLoadPath(String vsUpLoadPath) {
		vsUpLoadPath = StringUtil.replace(vsUpLoadPath, File.separator, "/");
		vsUpLoadPath = StringUtil.replace(vsUpLoadPath, "//", "/");

		if (vsUpLoadPath.charAt(0) == '/') {
			vsUpLoadPath = vsUpLoadPath.substring(1);
		}
		if (vsUpLoadPath.endsWith("/")) {
			vsUpLoadPath = vsUpLoadPath.substring(0, vsUpLoadPath.length() - 1);

		}
		return vsUpLoadPath.split("/");
	}

	/***
	 * 功能描述：获取文件的扩展名
	 * 参数一：oriFileName 需要判断的文件 
	 * 
	 * 返回：扩展名
	 * @param oriFileName
	 * @return
	 */
	public static String getFileExtendName(String oriFileName) {
		if (StringUtil.isBlank(oriFileName)) {
			return "file.file";

		}
		String extendName = "";
		int index = oriFileName.lastIndexOf(".");
		if (index > -1) {
			extendName = oriFileName.substring(index);

		}
		return extendName;

	}
	
	public void updateRemoteDirectory(BasicConnection con) {
	}

	public void connectionInitialized(BasicConnection con) {
		isThere = true;
	}

	public void updateProgress(String file, String type, long bytes) {
	}

	public void connectionFailed(BasicConnection con, String why) {
	}

	public void actionFinished(BasicConnection con) {
	}


}
