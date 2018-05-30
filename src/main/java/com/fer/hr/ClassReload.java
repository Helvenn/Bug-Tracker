package com.fer.hr;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ClassReload {
	
	public static <E> List<E> reloadList(List<?> list){
		//String filePath = cl.getResource("a.ser").toString().replace(oldChar, newChar);
		String filePath = "a.ser";
		List<E> newList = new ArrayList<E>();
		
		for(Object memb : list){
			try {
				FileOutputStream fileOut = new FileOutputStream(filePath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(memb);
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}

			try {
				FileInputStream fileIn = new FileInputStream(filePath);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				@SuppressWarnings("unchecked")
				E newMemb = (E)in.readObject();
				in.close();
				fileIn.close();
				newList.add(newMemb);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return newList;
	}
	
	public static <E> E reloadSingle(Object object){
		String filePath = "C:/Users/Josip/Desktop/ser/a.ser";
		E newMemb = null;
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			@SuppressWarnings("unchecked")
			Object obj = (E)in.readObject();
			newMemb = (E)obj;
			in.close();
			fileIn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(newMemb == null){
			throw new NullPointerException("Single class reload failed");
		}
		return newMemb;
	}
	
}
