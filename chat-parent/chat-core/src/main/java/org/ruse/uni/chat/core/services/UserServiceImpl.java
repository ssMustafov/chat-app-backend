package org.ruse.uni.chat.core.services;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ruse.uni.chat.core.dao.UserDao;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;
import org.ruse.uni.chat.core.security.PasswordCredential;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.core.security.SecurityUtil;

/**
 *
 * @author sinan
 */
@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	private UserDao userDao;

	@Override
	public void register(SecureUser user, PasswordCredential credential) {
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new ChatRuntimeException("Email cannot be empty");
		}
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			throw new ChatRuntimeException("username cannot be empty");
		}
		if (isEmailTaken(user.getEmail())) {
			throw new ChatRuntimeException("User with '" + user.getEmail() + "' email already exists");
		}
		if (isUsernameTaken(user.getUsername())) {
			throw new ChatRuntimeException("User with '" + user.getUsername() + " username already exists'");
		}

		User converted = SecurityUtil.convertSecureUsertToEntity(user, credential);
		converted.setRegisteredOn(new Date());
		userDao.save(converted);
	}

	@Override
	public SecureUser validateCredentials(String username, String password) {
		User user = userDao.validateCredentials(username, password);
		if (user != null) {
			return new SecureUser(user);
		}
		return null;
	}

	@Override
	public boolean isUsernameTaken(String username) {
		return userDao.findByUsername(username) != null;
	}

	@Override
	public boolean isEmailTaken(String email) {
		return userDao.findByEmail(email) != null;
	}

}
