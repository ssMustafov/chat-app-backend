package org.ruse.uni.chat.websocket;

import java.io.IOException;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;

/**
 * Simple annotated class that demonstrate the power of Atmosphere. This class
 * supports all transports, support message length guarantee, heart beat,
 * message cache thanks to the @ManagedService.
 */
@ManagedService(path = "/chat")
public class ChatEndpoint {

	/**
	 * Invoked when the connection as been fully established and suspended, e.g
	 * ready for receiving messages.
	 *
	 * @param resource
	 *            the atmosphere resource
	 */
	@Ready
	public void onReady(AtmosphereResource resource) {
		System.out.println("Browser connected: " + resource.uuid());
	}

	/**
	 * Invoked when the client disconnect or when an unexpected closing of the
	 * underlying connection happens.
	 *
	 * @param event
	 *            the event
	 */
	@Disconnect
	public void onDisconnect(AtmosphereResourceEvent event) {
		if (event.isCancelled()) {
			System.out.println("Browser unexpectedly disconnected: " + event.getResource().uuid());
		} else if (event.isClosedByClient()) {
			System.out.println("Browser {} closed the connection: " + event.getResource().uuid());
		}
	}

	/**
	 * Simple annotated class that demonstrate how
	 * {@link org.atmosphere.config.managed.Encoder} and
	 * {@link org.atmosphere.config.managed.Decoder can be used.
	 *
	 * @param message
	 *            an instance of {@link ChatMessage }
	 * @return the chat message
	 * @throws IOException
	 */
	@Message(encoders = { ChatMessageEncoder.class }, decoders = { ChatMessageDecoder.class })
	public ChatMessage onMessage(ChatMessage message) {
		System.out.println(message.getAuthor() + " just send: " + message.getMessage());
		return message;
	}

}