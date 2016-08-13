package org.ruse.uni.chat.core.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ruse.uni.chat.core.dao.UserDao;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;
import org.ruse.uni.chat.core.security.SecureUser;

/**
 *
 * @author sinan
 */
@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	private UserDao userDao;

	@Override
	public void register(SecureUser user) {
		if (isEmailTaken(user.getEmail())) {
			throw new ChatRuntimeException("User with '" + user.getEmail() + "' email already exists");
		}
		if (isUsernameTaken(user.getUsername())) {
			throw new ChatRuntimeException("User with '" + user.getUsername() + " username already exists'");
		}

		userDao.save(convertToUser(user));
	}

	@Override
	public SecureUser validateCredentials(String username, String password) {
		return new SecureUser(userDao.validateCredentials(username, password));
	}

	@Override
	public boolean isUsernameTaken(String username) {
		return userDao.findByUsername(username) != null;
	}

	@Override
	public boolean isEmailTaken(String email) {
		return userDao.findByEmail(email) != null;
	}

	private static User convertToUser(SecureUser secureUser) {
		User user = new User();
		user.setEmail(secureUser.getEmail());
		user.setName(secureUser.getName());
		user.setRegisteredOn(secureUser.getRegisteredOn());
		user.setUsername(secureUser.getUsername());
		return user;
	}

}
