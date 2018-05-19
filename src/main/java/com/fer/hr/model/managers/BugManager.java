package com.fer.hr.model.managers;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.model.*;

public class BugManager {
	private SessionFactory factory;

	public BugManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}
	
	@SuppressWarnings("unchecked")
	public List<Bug> getByUser(String userName){
		Session session = factory.openSession();
		Transaction tx = null;
		List<Bug> bugs = null;
		try {
			tx = session.beginTransaction();
			if (userName.isEmpty()){
				return null;
			}
			
			StringBuilder bob = new StringBuilder();
			bob.append("FROM Bug");

			bob.append(" WHERE ");
			bob.append("user = :userName");

			String query = bob.toString();

			bugs = session.createQuery(query).setParameter("userName", userName).list();


			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return bugs;
	}

	public Integer add(Bug bug) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(bug);

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

	public boolean delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Bug bug = (Bug) session.get(Bug.class, id);
			session.delete(bug);

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

	public Bug get(int id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Bug bug = null;

		try {
			tx = session.beginTransaction();
			bug = (Bug) session.get(Bug.class, id);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return bug;
	}

	@SuppressWarnings("unchecked")
	public List<Bug> getAddedByTimeframe(Timestamp start, Timestamp finish) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Bug> list = null;

		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("FROM Bug");

			if (start == null || finish == null) {
				throw new InvalidParameterException("Time started and time finished cannot be null");
			}

			bob.append(" WHERE ");
			bob.append("time_added > :tstart");
			bob.append(" AND ");
			bob.append("time_added < :tfinish");

			String query = bob.toString();

			list = session.createQuery(query).setParameter("tstart", start).setParameter("tfinish", finish).list();

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
	public List<Bug> getResolvedByTimeframe(Timestamp start, Timestamp finish) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Bug> list = null;

		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("SELECT * FROM Bug");

			if (start == null || finish == null) {
				throw new InvalidParameterException("Time started and time finished cannot be null");
			}

			bob.append(" WHERE ");
			bob.append("time_resolved > :tstart");
			bob.append(" AND ");
			bob.append("time_resolved < :tfinish");

			String query = bob.toString();

			list = session.createQuery(query).setParameter("tstart", start).setParameter("tfinish", finish).list();

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

	public boolean updateTimeResolved(String userName, Timestamp time) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Bug bug = (Bug) session.get(Bug.class, userName);
			bug.setTimeResolved(time);

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
	public List<Bug> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Bug> list = null;

		try {
			tx = session.beginTransaction();
			String query = "FROM Bug";
			list = (List<Bug>) session.createQuery(query).list();

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
	public List<Bug> getByProject(int projectId) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Bug> list = null;

		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("FROM Bug ");
			bob.append("WHERE project_id = :proj");
			String query = bob.toString();
			
			list = (List<Bug>) session.createQuery(query).setParameter("proj", projectId).list();

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
