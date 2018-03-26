<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/common/page/yt-page.min.css" />
<link rel="stylesheet" type="text/css" href="resources/css/module/codelist/codelist.css" />


<script language="Javascript" src="resources/js/module/codelist/checkboxSelect.js"></script>

<script language="Javascript" src="resources/js/vue.min.js"></script>
<script language="Javascript" src="resources/js/module/codelist/listTable.js"></script>

<div class="web-criterion-model" >
	<div id="class-manager" class="class-manager">
		<!--  导航部分 -->
		<div class="yt-crumbs-heard-nav">
			<span>当前位置：</span><span>系统管理></span><span class="now-position">代码生成器</span>
		</div>

		<!--  搜索部分 -->
		<!--<div id="" class="head-query">
			<span class="sel-text">分类名称：</span>
			<input type="text" class="yt-input classify-name" placeholder="请输入" />
			<button class="yt-option-btn yt-search" onselectstart="return false">
				<img src="resources/images/icons/yt-search.png" />查询
			</button>
			<button class="yt-option-btn reset-btn" onselectstart="return false">
				<img src="resources/images/icons/reset.png" />重置
			</button>
		</div> -->
  
  		
		<div id="class-list-box" class="class-list-box" style="padding: 0 10px;">
			<div class="class-but-box">
				<button class="yt-option-btn yt-add" onClick="dbConfig();" onselectstart="return false">
				<img src="resources/images/common/edit.png"/>数据配置</button>
				<button style="margin-left: 10px;" class="yt-option-btn yt-edit" onClick="pathConfig();" onselectstart="return false">  
                <img src="resources/images/common/edit.png"/>路径配置</button>
                <button style="margin-left: 10px;" class="yt-option-btn yt-delete" onClick="codeGenerate();" onselectstart="return false">  
    			<img src="resources/images/common/add.png" />代码生成</button>
			</div>
			
			<table class="yt-table" width="100%">
				<thead class="yt-thead">
					<tr>
						<th width="50px"><input name="idsAll" id="idsAll" type="checkbox" onclick="page_on_select('idsAll','ids');" value="checkbox" /></th>
						<th>表名</th>
						<th width="200px">别名</th>
					</tr>
				</thead>
				<form id="theForm" name="theForm">
				<tbody class="yt-tbody" v-for="tableName in tableList">
					<tr>
						<td align=center><input type="hidden" id="id" :value="tableName.tableName" /><input id="ids" name="ids" type="checkbox" class="checkbox" :value="tableName.tableName" /></td>
	                    <td>{{tableName.tableName}}</td>
	                    <td>{{tableName.tableComment}}</td>
					</tr>
				</tbody>
				</form>
			</table>
		</div>
		
		<!-- 新增弹出 -->
		<div class="yt-pop-model yt-edit-alert db-edit-alert" id="db_function" style="left: 50px; top:50px;">
				<!--标题区域START-->
				<div class="yt-model-heard"><img src="resources/images/common/edit.png"><span>更新数据库信息</span></div>
				<!--标题区域END-->
				<!--主体内容部分START-->
				<div class="yt-edit-alert-main cont-edit-test">
					<form id="dbForm" name="dbForm" action="" method="post" role="form">
							<table width=90% style="font-size: 14px; margin-left: 15px;">
								<tr>
									<td width=30% class="td-lf td-text-ri">驱动信息:</td>
									<td width=70% id="dbDriver1">
										<input type="text" name="dbDriver" id="dbDriver" v-model="dbDriver"  class="yt-input" />
									</td>
								</tr>
								<tr>
									<td width=30% class="td-lf td-text-ri">链接路径:</td>
									<td width=70% id="dbUrl1">
										<input type="text" name="dbUrl" id="dbUrl" v-model="dbUrl"  class="yt-input" />
									</td>
								</tr>
								<tr>
									<td width=30% class="td-lf td-text-ri">用户名称:</td>
									<td width=70% id="dbUser1">
										<input type="text" name="dbUser" id="dbUser" v-model="dbUser" class="yt-input" />
									</td>
								</tr>
								<tr>
									<td width=30% class="td-lf td-text-ri">用户密码:</td>
									<td width=70% id="dbPass1">
										<input type="text" name="dbPass" id="dbPass" v-model="dbPass" class="yt-input" />
									</td>
								</tr>
								<tr>
									<td width=30% class="td-lf td-text-ri">别名起始位置:</td>
									<td width=70% id="dbTableSub1">
										<input type="text" name="dbTableSub" id="dbTableSub" v-model="dbTableSub"  class="yt-input" />
									</td>
								</tr>
							</table>
							<div class="yt-eidt-model-bottom">  
								<input class="yt-model-bot-btn yt-model-sure-btn" id="dbsave" onclick="saveDb();" type="button" value="确定" />  
								<input class="yt-model-bot-btn yt-model-canel-btn" id="dbcanel" onclick="cancelDb();" type="button" value="取消" />  
							</div>  
				</form>
			</div>
		</div>
		
		<div class="yt-pop-model yt-edit-alert path-edit-alert" id="path_function" style="left: 50px; top:50px;">
				<!--标题区域START-->
				<div class="yt-model-heard"><img src="resources/images/common/edit.png"><span>更新路径信息</span></div>
				<!--标题区域END-->
				<!--主体内容部分START-->
				<div class="yt-edit-alert-main cont-edit-test">
				 	<form id="pathForm" name="pathForm" action="" method="post" role="form">
							<table width=90% style="font-size: 14px; margin-left: 15px;">
								<tr>
									<td width=30% class="td-lf td-text-ri">模板路径:</td>
									<td width=70% id="PathTemplate1">
										<input type="text" name="PathTemplate" id="PathTemplate"  v-model="PathTemplate" class="yt-input" />
									</td>
								</tr>
								
								<tr>
									<td width=30% class="td-lf td-text-ri">项目路径:</td>
									<td width=70% id="PathProject1">
										<input type="text" name="PathProject" id="PathProject" v-model="PathProject" class="yt-input" />
									</td>
								</tr>
								
								<tr>
									<td width=30% class="td-lf td-text-ri">java路径:</td>
									<td width=70% id="PathJava1">
										<input type="text" name="PathJava" id="PathJava" v-model="PathJava" class="yt-input" />
									</td>
								</tr>
								
								<tr>
									<td width=30% class="td-lf td-text-ri">页面路径:</td>
									<td width=70% id="PathJsp1">
										<input type="text" name="PathJsp" id="PathJsp" v-model="PathJsp" class="yt-input" />
									</td>
								</tr>
								
								<tr>
									<td width=30% class="td-lf td-text-ri">MyBatis路径:</td>
									<td width=70% id="PathMybatis1">
										<input type="text" name="PathMybatis" id="PathMybatis" v-model="PathMybatis" class="yt-input" />
									</td>
								</tr>
							
								<tr>
									<td width=30% class="td-lf td-text-ri">主MyBatis路径:</td>
									<td width=70% id="PathMainMybatis1">
										<input type="text" name="PathMainMybatis" id="PathMainMybatis" v-model="PathMainMybatis" class="yt-input" />
									</td>
								</tr>
							
								<tr>
									<td width=30% class="td-lf td-text-ri">包路径前置信息:</td>
									<td width=70% id="GenPathHead1">
										<input type="text" name="GenPathHead" id="GenPathHead" v-model="GenPathHead" class="yt-input" />
									</td>
								</tr>
								
								<tr>
									<td width=30% class="td-lf td-text-ri">js&css文件路径:</td>
									<td width=70% id="PathResources1">
										<input type="text" name="PathResources" id="PathResources" v-model="PathResources" class="yt-input" />
									</td>
								</tr>
								
								<tr>
									<td width=30% class="td-lf td-text-ri">菜单的主文件:</td>
									<td width=70% id="FilePathMenu1">
										<input type="text" name="FilePathMenu" id="FilePathMenu" v-model="FilePathMenu" class="yt-input" />
									</td>
								</tr>
							</table>
							<div class="yt-eidt-model-bottom">  
								<input class="yt-model-bot-btn yt-model-sure-btn" id="pathsave" onclick="savePath();" type="button" value="确定" />  
								<input class="yt-model-bot-btn yt-model-canel-btn" id="pathcanel" onclick="cancelPath();" type="button" value="取消" />  
							</div> 
				</form>
			</div>
		</div>
		
	</div>
</div>
			


