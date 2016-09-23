package org.ruse.uni.chat.websocket.notifications;

import java.io.Serializable;

import org.ruse.uni.chat.core.security.SecureUser;

/**
 * @author sinan
 */
public class Notification implements Serializable {

	private static final long serialVersionUID = -2785699808698782049L;

	private String roomId;
	private SecureUser user;
	private NotificationType type;

	public Notification() {
		// default
	}

	public Notification(String roomId, SecureUser user, NotificationType type) {
		this.roomId = roomId;
		this.user = user;
		this.type = type;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public SecureUser getUser() {
		return user;
	}

	public void setUser(SecureUser user) {
		this.user = user;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notification [").append("roomId=").append(roomId).append(", user=").append(user.getId())
				.append(", type=").append(type.getType()).append("]");
		return builder.toString();
	}

}
