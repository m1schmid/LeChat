package ch.hsr.lechat;
import java.util.ArrayList;
import java.util.List;

public class ChatUsers {

	private List<String> users = new ArrayList<String>();
	
	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public void add(String user) {
		this.users.add(user);
	}

	public void remove(String user) {
		this.users.remove(user);
	}

	public boolean contains(String user) {
		return this.users.contains(user);
	}

	public boolean isEmpty() {
		return this.users.isEmpty();
	}
}