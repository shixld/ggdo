 function updateObject(id){ 
	 var actionUrl = $("#toUpdateUrl").val(); 
	 $.ajax({ 
		 url : actionUrl, 
		 type : 'post', 
		 async : false, 
		 data : "id="+id, 
		 success : function(data) { 
			 var obj = new Function("return" + data)(); 
 
		 } 
	 }); 
 } 
