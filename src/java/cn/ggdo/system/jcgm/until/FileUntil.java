package cn.ggdo.system.jcgm.until;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 
 * @author 史晓林
 * 
 */
public class FileUntil {
	File returnfile = null;

	/**
	 * 文件读取
	 * 
	 * @param filePath
	 *            输入文件路径
	 * @param encoding
	 *            编码方式
	 * @return 文件内容
	 * @throws Exception
	 */
	public String readFile(String filePath, String encoding) throws Exception {
		InputStreamReader reader = new InputStreamReader(new FileInputStream(
				filePath), encoding);
		char[] buf = new char[1];
		StringBuffer sb = new StringBuffer();
		while ((reader.read(buf)) != -1) {
			sb.append(new String(buf));
			buf = new char[1];// 重新生成，避免和上次读取的数据重复
		}
		return sb.toString();
	}

	/**
	 * 文件写入
	 * 
	 * @param string
	 *            文件内容
	 * @param filePath
	 *            输出文件位置
	 * @param encoding
	 *            编码方式
	 * @throws Exception
	 */
	public void writerFile(String string, String filePath, String encoding)
			throws Exception {
		String folder = filePath.substring(0, filePath.lastIndexOf("/"));
		new File(folder).mkdirs();
		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(filePath), encoding);
		writer.write(string);
		writer.close();
	}

	/**
	 * 删除指定路径的文件或者文件夹
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public void deleteFileOrFolder(String filePath) {
		File rootfile = new File(filePath);
		if (rootfile.exists() && rootfile.isDirectory()) {
			File[] files = rootfile.listFiles();
			for (File file : files) {
				this.deleteFileOrFolder(file.getPath());
			}
			rootfile.delete();
		} else if (rootfile.exists()) {
			rootfile.delete();
		}
	}

	/**
	 * 删除指定路径下符合传入名称的文件或者文件夹
	 * 
	 * @param filePath
	 *            文件路径
	 * @param name
	 *            要删除的文件/文件夹的名称
	 */
	public void deleteFileOrFolderBatch(String filePath, String name) {
		File rootfile = new File(filePath);
		if (rootfile.exists() && rootfile.isDirectory()) {
			File[] files = rootfile.listFiles();
			for (File file : files) {
				this.deleteFileOrFolderBatch(file.getPath(), name);
				if (name.equals(file.getName())) {
					this.deleteFileOrFolder(file.getPath());
				}
			}
		}
	}

	/**
	 * 查询指定路径下的文件/文件夹
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            要查询的文件/文件夹的名称
	 * @return 文件对象
	 */
	public File findFile(String filePath, String fileName) {
		File rootfile = new File(filePath);
		if (rootfile.exists() && rootfile.isDirectory()) {
			File[] files = rootfile.listFiles();
			for (File file : files) {
				this.findFile(file.getPath(), fileName);
				if (fileName.equals(file.getName())) {
					returnfile = file;
					break;
				}
			}
		}
		return returnfile;
	}

	/**
	 * 根据key读取properties文件value
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String readPropertiesValue(String key) throws IOException {
		Properties props = new Properties();
		String filePath = FileUntil.class.getResource(
				"/properties/build.properties").getFile();
		InputStream in = new FileInputStream(filePath);
		props.load(in);
		in.close();
		try {
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
			return null;
		}
	}

	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname
	 *            键名
	 * @param keyvalue
	 *            键值
	 */
	public void updateProperties(Map<String, String> map) throws IOException {
		Properties props = new Properties();
		InputStream in = FileUntil.class
				.getResourceAsStream("/properties/build.properties");
		props.load(in);
		String filePath = FileUntil.class.getResource(
				"/properties/build.properties").getFile();
		OutputStream fos = new FileOutputStream(filePath);
		try {
			Iterator<Entry<String, String>> ite = map.entrySet().iterator();
			while (ite.hasNext()) {
				Entry<String, String> entry = ite.next();
				if (entry.getKey() != null && entry.getValue() != null)
					props.setProperty(entry.getKey(), entry.getValue());
			}
			props.store(fos, "Update more value");
		} catch (Exception e) {
			e.printStackTrace();
		}
		in.close();
		fos.flush();
		fos.close();
	}
}
