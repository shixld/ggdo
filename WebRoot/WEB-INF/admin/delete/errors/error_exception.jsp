<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>内部服务器异常</title>
		<jsp:include flush="true" page="/share/top.jsp" />
		<script language="javascript" type="text/javascript">
           
        </script>
	</head>

	<body>
		
		<div class="contion" style="z-index: 1040;">
           <div class="right-div">页面错误
		    </div>
		</div>
	</body>
</html>