package com.fer.hr.model.defaultIds;

public class DefaultRole {
	
	public static final int USER = 0;
	public static final String USER_TEXT = "User";
	
	public static final int DEVELOPER = 1;
	public static final String DEVELOPER_TEXT = "Developer";
	
	public static final int LEADER = 2;
	public static final String LEADER_TEXT = "Project leader";

	public static String getById(int roleId) {
		switch (roleId){
		case LEADER:
			return LEADER_TEXT;
		case DEVELOPER:
			return DEVELOPER_TEXT;
		default:
			return USER_TEXT;
		}
	}
	
}
