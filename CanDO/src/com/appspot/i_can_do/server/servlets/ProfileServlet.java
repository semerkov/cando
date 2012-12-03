package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appspot.i_can_do.master.model.Address;
import com.appspot.i_can_do.master.model.Email;
import com.appspot.i_can_do.master.model.Phone;
import com.appspot.i_can_do.master.security.AddressEmailType;
import com.appspot.i_can_do.master.security.PhoneType;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.CanDOService;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ProfileServlet extends HttpServlet{
	private static final long serialVersionUID = -178538787888163996L;
	private static final List<String> SECURITY_ACTIONS = Arrays
			.asList(new String[] { "addAddress","editAddress","removeAddress",
								   "addEmail","editEmail","removeEmail",
								   "addPhone","editPhone","removePhone"});
	private static final AddressEmailType[] addressEmailTypes = AddressEmailType.values();
	private static final PhoneType[] phoneTypes = PhoneType.values();
	private static final CanDOSecurityService service = CanDOSecurityService.instance();
	private User user;
	
	protected boolean isLoginState(HttpServletRequest request) {
		User userObj = (User) request.getSession().getAttribute("user");
		return userObj != null;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		if(user==null){
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
			if(user==null){
				try{
				request.getRequestDispatcher("/").forward(request, response);
				return;
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			request.setAttribute("user", user);
			request.getRequestDispatcher("profile.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if (SECURITY_ACTIONS.contains(action)) {
			  if (!isLoginState(request)) {
			  response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
			  "Only authorized user can perform this action"); return; 
			  }
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Not existing action!");
			return;
		}
		
		if(action.equals("addAddress")){
			addAddress(request,response);
		} else if(action.equals("addAddress")){
			addAddress(request,response);
		} else if(action.equals("editAddress")){
			editAddress(request,response);
		} else if(action.equals("removeAddress")){
			removeAddress(request,response);
		} else if(action.equals("addEmail")){
			addEmail(request,response);
		} else if(action.equals("editEmail")){
			editEmail(request,response);
		} else if(action.equals("removeEmail")){
			removeEmail(request,response);
		} else if(action.equals("addPhone")){
			addPhone(request,response);
		} else if(action.equals("editPhone")){
			editPhone(request,response);
		} else if(action.equals("removePhone")){
			removePhone(request,response);
		}
		seeMyProfile(request,response);
	}

	private void removePhone(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void editPhone(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void addPhone(HttpServletRequest request,
			HttpServletResponse response) {
		String phone = request.getParameter("phone");
		String phoneType =request.getParameter("phoneTypeCode");
		if(phone != null && phoneType != null){
			int code = Integer.parseInt(phoneType);
			Phone p = new Phone(phone,phoneTypes[code]);
			user.getProfile().getPhones().add(p);
			service.saveUser(user);
		}
	}

	private void removeEmail(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void editEmail(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void addEmail(HttpServletRequest request,
			HttpServletResponse response) {
		String email = request.getParameter("email");
		String emailType =request.getParameter("emailTypeCode");
		if(email != null && emailType != null){
			int code = Integer.parseInt(emailType);
			Email e = new Email(email,addressEmailTypes[code]);
			user.getProfile().getEmails().add(e);
			service.saveUser(user);
		}
		
	}

	private void seeMyProfile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("user", user);
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}
	
	private void addAddress(HttpServletRequest request,
			HttpServletResponse response){
		String address = request.getParameter("address");
		String addressType =request.getParameter("addressTypeCode");
		if(address != null && addressType != null){
			int code = Integer.parseInt(addressType);
			Address adr = new Address(address,addressEmailTypes[code]);
			user.getProfile().getAddresses().add(adr);
			service.saveUser(user);
		}
	}
	
	private void editAddress(HttpServletRequest request,
			HttpServletResponse response){
		String addressKey = request.getParameter("addressKey");
		String address = request.getParameter("address");
		String adddressType =request.getParameter("addressTypeCode");
		if(addressKey!= null && address != null && adddressType != null){
			Key addKey = KeyFactory.stringToKey(addressKey);
			int code = Integer.parseInt(adddressType);
			Address adr = new Address(address,addressEmailTypes[code]);
			user.getProfile().getAddresses().add(adr);
		}
	}
	
	private void removeAddress(HttpServletRequest request,
			HttpServletResponse response){
		
	}
	
}
