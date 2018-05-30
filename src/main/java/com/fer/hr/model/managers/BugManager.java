package com.fer.hr.model.managers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.ClassReload;
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

	public List<Bug> getByUser(String userName) {
		List<Bug> bugs = new ArrayList<>();
		if (userName == null){
			return bugs;
		}
		if (userName.isEmpty()) {
			return bugs;
		}
		List<Bug> all = getAll();
		for (Bug b : all){
			if (b.getUserName().equals(userName))
				bugs.add(b);
		}

		return bugs;
	}
	
	public Integer add(Bug bug) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			Random r = new Random(System.currentTimeMillis());
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO bug ");
			bob.append("(id, user_name, name, description, time_added, time_resolved, image_id, category_id, severity_id, state_id, project_id)");
			bob.append(" VALUES ");
			bob.append("(:id , :usr , :nm , :desc , :ta ,");
			if (bug.getTimeResolved() == null){
				bob.append(" null ");
			} else {
				bob.append(" :tr ");
			}
			bob.append(", null , :cid , :sevid , :stid , :pid)");
			String query = bob.toString();
			if (bug.getId() != 0) {
				id = bug.getId();
			} else {
				id = Math.abs(r.nextInt());
			}
			if (bug.getTimeResolved() == null){
				session.createNativeQuery(query)
				.setParameter("id", id)
				.setParameter("usr", bug.getUserName())
				.setParameter("nm", bug.getName())
				.setParameter("desc", bug.getDescription())
				.setParameter("ta", bug.getTimeAdded())
				.setParameter("cid", bug.getCategoryId())
				.setParameter("sevid", bug.getSeverityId())
				.setParameter("stid", bug.getStateId())
				.setParameter("pid", bug.getProjectId()).executeUpdate();
			} else {
				session.createNativeQuery(query)
				.setParameter("id", id)
				.setParameter("usr", bug.getUserName())
				.setParameter("nm", bug.getName())
				.setParameter("desc", bug.getDescription())
				.setParameter("ta", bug.getTimeAdded())
				.setParameter("cid", bug.getCategoryId())
				.setParameter("sevid", bug.getSeverityId())
				.setParameter("stid", bug.getStateId())
				.setParameter("tr", bug.getTimeResolved())
				.setParameter("pid", bug.getProjectId()).executeUpdate();
			}
			
			
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

	public void delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(Bug.class, id));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public Bug get(int id) {
		Bug bug = null;
		List<Bug> all = getAll();
		for(Bug b : all){
			if (b.getId() == id){
				bug = b;
				break;
			}
		}
		return bug;
	}

	public List<Bug> getAddedByTimeframe(Timestamp start, Timestamp finish) {
		List<Bug> list = new ArrayList<>();
		List<Bug> all = getAll();
		for (Bug b : all) {
			if (b.getTimeAdded() != null){
				if (b.getTimeAdded().compareTo(start) >= 0 && b.getTimeAdded().compareTo(finish) <= 0){
					list.add(b);
				}
			}
		}
		return list;
	}

	public List<Bug> getResolvedByTimeframe(Timestamp start, Timestamp finish) {
		List<Bug> list = new ArrayList<>();
		List<Bug> all = getAll();
		for (Bug b : all) {
			if (b.getTimeResolved() != null){
				if (b.getTimeResolved().compareTo(start) >= 0 && b.getTimeResolved().compareTo(finish) <= 0){
					list.add(b);
				}
			}
		}
		return list;
	}

	public void update(Bug bug) {
		delete(bug.getId());
		add(bug);
	}

	public List<Bug> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Bug> list = new ArrayList<>();

		try {
			tx = session.beginTransaction();
			String query = "FROM Bug";
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

	public List<Bug> getByProject(int projectId) {
		List<Bug> bugs = new ArrayList<>();
		List<Bug> all = getAll();
		for (Bug b : all) {
			if (b.getProjectId() == projectId) {
				bugs.add(b);
			}
		}
		return bugs;
	}

}
