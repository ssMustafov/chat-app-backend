package org.ruse.uni.chat.solr.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.message.Message;
import org.ruse.uni.chat.core.security.SecureUser;
import org.ruse.uni.chat.core.security.SecurityUtil;
import org.ruse.uni.chat.core.services.MessageService;
import org.ruse.uni.chat.core.services.UserService;
import org.ruse.uni.chat.core.util.JsonUtil;

/**
 * @author sinan
 */
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SolrMessagesRest {

	@Inject
	private MessageService messageService;

	@Inject
	private UserService userService;

	@GET
	@Path("/room/{roomId}")
	public Response getMessages(@PathParam("roomId") Long roomId, @QueryParam("start") int start,
			@QueryParam("rows") int rows) {
		List<Message> messages = messageService.getMessages(roomId, start, rows);
		JSONArray result = transformToJson(messages);
		return Response.ok(result.toString()).build();
	}

	@POST
	@Path("/search/{roomId}")
	public Response search(@PathParam("roomId") Long roomId, String data) {
		JSONObject json = new JSONObject(data);
		String searchTerm = JsonUtil.getString("term", json);

		if (searchTerm != null && !searchTerm.isEmpty()) {
			List<Message> messages = messageService.searchTerm(roomId, searchTerm);
			JSONArray result = transformToJson(messages);
			return Response.ok(result.toString()).build();
		}
		return Response.ok(new JSONArray().toString()).build();
	}

	private JSONArray transformToJson(List<Message> messages) {
		JSONArray arr = new JSONArray();
		messages.forEach(message -> {
			User user = userService.getById(message.getUserId());
			SecureUser secureUser = SecurityUtil.convertEntityToSecureUser(user);
			arr.put(message.toJson(secureUser));
		});
		return arr;
	}

}
