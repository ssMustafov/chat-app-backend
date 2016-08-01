package org.ruse.uni.chat.websocket;

import org.atmosphere.config.managed.Encoder;
import org.json.JSONObject;

/**
 *
 * @author sinan
 */
public class ChatMessageEncoder implements Encoder<ChatMessage, String> {

	@Override
	public String encode(ChatMessage message) {
		JSONObject json = new JSONObject();
		json.put("author", message.getAuthor());
		json.put("time", message.getTime());
		json.put("message", message.getMessage());
		return json.toString();
	}

}
