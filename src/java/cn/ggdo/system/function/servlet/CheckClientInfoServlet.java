package cn.ggdo.system.function.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckClientInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4738470322970942605L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		String agent = request.getHeader("user-agent"); 
    	out.println(agent);  
    	out.println(System.getProperty("os.name")); //win2003竟然是win xp？ 
    	out.println(System.getProperty("os.version")); 
    	out.println(System.getProperty("os.arch")); 
    	out.println(request.getHeader("user-agent")); //返回客户端浏览器的版本号、类型 
    	out.println(request.getMethod()); //：获得客户端向服务器端传送数据的方法有get、post、put等类型 
    	out.println(request.getRequestURI()); //：获得发出请求字符串的客户端地址 
    	out.println(request.getServletPath()); //：获得客户端所请求的脚本文件的文件路径 
    	out.println(request.getServerName()); //：获得服务器的名字 
    	out.println(request.getServerPort()); //：获得服务器的端口号 
    	out.println(request.getRemoteAddr()); //：获得客户端的ip地址 
    	out.println(request.getRemoteHost()); //：获得客户端电脑的名字，若失败，则返回客户端电脑的ip地址
    	out.println(request.getProtocol()); //： 
    	out.println(request.getHeaderNames()); //：返回所有request header的名字，结果集是一个enumeration（枚举）类的实例 
    	out.println("Protocol: " + request.getProtocol()); 
    	out.println("Scheme: " + request.getScheme()); 
    	out.println("Server Name: " + request.getServerName() ); 
    	out.println("Server Port: " + request.getServerPort()); 
    	out.println("Protocol: " + request.getProtocol()); 
    	out.println("Remote Addr: " + request.getRemoteAddr()); 
    	out.println("Remote Host: " + request.getRemoteHost()); 
    	out.println("Character Encoding: " + request.getCharacterEncoding()); 
    	out.println("Content Length: " + request.getContentLength()); 
    	out.println("Content Type: "+ request.getContentType()); 
    	out.println("Auth Type: " + request.getAuthType()); 
    	out.println("HTTP Method: " + request.getMethod()); 
    	out.println("Path Info: " + request.getPathInfo()); 
    	out.println("Path Trans: " + request.getPathTranslated()); 
    	out.println("Query String: " + request.getQueryString()); 
    	out.println("Remote User: " + request.getRemoteUser()); 
    	out.println("Session Id: " + request.getRequestedSessionId()); 
    	out.println("Request URI: " + request.getRequestURI()); 
    	out.println("Servlet Path: " + request.getServletPath()); 
    	out.println("Accept: " + request.getHeader("Accept")); 
    	out.println("Host: " + request.getHeader("Host")); 
    	out.println("Referer : " + request.getHeader("Referer")); 
    	out.println("Accept-Language : " + request.getHeader("Accept-Language")); 
    	out.println("Accept-Encoding : " + request.getHeader("Accept-Encoding")); 
    	out.println("User-Agent : " + request.getHeader("User-Agent")); 
    	out.println("Connection : " + request.getHeader("Connection")); 
    	out.println("Cookie : " + request.getHeader("Cookie"));
    	out.flush();
		out.close();
	}
}
