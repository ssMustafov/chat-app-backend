package org.ruse.uni.chat.core.services;

import java.util.List;

import org.ruse.uni.chat.core.message.Message;

/**
 * @author sinan
 */
public interface MessageService {

	List<Message> getMessages(Long roomId, int start, int rows);

}
