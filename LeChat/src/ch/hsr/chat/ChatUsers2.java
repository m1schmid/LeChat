package ch.hsr.chat;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class ChatUsers2 {

	private HashMap<String, String> users;
	
    @PostConstruct
    public void init() {
        users = new HashMap<String, String>();
    }
	
	public HashMap<String, String> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, String> users) {
		this.users = users;
	}

	public void add(String user, String topic) {
		users.put(user, topic);
	}

	public void remove(String user) {
		this.users.remove(user);
	}

	public boolean contains(String user) {
		return users.containsKey(user);
	}
	
	// TODO rename method
	public boolean isEmpty(String topic) {
		return users.containsValue(topic);
	}
}