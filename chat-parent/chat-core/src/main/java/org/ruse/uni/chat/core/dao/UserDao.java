package org.ruse.uni.chat.core.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.ruse.uni.chat.core.entity.User;
import org.ruse.uni.chat.core.query.DbQueries;

/**
 *
 * @author sinan
 */
@ApplicationScoped
public class UserDao extends BaseDao<User> {

	@Override
	@Transactional(TxType.REQUIRED)
	public User update(User user) {
		return getEntityManager().merge(user);
	}

	@Override
	public List<User> findAll() {
		TypedQuery<User> query = getEntityManager().createNamedQuery(DbQueries.GET_ALL_USERS_NAME, User.class);
		return query.getResultList();
	}

	public User findById(Long id) {
		return findById(User.class, id);
	}

	public User findByUsername(String username) {
		TypedQuery<User> query = getEntityManager().createNamedQuery(DbQueries.GET_USER_BY_USERNAME_NAME, User.class);
		query.setParameter("username", username);

		return getSingleResult(query);
	}

	public User findByEmail(String email) {
		TypedQuery<User> query = getEntityManager().createNamedQuery(DbQueries.GET_USER_BY_EMAIL_NAME, User.class);
		query.setParameter("email", email);

		return getSingleResult(query);
	}

	public User validateCredentials(String username, String password) {
		TypedQuery<User> query = getEntityManager().createNamedQuery(DbQueries.VALIDATE_CREDENTIALS_NAME, User.class);
		query.setParameter("username", username);
		query.setParameter("password", password);

		return getSingleResult(query);
	}

}
