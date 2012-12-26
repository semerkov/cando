package com.appspot.i_can_do.master.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries({
	@NamedQuery(name = "GroupType.getTypes", query = "SELECT g FROM GroupType g WHERE g.userKey = :key") })
public class GroupType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key userKey;
	private String groupName;
	
	public GroupType(){}
	
	public GroupType(Key userKey, String groupName){
		this.userKey = userKey;
		this.groupName = groupName;
	}
	
	public Key getKey() {
		return key;
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
	
	
}
