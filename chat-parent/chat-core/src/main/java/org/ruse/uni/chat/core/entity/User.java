package org.ruse.uni.chat.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ruse.uni.chat.core.entity.converters.PasswordHashConverter;
import org.ruse.uni.chat.core.query.DbQueries;

/**
 *
 * @author sinan
 */
@Entity(name = User.TABLE_NAME)
@NamedQueries({ @NamedQuery(name = DbQueries.GET_ALL_USERS_NAME, query = DbQueries.GET_ALL_USERS_QUERY),
		@NamedQuery(name = DbQueries.GET_USER_BY_USERNAME_NAME, query = DbQueries.GET_USER_BY_USERNAME_QUERY),
		@NamedQuery(name = DbQueries.GET_USER_BY_EMAIL_NAME, query = DbQueries.GET_USER_BY_EMAIL_QUERY),
		@NamedQuery(name = DbQueries.VALIDATE_CREDENTIALS_NAME, query = DbQueries.VALIDATE_CREDENTIALS_QUERY) })
public class User extends BaseEntity {

	private static final long serialVersionUID = -4161944523220700819L;

	public static final String TABLE_NAME = "chat_user";

	@Column(unique = true, length = 24)
	private String username;

	@Column(unique = true, length = 100)
	private String email;

	@Column(length = 100)
	private String name;

	@Column(nullable = false, length = 44)
	@Convert(converter = PasswordHashConverter.class)
	private String password;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredOn;

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

	/**
	 * Getter method for username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter method for username.
	 *
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter method for email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method for email.
	 *
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter method for name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for name.
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method for password.
	 *
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(100);
		builder.append("User [id=").append(getId()).append(", username=").append(username).append(", email=")
				.append(email).append(", name=").append(name).append(", registeredOn=").append(registeredOn)
				.append("]");
		return builder.toString();
	}

}
