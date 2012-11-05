package ch.hsr.lechat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


@ManagedBean(name="chatRooms")
@ApplicationScoped
public class ChatRooms {

	private HashMap<String, ChatUsers> chatRooms;
	
    @PostConstruct
    public void init() {
        this.chatRooms = new HashMap<String, ChatUsers>();
    }
	
    public List<String> getRooms(){
    	
    	return new ArrayList<String>(chatRooms.keySet());
	}
	
	public ChatUsers getUsers(String room) {
		return chatRooms.get(room);
	}

	public void put(String roomName, String user) {
		ChatUsers room = chatRooms.get(roomName);
		if(room == null){
			room = new ChatUsers();
			chatRooms.put(roomName, room);
		}
		room.add(user);
	}

	public void remove(String roomName, String user) {
		ChatUsers room = chatRooms.get(roomName);
		room.remove(user);
		if(room.isEmpty()){
			chatRooms.remove(roomName);
		}
	}

	public boolean contains(String user) {
		for (String room : chatRooms.keySet()) {
			if(chatRooms.get(room).contains(user)){
				return true;
			}
		}
		return false;
	}
}
