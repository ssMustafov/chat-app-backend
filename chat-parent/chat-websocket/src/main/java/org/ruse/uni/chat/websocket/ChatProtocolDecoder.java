package org.ruse.uni.chat.websocket;

import org.atmosphere.config.managed.Decoder;
import org.json.JSONObject;

/**
 *
 * @author sinan
 */
public class ChatProtocolDecoder implements Decoder<String, ChatProtocol> {

	@Override
	public ChatProtocol decode(String s) {
		JSONObject json = new JSONObject(s);
		ChatProtocol message = new ChatProtocol();
		message.setAuthor(json.getString("author"));
		message.setMessage(json.getString("message"));
		return message;
	}

}
