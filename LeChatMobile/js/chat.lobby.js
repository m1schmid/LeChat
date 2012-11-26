$('#lobby').live('pageinit', function(){

	post('Connect','', function(data){ playerToken = data.d; });
	
	$('#create-channel-link').on('click', gotoCreateChannel);	
	function gotoCreateChannel(){
		if(isValidUsername()){
			$('#errorMessage').message("hide");
			var username = $('#username').val();
			$(window).data('chat-data',{playerToken: playerToken, userName: username});
			$.mobile.changePage('create-channel.html');
		}
	}
}).live('pagebeforeshow',function(){
	
	post('GetChats','', listChats);
	
	$('.channel-link').live('click', joinChat);
	function listChats(object, status) {
		$('#channel-list').html('<li data-role="list-divider" role="heading">Channels</li>');
		$(object.d).each(function(){
			var memeberCount = '<span class="ui-li-count">'+ this.Players.length+'</span>';
			var channelLink = '<a href="#" class="channel-link" id="' + this.Id + '">' + this.Name + memeberCount +'</a>';
			$('#channel-list').append('<li>' + channelLink + '</li>');
		});
		
		$('#channel-list').listview('refresh');
	}	
	
	function joinChat(){
		var chatId = $(this).attr('id');
		if(isValidUsername()){
			$('#errorMessage').message("hide");
			var username = $('#username').val();
			$(window).data('chat-data',{playerToken: playerToken, chatId: chatId, userName: username});
			$.mobile.changePage('channel.html');
		}
	}
});