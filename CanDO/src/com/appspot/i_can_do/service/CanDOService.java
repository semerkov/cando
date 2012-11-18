package com.appspot.i_can_do.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.appspot.i_can_do.master.model.CalendarKeeper;
import com.appspot.i_can_do.master.model.Event;
import com.appspot.i_can_do.master.model.EventCalendar;
import com.appspot.i_can_do.master.security.Permission;
import com.appspot.i_can_do.master.security.User;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class CanDOService {
	private static final EntityManager em = EMF.get().createEntityManager();
	private static final Logger log = Logger
			.getLogger(CanDOSecurityService.class.getName());
	private static CanDOService service = new CanDOService();

	private CanDOService() {
	}

	public static CanDOService inctance() {
		return service;
	}

	@SuppressWarnings("unchecked")
	public List<EventCalendar> getCalendars(User user) {
		if (user == null)
			throw new NullPointerException("User cannot be null!");
		if (user.getKey() == null)
			throw new IllegalArgumentException("Not persiste user!");

		Query query = em
				.createNamedQuery("CalendarKeeper.getCalendarsKeysByUserKey");

		query.setParameter("userKey", user.getKey());

		List<Key> calendarsKeys = (List<Key>) query.getResultList();
		List<EventCalendar> calendars = new ArrayList<EventCalendar>();
		for (Key key : calendarsKeys) {
			Query calendarQuery = em
					.createNamedQuery("EventCalendar.getCalendarsByKey");
			calendarQuery.setParameter("key", key);
			EventCalendar c = (EventCalendar) calendarQuery.getSingleResult();
			calendars.add(c);
		}
		return calendars;
	}

	public EventCalendar addCalendar(EventCalendar calendar, Key ownerKey) {
		if (calendar != null && ownerKey != null) {
			EntityTransaction txn = em.getTransaction();
			txn.begin();
			try {
				if (calendar.getKey() == null) {
					em.persist(calendar);
					txn.commit();
					log.warning("Calendar created: " + calendar);
				}
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}
			}

			txn = em.getTransaction();
			txn.begin();
			try {
				CalendarKeeper keeper = new CalendarKeeper(calendar.getKey(),
						ownerKey, Permission.Owner);
				em.persist(keeper);
				txn.commit();
				log.warning("CalendarKeeper created" + keeper);
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}
			}
		} else {
			if (calendar == null) {
				log.warning("Calendar for add is null");
			} else {
				log.warning("Owner key for add is null");
			}
			throw new IllegalArgumentException("Calendar for add is null");
		}
		return calendar;
	}

	public EventCalendar saveCalendar(EventCalendar calendar) {
		EventCalendar newCalendar = null;
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			newCalendar = em.merge(calendar);
			em.flush();
			txn.commit();
			log.warning("Calendar saved: " + calendar);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return newCalendar;

	}

	public void removeCalendar(EventCalendar calendar) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			EventCalendar cal = em.find(EventCalendar.class, calendar.getKey());
			em.remove(cal);
			em.flush();
			txn.commit();
			log.warning("Calendar removed: " + calendar);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	public void removeCalendarByKey(String calendarKey) {
		Key key = KeyFactory.stringToKey(calendarKey);
		removeCalendarKeeper(key);
		EntityTransaction txn = em.getTransaction();		
		txn.begin();
		try {
			EventCalendar cal = em.find(EventCalendar.class, key);
			em.remove(cal);
			em.flush();
			txn.commit();
			log.warning("Calendar removed with key: " + key);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	private void removeCalendarKeeper(Key calendarKey){
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Query query = em
					.createNamedQuery("CalendarKeeper.getCalendarKeeperByCalendarKey");
			query.setParameter("eventCalendarKey", calendarKey);
			CalendarKeeper c =(CalendarKeeper) query.getSingleResult();
			em.remove(c);
			em.flush();
			txn.commit();
			log.warning("CalendarKeeper removed with key: " + calendarKey);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
	private void addEvent(Event event) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			em.persist(event);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	private void saveEvent(Event event) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			em.merge(event);
			em.flush();
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	private void removeEvent(Event event) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Event ev = em.find(Event.class, event.getKey());
			em.remove(ev);
			em.flush();
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
}
