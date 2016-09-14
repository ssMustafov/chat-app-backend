package org.ruse.uni.chat.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.json.JSONObject;
import org.ruse.uni.chat.core.query.DbQueries;
import org.ruse.uni.chat.core.security.SecurityUtil;

/**
 * @author sinan
 */
@Entity(name = File.TABLE_NAME)
@NamedQueries({ @NamedQuery(name = DbQueries.GET_ALL_FILES_NAME, query = DbQueries.GET_ALL_FILES_QUERY),
		@NamedQuery(name = DbQueries.GET_FILES_FOR_ROOM_NAME, query = DbQueries.GET_FILES_FOR_ROOM_QUERY) })
public class File extends BaseEntity {

	private static final long serialVersionUID = -5988425611838910610L;
	public static final String TABLE_NAME = "chat_file";

	@Column(nullable = false, length = 256)
	private String name;

	@Column(nullable = false, length = 128)
	private String filename;

	@Column(nullable = false, length = 24)
	private String extension;

	@Column(nullable = false, length = 256)
	private String mimetype;

	@Column(nullable = false, length = 512)
	private String location;

	@Column(nullable = false)
	private long size;

	@OneToOne
	private Room room;

	@OneToOne
	private User user;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("filename", filename);
		json.put("extension", extension);
		json.put("mimetype", mimetype);
		json.put("size", size);
		json.put("room", room.toJson(false));
		json.put("user", SecurityUtil.convertEntityToSecureUser(user).toJson());
		json.put("location", location);
		return json;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
