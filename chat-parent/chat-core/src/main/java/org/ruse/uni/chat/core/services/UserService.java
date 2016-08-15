package org.ruse.uni.chat.core.services;

import org.ruse.uni.chat.core.security.PasswordCredential;
import org.ruse.uni.chat.core.security.SecureUser;

/**
 *
 * @author sinan
 */
public interface UserService {

	SecureUser register(SecureUser user, PasswordCredential credential);

	SecureUser validateCredentials(String username, String password);

	boolean isUsernameTaken(String username);

	boolean isEmailTaken(String email);

	SecureUser getById(Long id);

	SecureUser getByUsername(String username);

	SecureUser getByEmail(String email);

}
