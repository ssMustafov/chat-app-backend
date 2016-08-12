package org.ruse.uni.chat.rest.exceptions;

import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;

public class AuthenticationException extends ChatRuntimeException {

	private static final long serialVersionUID = -5133113897028864702L;

	/**
	 * Creates new chat runtime exception.
	 */
	public AuthenticationException() {
		// default
	}

	/**
	 * Creates new chat runtime exception with the given message.
	 *
	 * @param message
	 *            the message for the exception
	 */
	public AuthenticationException(String message) {
		super(message);
	}

	/**
	 * Creates new chat runtime exception with the caused exception.
	 *
	 * @param cause
	 *            the caused exception
	 */
	public AuthenticationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates new chat runtime exception with given message and caused exception.
	 *
	 * @param message
	 *            the message for the exception
	 * @param cause
	 *            the caused exception
	 */
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

}
