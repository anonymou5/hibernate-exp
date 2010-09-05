package org.hibernate.tutorial;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.util.HibernateUtil;

public class EventManager {
	public static void main(String[] args) {
		EventManager manager = new EventManager();
		
		if (args[0].equals("store")) {
			manager.createAndStoreEvent("My Event", new Date());
		}
		
		HibernateUtil.getSessionFactory().close();
	}
	
	private void createAndStoreEvent(String title, Date date) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Event event = new Event();
		event.setTitle(title);
		event.setDate(date);
		
		session.save(event);
		
		session.getTransaction().commit();
	}
}
