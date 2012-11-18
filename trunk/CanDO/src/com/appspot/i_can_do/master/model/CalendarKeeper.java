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
	@NamedQuery(name = "CalendarKeeper.getCalendarsKeysByUserKey", query = "SELECT c.eventCalendarKey FROM CalendarKeeper c WHERE c.userKey = :userKey"),
	@NamedQuery(name = "CalendarKeeper.getCalendarKeeperByCalendarKey", query = "SELECT c FROM CalendarKeeper c WHERE c.eventCalendarKey = :eventCalendarKey")})
public class CalendarKeeper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key userKey;
	private Key eventCalendarKey;
	@Enumerated(EnumType.STRING)
	private Permission permission;

	public CalendarKeeper() {
	}

	public CalendarKeeper(Key eventCalendarKey, Key userKey,
			Permission permission) {
		this.eventCalendarKey = eventCalendarKey;
		this.userKey = userKey;
		this.permission = permission;
	}

	@Override
	public String toString() {
		return String
				.format("CalendarKeeper [key=%s, userKey=%s, eventCalendarKey=%s, permission=%s]",
						key, userKey, eventCalendarKey, permission);
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

	public Key getUserKey() {
		return userKey;
	}

	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}
	public Key getEventCalendarKey() {
		return eventCalendarKey;
	}

	public void setEventCalendarKey(Key eventCalendarKey) {
		this.eventCalendarKey = eventCalendarKey;
	}

}
