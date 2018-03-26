$(function() {
	/*调用单选框和复选框初始化方法*/
	$yt_baseElement.radioCheckBoxInit();
});
/********************************************************基础元素(B)************************************************************/
var $yt_baseElement={
	/**
	 * 
	 * (B04)表格行点击事件
	 * 
	 */
	tableRowActive:function(){
		$('.yt-table .yt-tbody tr').off("click").on("click",function(){
		    $(this).addClass('yt-table-active').siblings().removeClass('yt-table-active');
		});
	},
	/*
	 * 
	 * (B06)(B07)单选按钮多选按钮初始化事件
	 * 
	 */
	radioCheckBoxInit:function (){
		/***
		 * 
		 * 在body下给复选框绑定改变状态事件
		 * 
		 */
		$("body").delegate(".yt-checkbox input[type='checkbox']","change",function (){
			/*如果当前点击的label下的input被选中*/
			if($(this)[0].checked){
				/*当前点击的label添加checked*/
				$(this).parent().addClass("check");
			}else{
				$(this).parent().removeClass("check");
			}
		})
		/***
		 * 
		 * 在body下给点选按钮绑定改变状态事件
		 * 
		 */
		$("body").delegate(".yt-radio input[type='radio']","change",function (){
			//获取当前点击单选按钮的name值
			var docName = $(this).attr("name");
			if($(this).parents("form").length==0){
			//如果改变状态的单选按钮的父元素中没有form，则在body下找到所有name是docName的单选按钮，筛选出所有父元素不是form的单选按钮并将他的父元素check类名移除
		   		$(this).parents("body").find(".yt-radio input[type='radio'][name='"+docName+"']").not("form input[type='radio']").parent().removeClass("check");	
	   		}else{
	   		//如果改变状态的单选按钮的父元素中有form，则在form下找到所有name是docName的单选按钮,并将他的父元素check类名移除	
	   			$(this).parents("form").find(".yt-radio input[type='radio'][name='"+docName+"']").parent().removeClass("check");
	   		}
	   		$(this).parent().addClass("check");
		})
		//找到check下的复选框、单选框添加选中状态
		$(".yt-checkbox.check").find("input[type='checkbox']").prop("checked",true);
		$(".yt-radio.check").find("input[type='radio']").prop("checked",true);
	},
	/**
	 * (B09)进度条方法
	 * @param {Object} obj      当前进度条对象 
	 * @param {Object} lineWid  进度条动画时宽度
	 * @param {Object} speed    动画运行速度
	 */
	processerbar:function (obj,lineWid,speed){
		    $(obj).css("display","block");
		    //给进度条横线轴添加动画
			$(obj).find(".yt-progress-line").each(function(i,item){
				$(item).animate({
					width: (lineWid == undefined?0:lineWid)+"%"
				},speed);
			});
			//创建定时任务
		   var si = window.setInterval(
			function(){
				//获取进度条div的宽度
				var a=$(obj).find(".yt-progress-line").width();
				var c=$(obj).width();
				var b=(a/c*100).toFixed(0);
				//标签百分比显示
				$(obj).find(".yt-progress-percent").html(b+"%");
				$(obj).find(".yt-progress-percent").css("left",a-26+"px");
				//白色原点加左边距
				$(obj).find(".yt-progress-ball-div").css("left",a-10+"px");
				//如果到达最后清除定时任务
				if($(obj).find(".yt-progress-percent").html()=="100%") {
					window.clearInterval(si);
				}
			},10);
   },
   /*
    * 
    * 按钮点击变色
    * 
    * */
   btnClick:function(){
   	$('.yt-option-btn').click(function(){
   		$(this).addClass('yt-option-button-sure');
   	})
   },
   /**
	 * 
	 * (B10)数字输入框的方法
	 * 
	 */
	numberInput:function (obj){
		var myInputNumber = $(obj);
		/*获取向上按钮*/
		var spinUp = myInputNumber.find('[data-node=up]');
		/*获取向下按钮*/
		var spinDown = myInputNumber.find('[data-node=down]');
		//获取当前数据框
		var input = spinUp.parent().prev();
		/*给向上按钮绑定点击事件*/
		spinUp.click(function(){
			/*获取输入框的数值并转换成number类型，如果为空则赋值为0*/
		    var curVal = Number(input.val()) || 0;
		    //获取数字加减的基准数
		    var step = Number(input.attr('step')) || 1;
		    /*输入框的数值增加1*/
		    input.val( curVal + step );
		    input.css({'color':"#333"});
		});
		/*给向下按钮绑定点击事件*/
		spinDown.click(function(){
		    var curVal = Number(input.val()) || 0;
		    //获取数字加减的基准数
		    var step = Number(input.attr('step')) || 1;
		    if(curVal - step>=0){
		    	input.val( curVal - step );
		    }
		    input.css({'color':"#333"});
		});
		var keyCode = {};
		/*键盘事件判断输入的是不是数字*/
		input.keydown(function(e){
		    var kc = e.keyCode;
		    if(
	          (kc >= 48 && kc <= 57) || (kc >= 96 && kc <= 105) // [0-9]
		        || kc == 189  // - 负号
		        || kc == 190  // . 小数点
		        || kc == 110  // . 数字键盘的小数点
		        || kc == 8 // backspace
		        || kc == 37 // 左箭头
		        || kc == 39 // 右键头
	        ){
		        return true;
		    }
		    	return false;
		});
		input.keyup(function(){
			$(this).val($(this).val().replace(/[^\d.]/g,''));
			input.css({'color':"#333"});
		});
	},
	/**
	 * (B13)金额格式化处理
	 * @param {Object} s 需要处理的字符串
	 * @param {Object} n 小数点保留位数
	 */
	fmMoney: function(s, n) {
		n = n > 0 && n <= 20 ? n : 2;
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = s.split(".")[0].split("").reverse(),
			r = s.split(".")[1];
		t = "";
		for(i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	},
	/**
	 * (B13)金额格式化处理
	 * 金额格式化还原方法
	 * @param {Object} 金额的字符串
	 */
	rmoney: function(s) {
		return parseFloat(s.replace(/[^\d\.-]/g, ""));
	},
	/**
	 * 
	 * 
	 * 创建显示loading
	 * 
	 */
	showLoading:function(){
		//存储时间戳
		$yt_baseElement.timestamp = new Date().getTime();
		var loadingStr='<div class="yt-pop-model loading-model" style="text-align: center;position: fixed;background: none;">'
					  +'<div><img class="loading-img" style="width:160px;height:160px" src="resources/images/common/loading.gif"/></div>'
					  +'<div>';
		$("body").append(loadingStr);
		$yt_alert_Model.getDivPosition($(".loading-model"));
		$(".loading-model,#heard-nav-bak").show();
	},
	/**
	 * 
	 * 隐藏loading
	 * 
	 */
	hideLoading:function(){
		var setTime =0;
		//获取当前的时间戳
		var nowTime = new Date().getTime();
		//用当前的时间戳减去显示时获取到的时间戳得到差
		var thisTime = nowTime - $yt_baseElement.timestamp;
		//判断时间戳的差小于等于1000
		if(thisTime<=1000){
			setTime = 1000-thisTime;
			setTimeout(function(){
			$("#pop-modle-alert,#heard-nav-bak").hide();
			$(".loading-model").remove();
		},setTime);
		}else{
			$("#pop-modle-alert,#heard-nav-bak").hide();
			$(".loading-model").remove();
		}
		
	},
	/**
	 * 
	 * 单个页面loading显示
	 * 
	 */
	pageLoadingShow:function(){
		//存储时间戳
		$yt_baseElement.timestamp = new Date().getTime();
		var loadingStr='<div class="page-loading">'
		              +'<img class="loading-img" style="width:130px;height:130px" src="resources/images/common/loading.gif"/>'
		              +'</div>';
		$("#yt-index-main-nav").append(loadingStr);
		$(".page-loading").show();
	},
	/**
	 * 
	 * 
	 * 单个页面loading隐藏
	 * 
	 */
	pageLoadingHide:function(){
		var setTime =0;
		//获取当前的时间戳
		var nowTime = new Date().getTime();
		//用当前的时间戳减去显示时获取到的时间戳得到差
		var thisTime = nowTime - $yt_baseElement.timestamp;
		//判断时间戳的差小于等于1000
		if(thisTime<=1000){
			setTime = 1000-thisTime;
			setTimeout(function(){
			$(".page-loading").remove();
		},setTime);
		}else{
			$(".page-loading").remove();
		}
	}
}
/***********************************************************控件(C)**********************************************************/
var $yt_controlElement={
	/**
	 * (C07)消息气泡
	 * @param {Object} obj 操作对象
	 * @param {Object} isShort 是否截止到三位数缩写,默认true;
	 */
	bubbleFun:function(obj,isShort){
		//获取是否截止到三位数缩写的参数
		var isShort = isShort==undefined?true:isShort;
		//遍历所有气泡
		$(obj).each(function(i,n){
			//获取当前气泡长度,根据不同位数,增加宽度,一位数,二位数,三位数,3中情况
			var bubbleWid =  $(obj).width()-1;
			if($(n).text().length == 2){
					$(n).css("width",(bubbleWid+10)+"px");
			}
			//判断是否支持截止到三位数缩写
			if(isShort){
				//是3位数就显示99+
				if($(n).text().length >=3){
					$(n).html("<span style='margin-left: -5px;'>99<sup>+</sup></span>");
					$(n).css("width",(bubbleWid+20)+"px");
				}
			}else{
				//不支持截止到3位数,就设置自动宽度
				if($(n).text().length>=3){
					$(n).css({"width":"auto","padding":"0px 5px"});
				}
			}
		});
	}
}
var $yt_alert_Model={
	
	/**
	 * 
	 * (C19)弹出框
	 * @param {Object} option 参数对象
	 * 
	 */
	alertOne:function(option){//带有图标,按钮的提示框
		var alertStr ='<div class="yt-pop-model yt-alert-model yt-alert-one">'
		             +'<div class="yt-alert-close"></div>'
		             +'<div class="yt-alert-one-icon"></div>'
		   	    	 +'<div class="yt-alert-one-msg"></div>'
		   	    	 +'<div class="yt-alert-one-bottom">'	   
		   	    	 +'</div></div>';
		$("body").append(alertStr);
		$(".yt-alert-model").show();
		$("#heard-nav-bak").show();//显示顶部隐藏蒙层
		var haveAlertIcon = option.haveAlertIcon==undefined?false:option.haveAlertIcon;//是否有提示图标
		var haveCloseIcon = option.haveCloseIcon==undefined?false:option.haveCloseIcon;//是否有关闭图标
	    var iconUrl = option.iconUrl==undefined?"":option.iconUrl;//提示图标路径
	    var closeIconUrl = option.closeIconUrl==undefined?"":option.closeIconUrl;//关闭图标路径
	    var alertMsg = option.alertMsg==undefined?"":option.alertMsg;//提示信息
	    var leftBtnName = option.leftBtnName?option.leftBtnName:"确认";/*左侧按钮名称,默认为确定*/
		var rightBtnName =  option.rightBtnName?option.rightBtnName:"取消";/*右侧按钮名称,默认为取消*/
		var confirmFunction = option.confirmFunction;//点击确定按钮后执行的方法
		var cancelFunction  = option.cancelFunction;//点击取消按钮后执行的方法
		var thisAlert = $(".yt-alert-model");
	    //判断是否有关闭图标,有则拼接
	    if(haveCloseIcon && closeIconUrl!=""){
	    	$(".yt-alert-close").append('<img src="'+closeIconUrl+'">');
	    	$(".yt-alert-close img").bind("click",function (){
	    		//删除当前弹出框
				$yt_alert_Model.alertHide(thisAlert);
			})
	    }
		//判断是否有提示图标
		if(haveAlertIcon && iconUrl!=""){
	    	$(".yt-alert-one-icon").append('<img  class="warn-icon" src="'+iconUrl+'"/>');
	    }
		//通过判断confirmFunction传过来的方法是否有值，来显示是带有确定，取消按钮的，还是只有确定按钮
		if(confirmFunction){
			var leftButton = $('<input type="button" class="yt-model-bot-btn  yt-model-sure-btn" value="'+leftBtnName+'"/>');
			var rightButton = $('<input type="button" class="yt-model-bot-btn yt-model-canel-btn" value="'+rightBtnName+'"/>');
			$(".yt-alert-one-bottom").empty().append(leftButton);
			$(".yt-alert-one-bottom").append(rightButton);
			//点击左侧按钮触发事件
			leftButton.click(function (){
				//删除当前弹出框
				$yt_alert_Model.alertHide(thisAlert);
				//调用传输方法
				confirmFunction();
			});
			//点击右侧按钮触发事件
			rightButton.click(function (){
				//删除当前弹出框
				$yt_alert_Model.alertHide(thisAlert);
				if(cancelFunction){
					cancelFunction();
				}
			});
		}else{
			//一个按钮
			var action = $('<input type="button" class="yt-model-bot-btn yt-model-sure-btn" value="'+leftBtnName+'"/>');
			action.click(function(){
				//删除当前弹出框
				$yt_alert_Model.alertHide(thisAlert);
				if(cancelFunction){
					cancelFunction();
				}
			});
			$(".yt-alert-one-bottom").empty().append(action);
		}
		//获取提示信息
		$(".yt-alert-one-msg").html(alertMsg);
		/**
		 * 调用算取弹出框位置的方法
		 */
		$yt_alert_Model.getDivPosition($(".yt-alert-model"));
	},
	/**
	 * 
	 * (C19)弹出框;包含倒计时的提示框
	 * @param {Object} option参数对象
	 * 
	 */
	alertTwo:function(option){
        var alertStr ='<div class="yt-pop-model yt-alert-model yt-alert-two">'
                     +'<div class="yt-alert-two-icon">'
                     +'</div>'
                     +'<div class="yt-alert-two-msg"></div>'
                     +'<div class="yt-alert-two-attach"></div>'
                     +'</div>';
        $("body").append(alertStr);
		$(".yt-alert-model").show();
		$("#heard-nav-bak").show();//显示顶部隐藏蒙层
		var alertMsg = option.alertMsg == undefined?"":option.alertMsg;//提示信息
		var alertMsgTwo = option.alertMsgTwo == undefined?"":option.alertMsgTwo;//第二个提示信息
		var haveAlertIcon = option.haveAlertIcon==undefined?false:option.haveAlertIcon;//是否有提示图标
		var haveSecon = option.haveSecon==undefined?false:option.haveSecon;//是否带有倒计时
		var iconUrl = option.iconUrl==undefined?"":option.iconUrl;//提示图标路径
		var secondNum = option.secondNum=undefined?"":option.secondNum;//倒计时秒速
		var skipUrl=option.skipUrl=undefined?"":option.skipUrl;//调转页面路径
		var thisAlert = $(".yt-alert-model");
		//判断是否有图标
		if(haveAlertIcon && iconUrl!=""){
			$(".yt-alert-two .yt-alert-two-icon").append('<img src="'+iconUrl+'" />');
		}
		//判断是否带有倒计时
		if(haveSecon && secondNum !="" && skipUrl!=""){
			$(".yt-alert-two .yt-alert-two-attach").append('<span class="yt-attach-msg">'+alertMsgTwo+'</span><span class="yt-secs-num"></span>');
			for(var i = secondNum; i >= 0; i--) {
				window.setTimeout('$yt_alert_Model.getTimeNum('+i+',"'+skipUrl+'")',(secondNum - i) * 1000);
			}
		}else{
			$(".yt-alert-two .yt-alert-two-attach").append('<span class="yt-attach-msg">'+alertMsgTwo+'</span>');
			/*setTimeout(function(){
			//删除当前弹出框
			$yt_alert_Model.alertHide(thisAlert);
			},3000);*/
		}
		$(".yt-alert-two .yt-alert-two-msg").html(alertMsg);
		//点击蒙层关闭提示框
		$("#pop-modle-alert").off().on("click",function(){
			//删除当前弹出框
			$yt_alert_Model.alertHide(thisAlert);
		});
		//调用算取div显示位置方法
		$yt_alert_Model.getDivPosition($(".yt-alert-model"));
	},
	/**
	 * 
	 * (C19)弹出框;编辑框形式弹出框
	 * @param {Object} option
	 * 
	 */
	alertThree:function(option){
		/**
		 * 获取传输模块的类属性
		 */
		var modelAttributeClass =option.modelClassName.attr("class")==undefined?"":option.modelClassName.attr("class");
		/**
		 * 获取传输模块的ID属性
		 */
		var modelAttributeId = option.modelClassName.attr("id")==undefined?"":option.modelClassName.attr("id");
		var thisAlert = $(".yt-alert-table");
		var haveAlertIcon = option.haveAlertIcon==undefined?false:option.haveAlertIcon;//是否有提示图标
		var iconUrl = option.iconUrl==undefined?"":option.iconUrl;//提示图标路径
		var titleMsg = option.titleMsg==undefined?"":option.titleMsg//标题内容
		var isDrag = option.isDrag == undefined?true:option.isDrag;//是否支持拖拽,默认true
		var alertStr = '<table class="yt-pop-model yt-alert-table"><tr><td>'
		             +'<div class="yt-alert-three">'
		             +'<div class="yt-alert-three-title">'
		             +'<span class="yt-alert-three-icon"><img /></span>'
		             +'<span class="yt-alert-three-title-msg"></span>'
		             +'</div>'
		             +'<div class="warp-model"></div>'
		             +'</div></td></tr></table>';
		/**
		 * 判断页面是否存在这个编辑框
		 */
		if(!$("body").find(".yt-alert-table div").hasClass(modelAttributeClass)){
			//不存在进行追加
			$("body").append(alertStr);
		}
		//标题图片
		if(haveAlertIcon && iconUrl!=""){
			$(".yt-alert-three-icon img").attr("src",iconUrl);
		}
		//赋值标题内容
		$(".yt-alert-three-title-msg").html(titleMsg);
		/**
		 * 将编辑框中间内容包裹起来
		 */
		$(".warp-model").wrapInner(function(){
			return option.modelClassName;
		});
		//显示顶部隐藏蒙层
		$("#heard-nav-bak").show();
		//显示弹出框
		$(".yt-alert-table").show();
		option.modelClassName.show();
		option.modelClassName.css("display","block");
		//$(".yt-alert-table").find(option.modelClassName).show();
		/*if($(".yt-alert-table").hasClass(modelAttributeClass)){
			$(this).show();
		}*/
		//判断是否支持拖拽
		if(isDrag){
			$yt_model_drag.modelDragEvent($(".yt-alert-three .yt-alert-three-title"));
		}
		//调用算取div显示位置方法
		$yt_alert_Model.getDivPosition($(".yt-alert-table"));
	},
	/**
	 * 
	 * (C19)弹出框;单文本蒙层提示框
	 * @param {Object} alertMsg 提示内容
	 * @param {Object} hideTime 消失时间
	 * 
	 */
	prompt:function(alertMsg,hideTime){
		$(".yt-alert-four").remove();
		var alertStr = '<div class="yt-alert-four"><span class="yt-alert-four-msg">'+(alertMsg==undefined?"":alertMsg)+'</span></div>';
		$("body").append(alertStr);
		$(".yt-alert-four").css("display","block");
		window.clearTimeout();
		setTimeout(function(){
			//删除当前弹出框
			$(".yt-alert-four").remove();
		},hideTime==undefined?2000:hideTime);
	},
	alertHide:function(thisObj){
		//删除当前弹出框
		$(thisObj).remove();
		$("#pop-modle-alert").hide();
		$("#heard-nav-bak").hide();
	},
	getTimeNum:function(num, page_url){//计算倒计时方法:num:当前秒数;page_url页面跳转路径
		//赋值倒计时
		$(".yt-alert-two .yt-alert-two-attach .yt-secs-num").html(num);
		if(num == 0) {
			//window.location.href = page_url;
			//删除当前弹出框
			$yt_alert_Model.alertHide($(".yt-alert-model.yt-alert-two"));
			$left_menu.switchTab(1,$yt_option.base_path+page_url);
			
		}
	},getDivPosition:function(obj){//动态算取div的位置top值left值
		var scrollTop = "";
		var scrollLeft = "";
		var q_alert_top="";
		var q_alert_left = ($(window).width()-$(obj).outerWidth())/2;
		if($(obj).hasClass("yt-edit-alert")){
			q_alert_top = ($(window).height()-$(obj).find(".yt-edit-alert-main").outerHeight()-$(obj).find(".yt-edit-alert-title").height())/2;
		}else{
			q_alert_top = ($(window).height()-$(obj).height())/2;
		}
		if(q_alert_left>0){
			$(obj).css("left",q_alert_left+"px");
		}else{
			$(obj).css("left","0");
		}
		if(q_alert_top>0){
			$(obj).css("top",q_alert_top+"px");
		}else{
			$(obj).css("top","0px");
		}
	}
}
/**
 * 
 * 弹出框拖拽事件
 * 
 */
var $yt_model_drag={
		         modelDragEvent:function(dragObj,options){
		         //获取需要拖动的Div
				 var _moveDiv = $(dragObj);
           		 var _moveArea = options ? $(options) : $(document);//限定拖动区域，默认为整个文档内
           		 var isDown = false;//mousedown标记
           		  //ie的事件监听，拖拽div时禁止选中内容，firefox与chrome已在css中设置过-moz-user-select: none; -webkit-user-select: none;
		            /*if(document.attachEvent){
		                _moveDiv.attachEvent('onselectstart', function() {
		                    return false;
		                });
		            }*/
           		 //鼠标按下div事件
				 _moveDiv.mousedown(function(event){
				 	var scrollTop = $(window).scrollTop();
					var scrollLeft = $(window).scrollLeft();
				 	//拖动时鼠标样式
               		 _moveDiv.css("cursor", "move");
					 var e = event || window.event;
               		//获得鼠标指针离DIV元素左边界的距离
                	var x = e.pageX - _moveDiv.offset().left;
             		//获得鼠标指针离DIV元素上边界的距离 
                	var y = e.pageY - _moveDiv.offset().top;
                	//限定区域鼠标移动事件
               		_moveArea.on('mousemove', function(event) {
                    var ev = event || window.event;
                    //获得X轴方向移动的值 
                    var abs_x = ev.pageX - x - scrollLeft ;
                    //获得Y轴方向移动的值 
                    var abs_y = ev.pageY - y - scrollTop;
                     _moveDiv.parent().css("margin","inherit");
                    //div动态位置赋值
                    _moveDiv.parent().css({'left': abs_x, 'top': abs_y});
               		});
				});
				_moveDiv.mouseup(function() {
                //解绑拖动事件
                _moveArea.off('mousemove');
            	});
			}
	}



/**
 * 
 * jquery拓展方法
 * 
 */
$.fn.extend({
	/**
	 * 设置多选
	 * @param {Object} obj string类型参数
	 */
    setCheckBoxState:function(obj){
    	/**
    	 * 判断状态 check(选中),
    	 * uncheck(取消选中),
    	 * disabled(禁用),
    	 * undisabled(取消禁用),
    	 * half(半选)
    	 */
    	if(obj == "check"){
    		$(this).parent().addClass("check");	
    		$(this)[0].checked = true;
    	}else{
    		$(this).parent().removeClass("check");
    	}
    	/**
    	 * 取消选中
    	 */
    	if(obj == "uncheck"){
    		$(this).parent().removeClass("check");
    		$(this)[0].checked = false;
    		//清楚禁用样式
    		$(this).parent().removeClass("yt-check-disabled");	
    	}
    	/**
    	 * 禁用
    	 */
    	if(obj == "disabled"){
    		$(this)[0].disabled=true; 
    		$(this)[0].checked = false;
    		$(this).parent().addClass("yt-check-disabled");	
    	}
    	/**
    	 * 取消禁用
    	 */
    	if(obj == "undisabled"){
    		$(this)[0].disabled=false; 
    		$(this).parent().removeClass("yt-check-disabled");	
    	}
    	/**
    	 * 半选
    	 */
    	if(obj == "half"){
    		$(this).parent().addClass("yt-checkbox-half");
    	}else{
    		$(this).parent().removeClass("yt-checkbox-half");
    	}
   },
  /**
   * 设置单选
   * @param {Object} obj string类型目前有3个固定值check选中,uncheck取消选中,disabled禁用,undisabled取消禁用
   */
   setRadioState:function(obj){
   		var docName = $(this).attr("name");
   		/**
   	     * 判断传输的值check选中,uncheck不选中,disabled禁用
   	     */
   	    if(obj == "uncheck"){
   	    	//给当前当选按钮设置checked值
   			$(this)[0].checked = false;
   			//删除禁用样式
   			$(this).parent().removeClass("yt-radio-disabled");
   			//判断当前单选按钮祖父级是否有from标签元素
	   		if($(this).parents("form").length==0){
	   			//如果没有父级没有from,找到body下的所有name属性名与当前获取到的name相同的单选按钮,不包含form表单下的单选,父级label删除选中的样式
		   		$(this).parents("body").find(".yt-radio input[type='radio'][name='"+docName+"']").not("form input[type='radio']").parent().removeClass("check");
	   		}else{
	   			//如果包含就删除当前单选按钮父级from表单下所有的单选name值与当前name值相等的,单选按钮父级label选中类样式
	   			$(this).parents("form").find(".yt-radio input[type='radio'][name='"+docName+"']").parent().removeClass("check");
	   		}
   	    }
   	    /**
   	     * 判断是禁用给父级label添加禁用类样式
   	     */
   	    if(obj == "disabled"){
   	    	//给当前单选按钮设置禁用
   			$(this)[0].disabled = true;
   			$(this)[0].checked = false;
   	    	$(this).parent().addClass("yt-radio-disabled").removeClass("check");
   	    }
   	    /**
   	     * 取消禁用
   	     */
   	    if(obj == "undisabled"){
   	    	//给当前单选按钮设置禁用
   			$(this)[0].disabled = false;
   			$(this).parent().removeClass("yt-radio-disabled");
   	    }
   		//判断如果传输值是true给当前单选按钮的父级label添加选中样式
   		if(obj == "check"){
   			//给当前当选按钮设置checked值
   			$(this)[0].checked = true;
			$(this).parent().addClass("check");
			//判断当前单选按钮祖父级是否有from标签元素
	   		if($(this).parents("form").length==0){
	   			//如果没有父级没有from,找到body下的所有name属性名与当前获取到的name相同的单选按钮,不包含form表单下的单选,父级label删除选中的样式
		   		$(this).parents("body").find(".yt-radio input[type='radio'][name='"+docName+"']").not("form input[type='radio']").parent().removeClass("check");
	   		}else{
	   		//如果包含就删除当前单选按钮父级from表单下所有的单选name值与当前name值相等的,单选按钮父级label选中类样式
	   			$(this).parents("form").find(".yt-radio input[type='radio'][name='"+docName+"']").parent().removeClass("check");
	   		}
		}
   },
   /**
    * 设置下拉列表选中
    */
   setSelectVal:function(selVal){
   	 //1.设置当前select标签选中
   	 $(this).find("option[value='"+selVal+"']").prop("selected","selected");
   	 //2.设置当前select标签下动态生成的div中的值加选中状态
   	 //获取当前select标签选中的文本信息
   	 var selText = $(this).find("option:selected").text();
   	 $(this).next().find(".list li").removeClass("selected");
   	 $(this).next().find(".list li").removeClass("focus");
   	 $(this).next().find(".list li[data-value='"+selText+"']").addClass("selected");
   	 $(this).next().find(".list li[data-value='"+selText+"']").addClass("btn-primary");
   	 $(this).next().find(".list li[data-value='"+selText+"']").addClass("focus");
   	 $(this).next().find(".current").text(selText).css("color","#333");
   	 //可输入的下拉列表给输入框赋值
   	 $(this).next().find(".search-current").val(selText).css("color","#333");
   },
   /**
    * 显示方法
    */
   show:function (){
   			if($(this).hasClass("yt-pop-model")){
	   			$("#pop-modle-alert").css("display","block");
	   		}
	   	    //判断是否是弹出框表格
	   		if($(this).hasClass("yt-alert-table")){
	   			$("#pop-modle-alert").css("display","block");
	   		}
	   		return showHide( this, true );
   },
   /**
    * 隐藏方法
    */
   hide:function (){
	   	if($(this).hasClass("yt-pop-model")){
	   		$("#pop-modle-alert").css("display","none");	
	   	}
	   	return showHide(this);
	   	
   },
   setDatas:function (data){
   		var me =$(this);
   		var dataArr = [];
		for (var i = 0 ;i<Object.keys(data).length;i++) {
			dataArr.push({
				name:Object.keys(data)[i],
				value:data[Object.keys(data)[i]]
			})
		}
		$.each(dataArr,function (i,n){
			me.find("[data-val='"+n.name+"']").val(n.value);
			me.find("[data-text='"+n.name+"']").text(n.value);
		})
   },getDatas:function(){
   	 var me =$(this);
   	 var data= new Object();
   	 $.each(me.find("[data-val]"),function (i,n){
		data[$(n).attr('data-val')] = $(n).val()||$(n).text();
	 })
   	 $.each(me.find("[data-text]"),function (i,n){
		data[$(n).attr('data-text')] = $(n).text();
	 })
   	 return data;
   },
	/**
    * 
    * (B02)下拉列表插件执行方法
    * @param {Object} option 执行对象
    * 
    */
    niceSelect :function (option){
	   this.elementObj = $(this);
	   elementObj = $(this);
	   elementOption = option;
    // Methods
    if (typeof method == 'string') {      
      if (method == 'update') {
        this.each(function() {
          var $select = $(this);
          var $dropdown = $(this).next('.nice-select');
          var open = $dropdown.hasClass('open');
          
          if ($dropdown.length) {
            $dropdown.remove();
            create_nice_select($select);
            
            if (open) {
              $select.next().trigger('click');
            }
          }
        });
      } else if (method == 'destroy') {
        this.each(function() {
          var $select = $(this);
          var $dropdown = $(this).next('.nice-select');
          
          if ($dropdown.length) {
            $dropdown.remove();
            $select.css('display', '');
          }
        });
        if ($('.nice-select').length == 0) {
          $(document).off('.nice_select');
        }
      } else {
        console.log('Method "' + method + '" does not exist.')
      }
      return this;
    }
    //隐藏select
    this.hide();
    // Create custom markup
    this.each(function() {
      var $select = $(this);
      
      if (!$select.next().hasClass('nice-select')) {
        create_nice_select($select);
      }else{
    	  $select.next().remove();
    	  create_nice_select($select);
      }
    });
    /**
     * 创建列表div
     * @param {Object} $select
     */
    function create_nice_select($select) {
    	var showItem = '<span class="current"></span><ul class="list"></ul>';
    	if(option&&option.search){
    		showItem = '<span class="current" style="display:none"></span><input class="search-current" type="text" value=""/><ul class="list"></ul>';
    	}
      $select.after($('<div></div>')
        .addClass('nice-select')
        .addClass($select.attr('class') || '')
        .addClass($select.attr('disabled') ? 'disabled' : '')
        .attr('tabindex', $select.attr('disabled') ? null : '0')
        .html(showItem)
      );
        
      var $dropdown = $select.next();
      var $options = $select.find('option');
      var $selected = $select.find('option:selected');
      $dropdown.find('.current').html($selected.data('display') || $selected.text());
       $dropdown.find('.current').next().val($selected.data('display') || $selected.text());
      $options.each(function(i) {
        var $option = $(this);
        var display = $option.data('display');
		
        $dropdown.find('ul').append($('<li></li>')
          .attr('data-value', $option.val())
          .attr('title', $option.attr("title"))
          .attr('sel-val',$option.attr("sel-val"))
          .attr('data-display', (display || null))
          .addClass('option' +
            ($option.is(':selected') ? ' selected' : '') +
            ($option.is(':disabled') ? ' disabled' : ''))
          .html($option.text())
        );
      });
      $dropdown.find(".search-current").on("focus",function(){
      	  if($select.val()==null || $select.val() == undefined || $select.val()==""){
      	  	 $dropdown.find(".search-current").val("");
      	  }else{
      	  	$dropdown.find(".search-current").css("color","#333");
      	  }
      });
   /**
    *  文本框数据触发回调函数后重新加载数据,支持可搜索
    *
    */
	  	$dropdown.find(".search-current").keyup(function(event){
	  		if(event.keyCode == 37
	  		||event.keyCode == 38
	  		||event.keyCode == 39
	  		||event.keyCode == 40){
	  			return false;
	  		}
	  		//调用回调方法,传入数据
		  	option.backFunction($(this).val());
		  	$select.find('option');
			$dropdown.find('ul').empty();
		 //遍历列表数据
	  	 $select.find('option').each(function(i) {
	        var $option = $(this);
	        var display = $option.data('display');
	        $dropdown.find('ul').append($('<li></li>')
	          .attr('data-value', $option.val())
	          .attr('sel-val',$option.attr("sel-val"))
	          .attr('data-display', (display || null))
	          .attr('title', $option.attr("title"))
	          .addClass('option' +
	           // ($option.is(':selected') ? ' selected' : '') +
	            ($option.is(':disabled') ? ' disabled' : ''))
	          .html($option.text())
	        );
	      });
	       bindFunction($select,option);
		});
		//调用生成滚动条方法
		$(".nice-select ul.list").mCustomScrollbar({
			autoHideScrollbar:true,
			theme:"square"
		});
    }
    bindFunction($(this),option);
    function bindFunction(obj,option){
	    /**
	     * 
	     * 打开或是关闭事件
	     *
	     */
	     $(obj).next().off();
	    $(obj).next().on('click', function(event) {
	    	
	    	 var self = $(event.target);
      		 self.addClass('btn-primary');
	   		 var $dropdown = $(this);	
	   		 var selectVal = $(obj).val();
	      if(option&&option.search&&!self.hasClass("option")){
	      		option.backFunction("");
	      	$(obj).find("option[value='"+selectVal+"']").attr("selected","selected");
	      		
	      	var $select = $dropdown.siblings("select");
	      		$select.find('option')
				$dropdown.find('ul').empty();
		  	 $select.find('option').each(function(i) {
		        var $option = $(this);
		        var display = $option.data('display');
		        $dropdown.find('ul').append($('<li></li>')
		          .attr('data-value', $option.val())
		          .attr('data-display', (display || null))
		          .attr('title', $option.attr("title"))
		          .addClass('option' +
		            ($option.is(':selected') ? ' selected' : '') +
		            ($option.is(':disabled') ? ' disabled' : ''))
		          .html($option.text())
		        );
		      });
		       bindFunction($(this).prev(),option);
	      }
	      
	      $('.nice-select').not($dropdown).removeClass('open');
	      $dropdown.toggleClass('open');
	      //判断列表是否打开
	      if ($dropdown.hasClass('open')) {
	      	/**
	      	 * 算取列表显示位置
	      	 */
	      	//获取整个文档的高度
	      	var  docHei = $(window).height();
	      	//窗体滚动条的高度
	      	var winScrollTop = $(window).scrollTop();
	      	//获取当前元素上偏移坐标
	      	var  top = $dropdown.offset().top - winScrollTop;
	      	//算出当前对象距离底部的距离
	      	var botHei = docHei - top;
	      	//得到标签下展开列表的高度
	      	var  objHeight = $dropdown.find("ul").height();
	      	//判断如果当前对象距离底部的距离小于,展开列表的高度,就显示在上方
	      	if(botHei<objHeight){
	      		$dropdown.find("ul").css({"top":"inherit","bottom":"30px"});
	      	}
	        $dropdown.find('.option');  
	        $dropdown.find('.focus').removeClass('focus');
	        $dropdown.find('.selected').addClass('focus');
	      } else {
	        $dropdown.focus();
	      }
	    });
    	
    	
    	/**
    	 * 
    	 *列表数据点击事件
    	 *
    	 */
	    $(obj).next().find(".option:not(.disabled)").on('click', function(event) {
	      var $option = $(this);
	      var $dropdown = $option.closest('.nice-select');
	      
	      $dropdown.find('.selected').removeClass('selected');
	      $option.addClass('selected');
	      
	      var text = $option.data('display') || $option.text();
	      $dropdown.prev('select').val($option.data('value')).trigger('change');
        //判断是否包含此方法
   		if(option !=undefined && option.formateVal!=undefined){
   		  //调用格式化参数的方法,将当前select标签选中的option传过去
   		  text = option.formateVal($option);
   		}
	      $dropdown.find('.current').text(text);
	      $dropdown.find('.current').next().val(text);
	      $dropdown.find('.current,.search-current').css("color","#333");
	    });
	    
	    /**
	     * 键盘事件触发
	     */
	   $(obj).next().on('keydown', function(event) {    
	      var $dropdown = $(this);
	      var $focused_option = $($dropdown.find('.focus') || $dropdown.find('.list .option.selected'));
	      
	      /**
	       * 空格键加enter
	       */
	      if (event.keyCode == 32 || event.keyCode == 13) {
	        if ($dropdown.hasClass('open')) {
	          $focused_option.trigger('click');
	        } else {
	          $dropdown.trigger('click');
	        }
	        return false;
	      /**
	       * 向下键
	       */
	      } else if (event.keyCode == 40) {
	        if (!$dropdown.hasClass('open')) {
	          $dropdown.trigger('click');
	        } else {
	          var $next = $focused_option.nextAll('.option:not(.disabled)').first();
	          if ($next.length > 0) {
	            $dropdown.find('.focus').removeClass('focus');
	            $next.addClass('focus');
	          }
	        }
	        return false;
	      /**
	       * 向上键
	       */
	      } else if (event.keyCode == 38) {
	        if (!$dropdown.hasClass('open')) {
	          $dropdown.trigger('click');
	        } else {
	          var $prev = $focused_option.prevAll('.option:not(.disabled)').first();
	          if ($prev.length > 0) {
	            $dropdown.find('.focus').removeClass('focus');
	            $prev.addClass('focus');
	          }
	        }
	        return false;
	      /**
	       * Esc键
	       */
	      } else if (event.keyCode == 27) {
	        if ($dropdown.hasClass('open')) {
	          $dropdown.trigger('click');
	        }
	      /**
	       * Tab键
	       */
	      } else if (event.keyCode == 9) {
	        if ($dropdown.hasClass('open')) {
	          return false;
	        }
	      }
	    });
	    /**
	     * 样式兼容,ie10以下的
	     */
	    var style = document.createElement('a').style;
	    style.cssText = 'pointer-events:auto';
	    if (style.pointerEvents !== 'auto') {
	      $('html').addClass('no-csspointerevents');
	    }
	    
    }
    
   /**
    * 关闭当前选中的列表
    */
    $(document).on('click.nice_select', function(event) {
    	var elementDiv = $(".nice-select.open").siblings("select");
      if ($(event.target).closest('.nice-select').length === 0) {
      	if(elementDiv.find("option:selected").length==0
      	||elementDiv.find("option:selected").val()==""
      	||elementDiv.find("option:selected").val()==undefined
      	||elementDiv.next().find("li.selected").length==0 
      	|| elementDiv.next().find(".search-current").val() == ""){
      		
      		 if(elementDiv.next().find(".search-current").val() == ""){
	      	  	elementDiv.next().find(".search-current").val("请选择");
	      	  }
      		if(option&&option.search){
      			elementDiv.prepend("<option value='' selected='selected'></option>")
      		}
      	}else{
      		//elementDiv.next().find(".search-current").val(elementDiv.find("option:selected").text());
      	}
        $('.nice-select').removeClass('open').find('.option');  
      }
    });
    return this;
   },isOnScreen: function(){    
      var win = $(window);       
      var viewport = {
          top : win.scrollTop(),
          left : win.scrollLeft()
      };
      viewport.right = viewport.left + win.width();
      viewport.bottom = viewport.top + win.height();
       
      var bounds = this.offset();
      bounds.right = bounds.left + this.outerWidth();
      bounds.bottom = bounds.top + this.outerHeight();
       
      return (!(viewport.right < bounds.left || viewport.left > bounds.right || viewport.bottom < bounds.top || viewport.top > bounds.bottom));
       
  }
})


function showHide( elements, show ) {
	var elem, display,
		values = [],
		index = 0,
		length = elements.length;

	for ( ; index < length; index++ ) {
		elem = elements[ index ];
		if ( !elem.style ) {
			continue;
		}
		values[ index ] = jQuery._data( elem, "olddisplay" );
		if ( show ) {
			// Reset the inline display of this element to learn if it is
			// being hidden by cascaded rules or not
			if ( !values[ index ] && elem.style.display === "none" ) {
				elem.style.display = "";
			}

			// Set elements which have been overridden with display: none
			// in a stylesheet to whatever the default browser style is
			// for such an element
			if ( elem.style.display === "" && isHidden( elem ) ) {
				values[ index ] = jQuery._data( elem, "olddisplay", css_defaultDisplay(elem.nodeName) );
			}
		} else {
			display = curCSS( elem, "display" );

			if ( !values[ index ] && display !== "none" ) {
				jQuery._data( elem, "olddisplay", display );
			}
		}
	}

	// Set the display of most of the elements in a second loop
	// to avoid the constant reflow
	for ( index = 0; index < length; index++ ) {
		elem = elements[ index ];
		if ( !elem.style ) {
			continue;
		}
		if ( !show || elem.style.display === "none" || elem.style.display === "" ) {
			elem.style.display = show ? values[ index ] || "" : "none";
		}
	}

	return elements;
}

function isHidden( elem, el ) {
	elem = el || elem;
	return jQuery.css( elem, "display" ) === "none" || !jQuery.contains( elem.ownerDocument, elem );
}
var curCSS,core_pnum = /[\-+]?(?:\d*\.|)\d+(?:[eE][\-+]?\d+|)/.source,	rnumnonpx = new RegExp( "^(" + core_pnum + ")(?!px)[a-z%]+$", "i" ),elemdisplay = { BODY: "block" };
// NOTE: To any future maintainer, we've window.getComputedStyle
// because jsdom on node.js will break without it.
if ( window.getComputedStyle ) {
	curCSS = function( elem, name ) {
		var ret, width, minWidth, maxWidth,
			computed = window.getComputedStyle( elem, null ),
			style = elem.style;

		if ( computed ) {

			// getPropertyValue is only needed for .css('filter') in IE9, see #12537
			ret = computed.getPropertyValue( name ) || computed[ name ];

			if ( ret === "" && !jQuery.contains( elem.ownerDocument, elem ) ) {
				ret = jQuery.style( elem, name );
			}

			// A tribute to the "awesome hack by Dean Edwards"
			// Chrome < 17 and Safari 5.0 uses "computed value" instead of "used value" for margin-right
			// Safari 5.1.7 (at least) returns percentage for a larger set of values, but width seems to be reliably pixels
			// this is against the CSSOM draft spec: http://dev.w3.org/csswg/cssom/#resolved-values
			if ( rnumnonpx.test( ret ) && rmargin.test( name ) ) {
				width = style.width;
				minWidth = style.minWidth;
				maxWidth = style.maxWidth;

				style.minWidth = style.maxWidth = style.width = ret;
				ret = computed.width;

				style.width = width;
				style.minWidth = minWidth;
				style.maxWidth = maxWidth;
			}
		}

		return ret;
	};
} else if ( document.documentElement.currentStyle ) {
	curCSS = function( elem, name ) {
		var left, rsLeft,
			ret = elem.currentStyle && elem.currentStyle[ name ],
			style = elem.style;

		// Avoid setting ret to empty string here
		// so we don't default to auto
		if ( ret == null && style && style[ name ] ) {
			ret = style[ name ];
		}

		// From the awesome hack by Dean Edwards
		// http://erik.eae.net/archives/2007/07/27/18.54.15/#comment-102291

		// If we're not dealing with a regular pixel number
		// but a number that has a weird ending, we need to convert it to pixels
		// but not position css attributes, as those are proportional to the parent element instead
		// and we can't measure the parent instead because it might trigger a "stacking dolls" problem
		if ( rnumnonpx.test( ret ) && !rposition.test( name ) ) {

			// Remember the original values
			left = style.left;
			rsLeft = elem.runtimeStyle && elem.runtimeStyle.left;

			// Put in the new values to get a computed value out
			if ( rsLeft ) {
				elem.runtimeStyle.left = elem.currentStyle.left;
			}
			style.left = name === "fontSize" ? "1em" : ret;
			ret = style.pixelLeft + "px";

			// Revert the changed values
			style.left = left;
			if ( rsLeft ) {
				elem.runtimeStyle.left = rsLeft;
			}
		}

		return ret === "" ? "auto" : ret;
	};
}

// Try to determine the default display value of an element
function css_defaultDisplay( nodeName ) {
	if ( elemdisplay[ nodeName ] ) {
		return elemdisplay[ nodeName ];
	}

	var elem = jQuery( "<" + nodeName + ">" ).appendTo( document.body ),
		display = elem.css("display");
	elem.remove();

	// If the simple way fails,
	// get element's real default display by attaching it to a temp iframe
	if ( display === "none" || display === "" ) {
		// Use the already-created iframe if possible
		iframe = document.body.appendChild(
			iframe || jQuery.extend( document.createElement("iframe"), {
				frameBorder: 0,
				width: 0,
				height: 0
			})
		);

		// Create a cacheable copy of the iframe document on first call.
		// IE and Opera will allow us to reuse the iframeDoc without re-writing the fake HTML
		// document to it; WebKit & Firefox won't allow reusing the iframe document.
		if ( !iframeDoc || !iframe.createElement ) {
			iframeDoc = ( iframe.contentWindow || iframe.contentDocument ).document;
			iframeDoc.write("<!doctype html><html><body>");
			iframeDoc.close();
		}

		elem = iframeDoc.body.appendChild( iframeDoc.createElement(nodeName) );

		display = curCSS( elem, "display" );
		document.body.removeChild( iframe );
	}

	// Store the correct default display
	elemdisplay[ nodeName ] = display;

	return display;
}

/**
 * 
 *
 *处理自定义方法setDatas()赋值数据方法中keys在ie浏览器下的兼容性
 *
 *
 */
var DONT_ENUM =  "propertyIsEnumerable,isPrototypeOf,hasOwnProperty,toLocaleString,toString,valueOf,constructor".split(","),
    hasOwn = ({}).hasOwnProperty;
    for (var i in {
        toString: 1
    }){
        DONT_ENUM = false;
    }
 
 
    Object.keys = Object.keys || function(obj){//ecma262v5 15.2.3.14
            var result = [];
            for(var key in obj ) if(hasOwn.call(obj,key)){
                result.push(key) ;
            }
            if(DONT_ENUM && obj){
                for(var i = 0 ;key = DONT_ENUM[i++]; ){
                    if(hasOwn.call(obj,key)){
                        result.push(key);
                    }
                }
            }
            return result;
        };