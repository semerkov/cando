package com.appspot.i_can_do.master.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;

@Entity
public class EventCalendar {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String name;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Event> events;
	//private List<Event> sharedEvents;
	private String color;
	
	public EventCalendar(){
		events = new ArrayList<Event>();
	}
	public Key getKey() {
		return key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}/*
	public List<Event> getSharedEvents() {
		return sharedEvents;
	}
	public void setSharedEvents(List<Event> sharedEvents) {
		this.sharedEvents = sharedEvents;
	}*/
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return String.format(
				"EventCalendar [key=%s, name=%s, events=%s, color=%s]", key,
				name, events, color);
	}
}
