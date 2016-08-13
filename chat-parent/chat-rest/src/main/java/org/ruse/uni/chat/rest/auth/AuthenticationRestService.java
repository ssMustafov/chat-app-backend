package org.ruse.uni.chat.rest.auth;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.security.PasswordCredential;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.core.security.SecurityUtil;
import org.ruse.uni.chat.core.services.UserService;
import org.ruse.uni.chat.rest.annotations.PublicRest;
import org.ruse.uni.chat.rest.events.UserAuthenticatedEvent;
import org.ruse.uni.chat.rest.exceptions.AuthenticationException;
import org.ruse.uni.chat.security.jwt.JwtGenerator;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationRestService {

	@Inject
	private UserService userService;

	@Inject
	private JwtGenerator jwtGenerator;

	@Inject
	private Event<UserAuthenticatedEvent> userAuthenticatedEvent;

	@POST
	@PublicRest
	public Response authenticate(Credentials credentials) {
		try {
			SecureUser secureUser = authenticateInternal(credentials);
			String token = issueToken(secureUser);

			JSONObject json = new JSONObject();
			json.put("auth_key", token);
			json.put("message", "success");

			userAuthenticatedEvent.fire(new UserAuthenticatedEvent(secureUser));

			return Response.ok(json.toString()).build();
		} catch (AuthenticationException e) {
			JSONObject json = new JSONObject();
			json.put("message", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}
	}

	private SecureUser authenticateInternal(Credentials credentials) {
		SecureUser user = userService.validateCredentials(credentials.getUsername(), credentials.getPassword());
		if (user == null) {
			throw new AuthenticationException("Not existing user with that credentials");
		}
		return user;
	}

	private String issueToken(SecureUser user) {
		return jwtGenerator.generate(user);
	}

	@POST
	@PublicRest
	@Path("/register")
	public Response register(Credentials credentials) {
		try {
			registerInternal(credentials);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "success");
			return Response.ok(jsonObject.toString()).build();
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(jsonObject.toString()).build();
		}
	}

	private void registerInternal(Credentials credentials) {
		User user = createUser(credentials);
		userService.register(SecurityUtil.convertEntityToSecureUser(user), createPasswordCredential(credentials));
	}

	private static User createUser(Credentials credentials) {
		User user = new User();
		user.setEmail(credentials.getEmail());
		user.setName(credentials.getName());
		user.setUsername(credentials.getUsername());
		return user;
	}

	private static PasswordCredential createPasswordCredential(Credentials credentials) {
		return new PasswordCredential(credentials.getPassword());
	}

}
