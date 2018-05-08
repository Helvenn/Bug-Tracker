package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import model.keys.URBKey;

@Entity(name = "UserResolvingBug")
@Table(name = "user_resolving_bug")
public class UserResolvingBug implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private URBKey id;
	
	@Column(name = "time_started")
	private String timeStarted;
	
	@Column(name = "time_finished")
	private String timeFinished;
	
	@Column(name = "comment")
	private String comment;

	public URBKey getId() {
		return id;
	}

	public void setId(URBKey id) {
		this.id = id;
	}

	public String getTimeStarted() {
		return timeStarted;
	}

	public void setTimeStarted(String timeStarted) {
		this.timeStarted = timeStarted;
	}

	public String getTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(String timeFinished) {
		this.timeFinished = timeFinished;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public UserResolvingBug(){}

	public UserResolvingBug(int bugId, String userName, String timeStarted, String comment) {
		setId(new URBKey(bugId, userName));
		setComment(comment);
		setTimeStarted(timeStarted);
		setTimeFinished(null);
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
