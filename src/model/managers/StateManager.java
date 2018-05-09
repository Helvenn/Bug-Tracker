package model.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.State;

public class StateManager {
	private SessionFactory factory;

	public StateManager(SessionFactory factory) {
		this.factory = factory;
	}
	
	public Integer add(State state) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(state);

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
		Session session = factory.openSession();
		Transaction tx = null;
		State state = null;

		try {
			tx = session.beginTransaction();
			state = (State) session.get(State.class, id);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return state;
	}
	
	public boolean delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			State state = (State) session.get(State.class, id);
			session.delete(state);

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
	
	public boolean updateName(int id, String newDesc) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			State state = (State) session.get(State.class, id);
			state.setDescription(newDesc);

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
	public List<State> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<State> list = null;
		
		try {
			tx = session.beginTransaction();
			String query = "SELECT * FROM state";
			list = (List<State>) session.createQuery(query).list();
			
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
