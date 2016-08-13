package org.ruse.uni.chat.rest.exceptions;

import javax.ws.rs.core.Response.Status;

public class RestError {

	private Status status;
	private String errorMessage;
	private Throwable error;

	public RestError() {
		// default
	}

	public RestError(Status status, String errorMessage, Throwable error) {
		this.status = status;
		this.error = error;
		this.errorMessage = errorMessage;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

}
