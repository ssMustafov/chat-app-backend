package org.ruse.uni.chat.websocket;

import java.io.Serializable;
import java.util.Date;

import org.ruse.uni.chat.core.security.SecureUser;

/**
 * @author sinan
 */
public class ChatProtocol implements Serializable {

	private static final long serialVersionUID = 7930914755052800396L;
	private String data;
	private SecureUser user;
	private Date receivedDate;

	public ChatProtocol() {
		// default
	}

	public ChatProtocol(SecureUser user, String data, Date receivedDate) {
		this.user = user;
		this.receivedDate = receivedDate;
		this.data = data;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(256);
		builder.append("ChatProtocol [").append("user=").append(user).append(", receivedDate=").append(receivedDate)
				.append(", data=").append((data == null || data.isEmpty() ? "NULL" : "NOT_NULL")).append("]");
		return builder.toString();
	}

	public SecureUser getUser() {
		return user;
	}

	public void setUser(SecureUser user) {
		this.user = user;
	}

}