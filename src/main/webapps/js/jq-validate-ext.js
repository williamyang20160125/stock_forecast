jQuery.validator.addMethod("ipv4", function(value, element) {
    return this.optional(element) || checkIpv4(value);
});

function checkIpv4(value){
	return /^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/.test(value);
}

jQuery.validator.addMethod("mobile", function(value, element) {
    return this.optional(element) || checkMobile(value);
});

function checkMobile(value){
	return /^[1][3-8]+\d{9}$/.test(value);
}

function checkCron(value){
	return /^(?:[1-9]?\d|\*)(?:(?:[\/-][1-9]?\d)|(?:,[1-9]?\d)+)?$/.test(value);
}