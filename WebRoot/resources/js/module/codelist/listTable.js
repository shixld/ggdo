//获取列表数据的VUE代码
var v = new Vue({
	el: '#class-list-box',
	data: {
		tableList : ""
	},
	methods: {
		//请求后台获取表相关信息
		tableData: function() {
			var actionUrl = "ggdo/getListTables.action";
			$.ajax({
				url : actionUrl,
				type : 'post',
				async : false,
				data : "",
				success : function(data) {
					v.tableList = data;
				},
				complete:function(data) { 
  				}
			});
		}
   }
});
//调用获取数据的方法
v.tableData();

//DB的配置信息VUE的实现
var dbConfigVue = new Vue({
  el: '#db_function',
  data: {
	dbDriver: "",
	dbUrl: "",
	dbUser: "",
	dbPass: "",
	dbTableSub: ""
  },
  methods: {
		//请求后台获取表相关信息
		dbConfig: function() {
			/*
			 *提交表单验证 
			 */
			var actionUrl = "ggdo/toDBConfig.action";
			$.ajax({
				url : actionUrl,
				type : 'post',
				async : false,
				data : "",
				success : function(data) {
					dbConfigVue.dbDriver = data.dbDriver;
					dbConfigVue.dbUrl =  data.dbUrl;
					dbConfigVue.dbUser = data.dbUser;
					dbConfigVue.dbPass = data.dbPass;
					dbConfigVue.dbTableSub = data.dbTableSub;
				},
				complete:function(data) { 
  				}
			});
		}
	}
})

//Path的配置信息VUE的实现
var pathConfigVue = new Vue({
  el: '#path_function',
  data: {
	PathTemplate: "",
	PathProject: "",
	PathJava: "",
	PathJsp: "",
	PathMainMybatis: "",
	PathMybatis: "",
	GenPathHead: "",
	PathResources: "",
	FilePathMenu: ""
  },
  methods: {
		//请求后台获取表相关信息
		pathConfig: function() {
			/*
			 *提交表单验证 
			 */
			var actionUrl = "ggdo/toPathConfig.action";
			$.ajax({
				url : actionUrl,
				type : 'post',
				async : false,
				data : "",
				success : function(data) {
					pathConfigVue.PathTemplate = data.PathTemplate;
					pathConfigVue.PathProject = data.PathProject;
					pathConfigVue.PathJava = data.PathJava;
					pathConfigVue.PathJsp = data.PathJsp;
					pathConfigVue.PathMainMybatis = data.PathMainMybatis;
					pathConfigVue.PathMybatis = data.PathMybatis;
					pathConfigVue.GenPathHead = data.GenPathHead;
					pathConfigVue.PathResources = data.PathResources;
					pathConfigVue.FilePathMenu = data.FilePathMenu;
				}
			});
		}
	}
})

function dbConfig(){
	/** 
	 * 显示编辑弹出框和显示顶部隐藏蒙层 
	 */  
	$("#db_function").show();  
	/** 
	 * 调用算取div显示位置方法 
	 */  
	$yt_alert_Model.getDivPosition($(".db-edit-alert"));  

	dbConfigVue.dbConfig();
	
}

function pathConfig(){
	/** 
	 * 显示编辑弹出框和显示顶部隐藏蒙层 
	 */  
	$("#path_function").show();  
	/** 
	 * 调用算取div显示位置方法 
	 */  
	$yt_alert_Model.getDivPosition($(".path-edit-alert"));  
	/*
	 *提交表单验证 
	 */
	pathConfigVue.pathConfig();
}

function codeGenerate(){
	/*
	 *提交表单验证 
	 */
	var selectcount = checkselect("ids");
	var datas = $("input[id='ids']").serialize();
	if (selectcount > 0) {
		$.ajax({
			url : 'ggdo/codeGenerate.action',
			type : 'post',
			async : false,
			data : datas,
			success : function(data) {
				if (data.success) {
					alert(data.msg, 1000); 
				} else {
					alert("操作失败！", 1000); 
				}
				getTableList();
			}
		});
	}else {
		alert("请选择需要生成的表!");
	}
}

function saveDb(){
	/*
	 *提交表单验证 
	 */
	$.ajax({
				url : 'ggdo/dbConfig.action',
				type : 'post',
				async : false,
				data : $("#dbForm").serialize(),
				success : function(data) {
					if (data.success) {
						alert(data.msg, 1000); 
					} else {
						alert("操作失败！", 1000); 
					}
					cancelDb();
					getTableList();
				}
	});
}

function cancelDb(){
	//隐藏页面中自定义的表单内容  
	$("#db_function").hide();  
	//隐藏蒙层  
	$("#pop-modle-alert").hide(); 
}

function savePath(){
	/*
	 *提交表单验证 
	 */
	$.ajax({
				url : 'ggdo/pathConfig.action',
				type : 'post',
				async : false,
				data : $("#pathForm").serialize(),
				success : function(data) {
					if (data.success) {
						alert(data.msg, 1000); 
					} else {
						alert("操作失败！", 1000); 
					}
					cancelPath();
					getTableList();
				}
	});
}

function cancelPath(){
	//隐藏页面中自定义的表单内容  
	$("#path_function").hide();  
	//隐藏蒙层  
	$("#pop-modle-alert").hide(); 
}
//获取表的信息
function getTableList(){
	var actionUrl = "ggdo/getListTables.action";
	$.ajax({
		url : actionUrl,
		type : 'post',
		async : false,
		data : "",
		success : function(data) {
			v.tableList = data;
		},
		complete:function(data) { 
		}
	});
}