package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.utils.Crypto;
import com.google.gson.Gson;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(RegisterServlet.class
			.getName());
	private CanDOSecurityService security;
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList(new String[] { "testEmail" });

	@Override
	public void init(ServletConfig config) {
		// security = CanDOSecurityService.instance();
	}

	protected boolean isLoginState(HttpServletRequest request) {
		User userObj = (User) request.getSession().getAttribute("User");
		return userObj != null;
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String action = request.getParameter("action");

		/*
		if (!SECURITY_ACTIONS.contains(action) || !isLoginState(request)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Only authorized user can perform this action");
			return;

		}*/

		if ("testEmail".equals(action)) {
			testEmail(request, response);
		}
	}

	private void testEmail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		log.warning(email);
		// if (email != null && security.findUser(email).equals(User.NULL_USER))
		// {
		if (email != null) {
			writeJson(response, "free");
			
		} else {
			writeJson(response, "occuped");
		}
	}

	private static void writeJson(HttpServletResponse response, Object object)
			throws IOException {
		log.warning("create json");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String gson = new Gson().toJson(object);
		log.warning(gson);
		response.getWriter().write(gson);
	}
}
