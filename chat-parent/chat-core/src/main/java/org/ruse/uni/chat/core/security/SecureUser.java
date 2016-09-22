package org.ruse.uni.chat.core.security;

import java.util.Date;

import org.json.JSONObject;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.util.JsonUtil;

/**
 * Represents user with not sensitive fields and only getters.
 *
 * @author sinan
 */
public class SecureUser {

	private Long id;
	private String username;
	private String name;
	private String email;
	private Date registeredOn;
	private String image;

	public static final SecureUser SYSTEM_USER;

	static {
		User user = new User();
		user.setName("System User");
		user.setUsername("system");
		SYSTEM_USER = new SecureUser(user);
	}

	public SecureUser(User user) {
		id = user.getId();
		username = user.getUsername();
		name = user.getName();
		email = user.getEmail();
		registeredOn = user.getRegisteredOn();
		image = user.getImage();
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("username", username);
		json.put("name", name);
		json.put("email", email);
		json.put("registeredOn", registeredOn.getTime());
		json.put("image", image);
		return json;
	}

	public static SecureUser parseFromJson(JSONObject json) {
		User user = new User();
		user.setId(json.getLong("id"));
		user.setUsername(json.getString("username"));
		user.setName(json.getString("name"));
		user.setEmail(json.getString("email"));
		user.setRegisteredOn(new Date(json.getLong("registeredOn")));
		String image = JsonUtil.getString("image", json);
		if (image != null && !image.isEmpty()) {
			user.setImage(image);
		}
		return SecurityUtil.convertEntityToSecureUser(user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (id != other.getId()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.intValue() + username.length() * email.length();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SecureUser [id=").append(id).append(", username=").append(username).append(", name=")
				.append(name).append(", email=").append(email).append(", registeredOn=").append(registeredOn)
				.append("]");
		return builder.toString();
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public String getImage() {
		return image;
	}

}
