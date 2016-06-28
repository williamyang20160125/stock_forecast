Swiper = function(options){
	this.options = {
			startX : 0,//鼠标移动开始坐标
			startY : 0,//鼠标移动开始坐标
			endX:0,//鼠标移动结束坐标
			endY:0,//鼠标移动结束坐标
			
	};
	
	// User defined options
	for (i in options){
		this.options[i] = options[i];
	}
}