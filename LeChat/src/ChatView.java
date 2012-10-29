import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

public class ChatView {

	private final PushContext pushContext = PushContextFactory.getDefault().getPushContext();
	
    private ChatUsers users;
	private String globalMessage;
	private String username;
	private boolean loggedIn;
	private final static String CHANNEL = "/chat/";
	
	public void setUsers(ChatUsers users) {
		this.users = users;
	}

	public String getGlobalMessage() {
		return globalMessage;
	}

	public void setGlobalMessage(String globalMessage) {
		this.globalMessage = globalMessage;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void sendGlobal() {
		pushContext.push(CHANNEL + "*", username + ": " + globalMessage);
		globalMessage = null;
	}

	public void login() {
		RequestContext requestContext = RequestContext.getCurrentInstance();

		if (users.contains(username)) {
			loggedIn = false;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Username taken", "Try with another username."));

			requestContext.update("growl");
		} else {
			users.add(username);
			requestContext.execute("subscriber.connect('/" + username + "')");
			requestContext.update("form:users");
			pushContext.push(CHANNEL + "*", username + " joined the channel.");
			loggedIn = true;
		}
	}

	public void disconnect() {
		// remove user and update ui
		users.remove(username);
		RequestContext.getCurrentInstance().update("form:users");

		// push leave information
		pushContext.push(CHANNEL + "*", username + " left the channel.");

		// reset state
		loggedIn = false;
		username = null;
	}
}
