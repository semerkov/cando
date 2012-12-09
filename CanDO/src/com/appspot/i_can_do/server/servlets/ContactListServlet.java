package com.appspot.i_can_do.server.servlets;

import com.appspot.i_can_do.master.security.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ContactListServlet extends HttpServlet{
    private static final List<String> SECURITY_ACTIONS = Arrays.asList("addAddress");
    private User user;

    protected boolean isLoginState(HttpServletRequest request) {
        user = (User) request.getSession().getAttribute("user");
        return user != null;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!isLoginState(request)){
            request.getRequestDispatcher("/").forward(request, response);
        }

        request.getRequestDispatcher("contactList.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
