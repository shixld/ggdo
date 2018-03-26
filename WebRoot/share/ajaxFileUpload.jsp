<%@ page language="java" pageEncoding="gb2312"%>
<link href="<%= request.getContextPath() %>/resources/css/frameRight/content.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.easydrag.handler.beta2.js"></script>
<script type="text/javascript" language="javascript" src="<%= request.getContextPath() %>/resources/js/jquery.ajaxfileupload.js"></script>
<script language="javascript" type="text/javascript">
 
 	var allowedUploadFiles = null;
 	var callbackInputId = null;
 	var callbackImageId = null;
 	var callbackFunction = null;
 	
	$(function(){
		$("#div_uploadFile").easydrag();
		$("#div_uploadFile").setHandler('div_drag_title');
	});
	
	function stringEndWith(s1,s2)
	{
	  if(s1.length<s2.length)
	   return false;
	  if(s1==s2)
	   return true;
	  if(s1.substring(s1.length-s2.length)==s2)
	    return true;
	} 
	
	function showFileUploadDiv(inputId, imageId, allows, callback){
		$("#div_uploadFile").css("left", ($(document.body).width()-$("#div_uploadFile").width())/2);
		$("#div_uploadFile").css("top", ($(document.body).height()-$("#div_uploadFile").height())/2);
		$("#div_uploadFile").show();
		allowedUploadFiles = allows;
		callbackInputId = inputId;
		callbackImageId = imageId;
		callbackFunction = callback;
	}
	
	function closeFileUploadDiv(){
		$("#fileToUpload").val('');
		allowedUploadFiles = null;
		callbackInputId = null;
		callbackImageId = null;
		callbackFunction = null;
		$("#div_uploadFile").hide();
	}
	
	function ajaxFileUpload(){
	
		var fileToUploadTemp=$("#fileToUpload").val();
		if(fileToUploadTemp == null || fileToUploadTemp == ""){
			alert("请选择要上传的文件！");
			return false;
		}
		
		if(allowedUploadFiles){
			fileToUploadTemp = fileToUploadTemp.toLocaleLowerCase();
			allowedUploadFiles = allowedUploadFiles.toLocaleLowerCase();
			var types = allowedUploadFiles.split(";");
			var b = true;
			for(var i=0; i<types.length; i++){
				if(stringEndWith(fileToUploadTemp, '.'+types[i])){
					b = false;
					break;
				}
			}
			if(b){
				alert("不允许的文件格式！");
				return false;
			}
		}
		
		$.ajaxFileUpload
		(
			{
				url:'<%= request.getContextPath() %>/FileUploaded',
				type:"post",
				//secureuri:false,
				fileElementId:'fileToUpload',
				//dataType: 'json',
				dataType: 'text',
				success: function(data, textStatus){
					/*$(".ajax.ajaxResult").html("");
					$("item",data).each(function(i, domEle){
						$(".ajax.ajaxResult").append("<li>"+$(domEle).children("title").text()+"</li>");
					});*/
					//alert(data);

					if(callbackInputId)
						$('#'+callbackInputId).attr("value",data);
					if(callbackImageId)
						$('#'+callbackImageId).attr("src",data);
					if($.isFunction(callbackFunction))
						callbackFunction.apply(this, [data]);
						
					//closediv();
					closeFileUploadDiv();
					
				},
				error: function (data, status, e)
				{
					alert(data);
					alert(e);
				}
			}
		)
		
		return false;
	
	}
	
	function openFileBrower(){
		$('#fileToUpload').click();
		
	}
	
	function synFilePathValue(){
		$('#filePathShow').val($('#fileToUpload').val());
	}
</script>
<div id="div_uploadFile" style="position: absolute; display: none;">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="360" border="0" cellspacing="0" cellpadding="0" id="div_drag_title">
      <tr>
        <td width="6"><img src="<%= request.getContextPath() %>/resources/images/frameRight/tc_l.jpg" width="6" height="33" /></td>
        <td style="background:url(<%= request.getContextPath() %>/resources/images/frameRight/tc_c.jpg)"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="93%" class="f14 fff fbold">&nbsp;&nbsp;上传图片</td>
            <td width="7%"><img src="<%= request.getContextPath() %>/resources/images/frameRight/tc_gb.jpg" width="19" height="19" onclick="closeFileUploadDiv()" style="cursor: pointer;"/></td>
          </tr>
        </table></td>
        <td width="6"><img src="<%= request.getContextPath() %>/resources/images/frameRight/tc_r.jpg" width="6" height="33" /></td>
      </tr>
    </table>
      <table width="360" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #118cdc; border-top:0px; background:#fff; padding:10px">
        <tr>
          <td>
            <table width="330" height="67" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="39" align="center">上传图片：
            		<input type="file" id="fileToUpload" name="fileToUpload" value=""/>
            	</td>
              </tr>
              <tr>
                <td align="center"><input type="button" class="button_c"  value="提 交" onclick="ajaxFileUpload();"/></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</div>
