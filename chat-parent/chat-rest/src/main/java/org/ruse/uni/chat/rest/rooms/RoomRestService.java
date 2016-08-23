package org.ruse.uni.chat.rest.rooms;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ruse.uni.chat.core.entity.Room;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.services.RoomService;
import org.ruse.uni.chat.core.services.UserService;

/**
 * @author sinan
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomRestService {

	@Inject
	private RoomService roomService;

	@Inject
	private UserService userService;

	@Context
	private SecurityContext securityContext;

	@GET
	public Response getRooms(@QueryParam("userId") String userId, @QueryParam("fullLoad") boolean fullLoad) {
		if (userId == null || userId.isEmpty()) {
			JSONObject json = new JSONObject();
			json.put("message", "Missing userId");
			Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}
		User user = userService.getById(Long.valueOf(userId));
		if (user == null) {
			JSONObject json = new JSONObject();
			json.put("message", "User with that id does not exists");
			Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}
		List<Room> rooms = roomService.getRooms(user);

		JSONArray arr = new JSONArray();
		rooms.forEach(room -> arr.put(room.toJson(fullLoad)));
		return Response.ok(arr.toString()).build();
	}

	@GET
	@Path("{id}")
	public Response getRoomById(@PathParam("id") String id) {
		Room room = roomService.getById(Long.valueOf(id));
		if (room == null) {
			JSONObject json = new JSONObject();
			json.put("message", "Room with that id does not exist");
			return Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}
		return Response.ok(room.toJson(true).toString()).build();
	}

	@POST
	@Path("/add/user")
	public Response addUserToRoom(String data) {
		if (data == null || data.isEmpty()) {
			JSONObject json = new JSONObject();
			json.put("message", "Empty json payload passed");
			Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}
		JSONObject json = new JSONObject(data);
		String roomId = json.getString("roomId");
		long userId = json.getLong("userId");
		Room room = null;

		try {
			User user = userService.getById(userId);
			room = roomService.addUserToRoom(Long.valueOf(roomId), user);
		} catch (Exception e) {
			System.out.println("Failed to add user to room: " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}

		return Response.ok().entity(room.toJson(true).toString()).build();
	}

	@POST
	@Path("/remove/user")
	public Response removeUserFromRoom(String data) {
		if (data == null || data.isEmpty()) {
			JSONObject json = new JSONObject();
			json.put("message", "Empty json payload passed");
			Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}
		JSONObject json = new JSONObject(data);
		String roomId = json.getString("roomId");
		long userId = json.getLong("userId");
		Room room = null;

		try {
			User user = userService.getById(userId);
			room = roomService.removeUserFromRoom(Long.valueOf(roomId), user);
		} catch (Exception e) {
			System.out.println("Failed to remove user from room: " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}

		return Response.ok().entity(room.toJson(true).toString()).build();
	}

	@PUT
	public Response updateRoom(String data) {
		try {
			JSONObject requestJson = new JSONObject(data);
			long roomId = requestJson.getLong("roomId");
			String name = requestJson.getString("name");
			String description = "";
			if (requestJson.has("description")) {
				description = requestJson.getString("description");
			}

			Room room = roomService.getById(roomId);
			room.setName(name);
			room.setDescription(description);
			roomService.updateRoom(room);

			return Response.ok(room.toJson(true).toString()).build();
		} catch (Exception e) {
			System.out.println("Cannot update room: " + e.getMessage());

			JSONObject json = new JSONObject();
			json.put("message", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}
	}

	@POST
	public Response createRoom(String data) {
		try {
			JSONObject requestJson = new JSONObject(data);
			String name = requestJson.getString("name");
			String description = "";
			if (requestJson.has("description")) {
				description = requestJson.getString("description");
			}

			Room room = new Room();
			room.setDescription(description);
			room.setName(name);

			String currentUsername = securityContext.getUserPrincipal().getName();
			User user = userService.getByUsername(currentUsername);
			Set<User> users = new HashSet<>();
			users.add(user);
			room.setUsers(users);

			roomService.saveRoom(room);

			return Response.ok(room.toJson(true).toString()).build();
		} catch (Exception e) {
			System.out.println("Cannot create room: " + e.getMessage());

			JSONObject json = new JSONObject();
			json.put("message", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}
	}

}
