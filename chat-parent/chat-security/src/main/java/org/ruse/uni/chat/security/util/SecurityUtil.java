package org.ruse.uni.chat.security.util;

import javax.servlet.http.Cookie;

public class SecurityUtil {

	public static final String AUTH_COOKIE = "__AUTH_KEY__";

	private SecurityUtil() {
		// default
	}

	public static String extractJwtFromCookie(Cookie[] cookies) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(AUTH_COOKIE)) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
