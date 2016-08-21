package org.ruse.uni.chat.core.events;

import org.ruse.uni.chat.core.entity.User;

/**
 * Event fired after user is persisted(registered).
 *
 * @author sinan
 */
public class UserPersistedEvent {

	private User user;

	public UserPersistedEvent(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
