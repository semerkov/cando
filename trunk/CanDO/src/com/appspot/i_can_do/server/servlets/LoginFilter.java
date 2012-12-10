package com.appspot.i_can_do.server.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 10.12.12
 * Time: 11:59
 * To change this template use File | Settings | File Templates.
 */

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        try {

        if(request.getServletPath().contains("/login")){  // this is login page, so just continue request
            chain.doFilter(req, res);
        }

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login"); // No logged-in user found, so redirect to login page.

        } else {
            chain.doFilter(req, res); // Logged-in user found, so just continue request.
        }
        }catch (IOException e) {
           throw new  ServletException(e);
        }
    }

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }

}
