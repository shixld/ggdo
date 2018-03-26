/**
* all select  checkbox /not select all checkbox
* checkb1 is checkbox all's id,checkb2 is row checkbox id 
*/
function page_on_select(checkb1,checkb2){
     if(document.getElementById(checkb1).checked==true){
         var checkboxs = document.getElementsByName(checkb2);   
         for(var i=0;i<checkboxs.length;i++){
            checkboxs[i].checked =true;
          }        
     }else{
         var checkboxs = document.getElementsByName(checkb2);   
         for(var i=0;i<checkboxs.length;i++){
            checkboxs[i].checked =false;
          }  
     }     
  }
  
  /**
 *
 */
 function page_checkNum(pageIndex){
  	var num=document.getElementById(pageIndex).value;
	if(isNaN(num)){
		alert('Illegal input, must be a positive integer greater than zero!');
		document.getElementById(pageIndex).value="1";
        document.form.document.getElementById(pageIndex).focus();     
		return false;
	}
	if(parseInt(num)<1) {
		alert('Must be a positive integer greater than zero!');
		document.getElementById(pageIndex).value="1";
        document.form.document.getElementById(pageIndex).focus();   
		return false;	
	}
}
  /**
  * @desc check  select record count
  * @return select record count
  * @author wangws
  */
function checkselect(checkrowId) { 
			var count = 0;
			var code_Values = document.getElementsByName(checkrowId);
			if(code_Values.length>0){ 
				for(var i=0;i<code_Values.length;i++) 
				{ 
					if(code_Values[i].checked)
					  count = count+1;
				} 
			}
			return count;
		} 
  /**
  * @desc keep number and dot
  * @return select record count
  * @author wangws
  */
function keepNumberDot(obj){
   obj.value = obj.value.replace(/[^\d.]/g,"");  
   obj.value = obj.value.replace(/^\./g,"");  
   obj.value = obj.value.replace(/\.{2,}/g,".");
   obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

function checkInputPercent(txtObj){
	if(parseFloat(txtObj.value)>100||parseFloat(txtObj.value)<0){
		showMsg("enter value must between 0 and 100 !");
		txtObj.value="0";
		txtObj.focus();
		return;
	}else{
		showMsg("&nbsp;");
		return;
	}
}

function showMsg(msg) { 
	//$("#errormsg").html(msg);
	var spanElement = $("#errormsg").find("span");
	if (spanElement.length <= 0) {
		$("#errormsg").html(msg);
	} else {
		spanElement.each(function(i) {
			$(this).html(msg);
		});
	}
}
  

  
  