package cn.ggdo.system.function.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseAction {

	private static final long serialVersionUID = 1L;

	private PageInfo pageInfo;

	private String pageIndexs;

	private String parentId;
	// page show message
	protected String pageMsg;
	// exception msg
	protected String errorMsg;

	private HttpServletRequest servletRequest;

	private HttpServletResponse servletResponse;

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getPageIndexs() {
		if (pageIndexs == null) {
			pageIndexs = "1";
		}
		return pageIndexs;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public String getPageMsg() {
		return pageMsg;
	}

	protected HttpServletRequest getRequest() {
		return servletRequest;
	}

	protected HttpServletResponse getResponse() {
		return servletResponse;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setPageIndexs(String pageIndexs) {
		this.pageIndexs = pageIndexs;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public void setPageMsg(String pageMsg) {
		this.pageMsg = pageMsg;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @Description: AJAX输出 <b>json</b> 串 格式:<b> text/html</b>
	 * @param 直接输出
	 *            Json--->客户端
	 * @return String
	 */
	public String ajaxJson(String jsonString, HttpServletResponse response) {
		return ajax(jsonString, "text/html", response);
	}

	/**
	 * @Description: AJAX输出 <b>文本</b> 格式:<b>text/plain</b>
	 * @param text
	 *            文本
	 * @return String
	 */
	public String ajaxText(String text, HttpServletResponse response) {
		return ajax(text, "text/plain", response);
	}

	/**
	 * @Description: AJAX输出 <b>html</b> 格式:<b>text/html</b>
	 * @param html
	 * @return String
	 */
	public String ajaxHtml(String html, HttpServletResponse response) {
		return ajax(html, "text/html", response);
	}

	/**
	 * 
	 * 
	 * @Description: AJAX输出
	 * 
	 * @param content
	 * @param type
	 * @return String
	 * @throws
	 */
	public String ajax(String content, String type, HttpServletResponse response) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
