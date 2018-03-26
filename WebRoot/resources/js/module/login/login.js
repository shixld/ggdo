$(function() {
	//获取窗口高度
	var windowH = $(window).height();
	$('.login-bg-img').jqthumb({
		width: '100%',
		height: windowH
	});
});

$(document).ready(function() {
	//创建输入框回车事件
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if(ev.keyCode == 13) {
			checkFormq();
		}
	}
})


//提示信息内容
function createMsg(msg) {
	var str_html = '<font color=\"red\">' + msg + '</font>';
	return str_html;
}

//清除输入的空格
function trim(s) {
	var m = s.match(/^\s*(\S+(\s+\S+)*)\s*$/);
	return(m == null) ? "" : m[1];
}

//提交表单信息
function checkFormq() {
	//用户名
	var userLoginName = $("#userLoginName").val();
	//密码
	var userLoginPass = $("#password").val();
	//非空验证
	if(userLoginName == "" & trim(userLoginName) == "") {
		//提示信息
		$('.msg-div').text('用户名不能为空！');
		//重置内容
		$("#userLoginName").attr("value", '');
		//获取焦点
		$("#userLoginName").focus();
		return;
	} else if(userLoginPass == "" & trim(userLoginPass) == "") {
		//提示信息
		$('.msg-div').text('密码不能为空！');
		//重置内容
		$("#realpwd").attr("value", '');
		$("#password").attr("value", '');
		//获取焦点
		$("#password").focus();
		return;
	} else {
		/*$.ajax({
			url:$yt_option.base_path+"login",
			type: 'post',
			async: false,
			data: {
				user: userLoginName,
				pwd: userLoginPass,
				loginType:$yt_option.loginType,
				ajax:'ajax'
			},
			success: function(data) {
				var obj = data;
				if(obj.success == 0) {
					var systemObj = obj.obj;
					//重置提示
					$('.msg-div').text('');
					//跳转首页
					window.location.href = $yt_option.base_path;
				} else if(obj.success == 1) {
					//用户名或密码错误
					$('.msg-div').text('用户名或密码错误');
				}else if(obj.success == 2){
					//异常
					$('.msg-div').text('用户信息异常');
				}else if(obj.success == 3){
					//停用
					$('.msg-div').text('用户已停用');
				}else{
					$('.msg-div').text('用户名或密码错误');
				}
			},
			error:function(e){
				$('.msg-div').text('用户名或密码错误');
			}
		});*/
		location.href= $yt_option.websit_path+"index.html"
	}
}

/**
 * 重置输入框
 */
function clickReset(){
	$("#userLoginName").val('');
	$("#password").val('');
	$('.msg-div').text('');
}

//监听页面大小 动态改变背景
$(window).resize(function() {
	var windowH = $(window).height();
	$('.login-bg-img').jqthumb({
		width: '100%',
		height: windowH
	});
});