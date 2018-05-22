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


public class StateManager {
	private SessionFactory factory;

	public StateManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}
	
	public Integer add(State state) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			Random r = new Random(System.currentTimeMillis());
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO state ");
			bob.append("(id, description)");
			bob.append(" VALUES ");
			bob.append("(:id , :desc)");
			String query = bob.toString();
			id = Math.abs(r.nextInt());
			session.createNativeQuery(query)
					.setParameter("id", id)
					.setParameter("desc", state.getDescription())
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
	
	public State get(int id) {
		State st = null;
		List<State> all = getAll();
		for (State b : all) {
			if (b.getId() == id) {
				st = b;
				break;
			}
		}
		return st;
	}
	
	public void delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(State.class, id));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void update(State st) {
		delete(st.getId());
		add(st);
	}
	
	public List<State> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<State> list = new ArrayList<>();
		
		try {
			tx = session.beginTransaction();
			String query = "FROM State";
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
