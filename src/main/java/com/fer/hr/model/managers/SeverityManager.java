package com.fer.hr.model.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.fer.hr.model.*;


public class SeverityManager {
	private SessionFactory factory;

	public SeverityManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}
	
	public Integer add(Severity severity) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(severity);

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
	
	public Severity get(int id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Severity severity = null;

		try {
			tx = session.beginTransaction();
			severity = (Severity) session.get(Severity.class, id);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return severity;
	}
	
	public boolean delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Severity severity = (Severity) session.get(Severity.class, id);
			session.delete(severity);

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
	
	public boolean updateDesc(int id, String newDesc) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Severity severity = (Severity) session.get(Severity.class, id);
			severity.setDescription(newDesc);

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
	public List<Severity> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Severity> list = null;
		
		try {
			tx = session.beginTransaction();
			String query = "FROM severity";
			list = (List<Severity>) session.createQuery(query).list();
			
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
