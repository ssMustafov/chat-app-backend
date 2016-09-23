package org.ruse.uni.chat.core.events;

import org.ruse.uni.chat.core.security.SecureUser;

public class UserAuthenticatedEvent {

	private SecureUser user;

	public UserAuthenticatedEvent(SecureUser user) {
		this.user = user;
	}

	public SecureUser getAuthenticatedUser() {
		return user;
	}

}
