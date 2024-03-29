package com.appspot.i_can_do.master.model;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries({
	@NamedQuery(name="Event.GetEventsByCalendars",query="Select e FROM Event e WHERE e.eventCalendar IN (:calendars)")
	
})
public class Event implements Comparator<Event>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String name;
	private String description;
	private Date start;
	private Date finish;
	private Date warningTime;
	@ManyToOne
	private EventCalendar eventCalendar;

	public Event(){}
	
	public Event(String Name, String Description, Date Start, Date Finish, Date warningtime){
		name=Name;
		description=Description;
		start=Start;
		finish=Finish;
		warningTime=warningtime;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getFinish() {
		return finish;
	}
	public void setFinish(Date finish) {
		this.finish = finish;
	}
	public Date getWarningTime() {
		return warningTime;
	}
	public void setWarningTime(Date warningTime) {
		this.warningTime = warningTime;
	}
	public Key getKey(){
		return key;
	}

	@Override
	public int compare(Event o1, Event o2) {
		return (int) (o1.getStart().getTime()-o2.getStart().getTime());
	}
	
	public EventCalendar getEventCalendar() {
		return eventCalendar;
	}

	public void setEventCalendar(EventCalendar eventCalendar) {
		this.eventCalendar = eventCalendar;
	}
}
