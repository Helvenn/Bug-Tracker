package com.fer.hr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fer.hr.keys.*;

@Entity(name = "UserProject")
@Table(name = "user_project")
public class UserProject implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UPKey id;

	@Column(name = "role_id")
	private int roleId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public UserProject() {
	}

	public UserProject(String userName, int roleId, int projectId) {
		setId(new UPKey(projectId, userName));
		setRoleId(roleId);
	}

	public UPKey getId() {
		return id;
	}

	public void setId(UPKey id) {
		this.id = id;
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
		UserProject other = (UserProject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
