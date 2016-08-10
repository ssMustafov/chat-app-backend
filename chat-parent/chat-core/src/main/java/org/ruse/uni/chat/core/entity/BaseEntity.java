package org.ruse.uni.chat.core.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author sinan
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 8186141298052029949L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Entity [id=").append(id).append("]");
		return builder.toString();
	}

	/**
	 * Getter method for id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter method for id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
