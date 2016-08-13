package org.ruse.uni.chat.core.services;

import org.ruse.uni.chat.core.security.PasswordCredential;
import org.ruse.uni.chat.core.security.SecureUser;

/**
 *
 * @author sinan
 */
public interface UserService {

	void register(SecureUser user, PasswordCredential credential);

	SecureUser validateCredentials(String username, String password);

	boolean isUsernameTaken(String username);

	boolean isEmailTaken(String email);

}
