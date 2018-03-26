<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--------------------公共样式(开始)-----------------------%>
<link rel="stylesheet" type="text/css"	href="<%=basePath%>resources/css/bootstrap/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>resources/css/common.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>resources/css/index.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>resources/css/admin.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>resources/css/function.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>resources//css/easyui.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>resources/css/bootstrap/bootstrap-datetimepicker.min.css" />
<%--------------------公共样式(结束)-----------------------%>


<%--------------------公共js(开始)-----------------------%>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var bgObj = self.parent;
</script>
<script src="<%=basePath %>resources/js/jquery1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/index.js"></script>
<script src="<%=basePath %>resources/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/bootstrap/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/bootstrap/bootstrap-datetimepicker.fr.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript"	src="<%=basePath%>resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/b_alert.js" ></script>
<script type="text/javascript" src="<%=basePath %>resources/js/function.js" ></script>
<%--------------------公共js(结束)-----------------------%>
<title>top</title>
</head>
<body>

</body>
</html>