package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(RegisterServlet.class
			.getName());
	private CanDOSecurityService security;
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList(new String[] { "testEmail" });

	@Override
	public void init(ServletConfig config) {
		security = CanDOSecurityService.instance();
		log.info("Servlet created");
	}

	protected boolean isLoginState(HttpServletRequest request) {
		User userObj = (User) request.getSession().getAttribute("User");
		return userObj != null;
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String action = request.getParameter("action");

		// for test create new user
		User user = new User();
		user.setEmail("user@email.com");
		user.setName("Mario");
		user.setSername("Gavani");
		try {
			security.addNewUser(user, "password");
		} catch (LoginNameExistException e) {
			log.warning("Test user exist");
		}
		boolean b = !security.findUser("user@email.com").equals(User.NULL_USER);
		log.warning("Create user succes: " + Boolean.toString(b));

		if (SECURITY_ACTIONS.contains(action)) {
			// block for actions without log in
			if ("testEmail".equals(action)) {
				testEmail(request, response);
			}

			else if (!isLoginState(request)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Only authorized user can perform this action");
				return;
			}
			// block for actions with log in
			else {

			}

		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Not existing action!");
			return;
		}
	}

	private void testEmail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		boolean freeEmail = false;
		if (email != null) {
			freeEmail = security.findUser(email).equals(User.NULL_USER);
		}
		if (freeEmail) {
			writeJson(response, "free");

		} else {
			writeJson(response, "occuped");
		}

	}

	private static void writeJson(HttpServletResponse response, Object object)
			throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(object));
	}

}
