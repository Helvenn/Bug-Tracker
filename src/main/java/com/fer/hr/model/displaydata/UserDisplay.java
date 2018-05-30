package com.fer.hr.model.displaydata;

import java.io.Serializable;

public class UserDisplay implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userName;
	private String firstName;
	private String lastName;
	private String role;
	private boolean canBeRemoved;

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isCanBeRemoved() {
		return canBeRemoved;
	}

	public void setCanBeRemoved(boolean canBeRemoved) {
		this.canBeRemoved = canBeRemoved;
	}

	public UserDisplay() {
	}

	public UserDisplay(String userName, String firstName, String lastName, String role, boolean canBeRemoved) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.canBeRemoved = canBeRemoved;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		UserDisplay other = (UserDisplay) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
