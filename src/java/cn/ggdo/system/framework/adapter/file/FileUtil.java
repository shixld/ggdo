package cn.ggdo.system.framework.adapter.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

/**
 * 类名：FileUtil
 * 版本：1.0.0
 * 用途说明：文件处理
 * 业务区分(文件处理)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class FileUtil {
	private Logger logger = Logger.getLogger(getClass());

	public FileUtil(){
		
	}
	/***
	 * 目的将STRING写入到固定的文件夹中
	 * @param inStr
	 * @param path
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public void writeFile(String inStr, String path){
		try {
			path = convertPath(path);
			logger.info("Start To Write File In" + path);
			// 获得文件生成路径
			int index = path.lastIndexOf(File.separator);
			String loc = path.substring(0, index);

			File filePath = new File(loc);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(inStr);
			fileWriter.close();

		} catch (IOException ex) {
			logger.error("write string to file " + path + " error", ex);
			

		}

	}

	/**
	 * 向文件系统中写文件，文件同名会另起名称
	 * 
	 * @param is
	 * @param path
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public String writeFile(InputStream is, String path){
		return this.writeFile(is, path, true);

	}

	/**
	 * 向文件系统中写文件，文件同名会根据参数reName觉定另起名称
	 * 
	 * @param reName
	 *            当文件名相同时是否重命名。
	 * @param is输入流
	 * @param path完整的路径包括文件名
	 * @return 上传文件的文件名称
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */

	public String writeFile(InputStream is, String path, boolean reName){
		OutputStream os = null;

		try {
			path = convertPath(path);
			// 获得文件生成路径
			int index = path.lastIndexOf(File.separator);
			String loc = path.substring(0, index + 1);
			String fileName = path.substring(index + 1);
			File filePath = new File(loc);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}

			if (reName) {
				// 如果文件重名，则重命名文件
				File temp = new File(path);
				if (temp.exists()) {
					fileName = System.currentTimeMillis() + fileName;

				}

			}

			os = new FileOutputStream(loc + fileName);
			logger.info("Start To Write File In " + loc + fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];

			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {

				os.write(buffer, 0, bytesRead);
			}
			os.close();
			is.close();
			return fileName;
		} catch (FileNotFoundException e) {
			logger.error("write file error: file not found \n" + e);
			return "";
		} catch (IOException e) {
			logger.error("write file error: " + e);
			return "";
			
		}

	}

	/**
	 * 文件功能：将固定文件读出来转换为字符串
	 * 
	 * @param file
	 * @return
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public String fileReader(String file){
		String rStr = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			for (String inLine = reader.readLine(); inLine != null; inLine = reader
					.readLine()) {
				rStr = String.valueOf(rStr) + String.valueOf(inLine);

			}
		} catch (Exception ex) {
			logger.error("read str from file " + file + " error ", ex);
			
		}
		return rStr;
	}

	/**
	 * 文件功能：删除文件
	 * 
	 * @param fileSrc
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public void deleteFile(String fileSrc) {
		logger.info("start to delete file,path=" + fileSrc);
		File file = new File(fileSrc);
		file.delete();
	}

	/***
	 * 功能：替换文件路径的特殊字符
	 * @param path
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	private String convertPath(String path) {
		logger.info("start to replace the path");
		logger.info("path=" + path);
		path = path.replace('\\', File.separatorChar);
		path = path.replace('/', File.separatorChar);

		logger.info("after the replace,path=" + path);
		return path;

	}

	/**
	 * @category 获得控制台用户输入的信息
	 * 
	 * @return
	 * @throws IOException
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public String getInputMessage() throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("请输入您的命令∶");
		}
		byte buffer[] = new byte[1024];
		int count = System.in.read(buffer);
		// 最后两位为结束符，删去不要
		char[] ch = new char[count - 2];
		for (int i = 0; i < count - 2; i++)
			ch[i] = (char) buffer[i];
		String str = new String(ch);
		return str;
	}

	/**
	 * @category 以文件流的方式复制文件
	 * 
	 * @param src
	 *            文件源目录
	 * @param dest
	 *            文件目的目录
	 * @throws IOException
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++)
				out.write(buffer[i]);
		}
		in.close();
		out.close();
	}

	/**
	 * @category 文件输出示例利用PrintStream写文件
	 * @param fileName
	 *            文件名
	 *            
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	
	public void PrintStreamDemo(String fileName) {
		try {
			FileOutputStream out = new FileOutputStream(fileName);
			PrintStream p = new PrintStream(out);
			for (int i = 0; i < 10; i++)
				p.println("This is " + i + " line");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @category 文件输出示例利用StringBuffer写文件
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */
	public void StringBufferDemo(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file, true);
		for (int i = 0; i < 10000; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("这是第" + i + "行:前面介绍的各种方法都不关用,为什么总是奇怪的问题 ");
			out.write(sb.toString().getBytes("utf-8"));
		}
		out.close();
	}

	/**
	 * @category 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 *            
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {
			// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (newfile.exists())
				// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				logger.error(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		}
	}

	/**
	 * @category 转移文件目录
	 * 
	 * @param filename
	 *            文件名
	 * @param oldpath
	 *            旧目录
	 * @param newpath
	 *            新目录
	 * @param cover
	 *            若新目录下存在和转移文件具有相同文件名的文件时，是否覆盖新目录下文件，cover=true将会覆盖原文件，否则不操作
	 *            
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */
	public void changeDirectory(String filename, String oldpath,
			String newpath, boolean cover) {
		if (!oldpath.equals(newpath)) {
			File oldfile = new File(oldpath + "/" + filename);
			File newfile = new File(newpath + "/" + filename);
			// 若在待转移目录下，已经存在待转移文件
			if (newfile.exists()) {
				// 覆盖
				if (cover)
					oldfile.renameTo(newfile);
				else
					logger.error("在新目录下已经存在：" + filename);
			} else {
				oldfile.renameTo(newfile);
			}
		}
	}

	/**
	 * @category 读文件利用FileInputStream读取文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */
	public String FileInputStreamDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		while ((fis.read(buf)) != -1) {
			sb.append(new String(buf));
			// 重新生成，避免和上次读取的数据重复
			buf = new byte[1024];
		}
		return sb.toString();
	}

	/**
	 * @category 读文件利用BufferedReader读取
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */

	public String BufferedReaderDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null) {
			sb.append(temp + " ");
			temp = br.readLine();
		}
		return sb.toString();
	}


	/**
	 * @category 创建文件夹
	 * 
	 * @param path
	 *            目录
	 *            
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */
	public void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
	}

	/**
	 * @category 创建新文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 * @throws IOException
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */
	public void createFile(String path, String filename) throws IOException {
		File file = new File(path + "/" + filename);
		if (!file.exists())
			file.createNewFile();
	}

	/**
	 * @category 删除文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 *            
	 *作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public void delFile(String path, String filename) {
		File file = new File(path + "/" + filename);
		if (file.exists() && file.isFile())
			file.delete();
	}

	/**
	 * @category 递归删除文件夹
	 * 
	 * @param path
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */
	public void delDir(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isDirectory()) {
					delDir(path + "/" + tmp[i].getName());
				} else {
					tmp[i].delete();
				}
			}
			dir.delete();
		}
	}

	/**
	 * @category 递归复制文件夹
	 * 
	 * @param path
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */
	public boolean CopyDir(String path, String objectivepath) {
		File dir = new File(path);
		if (dir.exists()) {
			/* 判断目的文件夹是否存在 */
			if (!(new File(objectivepath).isDirectory())) {
				new File(objectivepath).mkdirs();
			} else {
			}
			/* 文件递归 */
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				FileReader fr;
				try {
					fr = new FileReader(path + tmp[i].getName());
					BufferedReader br = new BufferedReader(fr);
					FileWriter fw = new FileWriter(objectivepath
							+ tmp[i].getName());
					BufferedWriter bw = new BufferedWriter(fw);

					String line = br.readLine();
					while (line != null) {
						if (!line.equals("")) {
							bw.write(line + "\r\n");
						}
						line = br.readLine();
					}
					br.close();
					fr.close();
					bw.close();
					fw.close();
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
			/* 结束 */
		}
		return true;
	}

	/**
	 * 通过给定的地址和资源生成相应文件。
	 * 
	 * @param strPath
	 *            生成的文件的保存地址；
	 * @param strResource
	 *            通过URL读取指定页面后得到的源码
	 * @author shixld
	 * @time Apr 13th,2010
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 * 
	 */
	public boolean setFileNameAndUri(String strPath, String newFileName,
			String strResource) {

		try {
			File file = new File(strPath);
			// 如果必要的目录不存在，则创建
			if (!file.exists()) {
				if (!file.mkdirs()) {
					if (logger.isDebugEnabled()) {
						logger.debug("无法创建存储目录！");
					}
					return false;
				}
			}

			OutputStream outputStream = new FileOutputStream(new File(strPath
					+ newFileName));
			outputStream.write(strResource.getBytes());
			if (logger.isDebugEnabled()) {
				logger.info("已成功生成文件\"" + file.getName() + "\"\n相对路径："
						+ strPath + "\n绝对路径：" + file.getAbsolutePath());
			}
			return true;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
