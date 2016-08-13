package org.ruse.uni.chat.rest.filters;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.core.services.UserService;
import org.ruse.uni.chat.rest.exceptions.AuthenticationException;
import org.ruse.uni.chat.security.jwt.JwtGenerator;

/**
 *
 * @author sinan
 */
@Singleton
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Inject
	private UserService userService;

	@Inject
	private JwtGenerator jwtGenerator;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {
			validateToken(token);
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "Unauthenticated");
			requestContext
					.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(jsonObject.toString()).build());
		}
	}

	private void validateToken(String token) {
		SecureUser parsed = jwtGenerator.parse(token);
		if (parsed == null || !userService.isEmailTaken(parsed.getEmail())
				|| !userService.isUsernameTaken(parsed.getUsername())) {
			throw new AuthenticationException("Invalid token");
		}
	}

}
