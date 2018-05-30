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


public class RoleManager {
	private SessionFactory factory;

	public RoleManager() {
		try {
	         factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		  }
	}

	public Integer add(Role role) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			Random r = new Random(System.currentTimeMillis());
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO user_role ");
			bob.append("(id, name)");
			bob.append(" VALUES ");
			bob.append("(:id , :nm)");
			String query = bob.toString();
			if (role.getId() != 0) {
				id = role.getId();
			} else {
				id = Math.abs(r.nextInt());
			}
			session.createNativeQuery(query)
					.setParameter("id", id)
					.setParameter("nm", role.getName()).executeUpdate();
			
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

	public Role get(int id) {
		Role role = null;
		List<Role> all = getAll();
		for (Role b : all) {
			if (b.getId() == id) {
				role = b;
				break;
			}
		}
		return role;
	}

	public void delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(Role.class, id));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void update(Role role) {
		delete(role.getId());
		add(role);
	}

	public List<Role> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Role> list = new ArrayList<>();

		try {
			tx = session.beginTransaction();
			String query = "FROM Role";
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
