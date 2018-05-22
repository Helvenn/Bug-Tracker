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


public class CategoryManager {
	private SessionFactory factory;

	public CategoryManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}

	public Integer add(Category category) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			Random r = new Random(System.currentTimeMillis());
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO category ");
			bob.append("(id, description)");
			bob.append(" VALUES ");
			bob.append("(:id , :desc)");
			String query = bob.toString();
			id = Math.abs(r.nextInt());
			session.createNativeQuery(query)
					.setParameter("id", id)
					.setParameter("desc", category.getDescription())
					.executeUpdate();
			
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

	public Category get(int id) {
		Category cat = null;
		List<Category> all = getAll();
		for (Category b : all) {
			if (b.getId() == id) {
				cat = b;
				break;
			}
		}
		return cat;
	}

	public void delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(Category.class, id));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void update(Category cat) {
		delete(cat.getId());
		add(cat);
	}

	public List<Category> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Category> list = new ArrayList<>();

		try {
			tx = session.beginTransaction();
			String query = "FROM Category";
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
