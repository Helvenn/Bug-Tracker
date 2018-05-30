package com.fer.hr.model.managers;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fer.hr.ClassReload;
import com.fer.hr.model.History;

public class HistoryManager {
	private SessionFactory factory;

	public HistoryManager() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public void add(History history){
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Random r = new Random(System.currentTimeMillis());
			StringBuilder bob = new StringBuilder();
			bob.append("INSERT INTO history ");
			bob.append("(id, bug_id, time, new_state_id, person_in_charge, project_id)");
			bob.append(" VALUES ");
			bob.append("(:id , :bid , :tm , :ns , :pic , :pid)");
			String query = bob.toString();
			int id = Math.abs(r.nextInt());
			session.createNativeQuery(query)
					.setParameter("id", id)
					.setParameter("bid", history.getBugId())
					.setParameter("tm", history.getTime())
					.setParameter("ns", history.getNewState())
					.setParameter("pid", history.getProjectId())
					.setParameter("pic", history.getPersonInCharge()).executeUpdate();

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public List<History> getByBug(int bugId){
		List<History> his = new ArrayList<>();
		List<History> list = getAll();
		for (History h : list){
			if(h.getBugId() == bugId)
				his.add(h);
		}
		return his;
	}
	
	public List<History> getAll(){
		Session session = factory.openSession();
		Transaction tx = null;
		List<History> list = new ArrayList<>();

		try {
			tx = session.beginTransaction();
			String query = "FROM History";
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
	
	public void delete(int id){
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(session.get(History.class, id));

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<History> getByProject(int projectId){
		List<History> his = new ArrayList<>();
		List<History> all = getAll();
		
		for(History h : all){
			if (h.getProjectId() == projectId){
				his.add(h);
			}
		}

		return his;
	}
	
}
