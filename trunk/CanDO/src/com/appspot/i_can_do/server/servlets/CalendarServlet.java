package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.Arrays;
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
			.asList(new String[] { "retrieveCalenderTable", "addCalendar" });
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
		}else{
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

		if (action.equals("retrieveCalenderTable")) {
			retrieveCalenderTable(request, response);
		} else if (action.equals("addCalendar")) {
			addCalendar(request, response);
		}
	}

	private void retrieveCalenderTable(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		/*
		 * Long storeId = Long.parseLong(request.getParameter("storeId"));
		 * 
		 * List<Object[]> listAlog = sk.getAudioLog(storeId, 100L);
		 * request.setAttribute("EMPTY_MESSAGE", "Audio log is empty");
		 * request.setAttribute("audioItems", listAlog);
		 * request.setAttribute("storeId", storeId);
		 * 
		 * request.getRequestDispatcher("/WEB-INF/pages/audioLogsFragment.jsp").
		 * forward(request, response);
		 */
		request.getRequestDispatcher("/WEB-INF/pages/createMonth.jsp").forward(
				request, response);
	}

	public void addCalendar(HttpServletRequest request,
			HttpServletResponse response) {
		EventCalendar calendar = new EventCalendar();
		calendar.setName("New calendar");
		calendar.setColor("#EAEAEA");
		Date c1 = new Date();
		Event event = new Event("Train from Lviv", "Meet Natik from Lviv", c1,
				c1, c1);
		Event event2 = new Event("A letter", "Buy a letter", c1, c1, c1);
		calendar.getEvents().add(event);
		calendar.getEvents().add(event2);
		canDOService.addCalendar(calendar,testUser.getKey());
		calendar.getEvents().get(0).setName("Changed");
		calendar.setName("new name!");
		calendar = canDOService.saveCalendar(calendar);
		canDOService.removeCalendar(calendar);
	}

}
