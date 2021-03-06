package com.fer.hr.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fer.hr.keys.*;

@Entity(name = "UserResolvingBug")
@Table(name = "user_resolving_bug")
public class UserResolvingBug implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private URBKey id;

	public URBKey getId() {
		return id;
	}

	public void setId(URBKey id) {
		this.id = id;
	}


	public UserResolvingBug() {
	}

	public UserResolvingBug(int bugId, String userName) {
		setId(new URBKey(bugId, userName));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResolvingBug other = (UserResolvingBug) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
