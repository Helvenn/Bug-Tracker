package model.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BPKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "project_id")
	private int projectId;

	@Column(name = "bug_id")
	private int bugId;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getBugId() {
		return bugId;
	}

	public void setBugId(int bugId) {
		this.bugId = bugId;
	}
	
	public BPKey() {}
	public BPKey(int bugId, int projectId){
		setBugId(bugId);
		setProjectId(projectId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bugId;
		result = prime * result + projectId;
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
		BPKey other = (BPKey) obj;
		if (bugId != other.bugId)
			return false;
		if (projectId != other.projectId)
			return false;
		return true;
	}
	
	

}
