package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOMailService;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;
import com.appspot.i_can_do.service.utils.Crypto;
import com.appspot.i_can_do.service.utils.ServletUtils;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(RegisterServlet.class
			.getName());
	private CanDOSecurityService security;
	private CanDOMailService mailService;
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList(new String[] { "testEmail", "register", "confirRegister" });

	@Override
	public void init(ServletConfig config) {
		security = CanDOSecurityService.instance();
		mailService = CanDOMailService.instance();
		log.info("Servlet created");
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String action = request.getParameter("action");
		if (SECURITY_ACTIONS.contains(action)) {
			if("confirm".equals(action)){
				confirRegister(request,response);
			}
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Not existing action!");
			return;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String action = request.getParameter("action");

		if (SECURITY_ACTIONS.contains(action)) {
			// block for actions without log in
			if ("testEmail".equals(action)) {
				testEmail(request, response);
			}
			if("register".equals(action)){
				register(request,response);
			}
			
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Not existing action!");
			return;
		}
	}
	private void confirRegister(HttpServletRequest request,
			HttpServletResponse response){
		
	}
	
	private void register(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		
		if (security.findUser(email) != null) {
			log.warning("User is exist!");
			return;
		}
		if (password.equals("")||name.equals("")||surname.equals("")){
			log.warning("Password or name or surname is empty");
			return;
		}
		
		User user = new User();
		user.setEmail(email);
		user.setName(name);
	
		user.setSername(surname);
		byte[] confirmHash=Crypto.hashPassword((new Date()).toString());
		user.setConfirmHash(confirmHash);
		user.setLastEntryDate(new Date());
		user.setDisabled(true);
		try {
			security.addNewUser(user, password);
			String s = "Hello,"+name+
					"/n/r You are succesfull registred in project CanDO./n/rPlease confirm:/n/r"+
					"http://localhost:8888/register?action=confirm&user="+email+"&confirmHash="+Arrays.toString(confirmHash);
			mailService.SendEmailFromAdmin(email, "Confirm registration to CanDO", s);
			
		} catch (LoginNameExistException e) {
			log.warning("Test user exist");
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
