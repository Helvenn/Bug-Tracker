package tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import tracker.model.keys.BPKey;

@Entity(name = "BugProject")
@Table(name = "bug_project")
public class BugProject implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BPKey id;

	@Column(name = "bug_state_id")
	private int stateId;

	public BPKey getId() {
		return id;
	}

	public void setId(BPKey id) {
		this.id = id;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public BugProject() {
	}

	public BugProject(int projectId, int bugId, int stateId) {
		setStateId(stateId);
		setId(new BPKey(bugId, projectId));
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
		BugProject other = (BugProject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}