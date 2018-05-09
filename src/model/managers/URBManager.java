package model.managers;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.UserResolvingBug;
import model.keys.URBKey;

public class URBManager {
	private SessionFactory factory;

	public URBManager(SessionFactory factory) {
		this.factory = factory;
	}
	
	public URBKey add(UserResolvingBug urb) {
		Session session = factory.openSession();
		Transaction tx = null;
		URBKey key = null;

		try {
			tx = session.beginTransaction();
			key = (URBKey) session.save(urb);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return key;
	}
	
	public UserResolvingBug get(URBKey key) {
		Session session = factory.openSession();
		Transaction tx = null;
		UserResolvingBug urb = null;

		try {
			tx = session.beginTransaction();
			urb = (UserResolvingBug) session.get(UserResolvingBug.class, key);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return urb;
	}
	
	public boolean delete(URBKey key) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			UserResolvingBug urb = (UserResolvingBug) session.get(UserResolvingBug.class, key);
			session.delete(urb);

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
	
	public boolean updateComment(URBKey key, String comment) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			UserResolvingBug urb = (UserResolvingBug) session.get(UserResolvingBug.class, key);
			urb.setComment(comment);

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
	
	public boolean updateTimeFinished(URBKey key, Timestamp timeFinished) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			UserResolvingBug urb = (UserResolvingBug) session.get(UserResolvingBug.class, key);
			urb.setTimeFinished(timeFinished);

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
	public List<UserResolvingBug> getResolvedByUser(String userName) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<UserResolvingBug> list = null;
		
		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("SELECT * FROM user_resolving_bug ");
			bob.append("WHERE user_name = :uname");
			String query = bob.toString();
			
			list = (List<UserResolvingBug>) session.createQuery(query).setParameter("uname", userName).list();
			
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
	
	@SuppressWarnings("unchecked")
	public List<UserResolvingBug> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<UserResolvingBug> list = null;
		
		try {
			tx = session.beginTransaction();
			String query = "SELECT * FROM user_resolving_bug";
			list = (List<UserResolvingBug>) session.createQuery(query).list();
			
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
