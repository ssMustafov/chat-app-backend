package org.ruse.uni.chat.websocket.services;

import javax.inject.Inject;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.security.jwt.JwtGenerator;
import org.ruse.uni.chat.websocket.util.Util;

public class WebSocketServiceImpl implements WebSocketService {

	@Inject
	private JwtGenerator jwtGenerator;

	@Override
	public void initializeSocket(AtmosphereResourceFactory resourceFactory, AtmosphereResource resource) {
		// first check if the user was authenticated during initial handshake
		SecureUser user = Util.extractUser(resourceFactory, resource, jwtGenerator);
		if (user == null) {
			throw new ChatRuntimeException("Not authenticated");
		}
	}

}
