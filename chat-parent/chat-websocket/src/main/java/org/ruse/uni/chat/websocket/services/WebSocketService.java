package org.ruse.uni.chat.websocket.services;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.ruse.uni.chat.core.security.SecureUser;

public interface WebSocketService {

	SecureUser initializeSocket(AtmosphereResourceFactory resourceFactory, AtmosphereResource resource);

}
