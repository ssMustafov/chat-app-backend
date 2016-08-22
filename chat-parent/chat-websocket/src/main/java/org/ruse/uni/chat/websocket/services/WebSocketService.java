package org.ruse.uni.chat.websocket.services;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceFactory;

public interface WebSocketService {

	boolean initializeSocket(AtmosphereResourceFactory resourceFactory, AtmosphereResource resource);

}
