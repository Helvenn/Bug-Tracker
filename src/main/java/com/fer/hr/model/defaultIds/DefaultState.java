package com.fer.hr.model.defaultIds;



public class DefaultState {
	
	public static final int NEW = 0;
	public static final String NEW_TEXT = "New";
	
	public static final int ASSIGNED = 1;
	public static final String ASSIGNED_TEXT = "Assigned";
	
	public static final int OPEN = 2;
	public static final String OPEN_TEXT = "Open";
	
	public static final int FIXED = 3;
	public static final String FIXED_TEXT = "Fixed";
	
	public static final int PENDING_RETEST = 4;
	public static final String PENDING_RETEST_TEXT = "Pending retest";
	
	public static final int RETEST = 5;
	public static final String RETEST_TEXT = "Retest";
	
	public static final int REOPENED = 6;
	public static final String REOPENED_TEXT = "Reopened";
	
	public static final int DUPLICATE = 7;
	public static final String DUPLICATE_TEXT = "Duplicate";
	
	public static final int REJECTED = 8;
	public static final String REJECTED_TEXT = "Rejected";
	
	public static final int DEFFERED = 9;
	public static final String DEFERRED_TEXT = "Deferred";
	
	public static final int NOT_A_BUG = 10;
	public static final String NOT_A_BUG_TEXT = "Not a bug";
	
	public static final int VERIFIED = 11;
	public static final String VERIFIED_TEXT = "Verified";
	
	public static final int CLOSED = 12;
	public static final String CLOSED_TEXT = "Closed";
	
	public static String getById(int stateId) {
		switch (stateId){
		case ASSIGNED:
			return ASSIGNED_TEXT;
		case OPEN:
			return OPEN_TEXT;
		case FIXED:
			return FIXED_TEXT;
		case PENDING_RETEST:
			return PENDING_RETEST_TEXT;
		case RETEST:
			return RETEST_TEXT;
		case REOPENED: 
			return REOPENED_TEXT;
		case DUPLICATE:
			return DUPLICATE_TEXT;
		case REJECTED:
			return REJECTED_TEXT;
		case DEFFERED:
			return DEFERRED_TEXT;
		case NOT_A_BUG:
			return NOT_A_BUG_TEXT;
		case VERIFIED:
			return VERIFIED_TEXT;
		case CLOSED:
			return CLOSED_TEXT;
		default:
			return NEW_TEXT;
		}
			
	}
	
}
