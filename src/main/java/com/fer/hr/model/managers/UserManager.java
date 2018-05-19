package com.fer.hr.model.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
			userName = (String) session.save(user);

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

	public boolean delete(String userName) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			AppUser user = (AppUser) session.get(AppUser.class, userName);
			session.delete(user);

			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	public AppUser get(String userName) {
		Session session = factory.openSession();
		Transaction tx = null;
		AppUser user = null;

		try {
			tx = session.beginTransaction();
			user = (AppUser) session.get(AppUser.class, userName);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	public boolean updateName(String userName, String firstName, String lastName) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			AppUser user = (AppUser) session.get(AppUser.class, userName);
			if (firstName != null)
				user.setFirstName(firstName);
			if (lastName != null)
				user.setLastName(lastName);

			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<AppUser> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<AppUser> list = null;

		try {
			tx = session.beginTransaction();
			String query = "FROM appuser";
			list = session.createQuery(query).list();

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
