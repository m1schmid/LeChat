package ch.hsr.lechat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

@ManagedBean
@SessionScoped
public class ChatController {

	private final PushContext pushContext = PushContextFactory.getDefault().getPushContext();
	
	@ManagedProperty(value = "#{chatRooms}")
    private ChatRooms rooms;

	private String room;
	private String username;
	private String message;
	private boolean loggedIn;
	
	public void setRooms(ChatRooms rooms) {
		this.rooms = rooms;
	}

	public String getRoom(){
		return this.room;
	}
	
	public void setRoom(String room){
		this.room = room;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public void sendMessage() {
		message = MessageParser.parse(message);
		pushContext.push("/" + room + "/*", username + ": " + message);
		message = null;
	}
	
	public List<String> getUsers(){
		return rooms.getUsers(room).getUsers();
	}
	
	public void login() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		
		if (rooms.contains(username)) {
			loggedIn = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Try with another username.");
		} else if(username.isEmpty()) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username empty", "");
		} else if(room.isEmpty()) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select or create room!", "");
		} else {
			rooms.put(room, username);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logged in!", "");
			requestContext.execute("subscriber.connect('/" + room + "/" + username + "')");
			pushContext.push("/" + room + "/*", username + " joined the channel.");
			loggedIn = true;
		}
		
		FacesContext.getCurrentInstance().addMessage(null,msg);
	}

	public void disconnect() {

		//RequestContext.getCurrentInstance().execute("subscriber.disconnect()");
		rooms.remove(room, username);
		pushContext.push("/" + room + "/*", username + " left the channel.");
		
		// reset state
		room = null;
		username = null;
		loggedIn = false;
	}
}
