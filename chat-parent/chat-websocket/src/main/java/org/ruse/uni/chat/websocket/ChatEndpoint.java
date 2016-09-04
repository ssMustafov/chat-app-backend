package org.ruse.uni.chat.websocket;

import java.io.IOException;

import javax.inject.Inject;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.config.service.Singleton;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.websocket.services.WebSocketService;
import org.ruse.uni.chat.websocket.util.Util;

/**
 * @author sinan
 */
@Singleton
@ManagedService(path = "/chat/{room: [0-9]+}")
public class ChatEndpoint {

	private static final String USER_ID_KEY = "userId";

	@Inject
	private AtmosphereResourceFactory resourceFactory;

	@Inject
	private WebSocketService webSocketService;

	@Ready
	public void onReady(AtmosphereResource resource) {
		SecureUser user = webSocketService.initializeSocket(resourceFactory, resource);
		if (user == null) {
			try {
				resource.getResponse().close();
				resource.getRequest().destroy(true);
			} catch (IOException e) {
				System.out.println("Error closing connection: " + e.getMessage());
			}
		} else {
			Long roomId = Long.valueOf(Util.getRoomId(resource));
			System.out.println("Room id: " + roomId);
			System.out.println("Browser connected: " + resource.uuid());
			resource.getRequest().localAttributes().put(USER_ID_KEY, user.getId());
		}
	}

	@Disconnect
	public void onDisconnect(AtmosphereResourceEvent event) {
		if (event.isCancelled()) {
			System.out.println("Browser unexpectedly disconnected: " + event.getResource().uuid());
		} else if (event.isClosedByClient()) {
			System.out.println("Browser {} closed the connection: " + event.getResource().uuid());
		}
	}

	@Message(encoders = { ChatProtocolEncoder.class }, decoders = { ChatProtocolDecoder.class })
	public ChatProtocol onMessage(AtmosphereResource resource, ChatProtocol protocol) {
		System.out.println(protocol);

		String roomId = Util.getRoomId(resource);
		webSocketService.fireEvent(roomId, protocol);
		return protocol;
	}

}