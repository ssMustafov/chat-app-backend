package org.ruse.uni.chat.websocket;

import org.atmosphere.config.managed.Decoder;
import org.json.JSONObject;

/**
 *
 * @author sinan
 */
public class ChatMessageDecoder implements Decoder<String, ChatMessage> {

	@Override
	public ChatMessage decode(String s) {
		JSONObject json = new JSONObject(s);
		ChatMessage message = new ChatMessage();
		message.setAuthor(json.getString("author"));
		message.setMessage(json.getString("message"));
		return message;
	}

}
