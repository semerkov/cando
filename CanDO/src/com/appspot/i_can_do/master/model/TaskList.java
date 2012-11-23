package com.appspot.i_can_do.master.model;

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
@NamedQueries({
	@NamedQuery(name = "TaskList.getTasksListByKey", query = "SELECT t FROM TaskList t WHERE t.key = :key") })
public class TaskList {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	private String name;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Task> tasks;
	private String color;
	
	@Override
	public String toString() {
		return String.format("TaskList [key=%s, name=%s, tasks=%s, color=%s]",
				key, name, tasks, color);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Key getKey() {
		return key;
	}
}
