package org.ruse.uni.chat.core.message;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;
import org.ruse.uni.chat.core.security.SecureUser;

public class Message implements Serializable {

	private static final long serialVersionUID = 8132802368672816323L;

	private String id;
	private Long roomId;
	private Long userId;
	private String message;
	private Date sentOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSentOn() {
		return sentOn;
	}

	public void setSentOn(Date sentOn) {
		this.sentOn = sentOn;
	}

	public JSONObject toJson(SecureUser user) {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("roomId", roomId);
		json.put("message", message);
		json.put("sentOn", sentOn.getTime());
		json.put("user", user.toJson());
		return json;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
