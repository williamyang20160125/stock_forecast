Page = function(el,options){
	this.wrapper = typeof el == 'object' ? el : doc.getElementById(el);
	this.options = {
			pageSize : 20,
			currPage:1,
			filters:null,
			sidx:null,
			sord:null,
			append:true,
			showItems:null,
			url:null
	};
	
	// User defined options
	for (i in options){
		this.options[i] = options[i];
	}
	this.pageSize = this.options.pageSize;
	this.append = this.options.append;
	this.currPage = this.options.currPage;
	this.url = this.options.url;
	this.filters = this.options.filters;
	this.sidx = this.options.sidx;
	this.sord = this.options.sord;
	this.totalCount = 0;
	this.data = {};
	
	this.show = function(){
		this.load(this.currPage,this.pageSize,this.sidx,this.sord,this.filters);
	};
	
	this.showItems = this.options.showItems;
	
	this.showPageNav = function (){
		var obj = this;
		var div = '<div class="mypage text-center"><a class="btn btn-primary pull-left" >上一页</a><span class="lead" >'+obj.currPage+'/'+obj.totalPageCount()+'</span><a  class="btn btn-primary pull-right" >下一页</a></div>';
		if(this.append){
			$(el).append(div);
		}else{
			$(el).before(div);
		}
		$(el).children('.mypage').children('.pull-left').bind({
			click:function(){
				if(obj.currPage<=1){
					return ;
				}
				$.ajax({
					type:"POST",
					cache: false,
					data:{"page":obj.currPage - 1,'rows':obj.pageSize,'sidx':obj.sidx,'sord':obj.sord,'filters':JSON.stringify(obj.filters)},
					url: obj.url,
					success:function(data){
						if(data != null){
							obj.data = data.data;
							obj.currPage = data.currentPageNo;
							obj.totalCount = data.totalCount;
							
							$(el).empty();
							obj.showItems(el,data.data);
							
							obj.showPageNav();
						}
						
						
					}
				});
			}
		});
		
		$(el).children('.mypage').children('.pull-right').bind({
			click:function(){
				if(obj.currPage>=obj.totalPageCount()){
					return ;
				}
				
				$.ajax({
					type:"POST",
					cache: false,
					data:{"page":obj.currPage + 1,'rows':obj.pageSize,'sidx':obj.sidx,'sord':obj.sord,'filters':JSON.stringify(obj.filters)},
					url: obj.url,
					success:function(data){
						if(data != null){
							obj.data = data.data;
							obj.currPage = data.currentPageNo;
							obj.totalCount = data.totalCount;
							
							$(el).empty();
							
							obj.showItems(el,data.data);
							
							obj.showPageNav();
						}
						
						
					}
				});
			}
		})
	};
	
	this.load = function(page,pageSize,sidx,sord,filters){
		var obj = this;
		$.ajax({
			type:"POST",
			cache: false,
			data:{"page":page,'rows':pageSize,'sidx':sidx,'sord':sord,'filters':filters==null?null:JSON.stringify(filters)},
			url: this.url,
			success:function(data){
				if(data != null){
					obj.data = data.data;
					obj.currPage = data.currentPageNo;
					obj.totalCount = data.totalCount;
					
					$(el).empty();
					
					obj.showItems(el,data.data);
					
					obj.showPageNav();
				}
				
				
			}
		});
	};
	
	
	this.totalPageCount = function() {
		if (this.totalCount % this.pageSize == 0)
			return this.totalCount / this.pageSize;
		else
			return Math.ceil(this.totalCount / this.pageSize);
	};
	
};


