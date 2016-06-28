
function bindDragEvent(els){
	els.bind({
		dragstart:function(e){
			//var e = e || window.event;  
			var x = e.originalEvent.clientX;
			var y = e.originalEvent.clientY;
			
			$(this).data("x",x);
		},
		drag:function(e){
			//var e = e || window.event;  
			var x = e.originalEvent.clientX;
			var y = e.originalEvent.clientY;
			
			var w = $(this).width() * 0.20;
			
			var d = $(this).data("x") - x; 
			
			if(d<0){//向右移动
				d=0;
			}
			
			if(d>w){
				d=w;
			}
			
			$(this).children("div:first-child").css({'right':d+"px"});
		}, 
		dragend:function(e){
			//var e = e || window.event;  
			var x = e.originalEvent.clientX;
			var y = e.originalEvent.clientY;
			
			var d = $(this).data("x") - x; 
			
			var w = $(this).width() * 0.20;
			
			var d = $(this).data("x") - x; 
			if(d>w/2){
				d=w;
			}else{
				d=0;
			}
			$(this).children("div:first-child").css({'right':d+"px"});
		},
		touchstart:function(e){
			var e = e || window.event;  
			var x = e.originalEvent.targetTouches[0].clientX;
			var y = e.originalEvent.targetTouches[0].clientY;
			$(this).data("x",x);
			
		},
		touchmove:function(e){
			var e = e || window.event;  
			
			if (e.originalEvent.targetTouches.length == 1) {
			//	e.originalEvent.preventDefault();// 阻止浏览器默认事件，重要 
			}
			
			var x = e.originalEvent.targetTouches[0].clientX;
			var y = e.originalEvent.targetTouches[0].clientY;
			
			var d = $(this).data("x") - x; 
			var w = $(this).width() * 0.20;
			
			var d = $(this).data("x") - x; 
			if(d<0){//向右移动
				d=0;
			}
			if(d>w){
				d=w;
			}
			
			$(this).children("div:first-child").css({'right':d+"px"});
		}, 
		touchend:function(e){
			var e = e || window.event;  
			var x = e.originalEvent.changedTouches[0].clientX;
			var y = e.originalEvent.changedTouches[0].clientY;
			var d = $(this).data("x") - x ; 
			
			var w = $(this).width() * 0.20;
			
			var d = $(this).data("x") - x; 
			
			
			if(d>w/2){
				d=w;
			}else{
				d=0;
			}
			
			$(this).children("div:first-child").css({'right':d+"px"});
		}
	});
}