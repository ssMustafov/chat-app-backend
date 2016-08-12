package org.ruse.uni.chat.core.query;

/**
 *
 * @author sinan
 */
public interface DbQueries {

	String GET_ALL_USERS_NAME = "GET_ALL_USERS";
	String GET_ALL_USERS_QUERY = "SELECT auser FROM chat_user auser";

	String GET_USER_BY_USERNAME_NAME = "GET_USER_BY_USERNAME";
	String GET_USER_BY_USERNAME_QUERY = "SELECT auser FROM chat_user auser WHERE auser.username = :username";

	String GET_USER_BY_EMAIL_NAME = "GET_USER_BY_EMAIL";
	String GET_USER_BY_EMAIL_QUERY = "SELECT auser FROM chat_user auser WHERE auser.email = :email";

	String VALIDATE_CREDENTIALS_NAME = "VALIDATE_CREDENTIALS";
	String VALIDATE_CREDENTIALS_QUERY = "SELECT u FROM chat_user u WHERE u.username = :username AND u.password = :password";

}
