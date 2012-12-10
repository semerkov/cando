package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.exceptions.LoginFailedException;
import com.appspot.i_can_do.service.exceptions.LoginNameNotFoundException;
import com.appspot.i_can_do.service.utils.Crypto;
import com.appspot.i_can_do.service.utils.ServletUtils;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	// one week
	public static final int EXISTING_REMEMBER_COOKIE_TIME = 604800;
	public static final String REMEMBER_COOKIE_USER = "userCookie";
	public static final String REMEMBER_COOKIE_HASH = "userCookieHash";
	private static final CanDOSecurityService security = CanDOSecurityService.instance();
	private static final Logger log = Logger.getLogger(LoginServlet.class
			.getName());
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList(new String[] { "login" });

	@Override
	public void init(ServletConfig config) {
		log.info("Servlet created");
	}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String action = request.getParameter("action");

		if (SECURITY_ACTIONS.contains(action)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String rememberMe = request.getParameter("rememberMe");
			if (email != null && password != null) {
				try {
					User user = security.login(email, password);

					

					Date curDate = new Date();
					byte[] rememberCookiesHash = null;

					if (rememberMe.equals("1")) {
						rememberCookiesHash = Crypto.hashPassword(curDate
								.toString());
						Cookie c = new Cookie(REMEMBER_COOKIE_USER,
								user.getEmail());
						c.setMaxAge(EXISTING_REMEMBER_COOKIE_TIME);
						response.addCookie(c);
						c = new Cookie(REMEMBER_COOKIE_HASH,
							Arrays.toString(rememberCookiesHash));
						c.setMaxAge(EXISTING_REMEMBER_COOKIE_TIME);
						response.addCookie(c);
					}
					user.setLastEntryDate(curDate);
					user.setRememberCookiesHash(rememberCookiesHash);
					user.setRememberIpAdress(request.getRemoteAddr());
					security.saveUser(user);
					ServletUtils.writeJson(response, "ready");
					
					HttpSession session = request.getSession();
					log.info("create session: " + session.getId());
					session.setAttribute("user", user);
					
					log.info("login is successful: " + user.getEmail());
				} catch (LoginNameNotFoundException ex) {
					ServletUtils.writeJson(response, "LoginNameNotFound");
				} catch (LoginFailedException ex) {
					ServletUtils.writeJson(response, "LoginFailed");
				}
			} else {
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
						"Do not set parameters to login");
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Not existing action!");
			return;
		}
	}

	public User validateRememberCookies(String email, String hash,
			String ipAdress) {
		User user = null;
		try {
			user = security.findUser(email);
		} catch (NoResultException ex) {}
		if (user != null) {
			if (Arrays.toString(user.getRememberCookiesHash()).equals(hash)
					&& user.getRememberIpAdress().equals(ipAdress)
					&& ((new Date()).getTime() - user.getLastEntryDate()
							.getTime()) < EXISTING_REMEMBER_COOKIE_TIME * 100) {
				return user;
			}
		}
		return null;
	}
}