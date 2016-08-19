package org.ruse.uni.chat.websocket;

import javax.inject.Inject;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.ruse.uni.chat.websocket.services.WebSocketService;

/**
 * @author sinan
 */
@ManagedService(path = "/chat")
public class ChatEndpoint {

	@Inject
	private AtmosphereResourceFactory resourceFactory;

	@Inject
	private WebSocketService webSocketService;

	@Ready
	public void onReady(AtmosphereResource resource) {
		webSocketService.initializeSocket(resourceFactory, resource);

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

	@Message(encoders = { ChatMessageEncoder.class }, decoders = { ChatMessageDecoder.class })
	public ChatMessage onMessage(ChatMessage message) {
		System.out.println(message.getAuthor() + " just send: " + message.getMessage());
		return message;
	}

}