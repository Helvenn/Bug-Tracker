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
			Random r = new Random(System.currentTimeMillis());
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO severity ");
			bob.append("(id, description)");
			bob.append(" VALUES ");
			bob.append("(:id , :desc)");
			String query = bob.toString();
			if (severity.getId() != 0) {
				id = severity.getId();
			} else {
				id = Math.abs(r.nextInt());
			}
			session.createNativeQuery(query)
					.setParameter("id", id)
					.setParameter("desc", severity.getDescription())
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
	
	public Severity get(int id) {
		Severity sev = null;
		List<Severity> all = getAll();
		for (Severity b : all) {
			if (b.getId() == id) {
				sev = b;
				break;
			}
		}
		return sev;
	}
	
	public boolean delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(Severity.class, id));

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
	
	public void update(Severity sev) {
		delete(sev.getId());
		add(sev);
	}
	
	public List<Severity> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Severity> list = new ArrayList<>();
		
		try {
			tx = session.beginTransaction();
			String query = "FROM Severity";
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
