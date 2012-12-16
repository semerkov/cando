package com.appspot.i_can_do.master.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key userKey;
	private Key friendKey;
	private Key groupKey;
	
	public Contact(Key userKey, Key friendKey, Key groupKey){
		this.userKey = userKey;
		this.friendKey = friendKey;
		this.groupKey = groupKey;
	}
	public Contact(){}
	
	public Key getUserKey() {
		return userKey;
	}
	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}
	public Key getFriendKey() {
		return friendKey;
	}
	public void setFriendKey(Key friendKey) {
		this.friendKey = friendKey;
	}
	public Key getGroup() {
		return groupKey;
	}
	public void setGroup(Key group) {
		this.groupKey = group;
	}
	public Key getKey() {
		return key;
	}
}
