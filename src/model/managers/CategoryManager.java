package model.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Category;

public class CategoryManager {
	private SessionFactory factory;

	public CategoryManager(SessionFactory factory) {
		this.factory = factory;
	}

	public Integer add(Category category) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(category);

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

	public Category get(int id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Category cat = null;

		try {
			tx = session.beginTransaction();
			cat = (Category) session.get(Category.class, id);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return cat;
	}

	public boolean delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Category cat = (Category) session.get(Category.class, id);
			session.delete(cat);

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

	public boolean updateDesc(int id, String newDesc) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Category cat = (Category) session.get(Category.class, id);
			cat.setDescription(newDesc);

			tx.commit();
			return false;
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
	public List<Category> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Category> list = null;

		try {
			tx = session.beginTransaction();
			String query = "SELECT * FROM cagtegory";
			list = (List<Category>) session.createQuery(query).list();

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
