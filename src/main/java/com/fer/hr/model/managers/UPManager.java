package com.fer.hr.model.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.ClassReload;
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
		UPKey id = null;

		try {
			tx = session.beginTransaction();
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO user_project ");
			bob.append("(user_name, project_id, role_id)");
			bob.append(" VALUES ");
			bob.append("(:usr , :pid , :rid)");
			String query = bob.toString();
			id = up.getId();
			session.createNativeQuery(query)
					.setParameter("usr", up.getId().getUserName())
					.setParameter("pid", up.getId().getProjectId())
					.setParameter("rid", up.getRoleId()).executeUpdate();
			
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

	public UserProject get(UPKey id) {
		UserProject up = null;
		List<UserProject> all = getAll();
		for (UserProject b : all) {
			if (b.getId().equals(id)) {
				up = b;
				break;
			}
		}
		return up;
	}

	public void delete(UPKey key) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(UserProject.class, key));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void update(UserProject up) {
		delete(up.getId());
		add(up);
	}

	public List<UserProject> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<UserProject> list = new ArrayList<>();

		try {
			tx = session.beginTransaction();
			String query = "FROM UserProject";
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
	
	public List<UserProject> getAllByProject(int projectId) {
		List<UserProject> list = new ArrayList<>();
		List<UserProject> all = getAll();
		for (UserProject b : all) {
			if (b.getId().getProjectId() == projectId) {
				list.add(b);
			}
		}
		return list;
	}
	
	public List<UserProject> getAllByUser(String userName) {
		List<UserProject> list = new ArrayList<>();
		List<UserProject> all = getAll();
		for (UserProject b : all) {
			if (b.getId().getUserName().equals(userName)) {
				list.add(b);
			}
		}
		return list;
	}
}
