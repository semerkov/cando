package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appspot.i_can_do.master.security.User;

@SuppressWarnings("serial")
public class RootServlet extends HttpServlet {
	private static LoginServlet login = new LoginServlet();
	private static final Logger log = Logger.getLogger(RootServlet.class.getName());

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("Servlet created");

		RequestDispatcher dispatcher = null;

		boolean isLogin = false;
		
		HttpSession session = request.getSession();
		String userEmail = (String) session.getAttribute("user");
		
		
		if(userEmail!=null)
		{
			isLogin=true;
		}
		else
		{
			String email = "";
			String hash = "";
			String ipAdress = request.getRemoteAddr();
			Cookie cookies[] = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals(LoginServlet.REMEMBER_COOKIE_USER)) {
						email = c.getValue();
					} else if (c.getName().equals(LoginServlet.REMEMBER_COOKIE_HASH)) {
						hash = c.getValue();
					}
				}
				log.info("cookies check");
				if ((!email.equals("")) && (!hash.equals(""))) {
					User user = login.validateRememberCookies(email, hash, ipAdress);
					if (user != null) {
						session.setAttribute("user", user.getEmail());
						isLogin = true;
					} 
				}

			}
		}
		
		if(isLogin)
		{
			dispatcher = request.getRequestDispatcher("calendar");
		}
		else
		{
			dispatcher = request.getRequestDispatcher("login.jsp");
		}

		try {
			dispatcher.forward(request, response);
			
			}catch (ServletException ex) {
				
			}catch(IOException ex){	
		}
	}
}

