package com.appspot.i_can_do.server.servlets;


import java.util.ArrayList;
import java.util.Calendar;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.i_can_do.master.security.Event;


@SuppressWarnings("serial")
public class WarningEventsServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) {
		
		 ArrayList<Event> events = new ArrayList<Event>();	
		 Calendar c = Calendar.getInstance();
		 Calendar c1= Calendar.getInstance();
		 Calendar c2= Calendar.getInstance();
		 c.set(2012, 12, 15, 15, 0);
		 c1.set(2012, 12, 17, 12, 0);
		 c2.set(2012, 12, 18, 21, 0);
		 events.add(new Event("Собеседование", "это собеседование ёпт",c,c));
		 events.add(new Event("Сдача проекта", "этот ёпт проект",c1,c1));
		 events.add(new Event("КР Саенко", "это ёпт Саенко",c2,c2));
		 
		 request.setAttribute("events", events);
		 try{
	        request.getRequestDispatcher("/warningEventsBar.jsp").forward(request, response);
		 }
		 catch(Exception ex){}
	}
}