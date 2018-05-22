package com.fer.hr.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fer.hr.model.defaultIds.DefaultState;

@Entity(name = "Bug")
@Table(name = "bug")
public class Bug implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	private int id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "time_added")
	private Timestamp timeAdded;

	@Column(name = "time_resolved")
	private Timestamp timeResolved;

	@Column(name = "image_id")
	private Integer imageId;

	@Column(name = "category_id")
	private int categoryId;

	@Column(name = "severity_id")
	private int severityId;

	@Column(name = "state_id")
	private int stateId;

	@Column(name = "project_id")
	private int projectId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getSeverityId() {
		return severityId;
	}

	public void setSeverityId(int severityId) {
		this.severityId = severityId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public Bug() {
	}

	public Bug(String name, String description, Timestamp timeAdded, int projectId, int imageId, int categoryId,
			int severityId, String userName) {
		setCategoryId(categoryId);
		setDescription(description);
		setImageId(imageId);
		setName(name);
		setSeverityId(severityId);
		setTimeAdded(timeAdded);
		setProjectId(projectId);
		setTimeResolved(null);
		setStateId(DefaultState.NEW);
		setUserName(userName);
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
		Bug other = (Bug) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}
