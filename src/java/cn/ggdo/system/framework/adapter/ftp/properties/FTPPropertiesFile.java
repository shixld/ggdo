package cn.ggdo.system.framework.adapter.ftp.properties;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import cn.ggdo.system.framework.readconfig.ResourceUtil;

/**
 * 类名：FTPPropertiesFile
 * 版本：1.0.0
 * 用途说明：FTP文件处理
 * 业务区分(FTP文件处理)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class FTPPropertiesFile {

	public static final String FTP_HOST;
	public static final String FTP_PORT;

	public static final String FTP_USER;
	public static final String FTP_PASSWORD;
	public static final String FTP_FILE_PATH;

	static {
		ResourceUtil config = new ResourceUtil(
				"/adapterProperties/ftp.properties");

		FTP_HOST = config.getProPerties("ftp.host");
		FTP_PORT = config.getProPerties("ftp.port");
		FTP_USER = config.getProPerties("ftp.user");
		FTP_PASSWORD = config.getProPerties("ftp.password");
		FTP_FILE_PATH = config.getProPerties("file.path");
	}

	/***
	 * 获取配置文件的信息
	 * @param fileName
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
