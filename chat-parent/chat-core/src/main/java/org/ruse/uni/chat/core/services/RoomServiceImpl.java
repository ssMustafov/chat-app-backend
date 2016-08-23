package org.ruse.uni.chat.core.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.ruse.uni.chat.core.dao.RoomDao;
import org.ruse.uni.chat.core.entity.Room;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.events.UserPersistedEvent;
import org.ruse.uni.chat.core.exceptions.ChatRuntimeException;

/**
 * @author sinan
 */
@ApplicationScoped
public class RoomServiceImpl implements RoomService {

	private static final Long DEFAULT_ROOM_ID = Long.valueOf(1L);

	@Inject
	private RoomDao roomDao;

	@Override
	public List<Room> getRooms(User user) {
		return roomDao.getRooms(user);
	}

	@Override
	public void saveRoom(Room room) {
		roomDao.save(room);
	}

	@Override
	public Room deleteRoom(Long id) {
		return roomDao.remove(id);
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public Room addUserToRoom(Long id, User user) {
		Room room = getById(id);
		room.getUsers().add(user);
		saveRoom(room);
		return room;
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public Room removeUserFromRoom(Long id, User user) {
		Room room = getById(id);
		room.getUsers().removeIf(u -> u.getId().equals(user.getId()));
		saveRoom(room);
		return room;
	}

	@Override
	public Room getById(Long id) {
		return roomDao.findById(Room.class, id);
	}

	/**
	 * Adds every registered user to the default room.
	 *
	 * @param event
	 *            the event for newly registered user
	 */
	public void onUserPersisted(@Observes UserPersistedEvent event) {
		User user = event.getUser();
		if (user == null) {
			throw new ChatRuntimeException("Empty user in UserPersistedEvent");
		}

		addUserToRoom(DEFAULT_ROOM_ID, user);
	}

	@Override
	public boolean canJoin(Long roomId, User user) {
		Room room = getById(roomId);
		if (room == null) {
			return false;
		}
		return room.getUsers().stream().anyMatch(u -> u.getId().equals(user.getId()));
	}

	@Override
	public Room updateRoom(Room room) {
		return roomDao.update(room);
	}

}
