package org.ruse.uni.chat.websocket.notifications;

public class NotificationEvent {

	private Notification notification;

	public NotificationEvent(Notification notification) {
		this.notification = notification;
	}

	public Notification getNotification() {
		return notification;
	}

}
