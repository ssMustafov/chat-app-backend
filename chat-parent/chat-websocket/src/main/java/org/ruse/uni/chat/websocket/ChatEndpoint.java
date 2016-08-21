package org.ruse.uni.chat.websocket;

import javax.inject.Inject;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.config.service.Singleton;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.ruse.uni.chat.websocket.services.WebSocketService;
import org.ruse.uni.chat.websocket.util.Util;

/**
 * @author sinan
 */
@Singleton
@ManagedService(path = "/chat/{room: [0-9]+}")
public class ChatEndpoint {

	@Inject
	private AtmosphereResourceFactory resourceFactory;

	@Inject
	private WebSocketService webSocketService;

	@Ready(encoders = { ChatProtocolEncoder.class })
	public void onReady(AtmosphereResource resource) {
		webSocketService.initializeSocket(resourceFactory, resource);

		System.out.println("Room id: " + Util.getRoomId(resource));
		System.out.println("Browser connected: " + resource.uuid());
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
	public ChatProtocol onMessage(ChatProtocol protocol) {
		System.out.println(protocol);
		return protocol;
	}

}