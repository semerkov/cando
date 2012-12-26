package com.appspot.i_can_do.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.appspot.i_can_do.master.model.CalendarKeeper;
import com.appspot.i_can_do.master.model.Event;
import com.appspot.i_can_do.master.model.EventCalendar;
import com.appspot.i_can_do.master.model.Group;
import com.appspot.i_can_do.master.model.GroupType;
import com.appspot.i_can_do.master.model.Task;
import com.appspot.i_can_do.master.model.TaskKeeper;
import com.appspot.i_can_do.master.model.TaskList;
import com.appspot.i_can_do.master.security.Permission;
import com.appspot.i_can_do.master.security.User;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class CanDOService {
	private static final EntityManager em = EMF.get().createEntityManager();
	private static final Logger log = Logger
			.getLogger(CanDOSecurityService.class.getName());
	private static CanDOService service;

	private CanDOService() {
	}

	public static CanDOService inctance() {
		if (service == null)
			service = new CanDOService();
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
			EventCalendar c = em.find(EventCalendar.class, key);
			calendars.add(c);
		}
		return calendars;
	}

	public EventCalendar getCalendarByKey(String calendarKey) {
		Key key = KeyFactory.stringToKey(calendarKey);
		return em.find(EventCalendar.class, key);

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
		removeCalendarKeeper(calendar.getKey());
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

	private void removeCalendarKeeper(Key calendarKey) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Query query = em
					.createNamedQuery("CalendarKeeper.getCalendarKeeperByCalendarKey");
			query.setParameter("eventCalendarKey", calendarKey);
			CalendarKeeper c = (CalendarKeeper) query.getSingleResult();
			Key calendarKeeperKey = c.getKey();
			em.remove(c);
			em.flush();
			txn.commit();
			log.warning("CalendarKeeper removed with key: " + calendarKeeperKey);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	public EventCalendar getCalendar(String eventKey, Key userKey) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		Key eventFindKey = KeyFactory.stringToKey(eventKey);
		EventCalendar result = null;
		try {
			Query query = em
					.createNamedQuery("CalendarKeeper.getCalendarsKeysByUserKey");

			query.setParameter("userKey", userKey);

			List<Key> calendarsKeys = (List<Key>) query.getResultList();
			calendars: for (Key key : calendarsKeys) {
				EventCalendar c = em.find(EventCalendar.class, key);
				List<Event> events = c.getEvents();
				for (Event event : events) {
					if (event.getKey().equals(eventFindKey)) {
						result = c;
						break calendars;
					}
				}
			}
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return result;
	}

	public Event getEventByKey(String eventKey) {
		Key key = KeyFactory.stringToKey(eventKey);
		return em.find(Event.class, key);
	}

	public Event saveEvent(Event event) {
		Event newEvent = null;
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			newEvent = em.merge(event);
			em.flush();
			txn.commit();
			log.warning("Calendar saved: " + event);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return newEvent;
	}

	public void removeEventByKey(String eventKey) {
		Key key = KeyFactory.stringToKey(eventKey);
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Event event = em.find(Event.class, key);
			em.remove(event);
			em.flush();
			txn.commit();
			log.warning("Event removed with key: " + key);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	public Event addEvent(Event event) {
		if (event != null) {
			EntityTransaction txn = em.getTransaction();
			txn.begin();
			try {
				if (event.getKey() == null) {
					em.persist(event);
					txn.commit();
					log.warning("Event created: " + event);
				}
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}
			}
		}
		return event;
	}

	public Task addTask(Task task) {
		if (task != null) {
			EntityTransaction txn = em.getTransaction();
			txn.begin();
			try {
				if (task.getKey() == null) {
					em.persist(task);
					txn.commit();
					log.warning("Task created: " + task);
				}
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}
			}
		}
		return task;

	}

	public Task saveTask(Task task) {
		Task newTask = null;
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			newTask = em.merge(task);
			em.flush();
			txn.commit();
			log.warning("Task saved: " + task);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return newTask;
	}

	public void removeTaskByKey(String taskKey) {
		Key key = KeyFactory.stringToKey(taskKey);
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Task task = em.find(Task.class, key);
			em.remove(task);
			em.flush();
			txn.commit();
			log.warning("Task removed with key: " + key);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	public Task getTaskByKey(String taskKey) {
		Key key = KeyFactory.stringToKey(taskKey);
		return em.find(Task.class, key);
	}

	public List<TaskList> getTaskLists(User user) {
		if (user == null)
			throw new NullPointerException("User cannot be null!");
		if (user.getKey() == null)
			throw new IllegalArgumentException("Not persiste user!");

		Query query = em
				.createNamedQuery("TaskKeeper.getTasksListsKeysByUserKey");

		query.setParameter("userKey", user.getKey());

		List<Key> tasksKeys = (List<Key>) query.getResultList();
		List<TaskList> tasksList = new ArrayList<TaskList>();
		for (Key key : tasksKeys) {
			TaskList c = em.find(TaskList.class, key);
			tasksList.add(c);
		}
		return tasksList;
	}

	public List<TaskList> getTaskLists(User user, Permission permission) {
		if (user == null)
			throw new NullPointerException("User cannot be null!");
		if (user.getKey() == null)
			throw new IllegalArgumentException("Not persiste user!");

		Query query = em
				.createNamedQuery("TaskKeeper.getTasksListsKeysByUserKeyAndPermission");

		query.setParameter("userKey", user.getKey());
		query.setParameter("permission", permission);

		List<Key> tasksKeys = (List<Key>) query.getResultList();
		List<TaskList> tasksList = new ArrayList<TaskList>();
		for (Key key : tasksKeys) {
			TaskList c = em.find(TaskList.class, key);
			tasksList.add(c);
		}
		return tasksList;
	}

	public TaskList getTaskListByKey(String taskKey) {
		Key key = KeyFactory.stringToKey(taskKey);
		return em.find(TaskList.class, key);
	}

	public TaskList addTaskList(TaskList taskList, Key ownerKey) {
		if (taskList != null && ownerKey != null) {
			EntityTransaction txn = em.getTransaction();
			txn.begin();
			try {
				if (taskList.getKey() == null) {
					em.persist(taskList);
					txn.commit();
					log.warning("TaskList created: " + taskList);
				}
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}
			}

			txn = em.getTransaction();
			txn.begin();
			try {
				TaskKeeper keeper = new TaskKeeper(taskList.getKey(), ownerKey,
						Permission.Owner);
				em.persist(keeper);
				txn.commit();
				log.warning("TaskKeeper created" + keeper);
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}
			}
		} else {
			if (taskList == null) {
				log.warning("TaskList for add is null");
			} else {
				log.warning("Owner key for add is null");
			}
			throw new IllegalArgumentException("TaskList for add is null");
		}
		return taskList;
	}

	public TaskList saveTaskList(TaskList taskList) {
		TaskList newTaskList = null;
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			newTaskList = em.merge(taskList);
			em.flush();
			txn.commit();
			log.warning("TaskList saved: " + taskList);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return newTaskList;
	}

	public void removeTaskList(TaskList taskList) {
		EntityTransaction txn = em.getTransaction();
		removeTaskKeeper(taskList.getKey());
		txn.begin();
		try {
			TaskList tList = em.find(TaskList.class, taskList.getKey());
			em.remove(tList);
			em.flush();
			txn.commit();
			log.warning("TaskList removed: " + taskList);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	public void removeTaskListByKey(String taskListKey) {
		Key key = KeyFactory.stringToKey(taskListKey);
		removeTaskKeeper(key);
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			TaskList cal = em.find(TaskList.class, key);
			em.remove(cal);
			em.flush();
			txn.commit();
			log.warning("TaskList removed with key: " + key);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	private void removeTaskKeeper(Key taskListKey) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Query query = em
					.createNamedQuery("TaskKeeper.getTaskKeeperByTaskListKey");
			query.setParameter("taskListKey", taskListKey);
			TaskKeeper c = (TaskKeeper) query.getSingleResult();
			Key taskKeeperKey = c.getKey();
			em.remove(c);
			em.flush();
			txn.commit();
			log.warning("TaskKeeper removed with key: " + taskKeeperKey);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	public HashMap<User, String> getAllContacts(User user) {
		Query query = em.createNamedQuery("Group.getAllContacts");
		query.setParameter("key", user.getKey());
		List<Group> group = (List<Group>) query.getResultList();

		HashMap<User, String> result = new HashMap<User, String>();
		for (Group g : group) {
			result.put(em.find(User.class, g.getContactKey()),
					g.getGroupTypeKey() != null ? (em.find(GroupType.class, g.getGroupTypeKey())).getGroupName()
							: null);
		}
		return result;
	}

	public List<User> getNewContacts(User user) {
		Query contactUsersQuery = em
				.createQuery("SELECT g.contactKey FROM Group g WHERE g.userKey = :key");
		contactUsersQuery.setParameter("key", user.getKey());
		List<User> contactUsers = getUsersFromUsersKeys((List<Key>) contactUsersQuery
				.getResultList());

		Query query = em
				.createQuery("SELECT u FROM User u WHERE u.key <> :key AND u.key");
		query.setParameter("key", user.getKey());

		List users = new ArrayList();
		users.addAll(query.getResultList());

		users.removeAll(contactUsers);

		return users;
	}

	public List<User> getUsersFromUsersKeys(List<Key> userKeys) {
		List<User> contactUsers = new ArrayList<User>(userKeys.size());
		for (Key key : userKeys) {
			contactUsers.add(em.find(User.class, key));
		}
		return contactUsers;
	}

	public List<GroupType> getGroupTypes(User user) {
		Query query = em
				.createNamedQuery("GroupType.getTypes");
		query.setParameter("key", user.getKey());
		return (List<GroupType>)query.getResultList();
	}

	public GroupType addGroupType(User user, String groupName) {
		GroupType group = null;
		if (user != null && groupName != null) {
			EntityTransaction txn = em.getTransaction();
			txn.begin();
			try {
				if (user.getKey() != null) {
					group = new GroupType(user.getKey(), groupName);
					em.persist(group);
					txn.commit();
					log.warning("GroupType created: " + group);
				}
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}
			}
		}
		return group;
	}
	
	public GroupType saveGroupType(GroupType group) {
		GroupType newGroupType = null;
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			newGroupType = em.merge(group);
			em.flush();
			txn.commit();
			log.warning("GroupType saved: " + group);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return newGroupType;
	}
	
	public void removeGroupType(User user, Key key) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {	
			GroupType group = em.find(GroupType.class, key);
			em.remove(group);
			Query query = em.createNamedQuery("Group.getContactsByGroupType");
			query.setParameter("key", user.getKey());
			query.setParameter("typeKey", key);
			List<Group> result = (List<Group>)query.getResultList();
			for(Group g :result){
				g.setGroupTypeKey(null);
			}
			txn.commit();
			log.warning("GroupType removed with key: " + key);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		
	}

	public Group saveContact(Group group) {
		Group newGroup = null;
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			newGroup = em.merge(group);
			em.flush();
			txn.commit();
			log.warning("Group saved: " + group);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return newGroup;
	}

	public void removeContact(User user, Key contactKey) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Query query = em.createNamedQuery("Group.deleteByUserAndContact");
			query.setParameter("key", user.getKey());
			query.setParameter("contact", contactKey);
			query.executeUpdate();
			txn.commit();
			log.warning("Group removed with key: " + contactKey);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	public void addContact(User user, Key contactKey) {
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Group g = new Group(user.getKey(), null, contactKey);
			em.persist(g);
			txn.commit();
			log.warning("Group add: " + g);
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
	
	public void addGroupToContact(User user, Key contactKey, Key groupTypeKey){
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {	
			Query query = em.createNamedQuery("Group.getContactsByContactAndGroupType");
			query.setParameter("key", user.getKey());
			query.setParameter("contact", contactKey);
			query.setParameter("typeKey", groupTypeKey);
			try{
				Group g = (Group)query.getSingleResult();
				g.setGroupTypeKey(groupTypeKey);		
			}catch(NoResultException e){
				Group g = new Group(user.getKey(), groupTypeKey, contactKey);
				em.persist(g);
			}			
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
	
	public void removeGroupFromContact(User user, Key contactKey, Key groupTypeKey){
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			Query query = em.createNamedQuery("Group.deleteByUserContactAndGroup");
			query.setParameter("key", user.getKey());
			query.setParameter("contact", contactKey);
			query.setParameter("typeKey", groupTypeKey);
			query.executeUpdate();
			txn.commit();
			log.warning("GroupType removed");
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
}
