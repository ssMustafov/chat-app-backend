package org.ruse.uni.chat.core.events;

import org.ruse.uni.chat.core.security.SecureUser;

/**
 * @author sinan
 */
public class UserJoinedRoomEvent {

	private String roomId;
	private SecureUser user;

	public UserJoinedRoomEvent(String roomId, SecureUser user) {
		this.roomId = roomId;
		this.user = user;
	}

	public String getRoomId() {
		return roomId;
	}

	public SecureUser getUser() {
		return user;
	}

}
