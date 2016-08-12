package org.ruse.uni.chat.core.exceptions;

/**
 *
 *
 * @author sinan
 */
public class ChatRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -2623445262530398054L;

	/**
	 * Creates new chat runtime exception.
	 */
	public ChatRuntimeException() {
		// default
	}

	/**
	 * Creates new chat runtime exception with the given message.
	 *
	 * @param message
	 *            the message for the exception
	 */
	public ChatRuntimeException(String message) {
		super(message);
	}

	/**
	 * Creates new chat runtime exception with the caused exception.
	 *
	 * @param cause
	 *            the caused exception
	 */
	public ChatRuntimeException(Throwable cause) {
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
	public ChatRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
