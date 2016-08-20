package org.ruse.uni.chat.websocket.util;

import javax.servlet.http.Cookie;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.security.jwt.JwtGenerator;
import org.ruse.uni.chat.security.util.SecurityUtil;

public class Util {

	private Util() {
		// default
	}

	public static String extractJwtFromCookie(AtmosphereResource handshake) {
		Cookie[] cookies = handshake.getRequest().getCookies();
		return SecurityUtil.extractJwtFromCookie(cookies);
	}

	public static AtmosphereResource getHandshakeResource(AtmosphereResourceFactory resourceFactory,
			AtmosphereResource resource) {
		String handshakeId = resource.getRequest().getAttribute(ApplicationConfig.SUSPENDED_ATMOSPHERE_RESOURCE_UUID)
				.toString();
		return resourceFactory.find(handshakeId);
	}

	public static SecureUser extractUser(AtmosphereResourceFactory resourceFactory, AtmosphereResource resource,
			JwtGenerator jwtGenerator) {
		AtmosphereResource handshake = getHandshakeResource(resourceFactory, resource);
		String jwt = Util.extractJwtFromCookie(handshake);
		if (jwt != null && !jwt.isEmpty()) {
			return jwtGenerator.parse(jwt);
		}
		return null;
	}

	public static String getRoomId(AtmosphereResource resource) {
		String roomPath = resource.getBroadcaster().getID();
		int lastIndexOf = roomPath.lastIndexOf("/");
		return roomPath.substring(lastIndexOf + 1);
	}

}
