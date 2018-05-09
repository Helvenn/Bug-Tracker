package model.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Role;

public class RoleManager {
	private SessionFactory factory;

	public RoleManager(SessionFactory factory) {
		this.factory = factory;
	}

	public Integer add(Role role) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(role);

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
		Session session = factory.openSession();
		Transaction tx = null;
		Role role = null;

		try {
			tx = session.beginTransaction();
			role = (Role) session.get(Role.class, id);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return role;
	}

	public boolean delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Role role = (Role) session.get(Role.class, id);
			session.delete(role);

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
	
	public boolean updateName(int id, String newName) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Role role = (Role) session.get(Role.class, id);
			role.setName(newName);

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
}
