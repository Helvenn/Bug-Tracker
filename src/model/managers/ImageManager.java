package model.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Image;

public class ImageManager {
	private SessionFactory factory;

	public ImageManager(SessionFactory factory) {
		this.factory = factory;
	}
	
	public Integer add(Image image) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;

		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(image);

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
	
	public Image get(int id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Image image = null;

		try {
			tx = session.beginTransaction();
			image = session.get(Image.class, id);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return image;
	}
	
	public boolean delete(int id) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Image image = (Image) session.get(Image.class, id);
			session.delete(image);

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
			Image image = (Image) session.get(Image.class, id);
			image.setName(newName);

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
	public List<Image> getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Image> list = null;
		
		try {
			tx = session.beginTransaction();
			String query = "SELECT * FROM image";
			list = (List<Image>) session.createQuery(query).list();
			
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
