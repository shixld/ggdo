package cn.ggdo.system.framework.readconfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * 类名：FileResourceUtil 
 * 版本：1.0.0
 * 用途说明：读取filePath.properties配置文件相关的配置
 * 业务区分(配置文件)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class FileResourceUtil {
	
	public static final String FILE_UPLOAD_LOCAL_DIR;
	public static final String FILE_UPLOAD_REMOTE_URL;
	
	static{
		ResourceUtil config = new ResourceUtil("/properties/filePath.properties");
		
		FILE_UPLOAD_LOCAL_DIR = config.getProPerties("fileUpload.LocalDir");
		FILE_UPLOAD_REMOTE_URL = config.getProPerties("fileUpload.RemoteUrl");
	}
	
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
	 * ��ȡ�ļ����е���չ��û����չ����ļ��᷵�ؿմ�
	 * @param filename
	 * @return �����ļ�����չ����.doc��.xls��.jpg��
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
