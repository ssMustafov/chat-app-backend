package org.ruse.uni.chat.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.ruse.uni.chat.core.entity.BaseEntity;

/**
 * Base dao for persistence. Contains methods for base operations like save, update, remove and etc. Successors must
 * implement their specific operations itselfs.
 *
 * @author sinan
 */
public abstract class BaseDao<E extends BaseEntity> {

	private static final String UNIT_NAME = "chat";

	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager entityManager;

	/**
	 * Persists the instance into the db. The instance will be new row in the table.
	 *
	 * @param instance
	 *            the instance
	 */
	@Transactional(TxType.REQUIRED)
	public void save(E instance) {
		entityManager.persist(instance);
	}

	/**
	 * Removes instance from the db.
	 *
	 * @param instance
	 *            the instance
	 */
	public void remove(E instance) {
		entityManager.remove(instance);
	}

	/**
	 * Finds instance by db id.
	 *
	 * @param classType
	 *            the type of the instance
	 * @param id
	 *            the id of the instance
	 * @return the found instance or null
	 */
	public E findById(Class<E> classType, Long id) {
		return entityManager.find(classType, id);
	}

	/**
	 * Refreshes the given instance.
	 *
	 * @param instance
	 *            the instance to refresh
	 * @return the refreshed instance
	 */
	public E refresh(E instance) {
		entityManager.refresh(instance);
		return instance;
	}

	protected E getSingleResult(TypedQuery<E> query) {
		E result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			// its ok if not found
			// TODO: add logger
		}
		return result;
	}

	/**
	 * Updates the given instance. Beware that the returned instance is the updated. The passed instance will not be
	 * updated. The recommended way to update the instance is just to copy its properties, JPA will handle the update
	 * itself.
	 *
	 * @param instance
	 *            the instance to update
	 * @return the updated instance
	 */
	public abstract E update(E instance);

	/**
	 * Finds all the persisted instances.
	 *
	 * @return list of all persisted instances
	 */
	public abstract List<E> findAll();

	/**
	 * Getter method for entityManager.
	 *
	 * @return the entityManager
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
