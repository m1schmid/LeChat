import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class ChatUsers {

	private List<String> users;
	
    @PostConstruct
    public void init() {
        this.users = new ArrayList<String>();
    }
	
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
}