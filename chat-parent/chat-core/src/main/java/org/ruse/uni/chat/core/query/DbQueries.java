package org.ruse.uni.chat.core.query;

/**
 *
 * @author sinan
 */
public interface DbQueries {

	// user
	String GET_ALL_USERS_NAME = "GET_ALL_USERS";
	String GET_ALL_USERS_QUERY = "SELECT auser FROM chat_user auser";

	String GET_USER_BY_USERNAME_NAME = "GET_USER_BY_USERNAME";
	String GET_USER_BY_USERNAME_QUERY = "SELECT auser FROM chat_user auser WHERE auser.username = :username";

	String GET_USER_BY_EMAIL_NAME = "GET_USER_BY_EMAIL";
	String GET_USER_BY_EMAIL_QUERY = "SELECT auser FROM chat_user auser WHERE auser.email = :email";

	String VALIDATE_CREDENTIALS_NAME = "VALIDATE_CREDENTIALS";
	String VALIDATE_CREDENTIALS_QUERY = "SELECT u FROM chat_user u WHERE u.username = :username AND u.password = :password";

	// room
	String GET_ALL_ROOMS_NAME = "GET_ALL_ROOMS";
	String GET_ALL_ROOMS_QUERY = "SELECT room FROM chat_room room";

	String GET_ROOMS_FOR_USER_NAME = "GET_ROOMS_FOR_USER";
	String GET_ROOMS_FOR_USER_QUERY = "SELECT room FROM chat_room room INNER JOIN room.users uid WHERE uid = :user ";

	// file
	String GET_ALL_FILES_NAME = "GET_ALL_FILES";
	String GET_ALL_FILES_QUERY = "SELECT file FROM chat_file file";

	String GET_FILES_FOR_ROOM_NAME = "GET_FILES_FOR_ROOM";
	String GET_FILES_FOR_ROOM_QUERY = "SELECT file FROM chat_file file WHERE file.room = :id";

}
