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
			.asList(new String[] { "testEmail", "register", "confirm" });

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
			HttpServletResponse response) throws IOException{
		String email = request.getParameter("user");
		String hash = request.getParameter("confirmHash");
		User user = security.findUser(email);
		if(user!=null){
			StringBuilder h = new StringBuilder();
		    for (byte b : user.getConfirmHash()) {
		    	h.append(String.format("%02X", b));
		    }
		    if(hash.equals(h.toString())){
		    	user.setDisabled(false);
		    	user.setConfirmHash(null);
		    	security.saveUser(user);
		    	response.sendRedirect("/login");
		    	return;
		    }
		}
		response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
				"Not found a confirm key or user");
		
	}
	
	private void register(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		User user = security.findUser(email);
		if (user!= null){
			if(user.isDisabled()) 
				security.removeUser(user);
			else{
				ServletUtils.writeJson(response, "error");
				return;
				}
		}		
		if (password.equals("")||name.equals("")||surname.equals("")){
			log.warning("Password or name or surname is empty");
			ServletUtils.writeJson(response, "error");
			return;
		}
		
		user = new User();
		user.setEmail(email);
		user.setName(name);
	
		user.setSername(surname);
		byte[] confirmHash=Crypto.hashPassword((new Date()).toString());
		user.setConfirmHash(confirmHash);
		user.setLastEntryDate(new Date());
		user.setDisabled(true);
		try {
			security.addNewUser(user, password);
		} catch (LoginNameExistException e) {
			log.warning("Add user warning");
			ServletUtils.writeJson(response, "error");
			return;
		}
		StringBuilder message = new StringBuilder();
		message.append("Hello,");
		message.append(name);
		message.append("! \n\n");
		message.append("You are successfull registered in project CanDO. \n\n");
		message.append("Please confirm yours account: \n\n");
		message.append("http://i-can-do.appspot.com/register?action=confirm&user=");
		message.append(email);
		message.append("&confirmHash=");
	    for (byte b : confirmHash) {
	    	message.append(String.format("%02X", b));
	    }
		boolean b  = mailService.SendEmailFromAdmin(email, "Confirm registration to CanDO", message.toString());
		ServletUtils.writeJson(response, "ready");
	}

	private void testEmail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		if (email != null) {
			User user = security.findUser(email);
			if (user == null||user.isDisabled()) {
				ServletUtils.writeJson(response, "free");
			} else {
				ServletUtils.writeJson(response, "occuped");
			}
		}
	}
	

}
