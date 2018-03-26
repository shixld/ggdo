package cn.ggdo.system.framework.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import jodd.io.FileNameUtil;
import jodd.io.NetUtil;
import jodd.io.findfile.FindFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.ggdo.system.framework.constants.ConstantFileTypes;

/**
 * 类名：FileUtil 
 * 版本：1.0.0
 * 用途说明：文件操作工具类
 * 业务区分(IO处理工具)
 * 依赖 jodd.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class FileUtil {

	public static final int BUFFERSIZE = 2048;

	private FileUtil() {
		throw new Error("工具类不能实例化");
	}

	/**
	 * 创建父路径
	 * 
	 * @throws IOException
	 */
	public static void createParentDirs(final String path) throws IOException {
		new File(getFilePath(path)).mkdirs();
	}

	/**
	 * 从URL下载文件到本地
	 * 
	 * @throws IOException
	 */
	public static void downloadFileFromUrl(final String url, final File targetFile) throws IOException {
		NetUtil.downloadFile(url, targetFile);
	}

	/**
	 * 上传文件
	 * 
	 * @param upload
	 *            需要上传的文件
	 * @param targetPathNameWithoutSuffix
	 *            文件存放路径
	 */
	public static void uploadFile(final File upload, final String targetPathNameWithoutSuffix) {
		try {
			File file = new File(targetPathNameWithoutSuffix + "." + getSuffix(upload.getName()));
			createParentDirs(targetPathNameWithoutSuffix + "." + getSuffix(upload.getName()));
			FileUtils.copyFile(upload, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Spring MVC上传文件
	 * 
	 * @param cmFile
	 *            需要上传的文件
	 * @param targetPathNameWithoutSuffix
	 *            文件存放路径
	 * 
	 */
	public static void uploadFileSpringMVC(final CommonsMultipartFile cmFile, final String targetPathNameWithoutSuffix) {
		if (!cmFile.isEmpty()) {
			try {
				File file = new File(targetPathNameWithoutSuffix + "." + getSuffix(cmFile.getOriginalFilename()));
				createParentDirs(targetPathNameWithoutSuffix + "." + getSuffix(cmFile.getName()));
				cmFile.getFileItem().write(file); // 将上传的文件写入新建的文件中
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除一个文件或者目录
	 * 
	 * @param destFile
	 *            文件或者目录
	 * @throws IOException
	 */
	public static void delete(final File destFile) throws IOException {
		jodd.io.FileUtil.delete(destFile);
	}

	/**
	 * 清空一个目录 不删除目录本身
	 * 
	 * @param destDir
	 *            目录
	 * @throws IOException
	 */
	public static void cleanDir(final File destDir) throws IOException {
		jodd.io.FileUtil.cleanDir(destDir);
	}

	/**
	 * 重命名文件或文件夹
	 * 
	 * @param resFilePath
	 *            源文件路径
	 * @param newFileName
	 *            重命名
	 */
	public static boolean renameFile(final String resFilePath, final String newFileName) {
		String newFilePath = formatPath(getFilePath(resFilePath) + newFileName);
		File resFile = new File(resFilePath);
		File newFile = new File(newFilePath);
		return resFile.renameTo(newFile);
	}

	/**
	 * 读取文件或者目录的大小
	 * 
	 * @param distFilePath
	 *            目标文件或者文件夹
	 * @return 文件或者目录的大小，如果获取失败，则返回-1
	 */
	public static long genFileSize(final String distFilePath) {
		File distFile = new File(distFilePath);
		if (distFile.isFile()) {
			return distFile.length();
		} else if (distFile.isDirectory()) {
			return FileUtils.sizeOfDirectory(distFile);
		}
		return -1L;
	}

	/**
	 * 本地某个目录下的文件列表（不递归）
	 * 
	 * @param folder
	 *            ftp上的某个目录
	 * @param suffix
	 *            文件的后缀名（比如.mov.xml)
	 * @return 文件名称列表
	 */
	public static String[] listFilebySuffix(final String folder, final String suffix) {
		IOFileFilter fileFilter1 = new SuffixFileFilter(suffix);
		IOFileFilter fileFilter2 = new NotFileFilter(DirectoryFileFilter.INSTANCE);
		FilenameFilter filenameFilter = new AndFileFilter(fileFilter1, fileFilter2);
		return new File(folder).list(filenameFilter);
	}

	/**
	 * 遍历本地某个目录下的文件列表
	 * 
	 * @param folder
	 *            目录
	 * @param recursive
	 *            是否递归
	 * @param includeDirs
	 *            是否包含目录
	 */
	@SuppressWarnings("unchecked")
    public static Iterator<File> findFile(final String folder, final boolean recursive, final boolean includeDirs) {
		return new FindFile().setRecursive(recursive).setIncludeDirs(includeDirs).searchPath(folder).iterator();
	}

	/**
	 * 写文件到浏览器
	 * 
	 * @param inputStream
	 *            需要写的文件的文件流
	 * @param response
	 *            浏览器response
	 */
	public static void writeFileToBrowser(final InputStream inputStream, final HttpServletResponse response) {
		byte[] buff = new byte[BUFFERSIZE];
		int len = -1;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(inputStream);
			bos = new BufferedOutputStream(response.getOutputStream());
			while ((len = bis.read(buff, 0, buff.length)) != -1) {
				bos.write(buff, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param path
	 *            需要下载文件的路径
	 * @param targetName
	 *            文件新名称
	 * @param response
	 *            浏览器response
	 * @exception FileNotFoundException
	 */
	public static void download(final String path, final String targetName, final HttpServletResponse response) throws FileNotFoundException {
		// 清空response
		response.reset();
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");
		// 写明要下载的文件的大小
		response.setContentLength((int) new File(path).length());
		try {
			String targetName2 = new String(targetName.getBytes("utf-8"), "ISO8859-1");
			response.addHeader("Content-Disposition", "attachment;filename=" + targetName2);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		byte[] buff = new byte[BUFFERSIZE];
		int len = -1;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(new File(path)));
			bos = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			while ((len = bis.read(buff, 0, buff.length)) != -1) {
				bos.write(buff, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取文件类型
	 * 
	 * @param suffix
	 *            文件后缀
	 * @return 文件类型
	 */
	public static String getFileType(final String suffix) {
		if (suffix != null) {
			if (suffix.equals("jpg") || suffix.equals("png") || suffix.equals("gif") || suffix.equals("bmp")) {
				return ConstantFileTypes.FILE_TYPE_IMG;
			} else if (suffix.equals("mp4") || suffix.equals("wmv") || suffix.equals("avi") || suffix.equals("flv") || suffix.equals("rm") || suffix.equals("rmvb") || suffix.equals("mkv")) {
				return ConstantFileTypes.FILE_TYPE_VIDEO;
			} else if (suffix.equals("mp3") || suffix.equals("wav") || suffix.equals("midi")) {
				return ConstantFileTypes.FILE_TYPE_AUDIO;
			} else if (suffix.equals("doc") || suffix.equals("docx") || suffix.equals("xls") || suffix.equals("xlsx") || suffix.equals("ppt") || suffix.equals("ppts") || suffix.equals("txt") || suffix.equals("wps") || suffix.equals("et") || suffix.equals("dps")) {
				return ConstantFileTypes.FILE_TYPE_DOC;
			} else if (suffix.equals("rar") || suffix.equals("zip") || suffix.equals("tar") || suffix.equals("tgz") || suffix.equals("gz")) {
				return ConstantFileTypes.FILE_TYPE_RAR;
			} else {
				return ConstantFileTypes.FILE_TYPE_OTHER;
			}
		} else {
			return ConstantFileTypes.FILE_TYPE_OTHER;
		}
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 *            需要获取后缀的文件
	 * @return 文件后缀
	 */
	public static String getSuffix(final String fileName) {
		return FileNameUtil.getExtension(fileName);
	}

	/**
	 * 通过文件路径获取文件名
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件名称（不含后缀）
	 */
	public static String getFileNameWithoutSuffixByPath(final String path) {
		return FileNameUtil.getBaseName(path);
	}

	/**
	 * 通过文件路径获取文件名
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件名称（含后缀）
	 */
	public static String getFileNameWithSuffixByPath(final String path) {
		return FileNameUtil.getName(path);
	}

	/**
	 * 获取文件路径
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件路径（不含文件名称）
	 */
	public static String getFilePath(final String path) {
		return FileNameUtil.getFullPath(path);
	}

	/**
	 * 获取文件路径 不包含末尾的‘/’
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件路径（不含文件名称和“/”）
	 */
	public static String getFilePathNoEndSeparator(final String path) {
		return FileNameUtil.getFullPathNoEndSeparator(path);
	}

	/**
	 * 获取文件相对路径
	 * 
	 * @param path
	 *            文件路径
	 * @param basePath
	 *            需要替换掉的路径
	 * @return 文件相对路径
	 */
	public static String getFileRelativePath(final String path, final String basePath) {
		return path.replaceFirst(basePath, "");
	}

	/**
	 * 格式化文件路径，将其中不规范的分隔转换为标准的分隔符,并且去掉末尾的"/"符号。
	 * 
	 * @param path
	 *            路径
	 * @return 格式化后的路径
	 */
	public static String formatPath(final String path) {
		return FileNameUtil.normalize(path);
	}

}
