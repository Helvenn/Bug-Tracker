package model.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.BugProject;
import model.keys.BPKey;

public class BPManager {
	private SessionFactory factory;

	public BPManager(SessionFactory factory) {
		this.factory = factory;
	}
	
	public BPKey add(BugProject bp) {
		Session session = factory.openSession();
		Transaction tx = null;
		BPKey key = null;

		try {
			tx = session.beginTransaction();
			key = (BPKey)session.save(bp);

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
	
	public BugProject get(BPKey key) {
		Session session = factory.openSession();
		Transaction tx = null;
		BugProject bp = null;
		
		try {
			tx = session.beginTransaction();
			bp = (BugProject)session.get(BugProject.class, key);
			
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return bp;
	}
	
	public boolean delete(BPKey key) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			BugProject bp = (BugProject)session.get(BugProject.class, key);
			session.delete(bp);

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
	
	public boolean updateState(BPKey key, int stateId) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			BugProject bp = (BugProject)session.get(BugProject.class, key);
			bp.setStateId(stateId);

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
