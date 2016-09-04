package org.ruse.uni.chat.core.events;

import org.ruse.uni.chat.core.message.Message;

public class MessageSentEvent {

	private Message message;

	public MessageSentEvent(Message message) {
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}

}
