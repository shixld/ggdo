package cn.ggdo.system.framework.adapter.jdbc.properties;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import cn.ggdo.system.framework.readconfig.ResourceUtil;

/**
 * 类名：DbPropertiesFile
 * 版本：1.0.0
 * 用途说明：DB处理
 * 业务区分(DB处理)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class DbPropertiesFile {
	
	public static final String DB_NAME;
	public static final String DB_DRIVER;
	
	public static final String DB_USER;
	public static final String DB_PASSWORD;
	public static final String DB_URL;

	
	
	static{
		ResourceUtil config = new ResourceUtil("/adapterProperties/db.properties");
		
		DB_NAME = config.getProPerties("db_name");
		DB_DRIVER = config.getProPerties("db_driver");
		DB_USER = config.getProPerties("db_user");
		DB_PASSWORD = config.getProPerties("db_password");
		DB_URL = config.getProPerties("db_url");
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
	public static String getFileExpandedName(String filename){
		
		String ext = "";
		if(filename != null){
			int index = filename.lastIndexOf(".");
			if(index > -1){
				
				return filename.substring(index);
			}
		}
		return ext;
	}

}