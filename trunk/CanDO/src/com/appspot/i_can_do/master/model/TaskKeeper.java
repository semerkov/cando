package com.appspot.i_can_do.master.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.appspot.i_can_do.master.security.Permission;
import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries({
	@NamedQuery(name = "TaskKeeper.getTasksListsKeysByUserKey", query = "SELECT t.taskListKey FROM TaskKeeper t WHERE c.userKey = :userKey"),
	@NamedQuery(name = "TaskKeeper.getTaskKeeperByTaskListKey", query = "SELECT t FROM TaskKeeper t WHERE t.taskListKey = :taskListKey")})
public class TaskKeeper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key userKey;
	private Key taskListKey;
	@Enumerated(EnumType.STRING)
	private Permission permission;
	
	public TaskKeeper(Key taskListKey, Key userKey,
			Permission permission) {
		this.taskListKey = taskListKey;
		this.userKey = userKey;
		this.permission = permission;
	}
	
	@Override
	public String toString() {
		return String
				.format("TaskKeeper [key=%s, userKey=%s, taskListKey=%s, permission=%s]",
						key, userKey, taskListKey, permission);
	}

	public Key getUserKey() {
		return userKey;
	}
	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}
	public Key getTaskListKey() {
		return taskListKey;
	}
	public void setTaskListKey(Key taskListKey) {
		this.taskListKey = taskListKey;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public Key getKey() {
		return key;
	}
}
