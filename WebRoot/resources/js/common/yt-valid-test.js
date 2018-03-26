/**
 * 
 * 
 * 使用方法说明:
 * 1.给需要验证的标签加上validform自定义属性,json格式
 * 2.validform自定义json格式参数有一下:
 * 	 isNull 		验证是否为空true和false
 *	 size  		 	长度验证 int类型
 * 	 validType      提示类型,hoverBorder值,鼠标悬浮到标签,显示提示信息,验证错误,当前标签标红框
 *	 type  			 验证类型,如:特殊字符验证,手机号,邮箱,等个例。还可以直接加正则表达式例:type:'^[0-9]+$',详见验证说明下面的验证类型说明标注
 *	 blurFlag  		是否支持失去焦点验证,true和false,默认false不支持
 *	 msg         	 验证提示信息
 * 3.使用示例如下:
 *   <input type="text" value="" validform="{isNull:true,size:10,type:'pattern',blurFlag:true,msg:'重要信息不能为空,最多10个字'}" />
 */
/**
 * 	验证类型说明:
 * 	phone   手机号
 * 	telPhone 电话号
 * 	email    邮箱
 * 	fax      传真
 * 	idCard   身份证
 * 	pattern  特殊字符
 * 	upperCase 大写字母
 */
/**
 * 
 * 
 * 验证方法
 * 
 * 
 */

;(function($) {
	$.fn.extend({
		/**
		 * 统一验证方法
		 * @param {Object} obj 操作对象
		 */
		validForm: function() {
			//1.定义验证标识
			var validFlag = true;
			//2.获取接收传的对象
			var thisObj = $(this);
			var attrData = "";
			var jsonObj = "";
			//3.遍历所有的带有验证自定义属性的标签
			$.each(thisObj.find("[validform]"), function(i, n) {
				attrData = $(n).attr('validform');
				jsonObj = eval("(" + attrData + ")");
				//4.调用判断验证是否成功方法,传输当前标签和获取的自定义属性参数值
				 if(!validShowFlag($(n),jsonObj)){
				 	validFlag = false;
				 };
			});
			return validFlag;
		}
	});
	/**
	 * 
	 * 生成悬浮框提示信息方法
	 * @param {Object} obj 操作对象
	 * 
	 */
	function  validFontHoverMsg(obj){
		//1.定义验证标识
		var validFlag = true;
		//2.获取自定义属性
		var attrData = $(obj).attr('validform');
		//3.转json对象
		var jsonObj = eval("(" + attrData + ")");
		//4.调用生成提示框方法
		if($(obj)[0].tagName =="SELECT"){
			$(obj).next(".nice-select").poshytip({
				className: 'tip-valid-sty',
				content:jsonObj.msg,
				showOn: 'none',
				alignTo: 'target',
				alignX: 'right',
				alignY: 'center',
				offsetX: 8,
				offsetY: 10
			});
		}else{
			$(obj).poshytip({
				className: 'tip-valid-sty',
				content:jsonObj.msg,
				showOn: 'none',
				alignTo: 'target',
				alignX: 'right',
				alignY: 'center',
				offsetX: 8,
				offsetY: 10
			});
		}
		
	}
	/**
	 * 
	 * 对字段进行验证方法
	 * @param {Object} obj当前对象
	 * 
	 */
	function  validFont(obj) {
		//验证标识
		var validFlag = true;
		//获取自定义属性
		var attrData = $(obj).attr('validform');
		//转json对象
		var jsonObj = eval("(" + attrData + ")");
		//得到当前对象值
		var thisVal = "";
		//判断当前标签是不是select标签
		if($(obj)[0].tagName !="SELECT"){
			thisVal = $(obj).val()||$(obj).text();
		}else{
			thisVal = $(obj).val();
		}
		/**
		 * 验证非空
		 */
		if(jsonObj.isNull && jsonObj.isNull != undefined) {
			if(thisVal == "") {
				validFlag = false;
				return validFlag;
			}
		}
		/**
		 * 验证字段长度
		 */
		if(jsonObj.size != "" && jsonObj.size != undefined) {
			if(thisVal.length > jsonObj.size) {
				validFlag = false;
				return validFlag;
			}
		}
		/**
		 * 根据传入的验证类型,进行验证
		 */
		if(jsonObj["type"] !="" && jsonObj["type"] != undefined) {
			var reg = "";
			//自己传的正则
			if(_reg[jsonObj["type"]]){
				reg = _reg[jsonObj["type"]];
			}else{
				reg =  new   RegExp(jsonObj["type"]);   
			}
			if(!reg.test(thisVal) && thisVal!="") {
				validFlag = false;
				return validFlag;
			}
		}
		
		return validFlag;
	}
	/**
	 * 标识字段是否验证成功统一方法
	 * @param {Object} obj 当前标签
	 * @param {Object} jsonObj 自定义属性,json参数值
	 */
	function validShowFlag(obj,jsonObj){
			//1.调用生成验证提示信息方法,传输当前标签参数
			validFontHoverMsg($(obj));
			//2.定义变量接收,字段验证方法的返回值,传输当前标签值
			var validState = validFont($(obj));
		   if(!validState){
	 	    //3.判断验证类型,jsonObj.validType的值是hoverBorder代表悬浮显示提示信息加红框标识
			if(jsonObj.validType == "hoverBorder"){
				//添加标红框类样式
				$(obj).addClass("valid-bor-sty");
				//针对下拉列表的
				$(obj).next(".nice-select").addClass("valid-bor-sty");
				//判断如果是下拉列表的时候,写单独的鼠标移入移出事件
				if($(obj)[0].tagName =="SELECT"){
					//移入事件
					$(obj).next(".nice-select").bind("mouseover",function(){
						$(obj).next(".nice-select").css("cursor","pointer");
						$(obj).next(".nice-select").poshytip('show');
					});
					//移出事件
					$(obj).next(".nice-select").bind("mouseout",function(){
						$(obj).next(".nice-select").css("cursor","default");
						$(obj).next(".nice-select").poshytip('hide');
					});
				}
				//当前标签鼠标移入事件(显示验证提示语句)
				$(obj).bind("mouseover",function(){
					$(obj).css("cursor","pointer");
				    $(obj).poshytip('show');
				});
				//当前鼠标移出事件(隐藏验证提示语句)
				$(obj).bind("mouseout",function(){
					$(obj).css("cursor","default");
					$(obj).poshytip('hide');
				});
			}else{
				$(obj).nextAll('.valid-font').text(jsonObj.msg);
			}
			
	 	}else{
	 		/*
			 * 判断验证类型,jsonObj.validType的值是hoverBorder代表悬浮显示提示信息加红框标识
			 */
			if(jsonObj.validType == "hoverBorder"){
				//针对下拉列表的
				$(obj).next(".nice-select").removeClass("valid-bor-sty");
				$(obj).removeClass("valid-bor-sty");
				//移除鼠标悬浮事件
				$(obj).unbind("mouseover mouseout");
				//隐藏提示信息框
				$(obj).poshytip('hide');
				if($(obj)[0].tagName =="SELECT"){
				   $(obj).next(".nice-select").unbind("mouseover mouseout");
				   $(obj).next(".nice-select").poshytip('hide');
				}
			}else{
				$(obj).nextAll('.valid-font').text("");
			}
		
	 	}
	 	return  validState;
	}
	/**
	 * 
	 * 
	 * 
	 * 字段失去焦点验证触发事件
	 * 
	 * 
	 */
	$("body").delegate("[validform]","blur",function() {
		//获取自定义属性
		var attrData = $(this).attr('validform');
		//转json对象
		var jsonObj = eval("(" + attrData + ")"); 
		/**
		 * 
		 * 获取失去焦点验证标识(true失去焦点验证,false失去焦点不验证)
		 * 
		 */
		if(jsonObj.blurFlag){
			/**
		 	 * 调用接收验证是否成功操作方法,传入当前对象和自定义属性json参数
			 */
		    validShowFlag($(this),jsonObj);
		}
	});
	/**
	 * 
	 * 
	 * 
	 *文本框内容改变监听事件
	 * 
	 * 
	 */
	$("body").delegate("[validform]","input propertychange",function() {
		//隐藏提示信息框
		$(this).poshytip('hide');
		//获取自定义属性
		var attrData = $(this).attr('validform');
		//转json对象
		var jsonObj = eval("(" + attrData + ")"); 
		/**
	 	 * 调用接收验证是否成功操作方法,传入当前对象和自定义属性json参数
		 */
	    validShowFlag($(this),jsonObj);
	});
	/**
	 * 
	 * 
	 * 监听下拉列表的改变事件
	 * 
	 * 
	 */
	$("body").delegate("[validform]","select change",function(){
		//隐藏提示信息框
		$(this).next(".nice-select").poshytip('hide');
		//获取自定义属性
		var attrData = $(this).attr('validform');
		//转json对象
		var jsonObj = eval("(" + attrData + ")"); 
		/**
	 	 * 调用接收验证是否成功操作方法,传入当前对象和自定义属性json参数
		 */
	    validShowFlag($(this),jsonObj);
	});
	
})(jQuery);

/*valid 1.0
 *  _reg.valiNum(obj)               验证数字
    _reg.valiNormal(obj)               验证空值
    _reg.valiNormal(obj,num)          验证长度 
    _reg.valiNumPos(obj)              验证正整数七位
    _reg.valiNumNeg(obj)              验证负整数
    _reg.valiNumFloat(obj)             验证浮点数
    _reg.valiLetterEn(obj)               验证英文字母 不限大小写
    _reg.regLetterEnNum(obj)               验证英文字母数字 不限大小写
    _reg.regLetterEnNumCh(obj)               验证英文字母数字汉字不限大小写
    _reg.valiEmail(obj)                     验证邮箱
   _reg.valiTelePhone(obj)               验证固定电话
    _reg.valiCellPhone(obj)                验证手机号
    _reg.valiIdCard(obj)                      验证身份证 非严格模式
    _reg.valiUrl(obj)                              验证URL地址
    _reg.valiArray(obj,obj2)                   两个数组对比
 */
var _reg = {
	regNum: /^[0-9]+$/, //数字
	regNumPos: /^[1-9]\d{0,7}$/, //正整数
	regNumNatural: /^\d{0,6}$/, //自然数
	regNumNeg: /^-[1-9]\d{0,6}$/, //负整数
	regNumFloat: /(^[1-9]([0-9]{0,10})?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, //浮点数
	regLetterEn: /^[A-Za-z]+$/, //英文字母 不限大小写
	regLetterEnCh: /^[A-Za-z_\-\u4e00-\u9fa5]+$/, //英文字母 不限大小写
	regLetterEnNum: /^[A-Za-z0-9]+$/, //英文字母 不限大小写
	regLetterEnNumCh: /^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/, //英文字母 不限大小写
	regLetterEnNumChTwo: /^[A-Za-z0-9_\-\u4e00-\u9fa5\(\)]+$/, //英文字母 不限大小写和小括号
	regLetterEnNumChSys: /^[A-Za-z0-9\(\)\-\u4e00-\u9fa5]+$/, //英文字母 不限大小写
	regNumberBraces: /^[0-9\-\(\)\（\）]+$/, //数字、下划线和小括号（中英文）
	fax: /^(\d{3,4}-)?\d{7,8}$/, //传真号
	email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, //邮箱
	emailPostfix: /^@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, //邮箱
	telPhone: /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{8}$/, //固定电话
	phone: /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/, //手机号
	idCard: /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/, //验证身份证 非严格模式
	regUrl: /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/, //URL地址
	num: 0, //判断值小于某值
	otherReg: /^otherReg$/, //其他正则表达式
	upperCase: /^[A-Z]/, //大写英文字母
	validUrl:/[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/,//验证网址
	pattern: /[^`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]{1,}/, //特殊字符
	filter: function(obj, obj2) {
		_reg.num = (obj2 == undefined || obj2 == null || typeof obj2 == "string") ? 20 : obj2;
		if(obj == undefined) {
			tools.Notice("obj is undefined! From the vali.js");
			return false;
		} else if(typeof obj == "string") {
			return $.trim(obj);
		} else if(typeof obj == "object" && obj.get(0).nodeName == "INPUT") {
			return $(obj).val();
		} else if(typeof obj == "object" && obj.get(0).nodeName == "TEXTAREA") {
			return $(obj).val();
		} else if(obj.length != 0 && obj2.length != 0 && typeof obj2 == "object") {
			$.map(obj, function(index, elem) {
				if($(elem).val().length > 0) {
					return $(elem).val();
				}
			});
			return(obj.length != obj2.length);
		}
	},
	valiNormal: function(obj, obj2) {
		if(typeof obj2 == "number") {
			return(_reg.filter(obj, obj2).length >= _reg.num) ? true : false;
		} else {
			return(_reg.filter(obj, obj2).length == 0) ? true : false;
		}
	},
	valiNum: function(obj, obj2) {
		return(_reg.regNum.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiNumPos: function(obj, obj2) {
		return(_reg.regNumPos.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiNumNatural: function(obj, obj2) {
		return(_reg.regNumNatural.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiNumNeg: function(obj, obj2) {
		return(_reg.regNumNeg.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiNumFloat: function(obj, obj2) {
		return(_reg.regNumFloat.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiLetterEn: function(obj, obj2) {
		return(_reg.regLetterEn.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiLetterEnCh: function(obj, obj2) {
		return(_reg.regLetterEnCh.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiLetterEnNum: function(obj, obj2) {
		return(_reg.regLetterEnNum.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiLetterEnNumCh: function(obj, obj2) {
		return(_reg.regLetterEnNumCh.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiLetterEnNumChSys: function(obj, obj2) {
		return(_reg.regLetterEnNumChSys.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiEmail: function(obj, obj2) {
		return(_reg.email.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiEmailPostfix: function(obj, obj2) {
		return(_reg.regEmailPostfix.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiTelePhone: function(obj, obj2) {
		return(_reg.telPhone.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiCellPhone: function(obj, obj2) {
		return(_reg.phone.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiIdCard: function(obj, obj2) {
		return(_reg.idCard.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiUrl: function(obj, obj2) {
		return(_reg.regUrl.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiArray: function(obj, obj2) {
		return(_reg.regNumFloat.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiLetterEnNumChTwo: function(obj, obj2) {
		return(_reg.regLetterEnNumChTwo.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiFax: function(obj, obj2) {
		return(_reg.fax.test(_reg.filter(obj, obj2))) ? false : true;
	},
	valiPattern: function(obj, obj2) {
		return(_reg.pattern.test(_reg.filter(obj, obj2))) ? true : false;
	},
	valiRegNumberBraces: function(obj, obj2) {
		return(_reg.regNumberBraces.test(_reg.filter(obj, obj2))) ? false : true;
	},

	valiOther: function(obj, obj2) {
		if(_reg.otherReg.test("otherReg")) {
			tools.Notice("Please reset the otherReg before use this function!!");
		} else {
			return(_reg.otherReg.test(_reg.filter(obj, obj2))) ? false : true;
		}
	}
};
var idCardNoUtil = {
	/*省,直辖市代码表*/
	provinceAndCitys: {
		11: "北京",
		12: "天津",
		13: "河北",
		14: "山西",
		15: "内蒙古",
		21: "辽宁",
		22: "吉林",
		23: "黑龙江",
		31: "上海",
		32: "江苏",
		33: "浙江",
		34: "安徽",
		35: "福建",
		36: "江西",
		37: "山东",
		41: "河南",
		42: "湖北",
		43: "湖南",
		44: "广东",
		45: "广西",
		46: "海南",
		50: "重庆",
		51: "四川",
		52: "贵州",
		53: "云南",
		54: "西藏",
		61: "陕西",
		62: "甘肃",
		63: "青海",
		64: "宁夏",
		65: "新疆",
		71: "台湾",
		81: "香港",
		82: "澳门",
		91: "国外"
	},

	/*每位加权因子*/
	powers: ["7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"],

	/*第18位校检码*/
	parityBit: ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"],

	/*性别*/
	genders: { male: "1", female: "2" },

	/*校验地址码*/
	checkAddressCode: function(addressCode) {
		var check = /^[1-9]\d{5}$/.test(addressCode);
		if(!check) return false;
		if(idCardNoUtil.provinceAndCitys[parseInt(addressCode.substring(0, 2))]) {
			return true;
		} else {
			return false;
		}
	},
	getAddressCodeName: function(idCardNo) {
		idCardNo = idCardNo.substring(0, 6);
		return idCardNoUtil.provinceAndCitys[parseInt(idCardNo.substring(0, 2))];

	},
	/*校验日期码*/
	checkBirthDayCode: function(birDayCode) {
		var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
		if(!check) return false;
		var yyyy = parseInt(birDayCode.substring(0, 4), 10);
		var mm = parseInt(birDayCode.substring(4, 6), 10);
		var dd = parseInt(birDayCode.substring(6), 10);
		var xdata = new Date(yyyy, mm - 1, dd);
		if(xdata > new Date()) {
			return false; //生日不能大于当前日期
		} else if((xdata.getFullYear() == yyyy) && (xdata.getMonth() == mm - 1) && (xdata.getDate() == dd)) {
			return true;
		} else {
			return false;
		}
	},

	/*计算校检码*/
	getParityBit: function(idCardNo) {
		var id17 = idCardNo.substring(0, 17);
		/*加权 */
		var power = 0;
		for(var i = 0; i < 17; i++) {
			power += parseInt(id17.charAt(i), 10) * parseInt(idCardNoUtil.powers[i]);
		}
		/*取模*/
		var mod = power % 11;
		return idCardNoUtil.parityBit[mod];
	},

	/*验证校检码*/
	checkParityBit: function(idCardNo) {
		var parityBit = idCardNo.charAt(17).toUpperCase();
		if(idCardNoUtil.getParityBit(idCardNo) == parityBit) {
			return true;
		} else {
			return false;
		}
	},

	/*校验15位或18位的身份证号码*/
	checkIdCardNo: function(idCardNo) {
		//15位和18位身份证号码的基本校验
		var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
		if(!check) return false;
		//判断长度为15位或18位  
		if(idCardNo.length == 15) {
			return idCardNoUtil.check15IdCardNo(idCardNo);
		} else if(idCardNo.length == 18) {
			return idCardNoUtil.check18IdCardNo(idCardNo);
		} else {
			return false;
		}
	},

	//校验15位的身份证号码
	check15IdCardNo: function(idCardNo) {
		//15位身份证号码的基本校验
		var check = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/.test(idCardNo);
		if(!check) return false;
		//校验地址码
		var addressCode = idCardNo.substring(0, 6);
		check = idCardNoUtil.checkAddressCode(addressCode);
		if(!check) return false;
		var birDayCode = '19' + idCardNo.substring(6, 12);
		//校验日期码
		return idCardNoUtil.checkBirthDayCode(birDayCode);
	},

	//校验18位的身份证号码
	check18IdCardNo: function(idCardNo) {
		//18位身份证号码的基本格式校验
		var check = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/.test(idCardNo);
		if(!check) return false;
		//校验地址码
		var addressCode = idCardNo.substring(0, 6);
		check = idCardNoUtil.checkAddressCode(addressCode);
		if(!check) return false;
		//校验日期码
		var birDayCode = idCardNo.substring(6, 14);
		check = idCardNoUtil.checkBirthDayCode(birDayCode);
		if(!check) return false;
		//验证校检码   
		return idCardNoUtil.checkParityBit(idCardNo);
	},

	formateDateCN: function(day) {
		var yyyy = day.substring(0, 4);
		var mm = day.substring(4, 6);
		var dd = day.substring(6);
		return yyyy + '-' + mm + '-' + dd;
	},

	//获取信息
	getIdCardInfo: function(idCardNo) {
		var idCardInfo = {
			gender: "", //性别
			birthday: "", // 出生日期(yyyy-mm-dd)
			addressName: ""
		};
		if(idCardNo.length == 15) {
			var aday = '19' + idCardNo.substring(6, 12);
			idCardInfo.birthday = idCardNoUtil.formateDateCN(aday);
			if(parseInt(idCardNo.charAt(14)) % 2 == 0) {
				idCardInfo.gender = idCardNoUtil.genders.female;
			} else {
				idCardInfo.gender = idCardNoUtil.genders.male;
			}
			idCardInfo.addressName = idCardNoUtil.getAddressCodeName(idCardNo);
		} else if(idCardNo.length == 18) {
			var aday = idCardNo.substring(6, 14);
			idCardInfo.birthday = idCardNoUtil.formateDateCN(aday);
			if(parseInt(idCardNo.charAt(16)) % 2 == 0) {
				idCardInfo.gender = idCardNoUtil.genders.female;
			} else {
				idCardInfo.gender = idCardNoUtil.genders.male;
			}
			idCardInfo.addressName = idCardNoUtil.getAddressCodeName(idCardNo);
		}
		return idCardInfo;
	},

	/*18位转15位*/
	getId15: function(idCardNo) {
		if(idCardNo.length == 15) {
			return idCardNo;
		} else if(idCardNo.length == 18) {
			return idCardNo.substring(0, 6) + idCardNo.substring(8, 17);
		} else {
			return null;
		}
	},

	/*15位转18位*/
	getId18: function(idCardNo) {
		if(idCardNo.length == 15) {
			var id17 = idCardNo.substring(0, 6) + '19' + idCardNo.substring(6);
			var parityBit = idCardNoUtil.getParityBit(id17);
			return id17 + parityBit;
		} else if(idCardNo.length == 18) {
			return idCardNo;
		} else {
			return null;
		}
	}
};
/**
 * 验证日期
 */
var dateTime = {
	validDateTime: function(startTime, endTime) {
		var d1 = new Date(startTime.replace(/\-/g, "\/"));
		var d2 = new Date(endTime.replace(/\-/g, "\/"));
		if(startTime != "" && endTime != "" && d1 > d2) {
			return false;
		}
		return true;
	},
	formatDate: function(format) {
		var mydata = new Date();
		var o = {
			"M+": mydata.getMonth() + 1, // month  
			"d+": mydata.getDate(), // day  
			"h+": mydata.getHours(), // hour  
			"m+": mydata.getMinutes(), // minute  
			"s+": mydata.getSeconds(), // second  
			"q+": Math.floor((mydata.getMonth() + 3) / 3), // quarter  
			"S": mydata.getMilliseconds()
		}

		if(/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (mydata.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for(var k in o) {
			if(new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}
}