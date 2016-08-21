package org.ruse.uni.chat.core.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.ruse.uni.chat.core.entity.Room;
import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.query.DbQueries;

/**
 * @author sinan
 */
@ApplicationScoped
public class RoomDao extends BaseDao<Room> {

	@Override
	@Transactional(TxType.REQUIRED)
	public Room update(Room instance) {
		Room updated = findById(Room.class, instance.getId());
		updated.setDescription(instance.getDescription());
		updated.setName(instance.getName());
		updated.setUsers(instance.getUsers());
		return updated;
	}

	@Override
	public List<Room> findAll() {
		TypedQuery<Room> query = getEntityManager().createNamedQuery(DbQueries.GET_ALL_ROOMS_NAME, Room.class);
		return query.getResultList();
	}

	public List<Room> getRooms(User user) {
		TypedQuery<Room> query = getEntityManager().createNamedQuery(DbQueries.GET_ROOMS_FOR_USER_NAME, Room.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

	public Room remove(Long id) {
		Room room = findById(Room.class, id);
		remove(room);
		return room;
	}

}
