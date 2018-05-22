package com.fer.hr.model.managers;

import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.ClassReload;
import com.fer.hr.model.*;

public class ProjectManager {
	private SessionFactory factory;

	public ProjectManager() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public Integer add(Project project) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			Random r = new Random(System.currentTimeMillis());
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO project ");
			bob.append("(id, name, leader)");
			bob.append(" VALUES ");
			bob.append("(:id , :nm , :ldr)");
			String query = bob.toString();
			id = Math.abs(r.nextInt());
			session.createNativeQuery(query).setParameter("id", id).setParameter("nm", project.getName())
					.setParameter("ldr", project.getLeaderId()).executeUpdate();

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

	public Project get(int id) {
		Project proj = null;
		List<Project> all = getAll();
		for (Project b : all) {
			if (b.getId() == id) {
				proj = b;
				break;
			}
		}
		return proj;
	}

	public void delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(Project.class, id));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void update(Project proj) {
		delete(proj.getId());
		add(proj);
	}

	public List<Project> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Project> list = null;

		try {
			tx = session.beginTransaction();
			String query = "FROM Project";
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
