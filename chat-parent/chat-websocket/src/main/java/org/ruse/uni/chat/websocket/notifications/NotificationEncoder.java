package org.ruse.uni.chat.websocket.notifications;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

/**
 * @author sinan
 */
public class NotificationEncoder implements Encoder.Text<Notification> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public String encode(Notification notification) throws EncodeException {
		JSONObject json = new JSONObject();
		json.put("roomId", notification.getRoomId());
		json.put("type", notification.getType().getType());
		json.put("user", notification.getUser().toJson());
		return json.toString();
	}

}
