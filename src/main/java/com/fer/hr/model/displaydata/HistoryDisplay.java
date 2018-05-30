package com.fer.hr.model.displaydata;

import java.io.Serializable;
import java.sql.Timestamp;

public class HistoryDisplay implements Serializable {
	private static final long serialVersionUID = 1L;

	private String bugName;
	private Timestamp time;
	private String state;
	private String personFirstName;
	private String personLastName;

	public String getBugName() {
		return bugName;
	}

	public void setBugName(String bugName) {
		this.bugName = bugName;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public HistoryDisplay() {
	}

	public String getPersonFirstName() {
		return personFirstName;
	}

	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}

	public String getPersonLastName() {
		return personLastName;
	}

	public void setPersonLastName(String personLastName) {
		this.personLastName = personLastName;
	}

	public HistoryDisplay(String bugName, Timestamp time, String state, String personFirstName, String personLastName) {
		super();
		this.bugName = bugName;
		this.time = time;
		this.state = state;
		this.personFirstName = personFirstName;
		this.personLastName = personLastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bugName == null) ? 0 : bugName.hashCode());
		result = prime * result + ((personFirstName == null) ? 0 : personFirstName.hashCode());
		result = prime * result + ((personLastName == null) ? 0 : personLastName.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		HistoryDisplay other = (HistoryDisplay) obj;
		if (bugName == null) {
			if (other.bugName != null)
				return false;
		} else if (!bugName.equals(other.bugName))
			return false;
		if (personFirstName == null) {
			if (other.personFirstName != null)
				return false;
		} else if (!personFirstName.equals(other.personFirstName))
			return false;
		if (personLastName == null) {
			if (other.personLastName != null)
				return false;
		} else if (!personLastName.equals(other.personLastName))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

}
