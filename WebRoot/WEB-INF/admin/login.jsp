<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>登录</title>
		<link rel="stylesheet" type="text/css" href="resources/css/common/yt-common-ie.min.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/module/login/login.min.css?v=1.0" />
		<script src="resources/js/common/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="resources/js/common/jqthumb/jqthumb.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="resources/js/common/yt-option.js" type="text/javascript" charset="utf-8"></script>
		<script src="resources/js/module/login/login.min.js?v=1.0" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>

		<img class="login-bg-img" src="resources/images/module/login/login_bg.png" />

		<table class="login-table-body" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="login-body">
						<div class="form-conten-logo">
							<p><img src="resources/images/module/login/login_logo.png" /></p>
							<p><img style="margin-top: 44px;" src="resources/images/module/login/login_text.png" /></p>
						</div>
						<div class="form-conten-bg">
						</div>
						<div class="form-conten">
							<!--<img src="img/login-logo.png" />-->
							<!-- <img style="margin:47px 0 36px 0;" src="resources/images/module/login/login-title.png"/> -->
							<div class="login-title">
								登录
							</div>
							<div id="" class="login-input">
								<span><img src="resources/images/module/login/login_user.png" /></span>
								<input type="text" class="user_name" value="" id="userLoginName" name="admUser.userItcode" placeholder="请输入用户名" />
							</div>
							<div id="" class="login-input" style="margin-top: 20px;">
								<span><img src="resources/images/module/login/login_pwd.png" /></span>
								<input type="password" name="temp.password" id="password" placeholder="请输入密码" class="loginput" />
								<input type="hidden" name="admUser.userPassword" id="realpwd" />
							</div>
							<div class="button-box">
								<div class="msg-div"></div>
								<div class="login-button" onclick="checkFormq()">登录</div>
								<div class="reset-button" onclick="clickReset()">重置</div>
								<div style="clear: both;float: none;"></div>
							</div>
							<div style="clear: both;"></div>
							<div class="login_c" style="display: none">
							</div>
						</div>

					</div>
				</td>
			</tr>
		</table>

	</body>

</html>