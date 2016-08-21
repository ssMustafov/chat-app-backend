package org.ruse.uni.chat.core.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ruse.uni.chat.core.query.DbQueries;
import org.ruse.uni.chat.core.security.SecurityUtil;

/**
 * @author sinan
 */
@Entity(name = Room.TABLE_NAME)
@NamedQueries({ @NamedQuery(name = DbQueries.GET_ALL_ROOMS_NAME, query = DbQueries.GET_ALL_ROOMS_QUERY),
		@NamedQuery(name = DbQueries.GET_ROOMS_FOR_USER_NAME, query = DbQueries.GET_ROOMS_FOR_USER_QUERY) })
public class Room extends BaseEntity {

	public static final String TABLE_NAME = "chat_room";
	private static final long serialVersionUID = -8495117241619403150L;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(length = 256)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(referencedColumnName = "id")
	private Set<User> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String decsription) {
		this.description = decsription;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public JSONObject toJson(boolean fullLoad) {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("name", name);
		json.put("description", description);
		if (fullLoad) {
			JSONArray arr = new JSONArray();
			users.forEach(user -> arr.put(SecurityUtil.convertEntityToSecureUser(user).toJson()));
			json.put("users", arr);
		}
		return json;
	}

}
