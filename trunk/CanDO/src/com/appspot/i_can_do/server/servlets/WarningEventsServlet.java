package com.appspot.i_can_do.server.servlets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appspot.i_can_do.master.model.Event;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;

@SuppressWarnings("serial")
public class WarningEventsServlet extends HttpServlet {
	
	public static final String NOT_FOUND_WARNING_EVENTS = "notFoundWarningEvents";
	
	public void service(HttpServletRequest request, HttpServletResponse response) {
		
		ArrayList<Event> events = new ArrayList<Event>();
		events.clear();
		
		
		Date c = new Date();
		Date c1 = new Date();
		Date c2 = new Date();
		events.add(new Event("Buy a latter", "Buy a latter for Natik", c, c,c));
		events.add(new Event("Train from Lviv", "Meet Natik from Lviv", c1, c1,c1));
		events.add(new Event("Meeting", "Buy flowers", c2, c2,c2));

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