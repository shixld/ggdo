<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>首页</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>
<base href="<%=basePath%>" />
<script language="javascript" type="text/javascript">
	 var basePath = "<%=basePath%>";
</script>
<link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css" />
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/index.css" />
<script type="text/javascript" src="resources1/js/jquery1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="resources1/js/main.js?<%=Math.random() * 1000%>"></script>
</head>
<script type="text/javascript" type="text/javascript">
    	
		function logout(){
			 //window.location=basePath+"Admin/loginAction!loginOut.action";
		}
		function openBgJs(){
		   $("#common_alert_bg").show();
		}
		function closeBgJs(){
		   $("#common_alert_bg").hide(); 
		}
    </script>

<body class="main-body">
	<div class="contion" style="overflow:hidden">
	<!--导航栏START-->
			<div class="heard navbar-fixed-top">
				<div class="hearedlogo">
					<div class="logo">
						<img src="resources1/images/homePage/logo.png" />
					</div>
					<div class="tab-div">
						<ul id="topMenu" class="type-ul">
							<li class="cli"><a>首页</a><input type="hidden" value="1"></li>
							<!--  <li class="li-border"><a>生成功能</a><input type="hidden" value="2"></li>
							<li class="li-border"><a>配置管理</a><input type="hidden" value="3"></li> -->
						</ul>
						<div class="out" onclick="logout()">
							<img src="resources/images/homePage/out.png" class="out-img" /><span class="out-font">退出</span>
						</div>
					</div>
				</div>
			</div>
		<!--中间内容部分START-->
		<div class="midd">
			<!--左边列表STRAT-->
			<div class="left-div">
				<ul id="leftMenu" class="typetwo-ul">
					<li class="clik-twotype">功能菜单</li>
					<li>功能菜单</li>
					<li>功能菜单</li>
					<li>功能菜单</li>
				</ul>
			</div>
			<!--左边列表END-->
			<!--右边start-->
			<div class="main-right-div" style="z-index: 10001;">
				<iframe name="right" id="rightMain" onload="Javascript:SetCwinHeight();" src="role/lookForAll.action" frameborder="no" scrolling="none" width="100%" height="100%" allowtransparency="true"></iframe>
			</div>
			<!--右边end-->

		</div>
		<!--中间内容部分END-->
		
		
	</div>
	 <div id="common_alert_bg" style=" position: fixed;top: 0;right: 0;bottom: 0;left: 0;z-index: 10000;background-color: #000000;opacity: 0.6;filter: alpha(opacity=60);display: none;"></div>
</body>
</html>
