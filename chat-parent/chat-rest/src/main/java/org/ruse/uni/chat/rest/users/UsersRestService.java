package org.ruse.uni.chat.rest.users;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.security.SecurityUtil;
import org.ruse.uni.chat.core.services.UserService;

/**
 * @author sinan
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersRestService {

	@Inject
	private UserService userService;

	@GET
	public Response getAll() {
		List<User> users = userService.getAllUsers();
		JSONArray jsonArr = new JSONArray();
		users.forEach(user -> jsonArr.put(SecurityUtil.convertEntityToSecureUser(user).toJson()));
		return Response.ok(jsonArr.toString()).build();
	}

}
