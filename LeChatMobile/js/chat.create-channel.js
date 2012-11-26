$('#create-channel').live('pageinit', function(){
	
	chatData = $(window).data('chat-data');
	
	$('#create-channel-submit').on('click', createNewChannel);
	
	function createNewChannel(){
		var cName = $('#channel-name').val();
		if(cName != ''){
			post('CreateChannel', {playerToken: chatData.playerToken, channelName: cName}, function(response){
					chatData.chatId = response.d;
					$(window).data('chat-data', chatData);
					$.mobile.changePage('channel.html');
				});
		}
	}
});