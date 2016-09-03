package org.ruse.uni.chat.core.services;

import java.util.List;

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

	User changePassword(Long id, String currentPassword, String newPassword);

	User getByEmail(String email);

	User update(User user);

	List<User> getAllUsers();

}
