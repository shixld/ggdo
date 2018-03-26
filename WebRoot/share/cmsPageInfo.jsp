<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.ggdo.system.function.common.PageInfo" %>
<%@ page isELIgnored="false" %>
<%          
	String actionName = (String) request.getParameter("actionName");
    String formName = (String) request.getParameter("formName");
%>

<div class="more">
          		<ul class="page-ul">
                 <% 
                     PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");	               
		                if(pageInfo.getCurrentPageIndex()==1){
		                   %><li class="pag-li-wid2">首页</li><% 
		                }else{
		                   %><li class="pag-li-wid2" onclick="LinkPage('1');">首页</li> <%
		                }
			                if(pageInfo.isHasPrev()){
			                   %><li class="pag-li-wid3" onclick="LinkPage('${pageInfo.currentPageIndex-1}');">上一页</li><%
			                 }else{
			                   %><li class="pag-li-wid3">上一页</li><%
			                }
			                int totalPageNum=pageInfo.getTotalPageNum();
			                int currentPage_ = Integer.parseInt(String.valueOf(pageInfo.getCurrentPageIndex()));
            	            if(totalPageNum<=5){
			            	  for(int i=1;i<=totalPageNum;i++){
			            		  if(currentPage_== i){
			            	        %><li class="sel-pag-li pag-li-wid1"><%=i %></li><%
			            	     }else{
			            	        %><li class="pag-li-wid1" onclick="LinkPage('<%=i %>');"><%=i %></li><%
			            	     } 
			            	   %> <%} 
		            	   }else{		            		   
		            		   int currentPage_end = 5;
		            		   int nui = 1;
		            		   if(currentPage_>=5){
		            			   nui = currentPage_;
		            			   if(currentPage_+5>totalPageNum){
		            				   nui=totalPageNum-4;
		            			   }
		            			   currentPage_end = currentPage_+4;
			            		   if(currentPage_end > totalPageNum)
			            		   {
			            			   currentPage_end = totalPageNum;
			            		   }
			            		   if(currentPage_end>5){
			            			   %><li class="pag-li-wid1");">···</li> <%  
			            		   }
		            		   }
		            	     for(int i=nui;i<=currentPage_end;i++){		            	    	 
			            	     if(currentPage_== i){
			            	        %><li class="sel-pag-li pag-li-wid1"><%=i %></li><%
			            	     }else{
			            	        %><li class="pag-li-wid1" onclick="LinkPage('<%=i %>');"><%=i %></li><%
			            	     } 
			            	   %> <%}
			            	   if(totalPageNum>5&&totalPageNum>currentPage_end){
			            		   %><li class="pag-li-wid1");">···</li> <%  
			            	   }
			            	   
		            	   }
		            	   if(pageInfo.isHasNext()){
			                   %><li class="pag-li-wid3" onclick="LinkPage('${pageInfo.currentPageIndex+1}');">下一页</li><%
			                 }else{
			                   %><li class="pag-li-wid3">下一页</li><%
			                }
			                
		                 if(pageInfo.getCurrentPageIndex()==pageInfo.getTotalPageNum()){
		                   %><li class="pag-li-wid2">尾页</li><% 
		                }else{
		                   %><li class="pag-li-wid2" onclick="LinkPage('${pageInfo.totalPageNum}');">尾页</li><%
		                } 
                      %>    
			      </ul>
				</div>
<script language="javascript" type="text/javascript">
       function forwardTo(){
			var num=document.getElementById("pageIndex").value;
			var totalPage='<s:property value="pageInfo.totalPageNum"/>';
			if(num==""){
			   alert("Search pages can not be empty!");
			   document.getElementById("pageIndex").value="1";
			   document.getElementById("pageIndex").focus();
			  return;
			}
			if(isNaN(num)||parseInt(num)<1){
			  alert('Illegal input, must be a positive integer greater than zero!');
			  document.getElementById("pageIndex").value="1";
			  document.getElementById("pageIndex").focus();
			  return ;
			}
			if(parseInt(num)>parseInt(totalPage)){
			   alert("Enter the number of pages can not exceed the current total number of pages!");
			   document.getElementById("pageIndex").value="1";
			   document.getElementById("pageIndex").focus();
			   return ;	   
			}
			 var form=document.getElementById('<%=formName%>');
			 if(form.pageIndexs && form.pageIndexs.value.length > 0){
			   	form.action="<%=request.getContextPath() %>/<%=actionName%>";
			   	form.pageIndexs.value=num;
			 }else{
             	form.action="<%=request.getContextPath() %>/<%=actionName%>?pageIndexs="+num;
             }
		     form.submit();
		}
		function checkNum(){
		  	var num=document.getElementById("pageIndex").value;
			if(isNaN(num)){
				alert('Illegal input, must be a positive integer greater than zero!');
				document.getElementById("pageIndex").value="1";
	            document.getElementById("pageIndex").focus();
				return false;
			}
			if(parseInt(num)<1) {
				alert('Must be a positive integer greater than zero!');
				document.getElementById("pageIndex").value="1";
				document.getElementById("pageIndex").focus();
				return false;	
			}
		}
		
	   function LinkPage(currPage){
			if(currPage!=""&&currPage!=null){ 
			   var form=document.getElementById('<%=formName%>');
			   if(form.pageIndexs && form.pageIndexs.value.length > 0){
			   	form.action="<%=request.getContextPath() %>/<%=actionName%>";
			   	form.pageIndexs.value=currPage;
			   }else{
               	form.action="<%=request.getContextPath() %>/<%=actionName%>?pageIndexs="+currPage;
               }
		       form.submit();
			}
		}
  
</script>