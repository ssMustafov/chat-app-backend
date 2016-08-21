package org.ruse.uni.chat.core.services;

import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.security.PasswordCredential;

/**
 *
 * @author sinan
 */
public interface UserService {

	User register(User user, PasswordCredential credential);

	User validateCredentials(String username, String password);

	boolean isUsernameTaken(String username);

	boolean isEmailTaken(String email);

	User getById(Long id);

	User getByUsername(String username);

	User getByEmail(String email);

}
