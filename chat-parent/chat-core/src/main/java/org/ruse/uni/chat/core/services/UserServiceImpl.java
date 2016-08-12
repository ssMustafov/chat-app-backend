package org.ruse.uni.chat.core.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ruse.uni.chat.core.dao.UserDao;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;

/**
 *
 * @author sinan
 */
@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	private UserDao userDao;

	@Override
	public void register(User user) {
		if (isEmailTaken(user.getEmail())) {
			throw new ChatRuntimeException("User with '" + user.getEmail() + "' email already exists");
		}
		if (isUsernameTaken(user.getUsername())) {
			throw new ChatRuntimeException("User with '" + user.getUsername() + " username already exists'");
		}

		userDao.save(user);
	}

	@Override
	public User validateCredentials(String username, String password) {
		return userDao.validateCredentials(username, password);
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
