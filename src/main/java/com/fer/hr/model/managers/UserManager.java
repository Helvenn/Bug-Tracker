package com.fer.hr.model.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.ClassReload;
import com.fer.hr.model.*;

public class UserManager {
	private SessionFactory factory;

	public UserManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}

	public String add(AppUser user) {
		Session session = factory.openSession();
		Transaction tx = null;
		String userName = null;

		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO app_user ");
			bob.append("(user_name, password, first_name, last_name, email)");
			bob.append(" VALUES ");
			bob.append("(:un , :pw , :fn , :ln , :em)");
			String query = bob.toString();
			userName = user.getUserName();
			session.createNativeQuery(query)
					.setParameter("un", userName)
					.setParameter("pw", user.getPassword())
					.setParameter("fn", user.getFirstName())
					.setParameter("ln", user.getLastName())
					.setParameter("em", user.getEmail()).executeUpdate();
			
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return userName;
	}

	public void delete(String userName) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(AppUser.class, userName));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public AppUser get(String userName) {
		AppUser user = null;
		List<AppUser> all = getAll();
		for (AppUser b : all) {
			if (b.getUserName().equals(userName)) {
				user = b;
				break;
			}
		}
		return user;
	}

	public void update(AppUser user) {
		delete(user.getUserName());
		add(user);
	}

	public List<AppUser> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<AppUser> list = new ArrayList<>();

		try {
			tx = session.beginTransaction();
			String query = "FROM AppUser";
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
