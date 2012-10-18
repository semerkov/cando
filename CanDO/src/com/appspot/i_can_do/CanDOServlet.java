package com.appspot.i_can_do;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CanDOServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
