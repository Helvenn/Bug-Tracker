package com.fer.hr.model.post;

import java.io.Serializable;

public class UserPost implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userName;
	private Integer projectId;
	private Integer roleId;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserPost() {
	}

}
