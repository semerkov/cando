package com.appspot.i_can_do.master.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.appspot.i_can_do.master.security.Permission;
import com.google.appengine.api.datastore.Key;

@Entity
public class CalendarKeeper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key ownerKey;
	private Key eventCalendarKey;
	@Enumerated(EnumType.STRING)
	private Permission permission;

	public CalendarKeeper() {
	}

	public CalendarKeeper(Key eventCalendarKey, Key ownerKey,
			Permission permission) {
		this.eventCalendarKey = eventCalendarKey;
		this.ownerKey = ownerKey;
		this.permission = permission;
	}

	@Override
	public String toString() {
		return String
				.format("CalendarKeeper [key=%s, ownerKey=%s, eventCalendarKey=%s, permission=%s]",
						key, ownerKey, eventCalendarKey, permission);
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

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getOwnerKey() {
		return ownerKey;
	}

	public void setOwnerKey(Key ownerKey) {
		this.ownerKey = ownerKey;
	}
	public Key getEventCalendarKey() {
		return eventCalendarKey;
	}

	public void setEventCalendarKey(Key eventCalendarKey) {
		this.eventCalendarKey = eventCalendarKey;
	}

}
