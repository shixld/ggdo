package cn.ggdo.system.function.common;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import cn.ggdo.system.framework.readconfig.ResourceUtil;

public class FilePathUtil {

	public static final String FILE_UPLOAD_REMOTE_URL;
	
	static{
		ResourceUtil config = new ResourceUtil("/properties/filePath.properties");
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
