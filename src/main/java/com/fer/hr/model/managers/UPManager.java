package com.fer.hr.model.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.keys.UPKey;
import com.fer.hr.model.*;


public class UPManager {
	private SessionFactory factory;

	public UPManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}

	public UPKey add(UserProject up) {
		Session session = factory.openSession();
		Transaction tx = null;
		UPKey key = null;

		try {
			tx = session.beginTransaction();
			key = (UPKey) session.save(up);

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

	public UserProject get(UPKey key) {
		Session session = factory.openSession();
		Transaction tx = null;
		UserProject up = null;

		try {
			tx = session.beginTransaction();
			up = (UserProject) session.get(UserProject.class, key);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return up;
	}

	public boolean delete(UPKey key) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			UserProject up = (UserProject) session.get(UserProject.class, key);
			session.delete(up);

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

	public boolean updateRole(UPKey key, int roleId) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			UserProject up = (UserProject) session.get(UserProject.class, key);
			up.setRoleId(roleId);

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
	public List<UserProject> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<UserProject> list = null;

		try {
			tx = session.beginTransaction();
			String query = "FROM user_project";
			list = (List<UserProject>) session.createQuery(query).list();

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
	public List<UserProject> getAllByProject(int projectId) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<UserProject> list = null;
		
		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("FROM user_project ");
			bob.append("WHERE project_id = :pid");
			String query = bob.toString();
			
			list = (List<UserProject>) session.createQuery(query).setParameter("pid", projectId).list();
			
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
	public List<UserProject> getAllByUser(String userName) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<UserProject> list = null;
		
		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("FROM user_project ");
			bob.append("WHERE user_name = :uname");
			String query = bob.toString();
			
			list = (List<UserProject>) session.createQuery(query).setParameter("uname", userName).list();
			
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
