$(function() {
	$yt_common.init();
});

var $yt_common = {
	common_width: 0, //公用宽
	left_width: 0, //左菜单宽度
	html_width: 0, //页面宽度
	/**
	 * 
	 * 计算框架整体间距
	 * 
	 */
	setMainWidth: function() {
		if($(window).width() < this.common_width) {
			this.html_width = this.common_width;
		} else {
			this.html_width = $(window).width();
		}
		var left = (this.html_width - 1200) / 2;
		if(left < 0) {
			left = 0;
		}
		var main_left = this.left_width + left;
		var main_width = this.html_width - main_left;
		console.log("left" + left);
		$(".left-nav").css("left", left);
		$(".main-nav").css({
			width: main_width,
			left: main_left,
			"min-width": main_width
		});
		$(".head-nav").css({
			"min-width": this.common_width
		});
	},
	/**
	 * 
	 * 初始加载方法
	 * 
	 */
	init: function() {
		$("body").append('<div id="pop-modle-alert"></div>');
		/*
		 * 
		 * 调用左侧菜单初始化方法
		 * 
		 */
		$left_menu.init();
		/*计算隐藏menu按钮的位置*/
		var menuEvent = $('#menu-event'),
			win = window,
			docu = document,
			menuShow = true,
			winHeight = 0;
		// 获取窗口高度
		if(win.innerHeight) {
			winHeight = win.innerHeight;
		} else if((docu.body) && (docu.body.clientHeight)) {
			winHeight = docu.body.clientHeight;
		}
		menuEvent.css('top', (winHeight / 2) - menuEvent.height());
		/*点击menua按钮的事件*/
		menuEvent.click(function() {
			if(menuShow) {
				//隐藏
				$('.table-left-nav').hide();
				menuEvent.css('left', '0').find('img').css('transform', 'rotateY(180deg)');
			} else {
				//显示
				$('.table-left-nav').show();
				menuEvent.css('left', '200px').find('img').css('transform', 'rotateY(0deg)');
			}
			menuShow = !menuShow;
		});
		/*退出登录按钮*/
		$('.head-top-user').hover(function() {
			$('#CloseLogin').show();
			}, function(e) {
			$('#CloseLogin').hide();
		});
		$('#CloseLogin').hover(function() {
			$('#CloseLogin').show();
		}, function(e) {
			$('#CloseLogin').hide();
		});
		/**
		 * 
		 * 头部导航横向滚动监听事件
		 * 
		 */
		$(window).scroll(function() {
			$("#yt-index-head-nav").css("left", $(window).scrollLeft() * -1);
			$(".top-button-list").css("top", $(window).scrollTop() + 80);
			$(".peoper-list-model").css("top", $(window).scrollTop());
			//选取左侧菜单固定,left值,右侧内容距左的距离减去窗体横向滚动条距离减去左菜单的宽度,减去左菜单的宽度减去左侧菜单与右侧内容间距17像素
			$("#left-menu-cnt").css("left", $("#yt-index-main-nav").offset().left - $(window).scrollLeft() - $("#left-menu-cnt").width() - 17);

		});
		$(window).resize(function() {
			//选取左侧菜单固定,left值,右侧内容距左的距离减去窗体横向滚动条距离减去左菜单的宽度,减去左菜单的宽度减去左侧菜单与右侧内容间距17像素
			$("#left-menu-cnt").css("left", $("#yt-index-main-nav").offset().left - $(window).scrollLeft() - $("#left-menu-cnt").width() - 17);
		});
		
		$("#reqLogin").click(function (){
			location.href = $yt_option.websit_path +"login.html";
		})
		
         //调用生成滚动条方法  
        $(".yt-index-main-nav").mCustomScrollbar({  
            autoHideScrollbar:true,  
            theme:"square"  
        });  
 
	},
	eventStopPageaction: function() { //阻止冒泡
		var e = arguments.callee.caller.arguments[0] || event;
		if(e && e.stopPropagation) {
			// this code is for Mozilla and Opera
			e.stopPropagation();
		} else if(window.event) {
			// this code is for IE 
			window.event.cancelBubble = true;
		}
	},
	getToken: function() {
		var cookies = document.cookie.split(";");
		var dynamicKey = "";
		if(cookies.length > 0) {
			for(var i = 0; i < cookies.length; i++) {
				var cookie = cookies[i].split("=");

				if($.trim(cookie[0]) == $yt_common.ytAclCookieKey)
					dynamicKey = unescape(cookie[1]);
			}
		}
		return dynamicKey;
	}
}
/**
 * 左侧菜单数据显示
 * 左侧菜单一级菜单,二级菜单点击切换
 * 
 */
var $left_menu = {
	menuId: "",
	twoMenuId: "",
	init: function() {
		var me = this;
		/**
		 * 判断当前浏览器
		 */
		var contentType = "application/x-www-form-urlencoded; charset=utf-8";
		if(window.XDomainRequest) {
			contentType = "text/plain";
		} //for IE8,IE9
		var systemId = $("#yt-index-head-sys select").val();
		/**
		 * 
		 * 判断当前是自定义的菜单数据;还是访问后台获取
		 * $yt_option.is_test   值为true代表自定义数据
		 * $yt_option.is_test   值为false代表请求后台数据
		 * 
		 */
		if($yt_option.is_test) {
			//1.获取一次菜单json数据集
			var data = $yt_option.menu_data;
			$("#menu-list").empty();
			//2.遍历一级菜单数据,循环追加
			$.each(data, function(i, n) {
				var className = ""
				if(i == 0) {
					className = "no-border";
				}
				var one_menu = $('<li class="one-menu ' + className + '" menuId = "' + n.pkId + '"><div class="one-menu-text"><img class="one-menu-icon" src="resources/images/common/setting.png">' + n.menuName + '</div><ul></ul></li>');
				$("#menu-list").append(one_menu);
			});
			//3.一级菜单点击事件
			$("#left-menu-cnt .one-menu div").on("click", function() {
				//判断是否被选中,true包含选中类名,清除选中样式;改变图片
				if($(this).hasClass("check")) {
					$(this).removeClass("check");
					$(this).next("ul").hide();
				} else {
					/**
					 * 一级菜单没有被选中
					 * 
					 */
					//3.1获取当前一级菜单对象
					var one_menu = $(this).parent();
					//3.2获取当前一级菜单ID
					var parentMenuId = one_menu.attr("menuId");
					//3.3获取二级菜单自定json数据集
					var data = $yt_option.menu_data_two;
					//3.4清空二级菜单数据
					one_menu.find("ul").empty();
					var two_menu = "";
					//3.5遍历二级菜单数据
					$.each(data, function(j, m) {
						//3.6判断二级菜单父级ID是否相等当前选中一级菜单ID,等于则追加数据
						if(m.parentId == parentMenuId) {
							two_menu = $('<li class="two-menu" menuUrl = "' + m.menuUrl + '"><div title="' + m.menuName + '"> ' + m.menuName + '</div></li>');
						}
						one_menu.find("ul").append(two_menu);
						if(two_menu != "") {
							//3.7二级菜单点击事件
							two_menu.off().on("click", function() {
								//3.8添加选中样式,存储ID
								$left_menu.twoMenuId = m.pkId;
								//改变二级菜单字体颜色
								$("#left-menu-cnt .two-menu").removeClass("two-menu-cli");
								$(this).addClass("two-menu-cli");
								//改变二级菜单二级图标
								me.switchTab(m.pkId, $(this).attr("menuUrl"));
								$left_menu.menuId = parentMenuId;
							});
						}
					});
					//3.9显示二级菜单部分
					one_menu.find("ul").css("display", "block");
					//3.10给一级菜单选中效果
					$("#left-menu-cnt .one-menu .check").next().hide();
					$("#left-menu-cnt .one-menu div").removeClass("check");
					$(this).addClass("check");
				}
			});
			/*初始进入一级菜单二级菜单显示*/
			var one_menu = "";
			var parentMenuId = "";
			//判断一级菜单是否选择过
			if($left_menu.menuId != "" && $left_menu.menuId != null) {
				parentMenuId = $left_menu.menuId;
				$("#left-menu-cnt .one-menu").each(function(i, n) {
					if($(n).attr("menuid") == $left_menu.menuId) {
						one_menu = $(n);
					}
				});
				one_menu.find(".one-menu-text").addClass("check");
			} else {
				one_menu = $("#left-menu-cnt .one-menu div:eq(0)").parent();
				parentMenuId = one_menu.attr("menuId");
				/*初始显示第一个一级菜单和子级*/
				$("#left-menu-cnt .one-menu div:eq(0)").addClass("check");
			}
			//根据一级菜单获取二级菜单
			var data = $yt_option.menu_data_two;
			one_menu.find("ul").empty();
			var two_menu = "";
			$.each(data, function(j, m) {
				if(m.parentId == parentMenuId) {
					two_menu = $('<li class="two-menu" twoMenuId="' + m.pkId + '" menuUrl = "' + m.menuUrl + '"><div title="' + m.menuName + '">' + m.menuName + '</div></li>');
				}
				one_menu.find("ul").append(two_menu);
				if(two_menu != "") {
					/**
					 * 二级菜单点击事件
					 * 
					 */
					two_menu.off().on("click", function() {
						//改变二级菜单字体颜色
						$("#left-menu-cnt .two-menu").removeClass("two-menu-cli");
						$(this).addClass("two-menu-cli");
						//改变二级菜单二级图标
						me.switchTab(m.pkId, $(this).attr("menuUrl"));
						$left_menu.twoMenuId = m.pkId;
					});
				}
			});
			//显示二级菜单
			one_menu.find("ul").css("display", "block");
			//判断点击选中的二级菜单ID是否为空
			if($left_menu.twoMenuId == "") {
				//初始选中第一个二级
				$("#left-menu-cnt .two-menu:eq(0)").addClass("two-menu-cli");
				$("#left-menu-cnt .two-menu:eq(0)").addClass("check");
				//改变二级菜单二级图标
				$("#left-menu-cnt .two-menu:eq(0)").find("div .menu-two-icon").attr("src", "resources/images/common/blue-yuan.png");
			} else {
				//选中二级菜单点击存储相等的
				one_menu.find("ul li[twoMenuId='" + $left_menu.twoMenuId + "']").addClass("two-menu-cli");
				one_menu.find("ul li[twoMenuId='" + $left_menu.twoMenuId + "']").addClass("check");
				one_menu.find("ul li[twoMenuId='" + $left_menu.twoMenuId + "']").find("div .menu-two-icon").attr("src", "resources/images/common/blue-yuan.png");
			}

		} else {
			/**
			 * 
			 * 访问后台获取二级菜单操作,思路流程同上
			 * 
			 */
			var clickTwoMenId = "";
			$.ajax({
				url: $yt_option.base_path + 'getMenu',
				type: 'POST',
				contentType: contentType,
				//data:{"userId":$yt_common.user_info.pkId,"parentMenuId":0,"systemId":systemId},
				success: function(dataStr) {
					var data = $.parseJSON(dataStr);
					$("#menu-list").empty();
					$.each(data, function(i, n) {
						var className = "";
						var one_menu = $('<li class="one-menu" menuId = "' + n.pkId + '"  menuOrder ="' + n.menuOrder + '"><div class="one-menu-text"><img class="one-menu-icon" src="resources/images/common/setting.png">' + n.menuName + '</div><ul></ul></li>');

						$("#menu-list").append(one_menu);
					});
					$("#left-menu-cnt .one-menu div").on("click", function() {
						var one_menu = $(this).parent();
						var parentMenuId = one_menu.attr("menuId");
						if($(this).hasClass("check")) {
							$(this).removeClass("check");
							$(this).next("ul").hide();
						} else {
							//二级菜单查询
							$.ajax({
								url: $yt_option.base_path + 'getCliedMenu',
								type: 'POST',
								//data:{"userId":1,"parentMenuId":parentMenuId,"systemId":systemId},
								success: function(dataStr) {
									var data = $.parseJSON(dataStr);
									one_menu.find("ul").empty();
									var twoMenuId = "";
									$.each(data, function(j, m) {
										var two_menu = $('<li class="two-menu" twoMenuId="' + m.pkId + '" twoMenUrl= "' + m.menuUrl + '"><div>' + m.menuName + '</div></li>');
										one_menu.find("ul").append(two_menu);
										two_menu.click(function() {
											$("#left-menu-cnt .two-menu").removeClass("two-menu-cli");
											$("#left-menu-cnt .two-menu").removeClass("check");
											//留存点击过的二级菜单Id
											clickTwoMenId = m.pkId;
											$(this).addClass("two-menu-cli");
											$(this).addClass("check");
											me.switchTab(m.pkId, m.menuUrl);
										});
									});
									one_menu.find("ul").show();
									//二级菜单选中
									if(clickTwoMenId != "" && clickTwoMenId != null) {
										$("#left-menu-cnt .two-menu").removeClass("two-menu-cli");
										$("#left-menu-cnt .two-menu").removeClass("check");
										one_menu.find("ul li[twoMenuId='" + clickTwoMenId + "']").addClass("two-menu-cli");
										one_menu.find("ul li[twoMenuId='" + clickTwoMenId + "']").addClass("check");
									}
								}
							});

							//一级菜单选中效果
							$("#left-menu-cnt .one-menu .check").next().hide();
							$("#left-menu-cnt .one-menu div").removeClass("check");
							$(this).addClass("check");
						}
					});
					var one_menu = "";
					var parentMenuId = "";
					if($left_menu.menuId != "" && $left_menu.menuId != null) {
						parentMenuId = $left_menu.menuId;
						$("#left-menu-cnt .one-menu").each(function(i, n) {
							if($(n).attr("menuid") == $left_menu.menuId) {
								one_menu = $(n);
							}
						});
					} else {
						one_menu = $("#left-menu-cnt .one-menu div:eq(0)").parent();
						parentMenuId = one_menu.attr("menuId");
					}
					//二级菜单查询
					$.ajax({
						url: $yt_option.base_path + 'getCliedMenu',
						type: 'POST',
						data: {
							"userId": 1,
							"parentMenuId": parentMenuId,
							"systemId": systemId
						},
						success: function(dataStr) {
							var data = $.parseJSON(dataStr);
							one_menu.find("ul").empty();
							$.each(data, function(j, m) {
								var two_menu = $('<li class="two-menu"  twoMenuId="' + m.pkId + '" twoMenUrl= "' + m.menuUrl + '"><div>' + m.menuName + '</div></li>');
								one_menu.find("ul").append(two_menu);
								two_menu.click(function() {
									$("#left-menu-cnt .two-menu").removeClass("two-menu-cli");
									$(this).addClass("two-menu-cli");
									$("#left-menu-cnt .two-menu").removeClass("check");
									$(this).addClass("check");
								});
								me.switchTab(m.pkId, m.menuUrl);
							});
							one_menu.find("ul").show();
							clickTwoMenId = $("#left-menu-cnt .two-menu:eq(0)").attr("twoMenuId");

							//初始选中第一个二级
							$("#left-menu-cnt .two-menu:eq(0)").addClass("two-menu-cli");
							$("#left-menu-cnt .two-menu:eq(0)").addClass("check");
						}
					});

					$("#left-menu-cnt .one-menu div:eq(0)").addClass("check");
					if($left_menu.menuId != "" && $left_menu.menuId != null) {
						$("#menu-list .one-menu").each(function(i, n) {
							if($left_menu.menuId == $(n).attr("menuid")) {
								//$("#left-menu-cnt .one-menu .check").next().find("li").remove();
								$("#left-menu-cnt .one-menu div").removeClass("check");
								$(n).find(".one-text").addClass("check");
							}
						});
						return false;
					} else {
						/*初始显示第一个一级菜单和子级*/
						$("#left-menu-cnt .one-menu div:eq(0)").addClass("check");
					}
				}
			});
		}
	},
	/**
	 * 左侧菜单点击切换页面方法
	 * @param {Object} menuId   一级菜单ID
	 * @param {Object} url      页面路径
	 * @param {Object} twoMenuId  二级菜单ID
	 */
	switchTab: function(menuId, url, twoMenuId) {
		$left_menu.menuId = menuId;
		$left_menu.twoMenuId = twoMenuId;
		//判断传输的url路径
		if(url.indexOf("http://") != 0) {
			url = $yt_option.websit_path + url;
		}
		$yt_common.request_params = new Object();
		//截取url路径
		if(url.indexOf("?") != -1) {
			var str = url.substr(url.indexOf("?") + 1);
			strs = str.split("&");
			for(var i = 0; i < strs.length; i++) {
				$yt_common.request_params[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
			}
		} else {
			$yt_common.request_params = null;
		}
		$("#yt-index-main-nav").html("");
		/**
		 * 调用显示loading方法
		 */
		$yt_baseElement.showLoading();
		//走ajax跳转页面
		$.ajax({
			type: "get",
			url: url,
			async: false,
			success: function(data) {
				/**
				 * 调用隐藏loading方法
				 */
				$yt_baseElement.hideLoading();
				$("#yt-index-main-nav").html(data);
				$(window).scrollTop(0);
			}
		});
		//算取页面高度
		var divMinHei = $(window).height();
		$(".web-criterion-model").css("min-height", divMinHei);
	}
}

;(function($) {

	//首先备份下jquery的ajax方法  
	var _ajax = $.ajax;
	//重写jquery的ajax方法  
	$.addParam = function(oldP, addp) {
		return oldP + "&" + $.param(addp);
	};
	$.ajax = function(opt) {
		//备份opt中error和success方法  
		var fn = {
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//$yt_common.prompt("请求失败,请稍后重试");
				console.log(opt);
			},
			success: function(data, textStatus) {},
			complete: function(XHR, TS) {}
		}
		//错误信息
		if(opt.error) {
			fn.error = opt.error;
		}
		//成功信息
		if(opt.success) {
			fn.success = opt.success;
		}
		//配置url
		if(opt.url.indexOf("http://") != 0) {
			opt.url = $yt_option.base_path + opt.url
		}
		//统一参数配置
		var dynamicKey = $yt_common.getToken();
		var cookieKey = $yt_common.ytAclCookieKey;
		var currentUserInfo = $yt_common.user_info;
		var ytParams = {
			ajax: 1
		}
		ytParams[cookieKey] = dynamicKey;
		var gettype = Object.prototype.toString;
		//请求头内容
		var contentType = "application/x-www-form-urlencoded; charset=utf-8";
		if($yt_option.is_origin) {
			if(window.XDomainRequest) {
				contentType = "text/plain";
				jQuery.support.cors = true;
				opt.type = "get";
			} //for IE8,IE9
		}

		opt.contentType = contentType;
		if(opt.data) {
			if(opt.data != undefined && gettype.call(opt.data) == "[object Object]") {
				opt.data[cookieKey] = ytParams[cookieKey];
				opt.data.ajax = ytParams.ajax;
				opt.data.systemCode = ytParams.systemCode;
				opt.data.userItCode = ytParams.userItCode;
				opt.data.userId = ytParams.userId;
			} else if(opt.data != undefined) {
				var paramStr = jQuery.param(ytParams);
				opt.data += "&" + paramStr;
			}

		} else {
			opt.data = ytParams;
		}

		//扩展增强处理  
		var _opt = $.extend(opt, {
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//错误方法增强处理  
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success: function(data, textStatus) {
				var gettype = Object.prototype.toString;
				//成功回调方法增强处理
				try {
					if((jQuery.parseJSON(data).success == undefined ? "" : jQuery.parseJSON(data).success.toString()) == "1") {
						$yt_common.login_state = false;
						var loginUrl = jQuery.parseJSON(data).obj.ssoVerifyAddress;
						loginUrl = loginUrl.substr(0, loginUrl.indexOf("systemIndex=")) + "systemIndex=" + window.location.href;
						console.log(opt.data);
						location.href = loginUrl;
					} else {
						$yt_common.login_state = true;
						data = jQuery.parseJSON(data);
					}
				} catch(e) {}
				fn.success(data, textStatus);  
			},
			complete: function(XHR, TS) {
				//请求完成后回调函数 (请求成功或失败之后均调用)。  
				fn.complete(XHR, TS);
			}
		});
		return _ajax(_opt);
	};
})(jQuery);