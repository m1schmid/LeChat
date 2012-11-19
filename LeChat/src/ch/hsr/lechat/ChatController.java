package ch.hsr.lechat;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PreDestroy;
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
	private boolean loggedIn = false;
	
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
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "lang");
		
		if (rooms.contains(username)) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", bundle.getString("username_taken"));
		} else if(username.isEmpty()) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("username_empty"), "");
		} else if(room.isEmpty()) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("room_empty"), "");
		} else {
			rooms.put(room, username);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("logged_in"), "");
			requestContext.execute("subscriber.connect('/" + room + "/" + username + "')");
			pushContext.push("/" + room + "/*", username + " joined the channel.");
			loggedIn = true;
		}
		
		FacesContext.getCurrentInstance().addMessage(null,msg);
	}
	
	@PreDestroy
	public void disconnect() {

		rooms.remove(room, username);
		pushContext.push("/" + room + "/*", username + " left the channel.");
		
		room = null;
		username = null;
		loggedIn = false;
	}
}
