package model.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.AppUser;

public class UserManager {
	private SessionFactory factory;

	public UserManager(SessionFactory factory) {
		this.factory = factory;
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
}
