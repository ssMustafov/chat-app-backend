package org.ruse.uni.chat.websocket.services;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.websocket.ChatProtocol;

public interface WebSocketService {

	SecureUser initializeSocket(AtmosphereResourceFactory resourceFactory, AtmosphereResource resource);

	void fireEvent(String roomId, ChatProtocol protocol);

	SecureUser extractUser(AtmosphereResourceFactory resourceFactory, AtmosphereResource resource);

}
