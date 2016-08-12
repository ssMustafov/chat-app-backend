package org.ruse.uni.chat.core.services;

import org.ruse.uni.chat.core.entity.User;

/**
 *
 * @author sinan
 */
public interface UserService {

	void register(User user);

	User validateCredentials(String username, String password);

	boolean isUsernameTaken(String username);

	boolean isEmailTaken(String email);

}
