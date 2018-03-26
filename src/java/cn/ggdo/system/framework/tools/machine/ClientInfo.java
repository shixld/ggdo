package cn.ggdo.system.framework.tools.machine;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 类名：ClientInfo 
 * 版本：1.0.0
 * 用途说明：获取客户端机器相关信息
 * 业务区分(机器)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class ClientInfo {

	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),    
    // 字符串在编译时会被转码一次,所以是 "\\b"    
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)    
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"    
            +"|windows (phone|ce)|blackberry"    
            +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"    
            +"|laystation portable)|nokia|fennec|htc[-_]"    
            +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";    
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"    
            +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";    
      
    //移动设备正则匹配：手机端、平板  
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);    
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);    
        
    /** 
     * 检测是否是移动设备访问 
     *  
     * @Title: check 
     * @Date : 2016-5-7 下午01:29:07 
     * @param request request移动设备请求 
     * @return true:移动设备接入，false:pc端接入 
     */  
    public static boolean check(HttpServletRequest request){
    	 String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();    
         if(null == userAgent){    
             userAgent = "";    
         }  
        if(null == userAgent){    
            userAgent = "";    
        }    
        // 匹配    
        Matcher matcherPhone = phonePat.matcher(userAgent);    
        Matcher matcherTable = tablePat.matcher(userAgent);    
        if(matcherPhone.find() || matcherTable.find()){    
            return true;    
        } else {    
            return false;    
        }    
    } 

    
    /** 
     * 获得客户端真实IP地址
     *  
     * @Title: getIpAddr 
     * @Date : 2016-5-7 下午01:29:07 
     * @param request request移动设备请求 
     * @return String IP地址  
     */  

    //
    public String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    } 
    
    public void getOtherInfo(HttpServletRequest request){
    	String agent = request.getHeader("user-agent"); 
    	System.out.println(agent); 
    	StringTokenizer st = new StringTokenizer(agent,";"); 
    	st.nextToken(); 
    	String userbrowser = st.nextToken(); 
    	System.out.println(userbrowser); 
    	String useros = st.nextToken(); 
    	System.out.println(useros); 
    	System.out.println(System.getProperty("os.name")); //win2003竟然是win xp？ 
    	System.out.println(System.getProperty("os.version")); 
    	System.out.println(System.getProperty("os.arch")); 
    	System.out.println(request.getHeader("user-agent")); //返回客户端浏览器的版本号、类型 
    	System.out.println(request.getMethod()); //：获得客户端向服务器端传送数据的方法有get、post、put等类型 
    	System.out.println(request.getRequestURI()); //：获得发出请求字符串的客户端地址 
    	System.out.println(request.getServletPath()); //：获得客户端所请求的脚本文件的文件路径 
    	System.out.println(request.getServerName()); //：获得服务器的名字 
    	System.out.println(request.getServerPort()); //：获得服务器的端口号 
    	System.out.println(request.getRemoteAddr()); //：获得客户端的ip地址 
    	System.out.println(request.getRemoteHost()); //：获得客户端电脑的名字，若失败，则返回客户端电脑的ip地址
    	System.out.println(request.getProtocol()); //： 
    	System.out.println(request.getHeaderNames()); //：返回所有request header的名字，结果集是一个enumeration（枚举）类的实例 
    	System.out.println("Protocol: " + request.getProtocol()); 
    	System.out.println("Scheme: " + request.getScheme()); 
    	System.out.println("Server Name: " + request.getServerName() ); 
    	System.out.println("Server Port: " + request.getServerPort()); 
    	System.out.println("Protocol: " + request.getProtocol()); 
    	System.out.println("Remote Addr: " + request.getRemoteAddr()); 
    	System.out.println("Remote Host: " + request.getRemoteHost()); 
    	System.out.println("Character Encoding: " + request.getCharacterEncoding()); 
    	System.out.println("Content Length: " + request.getContentLength()); 
    	System.out.println("Content Type: "+ request.getContentType()); 
    	System.out.println("Auth Type: " + request.getAuthType()); 
    	System.out.println("HTTP Method: " + request.getMethod()); 
    	System.out.println("Path Info: " + request.getPathInfo()); 
    	System.out.println("Path Trans: " + request.getPathTranslated()); 
    	System.out.println("Query String: " + request.getQueryString()); 
    	System.out.println("Remote User: " + request.getRemoteUser()); 
    	System.out.println("Session Id: " + request.getRequestedSessionId()); 
    	System.out.println("Request URI: " + request.getRequestURI()); 
    	System.out.println("Servlet Path: " + request.getServletPath()); 
    	System.out.println("Accept: " + request.getHeader("Accept")); 
    	System.out.println("Host: " + request.getHeader("Host")); 
    	System.out.println("Referer : " + request.getHeader("Referer")); 
    	System.out.println("Accept-Language : " + request.getHeader("Accept-Language")); 
    	System.out.println("Accept-Encoding : " + request.getHeader("Accept-Encoding")); 
    	System.out.println("User-Agent : " + request.getHeader("User-Agent")); 
    	System.out.println("Connection : " + request.getHeader("Connection")); 
    	System.out.println("Cookie : " + request.getHeader("Cookie"));
    	
    }
}


