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
			function switchExceptionDetailShow(){
				$("#exceptionDetailDiv").toggle();
				if($("#exceptionDetailDiv").css("display") == "none")
					$("#switchImg").attr("src", "resources/images/frameRight/jt_x.gif");
				else
					$("#switchImg").attr("src", "resources/images/frameRight/jt_s.gif");
			}
		</script>
		<title>�������ڲ��쳣</title>
	</head>

	<body>
		<div>
			<div class="fl" style="width:231px"><img src="resources/images/frameRight/404_01.jpg" width="231" height="253" /></div>
		<div class="fl" style="width:530px">
		        <div><img src="resources/images/frameRight/500_02.jpg" /></div>
		        <div style="float:right; padding:5px 0 0 0"><a href="javascript:window.history.go(-1)"><img src="resources/images/frameRight/fhsy.jpg"  onmouseover="this.src='resources/images/frameRight/fhsy01.jpg'" onmouseout="this.src='resources/images/frameRight/fhsy.jpg'"/></a></div>
		  </div>
		  <div class="cl"></div>
		</div>
		<div style=" width:100%; margin:0 0 0 40px; border:1px solid #d3d3d3; background: #fff;">
			<div style="background:url(resources/images/frameRight/500_bj.jpg) repeat-x; margin:1px; font-size:14px; padding:3px 20px; color:#005891; cursor: pointer;" onclick="switchExceptionDetailShow()">
				��ϸ��Ϣ&nbsp;
				<img id="switchImg" src="resources/images/frameRight/jt_x.gif" width="10" height="11" />
			</div>
		    <div style="padding:10px; display: none;" id="exceptionDetailDiv">
		    	<font size="3" color="red"><s:property value="exception.message"/></font>
		    	<hr>
				<s:property value="exceptionStack"/>
		    </div>
		</div>
	</body>
</html>