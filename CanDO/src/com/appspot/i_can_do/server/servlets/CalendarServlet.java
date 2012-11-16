package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.i_can_do.master.security.User;

@SuppressWarnings("serial")
public class CalendarServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(RegisterServlet.class
			.getName());
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList(new String[] { "retrieveCalenderTable" });

	@Override
	public void init(ServletConfig config) {
		log.info("Servlet created");
	}

	protected boolean isLoginState(HttpServletRequest request) {
		User userObj = (User) request.getSession().getAttribute("User");
		return userObj != null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String action = request.getParameter("action");
		
		if (SECURITY_ACTIONS.contains(action)) {
			//TODO remove, for test only
			 /*if (!isLoginState(request)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Only authorized user can perform this action");
				return;
			}*/
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Not existing action!");
			return;
		}
		
		if(action.equals("retrieveCalenderTable")){
			retrieveCalenderTable(request,response);
		}
	}

	private void retrieveCalenderTable(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		/*
		Long storeId = Long.parseLong(request.getParameter("storeId"));
		
		List<Object[]> listAlog = sk.getAudioLog(storeId, 100L);
		request.setAttribute("EMPTY_MESSAGE", "Audio log is empty");
		request.setAttribute("audioItems", listAlog);
		request.setAttribute("storeId", storeId);
		
		request.getRequestDispatcher("/WEB-INF/pages/audioLogsFragment.jsp").forward(request, response);*/
		request.getRequestDispatcher("/WEB-INF/pages/createMonth.jsp").forward(request, response);

	}

}
