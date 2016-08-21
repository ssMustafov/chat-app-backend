package org.ruse.uni.chat.core.security;

import org.ruse.uni.chat.core.entity.User;

public class SecurityUtil {

	private SecurityUtil() {
		// not allowed
	}

	public static User convertSecureUsertToEntity(SecureUser secureUser) {
		return convertSecureUsertToEntity(secureUser, new PasswordCredential());
	}

	public static User convertSecureUsertToEntity(SecureUser secureUser, PasswordCredential credential) {
		User user = new User();
		user.setEmail(secureUser.getEmail());
		user.setName(secureUser.getName());
		user.setUsername(secureUser.getUsername());
		user.setPassword(credential.getPassword());
		return user;
	}

	public static SecureUser getSystemUser() {

		return null;
	}

	public static SecureUser convertEntityToSecureUser(User user) {
		return new SecureUser(user);
	}

}
