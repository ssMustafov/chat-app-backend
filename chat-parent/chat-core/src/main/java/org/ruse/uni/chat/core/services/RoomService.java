package org.ruse.uni.chat.core.services;

import java.util.List;

import org.ruse.uni.chat.core.entity.Room;
import org.ruse.uni.chat.core.entity.User;

/**
 * @author sinan
 */
public interface RoomService {

	List<Room> getRooms(User user);

	void saveRoom(Room room);

	Room deleteRoom(Long id);

	Room addUserToRoom(Long id, User user);

	Room removeUserFromRoom(Long id, User user);

	Room getById(Long id);

	boolean canJoin(Long roomId, User user);

	Room updateRoom(Room room);
}
