package com.appspot.i_can_do.master.security;

import java.util.Calendar;

public class Event{
	private String name;
	private String description;
	private Calendar start,finish;
	
	public Event(String Name, String Description, Calendar Start, Calendar Finish){
		name=Name;
		description=Description;
		start=Start;
		finish=Finish;
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
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	public Calendar getFinish() {
		return finish;
	}
	public void setFinish(Calendar finish) {
		this.finish = finish;
	}

}
