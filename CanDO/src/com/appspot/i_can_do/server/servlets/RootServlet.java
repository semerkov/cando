package com.appspot.i_can_do.server.servlets;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RootServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		String email="";
		String hash="";
		String ipAdress=request.getRemoteAddr();
		
		for(Cookie c: request.getCookies())
		{
			if(c.getName()==LoginServlet.REMEMBER_COOKIE_USER)
			{
				email = c.getValue();
			}
			else if(c.getName()==LoginServlet.REMEMBER_COOKIE_HASH)
			{
				hash = c.getValue();
			}
		}
		if(email!=""&&hash!="")
		{
			
		}
	}
}
