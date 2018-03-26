/**
 * 
 * 
 * C(05)分页方法
 * 
 * 
 * */
	var pageInfo = {
		pageSize:10, 	// 默认 10 页之后显示 ...
		pageNum:15,  	// 默认每页显示15条数据
		total:0,	 	// 默认共有 0条数据	
		pageDoc:null,	// 分页的类名，默认为 "opinion-page"
		init:function (pageIndex,confirmFunction,pageDoc,total,pageSize,pageNum){
			var me = this;
			/**
			 *上一页
			 * 
			 */
			pageDoc.find(".last-page.pointer").unbind().bind("click",function(){
				/*给上一页按钮解除所有事件并绑定点击事件*/
				var pageIndex = pageDoc.find(".num-text.active").text();/*获取当前有选中状态的页码*/
				pageIndex = parseInt(pageIndex)-1;/*当前页码数减1*/
				me.setPage(pageIndex,confirmFunction,pageDoc,total,pageSize,pageNum);/*创建新的分页*/
				confirmFunction(pageIndex);/*重新查询页面数据*/
				me.bindTrClick();/*给新数据绑定点击事件*/
			});
			/**
			 *下一页 
			 * 
			 */
			pageDoc.find(".next-page.pointer").unbind().bind("click",function(){
				var pageIndex = pageDoc.find(".num-text.active").text();
				pageIndex = parseInt(pageIndex)+1;
				me.setPage(pageIndex,confirmFunction,pageDoc,total,pageSize,pageNum);
				confirmFunction(pageIndex);
				me.bindTrClick();
			});
			/**
			 *页号 
			 * 
			 */
			pageDoc.find(".num-text").unbind().bind("click",function (){
				var pageIndex = $(this).text();
				me.setPage(pageIndex,confirmFunction,pageDoc,total,pageSize,pageNum);
				confirmFunction(pageIndex);
				me.bindTrClick();
			});
			/**
			 * 
			 * 首页
			 * 
			 */
			pageDoc.find(".frist-page.pointer").unbind().bind("click",function (){
				me.setPage(1,confirmFunction,pageDoc,total,pageSize,pageNum);
				confirmFunction(1);
				me.bindTrClick();
			});
			
			/**
			 * 尾页
			 * */
			pageDoc.find(".end-page.pointer").unbind().bind("click",function (){
				var pageCount =  Math.ceil(total/pageNum);
				me.setPage(pageCount,confirmFunction,pageDoc,total,pageSize,pageNum);
				confirmFunction(pageCount);
				me.bindTrClick();
			})
			me.bindTrClick();
			
		},
		bindTrClick:function (){
			$(".yt-table .yt-tbody tr").unbind().bind("click",function (){
				$(".yt-table .yt-tbody tr.yt-table-active").removeClass("yt-table-active");
				$(this).addClass("yt-table-active");
			});
		},
		config:function (pageIndex,confirmFunction,pageNum,pageSize,pageDoc){
		/*参数：1.当前显示页码；2.获取当前页面数据的方法；3.每页显示数据的条数；4.页码大于这个参数后显示... ；5.分页的类名*/
			
			if(!pageIndex){
				pageIndex = 1;
			}
			/*当前显示的页码，默认为1*/
			
			this.total = confirmFunction(pageIndex).total;
			/*从传入的方法中获取数据的总条数*/
			
			if(pageNum){
				this.pageNum = pageNum;
			}else{
				this.pageNum = 15;
			}
			/*当前页面显示数据条数，默认为15条*/
			
			if(pageSize){
				this.pageSize = pageSize;
			}else{
				this.pageSize = 10
			}
			/*当页码数大于当前传入参数时分页显示...，默认为10*/
			
			if(pageDoc){
				this.pageDoc = $(pageDoc).html('<table><tr></tr></table>');
			}else{
				this.pageDoc = $(".opinion-page").html('<table><tr></tr></table>');
			}
			/*分页的类名，默认为opinion-page*/
			
			// $(".commen-table tbody tr").setEvenTrColor();
			this.setPage(pageIndex,confirmFunction,this.pageDoc,this.total,this.pageSize,this.pageNum);
		},
		setPage:function (pageIndex,confirmFunction,pageDoc,total,pageSize,pageNum){
			/**
        	 *分页方法 
        	 */
        	pageIndex = parseInt(pageIndex);/*类型转换，并返回整数*/
        	var pageCount =  Math.ceil(total/pageNum);
        	/*共有多少页，并将计算（总条数/每页显示的条数）结果上取整*/
        	
        	pageDoc.find("table tr").empty();/*清空当前分页*/
        	
        	var countHtml = '<td><div class="data-count">共'+total+'条记录，</div></td><td><div class="data-count page-index">'+pageIndex+'/'+pageCount+'，</div></td>';
        	pageDoc.find("table tr").append(countHtml);
        	/*显示共有多少页，当前为第几页*/
        	
        	var lastButton = '<td><div class="last-page"><</div></td>';
        	var lastButtonActive = '<td><div class="last-page change-btn pointer"><</div></td>';
        	var nextButton = '<td><div class="next-page">></div></td>';
        	var nextButtonActive = '<td><div class="next-page change-btn pointer">></div></td>';
        	var firstButton = '<td><div class="frist-page">首页</div></td>';
        	var firstButtonAction = '<td><div class="frist-page pointer">首页</div></td>';
        	var endButton = '<td><div class="end-page">尾页</div></td>';
        	var endButtonAction = '<td><div class="end-page pointer">尾页</div></td>';
        	if(pageCount<=pageSize+1){
        		/*第一种情况：总页数小于可显示的页数，则页码全显示出来没有...*/
        		/**
        		 *  1 2 3 4 5
        		 */
        		if(pageIndex>1){/*如果当前显示页大于1，则显示可以点击的上一页和首页*/
        			pageDoc.find("table tr").append(firstButtonAction);
        			pageDoc.find("table tr").append(lastButtonActive);
        		}else{/*否则显示没有点击效果的页码*/
        			pageDoc.find("table tr").append(firstButton);
        			pageDoc.find("table tr").append(lastButton);
        		}
            	
        		for ( var int = 1; int <=pageCount; int++) {/*循环递增显示页码数，最大值为总页数*/
        			var pageHtml = '<td><div class="num-text change-btn pointer">'+int+'</div></td>';
        			if(int==pageIndex){/*如果int等于当前显示的页数，则添加选中状态*/
        				var pageHtml = '<td><div class="num-text change-btn active">'+int+'</div></td>';
        			}
        			pageDoc.find("table tr").append(pageHtml);
				}
            	
        		if(pageIndex<pageCount){/*如果当前页小于总页数，则显示可以点击的下一页和尾页*/
        			pageDoc.find("table tr").append(nextButtonActive);
        			pageDoc.find("table tr").append(endButtonAction);
        		}else{/*否则显示没有点击效果的页码*/
        			pageDoc.find("table tr").append(nextButton);
        			pageDoc.find("table tr").append(endButton);
        		}
        	}else if(pageCount>pageSize+1 && pageIndex<=pageSize-1){
        		/*第二种情况：总页数大于可显示的页数并且当前显示的页码小于等于可显示的页码，则后面显示...*/
        			
        		/**
        		 * 1 2 3 4 5 ··· 12
        		 */
        		if(pageIndex>1){/*如果当前页大于1，则显示可以点击的首页和上一页*/
        			pageDoc.find("table tr").append(firstButtonAction);
        			pageDoc.find("table tr").append(lastButtonActive);
        		}else{
        			pageDoc.find("table tr").append(firstButton);
        			/*页码在第一页的时候，去掉上一页功能*/
        			//pageDoc.find("table tr").append(lastButton);
        		}
            	
        		for ( var int = 1; int <=pageSize; int++) {/*循环递增显示页码数，最大值为页面可显示页数*/
        			var pageHtml = '<td><div class="num-text change-btn pointer">'+int+'</div></td>';
        			if(int==pageIndex){/*如果int等于当前显示的页数，则添加选中状态*/
        				var pageHtml = '<td><div class="num-text change-btn active">'+int+'</div></td>';
        			}
        			pageDoc.find("table tr").append(pageHtml);
				}/*否则页面显示...*/
        		var pageHtml = '<td><div class="">···</div></td>';
        		pageDoc.find("table tr").append(pageHtml);
            	pageDoc.find("table tr").append(nextButtonActive);
            	pageDoc.find("table tr").append(endButtonAction);
        	}else if(pageCount>pageSize+1 && pageIndex>=pageCount-pageSize+2){
        		/*第三种情况：如果总页数大于可显示的页数，则在页码前面显示...*/
        		
        		/**
        		 *  1··· 4 5 6 7 8
        		 */
        		
        		pageDoc.find("table tr").append(firstButtonAction);
        		pageDoc.find("table tr").append(lastButtonActive);
        		var pageHtml = '<td><div class="">···</div></td>';
        		pageDoc.find("table tr").append(pageHtml);
        		/*页面添加可点击的首页和上一页和...*/
        		
        		for ( var int = pageCount-pageSize+1; int <=pageCount; int++) {
        			/*定义int为总页数-可显示的页数+1；递增，最大值为总页数*/
        			var pageHtml = '<td><div class="num-text change-btn pointer">'+int+'</div></td>';
        			if(int==pageIndex){
        				var pageHtml = '<td><div class="num-text change-btn active">'+int+'</div></td>';
        			}
        			pageDoc.find("table tr").append(pageHtml);
				}
            	
        		if(pageIndex<pageCount){
        			pageDoc.find("table tr").append(nextButtonActive);
        			pageDoc.find("table tr").append(endButtonAction);
        		}else{
        			/*页码在最后一页时，去掉下一页功能*/
        			//pageDoc.find("table tr").append(nextButton);
        			pageDoc.find("table tr").append(endButton);
        		}
        		
        	}else{
        		/*第四种情况：页码前后都显示...*/
        		
        		/**
        		 *1··· 4 5 6 7 8 ···12 
        		 */
        		pageDoc.find("table tr").append(firstButtonAction);
        		pageDoc.find("table tr").append(lastButtonActive);
        		var pageHtml = '<td><div class="">···</div></td>';
        		pageDoc.find("table tr").append(pageHtml);
        		var pageSizeNum = pageSize%2==0?pageSize/2:Math.ceil(pageSize/2)-1;
        		for ( var int = pageIndex-pageSizeNum; int <=pageIndex+pageSizeNum; int++) {
        			var pageHtml = '<td><div class="num-text change-btn pointer">'+int+'</div></td>';
        			if(int==pageIndex){
        				var pageHtml = '<td><div class="num-text change-btn active">'+int+'</div></td>';
        			}
        			pageDoc.find("table tr").append(pageHtml);
				}
        		var pageHtml = '<td><div class="">···</div></td>';
        		pageDoc.find("table tr").append(pageHtml);
        		pageDoc.find("table tr").append(nextButtonActive);
        		pageDoc.find("table tr").append(endButtonAction);
        	}
        	this.init(pageIndex,confirmFunction,pageDoc,total,pageSize,pageNum);
		}
	}