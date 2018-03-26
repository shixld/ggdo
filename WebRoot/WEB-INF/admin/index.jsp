﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>首页</title>
		
		<link rel="stylesheet" type="text/css" href="resources/js/common/scroll/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/common/yt-common.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/common/yt-frame-base.css" />
		<!--[if lt IE 9 ]><link rel="stylesheet" type="text/css" href="resources/css/yt-common-ie.min.css"/><![endif]-->
		<script type="text/javascript" src="resources/js/common/jquery.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="resources/js/common/scroll/jquery.mCustomScrollbar.concat.min.js"></script>
		<script type="text/javascript" src="resources/js/common/yt-option.js"></script>
		<script type="text/javascript" src="resources/js/common/yt-common.js"></script>
		<script type="text/javascript" src="resources/js/common/yt-frame.js"></script>

	</head>

	<body>
		<div id="yt-index-head-nav">
			<div class="head-top-logo nav-float">
				<img src="resources/images/common/yt_logo.png">
			</div>
			<div class="head-top-nav nav-float">
				<ul class="head-top-ul">
					<div style="clear: both;"></div>
				</ul>
			</div>
			<div class="head-top-user nav-float" style="float: right;">
				<div class="head-top-via nav-float"><img class="user-via" src=""></div>
				<div class="head-top-name nav-float"><span id="top-name"></span><img class="name-load" src="resources/images/common/name_load.png"></div>
				<div style="clear: both;"></div>
				<!--<div class="head-top-name-button">退出</div>-->
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="yt-index-bottom-nav">
			<table style="width: 100%;" class="index-cont-talbe">
				<tr>
					<td valign="top" class="table-left-nav">
						<div id="yt-index-left-nav">
							<div id="left-menu-cnt">

								<div id="nav-resource">
									<ul id="menu-list">

									</ul>
								</div>
							</div>
							
						</div>
					</td>
					<td valign="top">
						<div id="yt-index-main-nav">

						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="menu-event">
			<img src="resources/images/common/list_open.png" />
		</div>
		<div id="CloseLogin">
			<ul>
				<li id="reqLogin"><img><span>退出登录</span></li>
			</ul>
		</div>
	</body>

</html>