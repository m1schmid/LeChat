$.support.cors = true;
var serviceUrl = 'http://sifsv-80018.hsr.ch/Service/ChatService.asmx/';
var playerToken;	
var chatData;
	
function post(request, data, callback){
	var request = {
		url: serviceUrl + request,
		type: "POST",
		dataType: "json",
		async: false,
		success: callback,
		contentType: "application/json; charset=utf-8"
	}
	if(data != '')
		request.data = JSON.stringify(data);
	$.ajax(request);
}
	
function isValidUsername(){
	var base = this;
	base.username = $('#username').val();
	base.valid = false;
	base.errorMsg = 'Empty username!';
	
	if(username != ''){
		post('IsNameUnique', {name: username}, 
			function(data){
				base.valid = data.d;
				base.errorMsg = (base.valid) ? '' : 'Not unique username!';
			});
	}
	if(base.errorMsg != ''){
   		$("#errorMessage").message({type:"error", message:base.errorMsg, dismiss: false});
    	$("#errorMessage").message("show");
	}
	return base.valid;
}