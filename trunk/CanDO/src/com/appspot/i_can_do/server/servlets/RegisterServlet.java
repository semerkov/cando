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
import com.appspot.i_can_do.service.utils.ServletUtils;

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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
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
		boolean b = false;
		b = (security.findUser("user@email.com") != null);
		log.warning("Create user success: " + Boolean.toString(b));

		if (SECURITY_ACTIONS.contains(action)) {
			// block for actions without log in
			if ("testEmail".equals(action)) {
				testEmail(request, response);
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
		if (email != null) {
			if (security.findUser(email) == null) {
				ServletUtils.writeJson(response, "free");

			} else {
				ServletUtils.writeJson(response, "occuped");
			}
		}

	}

}
