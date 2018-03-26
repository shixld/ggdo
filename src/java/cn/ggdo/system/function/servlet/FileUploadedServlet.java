package cn.ggdo.system.function.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import cn.ggdo.system.function.common.FilePathUtil;


/**     
 *     
 * @author Steve     
 */
public class FileUploadedServlet extends HttpServlet {
	/**     
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.     
	 * @param request servlet request     
	 * @param response servlet response     
	 */
	private static final long serialVersionUID = 8609445912918697217L;
	private static Logger logger = Logger.getLogger(FileUploadedServlet.class);

	// 限制文件的上传大小      
	private int maxPostSize = 100 * 1024 * 1024;

	public FileUploadedServlet() {
		super();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	/** 
     * 上传文件功能
     *  
     * @Title: check 
     * @Date : 2016-5-7 下午01:29:07 
     * @param request request移动设备请求 
     * @return 输出流为文件网站的访问方式
     */
	@SuppressWarnings("unchecked")
	protected void processRequest(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		//指定输出流的编码方式和格式
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
	
		//获取应用服务器的物理地址，文件需要上传到上面
        String basePath="";
        String tomcatPath = request.getSession().getServletContext().getRealPath("/").toString();
        tomcatPath = tomcatPath.toString().replaceAll("\\\\","/");
        String tomcatP = tomcatPath.substring(tomcatPath.length()-1, tomcatPath.length());
        if(!tomcatP.equals("/")){
        	basePath= tomcatPath+"/";
        }else{
        	basePath = tomcatPath;
        }    
        String uploadDir =basePath;
        
        //远程访问路径
		String urlpath = FilePathUtil.FILE_UPLOAD_REMOTE_URL;
		if (uploadDir == null || urlpath == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("无法获取文件上传配置路径！");
			}
			out.println("无法获取文件上传配置路径！");
			return;
		}

		//保存文件到服务器中      
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		upload.setSizeMax(maxPostSize);

		try {
			//支持多文件上传
			List<FileItem> fileItems = upload.parseRequest(request);
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					try {
						
						String path = item.getName();
						String fileName = path.substring(path.lastIndexOf("\\")+1);
						
						//将上传的文件根据扩展名保存到ckfinder的相应目录下面
						String extName = FilePathUtil.getFileExpandedName(fileName).toLowerCase();
						String newFileName = UUID.randomUUID().toString() + extName;
						
						uploadDir += urlpath;
						File fUploadDir = new File(uploadDir);
						if (!fUploadDir.exists()) {
							if (!fUploadDir.mkdirs()) {
								if (logger.isDebugEnabled()) {
									logger.debug("无法创建存储目录！");
								}
								out.println("无法创建存储目录!");
								return;
							}
						}
						
						
						if (logger.isDebugEnabled()) {
							logger.debug("File Name is" + newFileName);
						}
						
						item.write(new File(uploadDir + newFileName));
						//返回访问文件的路径
						out.println(urlpath + newFileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		out.flush();
		out.close();
	}

	/**     
	 * Handles the HTTP <code>GET</code> method.     
	 * @param request servlet request     
	 * @param response servlet response     
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**     
	 * Handles the HTTP <code>POST</code> method.     
	 * @param request servlet request     
	 * @param response servlet response     
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**     
	 * Returns a short description of the servlet.     
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}

	public void setMaxPostSize(int maxPostSize) {
		this.maxPostSize = maxPostSize;
	}
}