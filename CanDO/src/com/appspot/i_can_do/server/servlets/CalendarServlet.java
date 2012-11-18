package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
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

import com.appspot.i_can_do.master.model.Event;
import com.appspot.i_can_do.master.model.EventCalendar;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.CanDOService;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;

@SuppressWarnings("serial")
public class CalendarServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(RegisterServlet.class
			.getName());
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList(new String[] { "retrieveCalenderTable", "addCalendar",
					"retrieveEventCalendarMenu" });
	private CanDOService canDOService;

	private User testUser;// TODO remove test user when complete registration
							// and etc.

	@Override
	public void init(ServletConfig config) {
		canDOService = CanDOService.inctance();
		log.info("Servlet created");

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
	}

	protected boolean isLoginState(HttpServletRequest request) {
		User userObj = (User) request.getSession().getAttribute("User");
		return userObj != null;
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
			List<EventCalendar> calendars = canDOService.getCalendars(testUser);
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
		if (!("".equals(name) && "".equals(color))) {
			EventCalendar calendar = new EventCalendar();
			calendar.setName(name);
			calendar.setColor(color);
			canDOService.addCalendar(calendar, testUser.getKey());
			fillEventCalendarMenu(request, response);
		}
		return;
	}

	public void fillEventCalendarMenu(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<EventCalendar> calendars = canDOService.getCalendars(testUser);
		request.setAttribute("calendars", calendars);
		request.getRequestDispatcher(
				"/WEB-INF/pages/createEventCalendarMenu.jsp").forward(request,
				response);
		return;
	}

	public void initTestCalendars() {
		EventCalendar calendar = new EventCalendar();
		calendar.setName("Trip calendar");
		calendar.setColor("#EAEAEA");
		Date c1 = new Date();
		Event event = new Event("Train from Lviv", "Meet Natik from Lviv", c1,
				c1, c1);
		Event event2 = new Event("Trip to Kiev", "New Year in Kiev!!", c1, c1,
				c1);
		calendar.getEvents().add(event);
		calendar.getEvents().add(event2);
		canDOService.addCalendar(calendar, testUser.getKey());

		EventCalendar calendar1 = new EventCalendar();
		calendar1.setName("Gifts");
		calendar1.setColor("#EAEAEA");
		Date c2 = new Date();
		Event event3 = new Event("Moms birthday", "Buy a gift", c2, c2, c2);
		Event event4 = new Event("Natik birthday", "Buy a gift", c2, c2, c2);
		calendar1.getEvents().add(event3);
		calendar1.getEvents().add(event4);
		canDOService.addCalendar(calendar1, testUser.getKey());
		return;
	}
}
