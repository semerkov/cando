package com.appspot.i_can_do.server.servlets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appspot.i_can_do.master.model.Event;
import com.appspot.i_can_do.master.model.EventCalendar;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.CanDOService;

@SuppressWarnings("serial")
public class WarningEventsServlet extends HttpServlet {

	public static final String NOT_FOUND_WARNING_EVENTS = "notFoundWarningEvents";
	private static CanDOService service = CanDOService.inctance();

	@Override
	public void init() {

	}

	public void service(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<EventCalendar> listCalendars = service.getCalendars(user);

		List<Event> events = new ArrayList<Event>();

		Date date = new Date();
		Date tempDate = new Date();

		for (EventCalendar calendar : listCalendars) {
			for (Event event : calendar.getEvents()) {
				tempDate.setTime(0);
				tempDate.setDate(event.getWarningTime().getDate());
				tempDate.setHours(event.getWarningTime().getHours());
				tempDate.setMinutes(event.getWarningTime().getMinutes());
				tempDate.setTime(event.getStart().getTime()
						- tempDate.getTime());
				if (tempDate.before(date) && event.getFinish().after(date)) {
					events.add(event);
				}
				Collections.sort(events, new Event());
			}
		}
		try {
			if (events.size() != 0) {
				request.setAttribute("events", events);
				request.getRequestDispatcher("/warningEventsBar.jsp").forward(
						request, response);

			} else {
				response.setContentType("text/plain");
				response.getWriter().print(NOT_FOUND_WARNING_EVENTS);
				return;
			}
		} catch (Exception ex) {
		}
	}
}