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
		@NamedQuery(name = "Group.getContactsByGroupType", query = "SELECT g FROM Group g WHERE g.userKey = :key AND g.groupTypeKey = :typeKey"),
		@NamedQuery(name = "Group.getContactsByContactAndGroupType", query = "SELECT g FROM Group g WHERE g.userKey = :key AND g.contactKey = :contact AND g.groupTypeKey = :typeKey"),
		@NamedQuery(name = "Group.getAllContacts", query = "SELECT DISTINCT g FROM Group g WHERE g.userKey = :key"),
		@NamedQuery(name = "Group.deleteByUserAndContact", query = "DELETE FROM Group g WHERE g.userKey = :key AND g.contactKey = :contact"),
		@NamedQuery(name = "Group.deleteByUserContactAndGroup", query = "DELETE FROM Group g WHERE g.userKey = :key AND g.contactKey = :contact AND g.groupTypeKey = :typeKey")
		})
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key userKey;
	private Key groupTypeKey;
	private Key contactKey;

	public Group() {
	}

	public Group(Key userKey, Key groupTypeKey, Key contactKey) {
		this.userKey = userKey;
		this.groupTypeKey = groupTypeKey;
		this.setContactKey(contactKey);
	}

	public Key getUserKey() {
		return userKey;
	}

	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}

	public Key getGroupTypeKey() {
		return groupTypeKey;
	}

	public void setGroupTypeKey(Key groupTypeKey) {
		this.groupTypeKey = groupTypeKey;
	}

	public Key getKey() {
		return key;
	}

	public Key getContactKey() {
		return contactKey;
	}

	public void setContactKey(Key contactKey) {
		this.contactKey = contactKey;
	}

}
