var height = 0;

function updateChannel(){
	post('GetChat', {chatId: chatData.chatId}, function(data){
		$('#channel-title').text('Channel: ' + data.d.Name);
		$('#messages').text('');
		$('#channel-member > div').html('');
		
		$(data.d.Players).each(function(){
			var playerToken = this.PlayerToken; 
			var playerElement = '<p id="' + playerToken + '">' + this.PlayerName + '</p>';
			$('#channel-member > div').append(playerElement);
		});
		var text = '';
		$(data.d.ChatLines).each(function(){
			var cssClass="triangle-right left";
			if(this.Player.PlayerName == chatData.userName){
				cssClass="triangle-right right";
			}
			
			$('<p class="' + cssClass + '">').text(this.Text).prepend('<i>' + this.Player.PlayerName + '</i>:<br />').appendTo('#messages');
		});

		if(height != $(document).height()){
			height = $(document).height();
			$("html, body").animate({ scrollTop: $(document).height() }, "slow");
		}
	});
}
var channelVisible;
$('#channel').live('pageinit', function(){
	chatData = $(window).data('chat-data');
	
	post('JoinChat', chatData, updateChannel);

	$('#send').on('click', function(){
		var message = $('#message').val();
		if(message != ''){
			post('WriteLine', {playerToken: chatData.playerToken, text: message}, updateChannel);
			$('#message').val('');
		}
	});
	
	$('.leave').on('click', function(){
		channelVisible = false;
		post('LeaveChat', {playerToken: chatData.playerToken}, function(){
				$.mobile.changePage('lobby.html');
		});
	});
}).live('pagebeforeshow', function(){
	var chatData = $(window).data('chat-data');
	channelVisible = true;
	
	(function poll(){
		setTimeout(function(){
			updateChannel();
			if(channelVisible)
				poll();
	  	}, 500);
	})();
});