package com.fer.hr.model.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.ClassReload;
import com.fer.hr.model.*;

public class ImageManager {
	private SessionFactory factory;

	public ImageManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}
	
	public Integer add(Image image) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			Random r = new Random(System.currentTimeMillis());
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO image ");
			bob.append("(id, name, data)");
			bob.append(" VALUES ");
			bob.append("(:id , :nm , :data)");
			String query = bob.toString();
			id = Math.abs(r.nextInt());
			session.createNativeQuery(query)
					.setParameter("id", id)
					.setParameter("nm", image.getFileName())
					.setParameter("data", image.getData()).executeUpdate();
			
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}
	
	public Image get(int id) {
		Image image = null;
		List<Image> all = getAll();
		for (Image b : all) {
			if (b.getId() == id) {
				image = b;
				break;
			}
		}
		return image;
	}
	
	public void delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(Image.class, id));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void update(Image image) {
		delete(image.getId());
		add(image);
	}
	
	public List<Image> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Image> list = new ArrayList<>();
		
		try {
			tx = session.beginTransaction();
			String query = "SELECT * FROM image";
			list = ClassReload.reloadList(session.createQuery(query).list());
			
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
}
