<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>生成代码页面</title>
		<jsp:include flush="true" page="/share/top.jsp" />
		<script language="Javascript" src="resources/js/checkboxSelect.js"></script>
        <script language="javascript" type="text/javascript">
            function on_select(){
                if (document.getElementById("checkbox2").checked == true) {
                    var checkboxs = document.getElementsByName("ids");
                    for (var i = 0; i < checkboxs.length; i++) {
                        checkboxs[i].checked = true;
                    }
                }
                else {
                    var checkboxs = document.getElementsByName("ids");
                    for (var i = 0; i < checkboxs.length; i++) {
                        checkboxs[i].checked = false;
                    }
                }
            }
            function dbConfig(){
            	$(".modal_bg").show();
				bgObj.openBgJs();
				$("#db_function").show();
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
								var obj = new Function("return" + data)();
								$("#dbDriver").val(obj.dbDriver); 
								$("#dbUrl").val(obj.dbUrl); 
								$("#dbUser").val(obj.dbUser); 
								$("#dbPass").val(obj.dbPass); 
								$("#dbTableSub").val(obj.dbTableSub); 
							}
				});
            }
            
            function pathConfig(){
                $(".modal_bg").show();
				bgObj.openBgJs();
				$("#path_function").show();
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
								var obj = new Function("return" + data)();
								$("#PathTemplate").val(obj.PathTemplate); 
								$("#PathProject").val(obj.PathProject); 
								$("#PathJava").val(obj.PathJava); 
								$("#PathJsp").val(obj.PathJsp); 
								$("#PathMainMybatis").val(obj.PathMainMybatis); 
								$("#PathMybatis").val(obj.PathMybatis); 
								$("#GenPathHead").val(obj.GenPathHead); 
								$("#PathResources").val(obj.PathResources); 
								$("#FilePathMenu").val(obj.FilePathMenu); 
							}
				});
            }
            
            function codeGenerate(){
                /*var selectcount = checkselect("ids");
                if (selectcount > 0) {
                    $("form[id='theForm']").attr('action', 'ggdo/codeGenerate.action');
                    $("form[id='theForm']").submit();
                }else {
                    alert("请选择需要生成的表!");
                }*/
                /*
				 *提交表单验证 
				 */
				var selectcount = checkselect("ids");
                if (selectcount > 0) {
					$.ajax({
								url : 'ggdo/codeGenerate.action',
								type : 'post',
								async : false,
								data : $("#theForm").serialize(),
								success : function(data) {
									var obj = new Function("return" + data)();
									if (obj.success) {
										prompt(obj.msg);
									} else {
										prompt("操作失败！");
									}
									location.href = "ggdo/toListTables.action";
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
								closeAddModel();
								var obj = new Function("return" + data)();
								if (obj.success) {
									prompt(obj.msg);
								} else {
									prompt("操作失败！");
								}
								location.href = "ggdo/toListTables.action";
							}
				});
			}
			
			function cancelDb(){
				closeAddModel();
				$("form[id='dbForm']").attr('action','ggdo/toListTables.action');
				$("form[id='dbForm']").submit();
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
								closeAddModel();
								var obj = new Function("return" + data)();
								if (obj.success) {
									prompt(obj.msg);
								} else {
									prompt("操作失败！");
								}
								location.href = "ggdo/toListTables.action";
							}
				});
			}
			
			function cancelPath(){
				closeAddModel();
				$("form[id='pathForm']").attr('action','ggdo/toListTables.action');
				$("form[id='pathForm']").submit();
			}
        </script>
	</head>
        
    <body style="margin:0; ">
    
    	<div class="contion" style="z-index: 1040; ">
           <div class="right-div">
           		
				 <div class="form-group col-xs-12 ">
		           		<button onclick="dbConfig();" class="btn btn-default btnconfig" id="add">
							<span class="glyphicon glyphicon-plus up-icon"></span>
							数据配置</button>
						<button onclick="pathConfig();"  class="btn btn-default btnconfig mar-lf-5">
							<span class="glyphicon glyphicon-pencil up-icon"></span>
							路径配置</button>
						<button onclick="codeGenerate();" class="btn btn-default btnconfig mar-lf-5">
							<span class="glyphicon glyphicon-remove add-icon"></span>
							代码生成</button>
		          </div>
				   
				   <form id="theForm" name="theForm" action="ggdo/toListTables.action" method="post">
				   <table id="infoTable" class="table table-bordered" style="font-size: 14px; margin-left: 15px;">
						 <thead class="tablehead">
						 	<tr>
		                        <th style="width: 10%">
		                            <input name="idsAll" id="idsAll" type="checkbox" onclick="page_on_select('idsAll','ids');" value="checkbox" />
		                        </th>
								<th><strong>表名</strong></th>
								<th><strong>别名</strong></th>
	                    	</tr>
	                    </thead>
	                    <tbody>
							<c:forEach items="${tableList}" var="tableName">
								<tr>
										<td style="display:none;"><input type="hidden" id="id" value="${tableName.tableName }"/></td>
										<td align=center>
	                                        <input id="ids" name="ids" type="checkbox" class="checkbox" value='${tableName.tableName}' />
	                                    </td>
	                                    <td>${tableName.tableName}</td>
	                                    <td>${tableName.tableComment}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
           </div>
        </div>
        
        <!-- 遮罩层STRAT -->
		<div class="shade"></div>
		<!-- 遮罩层END -->
		
		<!-- 新增弹出框 -->
	<div id="moda_alert_bg" class="modal_bg"></div>
	
	<div class="moda-function" id="db_function" style="display: none; position:absolute;z-index: 10001;height: 370px; left: 390px; top: 50px;">
     <form id="dbForm" name="dbForm" action="" method="post" role="form">
     			<div class="function-title">
					<img src="resources/images/down.png" class="down-icon" /><span id="editTitle">更新数据库信息</span>
				</div>
				<div class="panel-function">
					<table width=90% style="font-size: 14px; margin-left: 15px;">
						<tr>
							<td width=30% class="td-lf td-text-ri">驱动信息:</td>
							<td width=70% id="dbDriver1">
								<input type="text" name="dbDriver" id="dbDriver" class="form-control" placeholder="">
							</td>
						</tr>
						<tr>
							<td width=30% class="td-lf td-text-ri">链接路径:</td>
							<td width=70% id="dbUrl1">
								<input type="text" name="dbUrl" id="dbUrl" class="form-control" placeholder="">
							</td>
						</tr>
						<tr>
							<td width=30% class="td-lf td-text-ri">用户名称:</td>
							<td width=70% id="dbUser1">
								<input type="text" name="dbUser" id="dbUser" class="form-control" placeholder="">
							</td>
						</tr>
						<tr>
							<td width=30% class="td-lf td-text-ri">用户密码:</td>
							<td width=70% id="dbPass1">
								<input type="text" name="dbPass" id="dbPass" class="form-control" placeholder="">
							</td>
						</tr>
						<tr>
							<td width=30% class="td-lf td-text-ri">别名起始位置:</td>
							<td width=70% id="dbTableSub1">
								<input type="text" name="dbTableSub" id="dbTableSub" class="form-control" placeholder="">
							</td>
						</tr>
						<tr>
							<td colspan="2" class="col-xs-9" style="padding-bottom: 60px;">
								<input type="button" class="btn function-sure" onclick="saveDb();" value="确定"/>
					            <input type="button" class="btn function-canel" onclick="cancelDb();" value="取消"/>
							</td>
						</tr>
					</table>
			</div>
		</form>
	</div>
		
	<div class="moda-function" id="path_function" style="display: none; position:absolute;z-index: 10001; height: 580px; left: 390px; top: 50px;">
     <form id="pathForm" name="pathForm" action="" method="post" role="form">
     			<div class="function-title">
					<img src="resources/images/down.png" class="down-icon" /><span id="editTitle">更新路径信息</span>
				</div>
				<div class="panel-function">
					<table width=90% style="font-size: 14px; margin-left: 15px;">
						<tr>
							<td width=30% class="td-lf td-text-ri">模板路径:</td>
							<td width=70% id="PathTemplate1">
								<input type="text" name="PathTemplate" id="PathTemplate" class="form-control" placeholder="">
							</td>
						</tr>
						
						<tr>
							<td width=30% class="td-lf td-text-ri">项目路径:</td>
							<td width=70% id="PathProject1">
								<input type="text" name="PathProject" id="PathProject" class="form-control" placeholder="">
							</td>
						</tr>
						
						<tr>
							<td width=30% class="td-lf td-text-ri">java路径:</td>
							<td width=70% id="PathJava1">
								<input type="text" name="PathJava" id="PathJava" class="form-control" placeholder="">
							</td>
						</tr>
						
						<tr>
							<td width=30% class="td-lf td-text-ri">页面路径:</td>
							<td width=70% id="PathJsp1">
								<input type="text" name="PathJsp" id="PathJsp" class="form-control" placeholder="">
							</td>
						</tr>
						
						<tr>
							<td width=30% class="td-lf td-text-ri">MyBatis路径:</td>
							<td width=70% id="PathMybatis1">
								<input type="text" name="PathMybatis" id="PathMybatis" class="form-control" placeholder="">
							</td>
						</tr>
					
						<tr>
							<td width=30% class="td-lf td-text-ri">主MyBatis路径:</td>
							<td width=70% id="PathMainMybatis1">
								<input type="text" name="PathMainMybatis" id="PathMainMybatis" class="form-control" placeholder="">
							</td>
						</tr>
					
						<tr>
							<td width=30% class="td-lf td-text-ri">包路径前置信息:</td>
							<td width=70% id="GenPathHead1">
								<input type="text" name="GenPathHead" id="GenPathHead" class="form-control" placeholder="">
							</td>
						</tr>
						
						<tr>
							<td width=30% class="td-lf td-text-ri">js&css文件路径:</td>
							<td width=70% id="PathResources1">
								<input type="text" name="PathResources" id="PathResources" class="form-control" placeholder="">
							</td>
						</tr>
						
						<tr>
							<td width=30% class="td-lf td-text-ri">菜单的主文件:</td>
							<td width=70% id="FilePathMenu1">
								<input type="text" name="FilePathMenu" id="FilePathMenu" class="form-control" placeholder="">
							</td>
						</tr>
					
						<tr>
							<td colspan="2" class="col-xs-9" style="padding-bottom: 60px;">
								<input type="button" class="btn function-sure" onclick="savePath();" value="确定"/>
					            <input type="button" class="btn function-canel" onclick="cancelPath();" value="取消"/>
							</td>
						</tr>
					</table>
			</div>
		</form>
	</div>
		
    </body>
</html>
