package com.fer.hr.model.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.ClassReload;
import com.fer.hr.keys.URBKey;
import com.fer.hr.model.*;

public class URBManager {
	private SessionFactory factory;

	public URBManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}
	
	public URBKey add(UserResolvingBug urb) {
		Session session = factory.openSession();
		Transaction tx = null;
		URBKey id = null;

		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO user_resolving_bug ");
			bob.append("(bug_id, user_name)");
			bob.append(" VALUES ");
			bob.append("(:bid, :un)");
			String query = bob.toString();
			id = urb.getId();
			session.createNativeQuery(query)
					.setParameter("bid", urb.getId().getBugId())
					.setParameter("un", urb.getId().getUserName())
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
	
	public UserResolvingBug get(URBKey key) {
		UserResolvingBug urb = null;
		List<UserResolvingBug> all = getAll();
		for (UserResolvingBug b : all) {
			if (b.getId().equals(key)) {
				urb = b;
				break;
			}
		}
		return urb;
	}
	
	public void delete(URBKey key) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(UserResolvingBug.class, key));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void update(UserResolvingBug urb) {
		delete(urb.getId());
		add(urb);
	}
	
	public List<UserResolvingBug> getResolvedByUser(String userName) {
		List<UserResolvingBug> list = new ArrayList<>();
		List<UserResolvingBug> all = getAll();
		for (UserResolvingBug b : all) {
			if (b.getId().getUserName().equals(userName)) {
				list.add(b);
			}
		}
		return list;
	}
	
	public List<UserResolvingBug> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<UserResolvingBug> list = new ArrayList<>();
		
		try {
			tx = session.beginTransaction();
			String query = "FROM UserResolvingBug";
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
