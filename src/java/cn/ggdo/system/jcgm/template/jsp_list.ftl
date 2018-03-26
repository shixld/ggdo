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
		<title>${LOWER_BEAN_NAME}_list_page</title>
		<jsp:include flush="true" page="/share/top.jsp" />
${INCLUDE_RESOURCES_FILE}
	</head>
        
    <body style="margin:0; ">
    
    	<div class="contion" style="z-index: 1040;">
           <div class="right-div">
           		<form id="theForm" name="theForm" action="${LOWER_BEAN_NAME}/lookForAll.action" method="post">
					  <input type="hidden" name="returnUrl" id="returnUrl" value='${LOWER_BEAN_NAME}/lookForAll.action'/>
					  <input type="hidden" name="deleteUrl" id="deleteUrl" value='${LOWER_BEAN_NAME}/deleteBeanById.action'/>
					  <input type="hidden" name="addUrl" id="addUrl" value='${LOWER_BEAN_NAME}/saveBean.action'/>
					  <input type="hidden" name="toUpdateUrl" id="toUpdateUrl" value='${LOWER_BEAN_NAME}/getBeanById.action'/>
					  <input type="hidden" name="updateUrl" id="updateUrl" value='${LOWER_BEAN_NAME}/updateBean.action'/>
					 <!-- <div class="form-group col-xs-5">
						<label class="col-xs-1  control-label" style="width: auto; color: #919697; margin-top: 10px; margin-left:15px;">用户名:</label>
						<div class="col-xs-1 pad">
							<input type="text" name="name" class="form-control" placeholder=""/>
						</div>
					</div> 
					<div class="form-group col-xs-3">
						<button type="submit" class="btn btn-default">查询</button>
						<button type="reset" class="btn btn-default">重置</button>
					</div>-->
		         </form>
		           
		           <div class="form-group col-xs-12 ">
		           		<button onclick="showAddModal(0)" class="btn btn-default btnconfig" id="add">
							<span class="glyphicon glyphicon-plus add-icon"></span>
							新增</button>
						<button onclick="showAddModal(1)"  class="btn btn-default btnconfig mar-lf-5">
							<span class="glyphicon glyphicon-pencil up-icon"></span>
							编辑</button>
						<button onclick="RemoveObject();" class="btn btn-default btnconfig mar-lf-5">
							<span class="glyphicon glyphicon-remove up-icon"></span>
							删除</button>
		          </div>
				   
				   
				   <table id="infoTable" class="table table-bordered" style="font-size: 14px; margin-left: 15px;">
					<thead>
						<tr>
${TABLE_HEAD}
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${LIST_VALUE}" var="${LOWER_BEAN_NAME}">
						<tr>
${TABLE_BODY}
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<jsp:include flush="true" page="/share/cmsPageInfo.jsp">
					<jsp:param name="actionName" value="${LOWER_BEAN_NAME}/lookForAll.action" />
					<jsp:param name="formName" value="theForm" />
				</jsp:include>
           </div>
        </div>
        
        <!-- 遮罩层STRAT -->
		<div class="shade"></div>
		<!-- 遮罩层END -->
		
		<!-- 新增弹出框 -->
	<div id="moda_alert_bg" class="modal_bg"></div>
	<div class="easyui-window" id="forgetforms" title="&nbsp;&nbsp;&nbsp;<img src='resources/images/down.png' class='down-icon'/>&nbsp;操作功能"  data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true" style="width: 600; height: 500; left: 390px; top: 50px; ">
	<form id="dataForm" name="dataForm" action="" method="post" role="form">
     			<input type="hidden" name="actionFlag" id="actionFlag" class="form-control">
				<input type="hidden" id="id"/>
				<div class="panel-function">
					<table width=90% style="font-size: 14px; margin-left: 15px;">
${ADD_TABLE}
						<tr>
							<td colspan="2" class="col-xs-9" style="padding-bottom: 60px;">
								<input type="button" class="btn function-sure" onclick="saveBean();" value="确定"/>
					            <input type="button" class="btn function-canel" onclick="closeAddModel()" value="取消"/>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
    </body>
</html>
