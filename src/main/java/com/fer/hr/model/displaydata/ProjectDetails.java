package com.fer.hr.model.displaydata;

import java.io.Serializable;
import java.util.List;

public class ProjectDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private List<HistoryDisplay> history;
	private List<BugDisplay> bugs;
	private List<UserDisplay> users;
	private boolean leader;

	public boolean isLeader() {
		return leader;
	}

	public void setLeader(boolean leader) {
		this.leader = leader;
	}

	public List<BugDisplay> getBugs() {
		return bugs;
	}

	public void setBugs(List<BugDisplay> bugs) {
		this.bugs = bugs;
	}

	public List<UserDisplay> getUsers() {
		return users;
	}

	public void setUsers(List<UserDisplay> users) {
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HistoryDisplay> getHistory() {
		return history;
	}

	public void setHistory(List<HistoryDisplay> history) {
		this.history = history;
	}

	public ProjectDetails() {
	}

	public ProjectDetails(int id, String name, List<HistoryDisplay> history, List<BugDisplay> bugs,
			List<UserDisplay> users) {
		super();
		this.id = id;
		this.name = name;
		this.history = history;
		this.bugs = bugs;
		this.users = users;
		this.leader = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		ProjectDetails other = (ProjectDetails) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
