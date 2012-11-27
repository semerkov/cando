package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appspot.i_can_do.master.model.Event;
import com.appspot.i_can_do.master.model.EventCalendar;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.CanDOService;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;
import com.appspot.i_can_do.service.utils.ServletUtils;

@SuppressWarnings("serial")
public class CalendarServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(CalendarServlet.class
			.getName());
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList(new String[] { "retrieveCalenderTable", "addCalendar",
					"retrieveEventCalendarMenu", "removeCalendar",
					"updateCalendar", "addEvent", "updateEvent", "removeEvent",
					"viewEvent","getEvent" });
	private SimpleDateFormat formatter = new SimpleDateFormat(
			"dd.MM.yyyy hh:mm");
	private CanDOService canDOService;

	private User user;// TODO remove test user when complete registration
							// and etc.

	@Override
	public void init(ServletConfig config) {
		canDOService = CanDOService.inctance();
		log.info("Servlet created");
		/*
		String email = "user@email.com";
		testUser = CanDOSecurityService.instance().findUser(email);
		if (testUser == null) {
			// for test create new user
			testUser = new User();
			testUser.setEmail(email);
			testUser.setName("Mario");
			testUser.setSername("Gavani");
			try {
				CanDOSecurityService.instance()
						.addNewUser(testUser, "password");
				log.warning("Test user created");
			} catch (LoginNameExistException e) {
				log.warning("Test user exist");
			}
		} else {
			log.warning("Test user exist");
		}
		*/
	}

	protected boolean isLoginState(HttpServletRequest request) {
		User userObj = (User) request.getSession().getAttribute("User");
		return userObj != null;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		if(user==null){
		HttpSession session = request.getSession();
		String userEmail = (String) session.getAttribute("user");
		user = CanDOSecurityService.instance().findUser(userEmail);
			if(user==null){
				try{
				request.getRequestDispatcher("/").forward(request, response);
				return;
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			request.setAttribute("user", user);
			request.getRequestDispatcher("calendar.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String action = request.getParameter("action");

		if (SECURITY_ACTIONS.contains(action)) {
			// TODO remove, for test only
			/*
			 * if (!isLoginState(request)) {
			 * response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
			 * "Only authorized user can perform this action"); return; }
			 */
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Not existing action!");
			return;
		}

		if (action.equals("retrieveEventCalendarMenu")) {
			fillEventCalendarMenu(request, response);
		} else if (action.equals("retrieveCalenderTable")) {
			retrieveCalenderTable(request, response);
		} else if (action.equals("addCalendar")) {
			addCalendar(request, response);
		} else if (action.equals("removeCalendar")) {
			removeCalendar(request, response);
		} else if (action.equals("updateCalendar")) {
			updateCalendar(request, response);
		} else if (action.equals("addEvent")) {
			addEvent(request, response);
		} else if (action.equals("updateEvent")) {
			updateEvent(request, response);
		} else if (action.equals("removeEvent")) {
			removeEvent(request, response);
		} else if (action.equals("viewEvent")) {
			viewEvent(request, response);
		}else if (action.equals("getEvent")){
			getEvent(request, response);
		}
	}

	private static final List<String> SECURITY_MONTH_ACTIONS = Arrays
			.asList(new String[] { "this", "next", "previous" });
	private static final List<Integer> SECURITY_MONTH = Arrays
			.asList(new Integer[] { Calendar.JANUARY, Calendar.FEBRUARY,
					Calendar.MARCH, Calendar.APRIL, Calendar.MAY,
					Calendar.JUNE, Calendar.JULY, Calendar.AUGUST,
					Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER,
					Calendar.DECEMBER });

	private void retrieveCalenderTable(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer month = Integer.parseInt(request.getParameter("currentMonth")
				.trim());
		Integer year = Integer.parseInt(request.getParameter("currentYear")
				.trim());
		String monthAction = request.getParameter("monthAction");
		String calendarKeys = request.getParameter("selectedCalendars");
		List<EventCalendar> calendars = new ArrayList<EventCalendar>();
		if (calendarKeys != null) {
			String[] k = calendarKeys.split(",");
			for (String key : k) {
				if (!key.isEmpty()) {
					calendars.add(canDOService.getCalendarByKey(key));
				}
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(0);
		if (SECURITY_MONTH_ACTIONS.contains(monthAction)
				&& SECURITY_MONTH.contains(month)) {
			if (month.equals(Calendar.DECEMBER) && monthAction.equals("next")) {
				// next year case
				calendar.set(Calendar.YEAR, year + 1);
				calendar.set(Calendar.MONTH, Calendar.JANUARY);

			} else if (month.equals(Calendar.JANUARY)
					&& monthAction.equals("previous")) {
				// previous year case
				calendar.set(Calendar.YEAR, year - 1);
				calendar.set(Calendar.MONTH, Calendar.DECEMBER);

			} else {
				// common case
				calendar.set(Calendar.YEAR, year);
				int offset = 0;// if "this" case
				if (monthAction.equals("next")) {
					offset++;
				} else if (monthAction.equals("previous")) {
					offset--;
				}
				calendar.set(Calendar.MONTH, month + offset);
			}
			request.setAttribute("calendar", calendar);
			request.setAttribute("calendars", calendars);
			request.getRequestDispatcher("/WEB-INF/pages/createMonth.jsp")
					.forward(request, response);
		}

	}

	public void addCalendar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("calendarName");
		String color = "#EAEAEA";
		// String color = request.getParameter("calendarName");
		if (name != null && color != null) {
			EventCalendar calendar = new EventCalendar();
			calendar.setName(name);
			calendar.setColor(color);
			canDOService.addCalendar(calendar, user.getKey());
			fillEventCalendarMenu(request, response);
		}
	}

	public void fillEventCalendarMenu(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<EventCalendar> calendars = canDOService.getCalendars(user);
		request.setAttribute("calendars", calendars);
		request.getRequestDispatcher(
				"/WEB-INF/pages/createEventCalendarMenu.jsp").forward(request,
				response);
	}

	public void removeCalendar(HttpServletRequest request,
			HttpServletResponse response) {
		String calendarKey = request.getParameter("calendarKey");
		if (!"".equals(calendarKey)) {
			canDOService.removeCalendarByKey(calendarKey);
		}

	}

	public void updateCalendar(HttpServletRequest request,
			HttpServletResponse response) {
		String calendarKey = request.getParameter("calendarKey");
		String calendarName = request.getParameter("calendarName");
		if (calendarKey != null && calendarName != null) {
			EventCalendar calendar = canDOService.getCalendarByKey(calendarKey);
			calendar.setName(calendarName);
			canDOService.saveCalendar(calendar);
		}
	}

	@SuppressWarnings("deprecation")
	public void addEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String calendarKey = request.getParameter("calendarKey");
		String eventName = request.getParameter("eventName");
		String eventFinishDay = request.getParameter("eventFinishDay");
		String eventStartDay = request.getParameter("eventStartDay");
		String eventDesc = request.getParameter("eventDesc");
		if (!("".equals(calendarKey) || "".equals(eventName) || ""
				.equals(eventFinishDay))) {
			EventCalendar calendar = canDOService.getCalendarByKey(calendarKey);
			Event event = new Event();
			event.setName(eventName);
			if(eventDesc != null){
				event.setDescription(eventDesc);
			}else{
				event.setDescription("");
			}
			
			Date finish;
			try {
				finish = formatter.parse(eventFinishDay);
			} catch (ParseException e) {
				return;
			}
			event.setFinish(finish);
			Date start;
			try {
				start = formatter.parse(eventStartDay);
			} catch (ParseException e) {
				start = new Date();
			}
			event.setStart(start);
			
			Date warningTime = new Date();
			warningTime.setHours(0);
			event.setWarningTime(warningTime);
			
			
			calendar.getEvents().add(event);
			canDOService.saveCalendar(calendar);
		}
	}

	public void updateEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String eventKey = request.getParameter("eventKey");
		String eventName = request.getParameter("eventName");
		String eventFinishDay = request.getParameter("eventFinishDay");
		String eventStartDay = request.getParameter("eventStartDay");
		String eventDesc = request.getParameter("eventDesc");
		int warningTimeDays = Integer.valueOf(request.getParameter("warningTimeDays"));
		int warningTimeHours = Integer.valueOf(request.getParameter("warningTimeHours"));
		int warningTimeMinutes = Integer.valueOf(request.getParameter("warningTimeMinutes"));
		if (!("".equals(eventKey) || "".equals(eventName) || ""
				.equals(eventFinishDay))) {
			Event event = canDOService.getEventByKey(eventKey);
			event.setName(eventName);
			if(eventDesc != null){
				event.setDescription(eventDesc);
			}else{
				event.setDescription("");
			}
			
			Date finish;
			try {
				finish = formatter.parse(eventFinishDay);
			} catch (ParseException e) {
				return;
			}
			event.setFinish(finish);
			Date start;
			try {
				start = formatter.parse(eventStartDay);
			} catch (ParseException e) {
				start = new Date();
			}
			event.setStart(start);
			
			Date warningTime = new Date();
			warningTime.setDate(warningTimeDays);
			warningTime.setHours(warningTimeHours);
			warningTime.setMinutes(warningTimeMinutes);
			event.setWarningTime(warningTime);
			
			canDOService.saveEvent(event);
		}
	}

	public void removeEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String eventKey = request.getParameter("eventKey");
		if (eventKey != null) {
			EventCalendar calendar = canDOService.getCalendar(eventKey, user.getKey());
			calendar.getEvents().remove(canDOService.getEventByKey(eventKey));
			canDOService.saveCalendar(calendar);
		}
	}

	private void viewEvent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String eventKey = request.getParameter("eventKey");
		if (!"".equals(eventKey)) {
			Event event = canDOService.getEventByKey(eventKey);
			EventCalendar calendar = canDOService.getCalendar(eventKey, user.getKey());
			request.setAttribute("formatter", formatter);
			request.setAttribute("event", event);
			request.setAttribute("calendarName", calendar.getName());
			request.getRequestDispatcher("/WEB-INF/pages/viewEvent.jsp")
					.forward(request, response);
		}

	}
	
	private void getEvent(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String eventKey = request.getParameter("eventKey");
		if (eventKey != null) {
			Event event = canDOService.getEventByKey(eventKey);
			EventCalendar calendar = canDOService.getCalendar(eventKey, user.getKey());
			String[] data = new String[8];
			data[0] = event.getName()!=null?event.getName() : "";
			data[1] = event.getDescription()!=null?event.getDescription() : "";
			data[2] = formatter.format(event.getStart());
			data[3] = formatter.format(event.getFinish());
			data[4] = calendar.getName();
			Date warningTime = event.getWarningTime();
			data[5] = String.valueOf(warningTime.getDay());
			data[6] = String.valueOf(warningTime.getHours());
			data[7] = String.valueOf(warningTime.getMinutes());
			
			ServletUtils.writeJson(response, data);
		}
	}
}
