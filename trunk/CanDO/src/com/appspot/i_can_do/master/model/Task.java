package com.appspot.i_can_do.master.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.appspot.i_can_do.master.security.State;
import com.google.appengine.api.datastore.Key;

@Entity
public class Task {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String name;
	private Date date;
	@Enumerated(EnumType.STRING)
	private State state;
	
	@Override
	public String toString() {
		return String.format("Task [key=%s, name=%s, date=%s, state=%s]", key,
				name, date, state);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Key getKey() {
		return key;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
}
