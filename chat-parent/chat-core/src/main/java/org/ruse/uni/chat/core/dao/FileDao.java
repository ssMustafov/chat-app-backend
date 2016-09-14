package org.ruse.uni.chat.core.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.ruse.uni.chat.core.entity.File;
import org.ruse.uni.chat.core.entity.Room;
import org.ruse.uni.chat.core.query.DbQueries;

/**
 * @author sinan
 */
@ApplicationScoped
public class FileDao extends BaseDao<File> {

	@Override
	@Transactional(TxType.REQUIRED)
	public File update(File instance) {
		return getEntityManager().merge(instance);
	}

	@Override
	public List<File> findAll() {
		TypedQuery<File> query = getEntityManager().createNamedQuery(DbQueries.GET_ALL_FILES_NAME, File.class);
		return query.getResultList();
	}

	public List<File> getFilesForRoom(Room room) {
		TypedQuery<File> query = getEntityManager().createNamedQuery(DbQueries.GET_FILES_FOR_ROOM_NAME, File.class);
		query.setParameter("id", room);
		return query.getResultList();
	}

}
