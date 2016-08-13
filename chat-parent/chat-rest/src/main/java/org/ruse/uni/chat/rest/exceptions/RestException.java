package org.ruse.uni.chat.rest.exceptions;

import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;

/**
 *
 * @author sinan
 */
public class RestException extends ChatRuntimeException {

	private static final long serialVersionUID = 3966305132831629922L;

	private RestError error;

	public RestException() {
		// default
	}

	public RestException(RestError error) {
		this.error = error;
	}

	public RestException(RestError error, Throwable cause) {
		super(cause);
		this.error = error;
	}

	public RestError getError() {
		return error;
	}

	public void setError(RestError error) {
		this.error = error;
	}

}
