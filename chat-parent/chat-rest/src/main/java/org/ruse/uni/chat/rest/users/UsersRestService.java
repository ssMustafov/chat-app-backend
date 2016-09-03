package org.ruse.uni.chat.rest.users;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.security.SecurityUtil;
import org.ruse.uni.chat.core.services.UserService;
import org.ruse.uni.chat.core.util.JsonUtil;

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

	@POST
	@Path("{id}")
	public Response updateUser(@PathParam("id") String id, String data) {
		JSONObject json = new JSONObject(data);
		String email = JsonUtil.getString("email", json);
		String name = JsonUtil.getString("name", json);
		boolean set = false;

		try {
			User user = userService.getById(Long.valueOf(id));
			if (email != null && !email.isEmpty()) {
				user.setEmail(email);
				set = true;
			}
			if (name != null && !name.isEmpty()) {
				user.setName(name);
				set = true;
			}
			if (set) {
				userService.update(user);
			}
			return Response.ok().build();
		} catch (Exception e) {
			JSONObject error = new JSONObject();
			error.put("message", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(error.toString()).build();
		}
	}

}
