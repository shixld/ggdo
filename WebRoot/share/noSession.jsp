<%@ page language="java" contentType="text/html; charset=GB2312" pageEncoding="gb2312" isELIgnored="false"%>
<%@page import="java.util.Enumeration"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
		<link href="resources/css/frameRight/content.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="resources/js/jquery-1.4.4.js"></script> 
		<script type="text/javascript" src="resources/js/frameRight/layout.js"></script>
		<script type="text/javascript" src="resources/js/validate.js"></script>
		<script language="javascript" type="text/javascript">
		</script>
		<title>��δ��½��Ự�ѳ�ʱ</title>
	</head>

	<body>
		<div>
			<div class="fl" style="width:231px"><img src="resources/images/frameRight/404_01.jpg" width="231" height="253" /></div>
			<div class="fl" style="width:501px">
		        <div><img src="resources/images/frameRight/hhcs.gif" /></div>
		        <div style="float:right; padding:5px 0 0 0"><a href="Hr/login/login.jsp"><img src="resources/images/frameRight/cxdl01.jpg"  onmouseover="this.src='resources/images/frameRight/cxdl02.jpg'" onmouseout="this.src='resources/images/frameRight/cxdl01.jpg'"/></a></div>
		  </div>
		</div>
	</body>
</html>