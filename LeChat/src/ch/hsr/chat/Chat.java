package ch.hsr.chat;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;


public class Chat {

	private ChatUsers2 users;
	private final PushContext pushContext = PushContextFactory.getDefault().getPushContext();
	
	public Chat() {
	}

}
