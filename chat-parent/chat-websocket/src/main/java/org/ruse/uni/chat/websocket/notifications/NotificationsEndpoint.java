package org.ruse.uni.chat.websocket.notifications;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = NotificationsEndpoint.PATH, encoders = { NotificationEncoder.class })
public class NotificationsEndpoint {

	public static final String PATH = "/notifications";

	private static final Set<Session> clients = Collections.synchronizedSet(new HashSet<>(64));

	public void onNotification(@Observes NotificationEvent event) {
		System.out.println("Sending notification for: " + event.getNotification());

		clients.forEach(client -> {
			try {
				client.getBasicRemote().sendObject(event.getNotification());
			} catch (IOException | EncodeException e) {
				System.out.println("Error on message: " + e.getMessage());
				removeClient(client);
			}
		});
	}

	@OnOpen
	public void onOpen(Session client) {
		clients.add(client);
		System.out.println("New websocket session opened: " + client.getId());
	}

	@OnClose
	public void onClose(Session session) {
		removeClient(session);
		System.out.println("Websoket session closed: " + session.getId());
	}

	private void removeClient(Session session) {
		try {
			session.close();
		} catch (IOException e) {
			System.out.println("Error closing client socket: " + e.getMessage());
		}
		clients.remove(session);
	}

}
