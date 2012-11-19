package ch.hsr.lechat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


@ManagedBean(name="chatRooms")
@ApplicationScoped
public class ChatRooms {

	private Map<String, ChatUsers> chatRooms;
	
    @PostConstruct
    public void init() {
        this.chatRooms = Collections.synchronizedMap(new HashMap<String, ChatUsers>());
    }
	
    public List<String> getRooms(){
    	return new ArrayList<String>(chatRooms.keySet());
	}
	
	public ChatUsers getUsers(String room) {
		return chatRooms.get(room);
	}

	public void put(String roomName, String user) {
		if(!chatRooms.containsKey(roomName)){
			chatRooms.put(roomName, new ChatUsers());
		}
		chatRooms.get(roomName).add(user);
	}

	public void remove(String roomName, String user) {
		ChatUsers roomUsers = chatRooms.get(roomName);
		roomUsers.remove(user);
		if(roomUsers.isEmpty())
			chatRooms.remove(roomName);
	}

	public boolean contains(String user) {
		for (ChatUsers roomUsers : chatRooms.values()) {
			if(roomUsers.contains(user))
				return true;
		}
		return false;
	}
}
