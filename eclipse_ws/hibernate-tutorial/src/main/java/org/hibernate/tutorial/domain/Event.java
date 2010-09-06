package org.hibernate.tutorial.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Event {
	private Long id;
	private String title;
	private Date date;
	private Set<Person> participants = new HashSet<Person>();

	public Event() {
	}
	
	public Long getId() {
		return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	protected Set<Person> getParticipants() {
		return participants;
	}

	protected void setParticipants(Set<Person> participants) {
		this.participants = participants;
	}
	
	public void addParticipant(Person person) {
		this.getParticipants().add(person);
		person.getEvents().add(this);
	}
	
	public void removeParticipant(Person person) {
		this.getParticipants().remove(person);
		person.getEvents().remove(this);
	}
}
