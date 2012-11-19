package ch.hsr.lechat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MessageParser {
	
	private static final Map<String, String> smileys;
	
	static {
	  HashMap<String, String> m = new HashMap<String, String>();
	  m.put("(no)","<img src='/LeChat/images/memes/no.png'/>");
	  m.put("(foreveralone)","<img src='/LeChat/images/memes/foreveralone.png'/>");
	  m.put("(megusta)","<img src='/LeChat/images/memes/megusta.png'/>");
	  m.put("(kidding)","<img src='/LeChat/images/memes/kidding.png'/>");
	  m.put("(tableflip)","<img src='/LeChat/images/memes/tableflip.png'/>");
	  m.put("(trololo)","<img src='/LeChat/images/memes/trololo.png'/>");
	  smileys = Collections.unmodifiableMap(m);
	}
	
	public static String parse(String message){
		
		for (String smiley : smileys.keySet()) {
			message = message.replace(smiley, smileys.get(smiley));
		}
		
		return message;
	}

}
