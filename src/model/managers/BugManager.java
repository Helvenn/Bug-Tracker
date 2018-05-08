package model.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Bug;

public class BugManager {
	private SessionFactory factory;

	public BugManager(SessionFactory factory) {
		this.factory = factory;
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
	public List<Bug> getByProject(int projectId) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Bug> list = null;

		try {
			tx = session.beginTransaction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT * FROM bug");
			query.append("WHERE project_id = :id");
			list = session.createQuery(query.toString()).setParameter("id", projectId).list();

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
	public List<Bug> getByTimeframe(String start, String finish) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Bug> list = null;
		
		//TODO complete the query when the tables are updated.
		int startYear = 0;

		try {
			tx = session.beginTransaction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT * FROM bug");
			query.append("WHERE project_id = :id");
			list = session.createQuery(query.toString()).setParameter("id", projectId).list();

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
