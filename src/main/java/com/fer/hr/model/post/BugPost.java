package com.fer.hr.model.post;

import java.io.Serializable;

public class BugPost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String desc;
	private Integer catId;
	private Integer projId;
	private Integer sevId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public Integer getProjId() {
		return projId;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public Integer getSevId() {
		return sevId;
	}

	public void setSevId(Integer sevId) {
		this.sevId = sevId;
	}

	public BugPost() {
	}

}
