package com.fer.hr.model.defaultIds;

public class DefaultCategory {
	
	public static final int COSMETIC = 0;
	public static final String COSMETIC_TEXT = "Cosmetic issue";
	
	public static final int NETWORK = 1;
	public static final String NETWORK_TEXT = "Network issue";
	
	public static final int BEHAVIOR = 2;
	public static final String BEHAVIOR_TEXT = "Unexpected behavior";
	
	public static String getById(int categoryId) {
		switch (categoryId){
		case COSMETIC:
			return COSMETIC_TEXT;
		case NETWORK:
			return NETWORK_TEXT;
		default:
			return BEHAVIOR_TEXT;
		}
	}
}
