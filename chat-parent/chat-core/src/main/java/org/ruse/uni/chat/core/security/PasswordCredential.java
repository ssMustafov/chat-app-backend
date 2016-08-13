package org.ruse.uni.chat.core.security;

public class PasswordCredential {

	private String password;

	public PasswordCredential() {
		// default
	}

	public PasswordCredential(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
