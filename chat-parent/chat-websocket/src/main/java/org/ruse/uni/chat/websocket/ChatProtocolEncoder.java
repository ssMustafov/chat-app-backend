package org.ruse.uni.chat.websocket;

import org.atmosphere.config.managed.Encoder;
import org.json.JSONObject;

/**
 *
 * @author sinan
 */
public class ChatProtocolEncoder implements Encoder<ChatProtocol, String> {

	@Override
	public String encode(ChatProtocol protocol) {
		JSONObject json = new JSONObject();
		json.put("user", protocol.getUser().toJson());
		json.put("receivedDate", protocol.getReceivedDate().getTime());
		json.put("data", protocol.getData());
		return json.toString();
	}

}
