package org.ruse.uni.chat.websocket.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.events.MessageSentEvent;
import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;
import org.ruse.uni.chat.core.message.Message;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.core.services.RoomService;
import org.ruse.uni.chat.core.services.UserService;
import org.ruse.uni.chat.security.jwt.JwtGenerator;
import org.ruse.uni.chat.websocket.ChatProtocol;
import org.ruse.uni.chat.websocket.util.Util;

/**
 * @author sinan
 */
@ApplicationScoped
public class WebSocketServiceImpl implements WebSocketService {

	@Inject
	private JwtGenerator jwtGenerator;

	@Inject
	private RoomService roomService;

	@Inject
	private UserService userService;

	@Inject
	private Event<MessageSentEvent> event;

	@Override
	public SecureUser initializeSocket(AtmosphereResourceFactory resourceFactory, AtmosphereResource resource) {
		// first check if the user was authenticated during initial handshake
		SecureUser user = Util.extractUser(resourceFactory, resource, jwtGenerator);
		if (user == null) {
			throw new ChatRuntimeException("Not authenticated");
		}

		String roomId = Util.getRoomId(resource);
		User userEntity = userService.getById(user.getId());
		boolean canJoin = roomService.canJoin(Long.valueOf(roomId), userEntity);
		if (!canJoin) {
			throw new ChatRuntimeException("Can't connect to this room: " + roomId);
		}

		return user;
	}

	@Override
	public void fireEvent(String roomId, ChatProtocol protocol) {
		Message message = new Message();
		message.setMessage(protocol.getData());
		message.setRoomId(Long.valueOf(roomId));
		message.setUserId(protocol.getUser().getId());
		message.setSentOn(protocol.getReceivedDate());

		event.fire(new MessageSentEvent(message));
	}

}
