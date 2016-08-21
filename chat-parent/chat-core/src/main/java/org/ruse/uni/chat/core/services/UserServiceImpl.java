package org.ruse.uni.chat.core.services;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.ruse.uni.chat.core.dao.UserDao;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.events.UserPersistedEvent;
import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;
import org.ruse.uni.chat.core.security.PasswordCredential;

/**
 *
 * @author sinan
 */
@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	private UserDao userDao;

	@Inject
	private Event<UserPersistedEvent> userPersistedEvent;

	@Override
	public User register(User user, PasswordCredential credential) {
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new ChatRuntimeException("Email cannot be empty");
		}
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			throw new ChatRuntimeException("Username cannot be empty");
		}
		if (credential.getPassword() == null || credential.getPassword().isEmpty()) {
			throw new ChatRuntimeException("Password cannot be empty");
		}
		if (isEmailTaken(user.getEmail())) {
			throw new ChatRuntimeException("User with '" + user.getEmail() + "' email already exists");
		}
		if (isUsernameTaken(user.getUsername())) {
			throw new ChatRuntimeException("User with '" + user.getUsername() + " username already exists'");
		}

		user.setPassword(credential.getPassword());
		user.setRegisteredOn(new Date());
		userDao.save(user);

		userPersistedEvent.fire(new UserPersistedEvent(user));

		return user;
	}

	@Override
	public User validateCredentials(String username, String password) {
		User user = userDao.validateCredentials(username, password);
		if (user != null) {
			return user;
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

	@Override
	public User getById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public User getByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User getByEmail(String email) {
		return userDao.findByEmail(email);
	}

}
