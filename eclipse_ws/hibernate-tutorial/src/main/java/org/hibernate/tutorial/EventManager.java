package org.hibernate.tutorial;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.util.HibernateUtil;

public class EventManager {
	public static void main(String[] args) {
		EventManager manager = new EventManager();
		
		if (args[0].equals("store")) {
			manager.createAndStoreEvent("My Event", new Date());
		} else if (args[0].equals("list")) {
			List events = manager.listEvents();
			for (int i = 0; i < events.size(); i++) {
				Event event = (Event) events.get(i);
				System.out.println("Event: " + event.getTitle() + " Time: " + event.getDate());
			}
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
	
	private List listEvents() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createQuery("from Event").list();
		session.getTransaction().commit();
		
		return result;
	}
}
