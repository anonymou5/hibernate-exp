package org.hibernate.tutorial;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.domain.Person;
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
				System.out.println("Event: " + event.getTitle() + " Time: "
						+ event.getDate());
			}
		} else if (args[0].equals("addpersontoevent")) {
			Long eventId = manager.createAndStoreEvent("My Event", new Date());
			Long personId = manager.createAndStorePerson("Foo", "Bar");
			manager.addPersonToEvent(personId, eventId);
			System.out.println("Added person " + personId + " to event "
					+ eventId);
		} else if (args[0].equals("addemailtoperson")) {
			Long personId = manager.createAndStorePerson("Foo", "Bar");
			String emailAddress = personId + "@domain.com";
			manager.addEmailToPerson(personId, emailAddress);
			System.out.println("Added email address " + emailAddress
					+ " to person " + personId);
		}

		HibernateUtil.getSessionFactory().close();
	}

	private Long createAndStoreEvent(String title, Date date) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Event event = new Event();
		event.setTitle(title);
		event.setDate(date);

		Long eventId = (Long) session.save(event);

		session.getTransaction().commit();

		return eventId;
	}

	private Long createAndStorePerson(String firstname, String lastname) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person person = new Person();
		person.setAge(12);
		person.setFirstname(firstname);
		person.setLastname(lastname);

		Long personId = (Long) session.save(person);

		session.getTransaction().commit();

		return personId;
	}

	private List listEvents() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List result = session.createQuery("from Event").list();
		session.getTransaction().commit();

		return result;
	}

	private void addPersonToEvent(Long personId, Long eventId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person person = (Person) session.load(Person.class, personId);
		Event event = (Event) session.load(Event.class, eventId);
		person.addToEvent(event);

		session.getTransaction().commit();
	}

	@SuppressWarnings("unused")
	private void addEmailToPerson(Long personId, String emailAddress) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person person;
		if (false) {
			person = (Person) session.load(Person.class, personId);
			// adding to the emailAddress collection might trigger a lazy load
			// of the collection
		} else {
			person = (Person) session
				.createQuery("select p from Person p left join fetch p.emailAddresses where p.id = :pid")
				.setParameter("pid", personId)
				.uniqueResult();
		}
		person.getEmailAddresses().add(emailAddress);

		session.getTransaction().commit();
	}
}
