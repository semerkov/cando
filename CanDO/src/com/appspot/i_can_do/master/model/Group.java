package com.appspot.i_can_do.master.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key userKey;
	private String groupName;
	
	public Group(){}
	
	public Group(Key userKey, String groupName){
		this.userKey=userKey;
		this.groupName=groupName;
	}
	
	
	public Key getUserKey() {
		return userKey;
	}
	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Key getKey() {
		return key;
	}
	
}
