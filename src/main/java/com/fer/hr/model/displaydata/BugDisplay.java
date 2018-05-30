package com.fer.hr.model.displaydata;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fer.hr.model.defaultIds.DefaultState;

public class BugDisplay implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String description;
	private Timestamp timeAdded;
	private Timestamp timeResolved;
	private String category;
	private String severity;
	private String state;
	private String project;
	private boolean canDelete;
	private boolean canAssign;
	private boolean canOpen;
	private boolean canFix;
	private boolean canRetest;
	private boolean canVerify;
	private boolean canClose;
	private boolean canReopen;
	private boolean canReject;

	public boolean isCanDelete() {
		return canDelete;
	}

	public boolean isCanAssign() {
		return canAssign;
	}

	public void setCanAssign(boolean canAssign) {
		this.canAssign = canAssign;
	}

	public boolean isCanOpen() {
		return canOpen;
	}

	public void setCanOpen(boolean canOpen) {
		this.canOpen = canOpen;
	}

	public boolean isCanFix() {
		return canFix;
	}

	public void setCanFix(boolean canFix) {
		this.canFix = canFix;
	}

	public boolean isCanVerify() {
		return canVerify;
	}

	public void setCanVerify(boolean canVerify) {
		this.canVerify = canVerify;
	}

	public boolean isCanClose() {
		return canClose;
	}

	public void setCanClose(boolean canClose) {
		this.canClose = canClose;
	}

	public boolean isCanReopen() {
		return canReopen;
	}

	public void setCanReopen(boolean canReopen) {
		this.canReopen = canReopen;
	}

	public boolean isCanReject() {
		return canReject;
	}

	public void setCanReject(boolean canReject) {
		this.canReject = canReject;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getTimeAdded() {
		return timeAdded;
	}

	public void setTimeAdded(Timestamp timeAdded) {
		this.timeAdded = timeAdded;
	}

	public Timestamp getTimeResolved() {
		return timeResolved;
	}

	public void setTimeResolved(Timestamp timeResolved) {
		this.timeResolved = timeResolved;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public boolean isCanRetest() {
		return canRetest;
	}

	public void setCanRetest(boolean canRetest) {
		this.canRetest = canRetest;
	}

	public BugDisplay() {
	}

	public BugDisplay(int id, String name, String description, Timestamp timeAdded, Timestamp timeResolved,
			String category, String severity, String state, String project) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.timeAdded = timeAdded;
		this.timeResolved = timeResolved;
		this.category = category;
		this.severity = severity;
		this.state = state;
		this.project = project;
		this.canDelete = this.state.equals(DefaultState.NEW_TEXT);
		this.canRetest = false;
		this.canAssign = false;
		this.canOpen = false;
		this.canFix = false;
		this.canVerify = false;
		this.canClose = false;
		this.canReopen = false;
		this.canReject = false;
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
		BugDisplay other = (BugDisplay) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
