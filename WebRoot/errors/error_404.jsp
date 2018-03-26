<%@ page language="java" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<base href="<%=basePath%>">
<title>出错了404</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<link rel="shortcut icon" href="resources/imgs/common/favicon.ico" />
<link rel="stylesheet" type="text/css" href="resources/css/errors.css" />
</head>
<body>
	<div class="ErrorContent"
		style="width: 1000px; margin: auto; text-align: center; position: relative;">
		<img src="resources/imgs/common/error.gif" />
		<div id="Word">出错了！ 404</div>
		<div id="Time">00:00:00</div>
		<div id="Date">0000-00-00</div>
	</div>
	<script>
		Date.prototype.format = function(format) {
			var o = {
				"M+" : this.getMonth() + 1, //month
				"d+" : this.getDate(), //day
				"h+" : this.getHours(), //hour
				"m+" : this.getMinutes(), //minute
				"s+" : this.getSeconds(), //second
				"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
				"S" : this.getMilliseconds()
			//millisecond
			};
			if (/(y+)/.test(format))
				format = format.replace(RegExp.$1, (this.getFullYear() + "")
						.substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(format))
					format = format.replace(RegExp.$1,
							RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
									.substr(("" + o[k]).length));
			return format;
		};
		setInterval(function() {
			var time = new Date().format("hh:mm:ss");
			var date = new Date().format("yyyy-MM-dd");
			document.getElementById("Time").innerHTML = time;
			document.getElementById("Date").innerHTML = date;
		}, 1000);
	</script>
</body>
</html>