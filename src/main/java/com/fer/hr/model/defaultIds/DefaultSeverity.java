package com.fer.hr.model.defaultIds;

public class DefaultSeverity {

	public static final int TRIVIAL = 0;
	public static final String TRIVIAL_TEXT = "Trivial";
	
	public static final int MINOR = 1;
	public static final String MINOR_TEXT = "Minor";
	
	public static final int MAJOR = 2;
	public static final String MAJOR_TEXT = "Major";
	
	public static final int CRITICAL = 3;
	public static final String CRITICAL_TEXT = "Critical";
	
	public static String getById(int severityId) {
		switch (severityId){
		case CRITICAL:
			return CRITICAL_TEXT;
		case MAJOR:
			return MAJOR_TEXT;
		case MINOR:
			return MINOR_TEXT;
		default:
			return TRIVIAL_TEXT;
		}
	}
}
