package com.appspot.i_can_do.server.servlets;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.i_can_do.master.security.Event;

@SuppressWarnings("serial")
public class WarningEventsServlet extends HttpServlet {
	
	public static final String NOT_FOUND_WARNING_EVENTS = "notFoundWarningEvents";
	
	public void service(HttpServletRequest request, HttpServletResponse response) {
		
		ArrayList<Event> events = new ArrayList<Event>();
		events.clear();
		
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c.clear();
		c1.clear();
		c2.clear();
		c.set(13, 11, 14, 15, 0);
		c1.set(12, 11, 1, 12, 0);
		c2.set(12, 11, 18, 21, 0);
		events.add(new Event("Собеседование", "это собеседование ёпт", c, c));
		events.add(new Event("Сдача проекта", "этот ёпт проект", c1, c1));
		events.add(new Event("КР Саенко", "это ёпт Саенко", c2, c2));

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