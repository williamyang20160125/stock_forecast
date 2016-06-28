Activity = function(options){
	this.options = {
			orientation : null,
			action:null,
			onChange:null
	};
	
	// User defined options
	for (i in options){
		this.options[i] = options[i];
	}
	
	this.orientation = this.options.orientation;
	this.action = this.options.action;
	this.onChange = this.options.onChange;
}
