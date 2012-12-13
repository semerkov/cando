package com.appspot.i_can_do.server.servlets;

import com.appspot.i_can_do.master.security.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 10.12.12
 * Time: 11:59
 * To change this template use File | Settings | File Templates.
 */

public class LoginFilter  implements Filter {
    private static LoginServlet login = new LoginServlet();
    private static final Logger log = Logger.getLogger(LoginFilter.class.getName());

    @Override
    public void init(FilterConfig config) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(true);
        try {
            if(request.getServletPath().contains("/login")){  // this is login page, so just continue request
              chain.doFilter(req, res);
        }

        boolean isLogin = false;
        User user = (User) session.getAttribute("user");

            if(user!=null)
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
                        user = login.validateRememberCookies(email, hash, ipAdress);
                        if (user != null) {
                            session.setAttribute("user", user);
                            isLogin = true;
                        }
                    }

                }
            }

            if(isLogin)
            {
                chain.doFilter(req, res); // Logged-in user found, so just continue request.
            }
            else
            {
                response.sendRedirect("/login");
            }
         }
        catch (IOException ex){
            throw new ServletException(ex);
        }
    }

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }

}
