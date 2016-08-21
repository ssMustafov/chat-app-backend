package org.ruse.uni.chat.websocket;

import java.util.Date;

import org.atmosphere.config.managed.Decoder;
import org.json.JSONObject;
import org.ruse.uni.chat.core.security.SecureUser;

/**
 *
 * @author sinan
 */
public class ChatProtocolDecoder implements Decoder<String, ChatProtocol> {

	@Override
	public ChatProtocol decode(String s) {
		JSONObject json = new JSONObject(s);
		ChatProtocol protocol = new ChatProtocol();
		protocol.setUser(SecureUser.parseFromJson(json.getJSONObject("user")));
		protocol.setReceivedDate(new Date());
		protocol.setData(json.getString("data"));
		return protocol;
	}

}
