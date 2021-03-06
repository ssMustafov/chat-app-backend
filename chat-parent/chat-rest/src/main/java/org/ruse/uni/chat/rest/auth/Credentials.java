package org.ruse.uni.chat.rest.auth;

import java.io.Serializable;

public class Credentials implements Serializable {

	private static final long serialVersionUID = -3341998650312827998L;

	private String username;
	private String password;
	private String name;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
