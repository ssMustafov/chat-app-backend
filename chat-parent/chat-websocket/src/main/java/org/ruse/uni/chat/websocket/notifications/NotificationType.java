package org.ruse.uni.chat.websocket.notifications;

/**
 * @author sinan
 */
public enum NotificationType {
	MESSAGE_RECEIVED("message"), USER_JOINED_ROOM("joined"), USER_LEFT_ROOM("left");

	private String type;

	private NotificationType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return type;
	}
}
