package org.ruse.uni.chat.rest.filters;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

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
			SecureUser user = validateToken(token);
			requestContext.setSecurityContext(new ChatSecurityContext(user));
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", e.getMessage());
			requestContext
					.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(jsonObject.toString()).build());
		}
	}

	private SecureUser validateToken(String token) {
		SecureUser parsed = jwtGenerator.parse(token);
		if (parsed == null || !userService.isEmailTaken(parsed.getEmail())
				|| !userService.isUsernameTaken(parsed.getUsername())) {
			throw new AuthenticationException("Invalid token");
		}
		return parsed;
	}

	private class ChatSecurityContext implements SecurityContext {

		private SecureUser user;

		public ChatSecurityContext(SecureUser user) {
			this.user = user;
		}

		@Override
		public Principal getUserPrincipal() {
			return new Principal() {

				@Override
				public String getName() {
					return user.getUsername();
				}
			};
		}

		@Override
		public boolean isUserInRole(String role) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSecure() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getAuthenticationScheme() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
