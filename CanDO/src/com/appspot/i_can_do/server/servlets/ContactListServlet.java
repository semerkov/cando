package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.i_can_do.master.model.GroupType;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOService;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ContactListServlet extends HttpServlet {
	private static final long serialVersionUID = 6393392702925035918L;
	private static final Logger log = Logger.getLogger(ContactListServlet.class
			.getName());
	private static final List<String> SECURITY_ACTIONS = Arrays.asList(
			"addContact", "removeContact", "addToGroup", "removeFromGroup",
			"addGroup", "removeGroup", "editGroup");
	private CanDOService canDOService;
	private User user;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		canDOService = CanDOService.inctance();
		log.info("Servlet created");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		user = (User) request.getSession().getAttribute("user");

		HashMap<User, String> contacts = canDOService.getAllContacts(user);
		List<User> newContacts = canDOService.getNewContacts(user);
		if (newContacts.size() == 1 && newContacts.get(0) == null)
			newContacts = new ArrayList<User>(0);
		List<GroupType> groupTypes = canDOService.getGroupTypes(user);

		request.setAttribute("groupTypes", groupTypes);
		request.setAttribute("contacts", contacts);
		request.setAttribute("newContacts", newContacts);
		request.setAttribute("user", user);
		request.getRequestDispatcher("contactList.jsp").forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if (!SECURITY_ACTIONS.contains(action)) {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Not existing action!");
			return;
		}

		if (action.equals("addContact")) {
			addContact(request, response);
		} else if (action.equals("removeContact")) {
			removeContact(request, response);
		} else if (action.equals("addToGroup")) {
			addToGroup(request, response);
		} else if (action.equals("removeFromGroup")) {
			removeFromGroup(request, response);
		} else if (action.equals("addGroup")) {
			addGroup(request, response);
		} else if (action.equals("removeGroup")) {
			removeGroup(request, response);
		} else if (action.equals("editGroup")) {

		}
	}

	private void removeGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String groupKey = request.getParameter("groupKey");
		if (groupKey != null && groupKey != "") {
			Key key = KeyFactory.stringToKey(groupKey);
			canDOService.removeGroupType(user, key);
		}
	}

	public void addGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String groupName = request.getParameter("name");
		if (groupName != null && groupName != "") {
			canDOService.addGroupType(user, groupName);
		}
	}

	public void addContact(HttpServletRequest request,
			HttpServletResponse response) {
		String contactKey = request.getParameter("contactKey");
		if (contactKey != null && contactKey != "") {
			Key key = KeyFactory.stringToKey(contactKey);
			canDOService.addContact(user, key);
		}
	}
	
	private void removeContact(HttpServletRequest request,
			HttpServletResponse response) {
		String contactKey = request.getParameter("contactKey");
		if (contactKey != null && contactKey != "") {
			Key key = KeyFactory.stringToKey(contactKey);
			canDOService.removeContact(user, key);
		}
	}

	private void addToGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String contact = request.getParameter("contactKey");
		String group = request.getParameter("groupKey");
		if (contact != null && contact != "" && group != null && group != "") {
			Key contactKey = KeyFactory.stringToKey(contact);
			Key groupKey = KeyFactory.stringToKey(group);
			canDOService.addGroupToContact(user, contactKey, groupKey);
		}
	}
	
	private void removeFromGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String contact = request.getParameter("contactKey");
		String group = request.getParameter("groupKey");
		if (contact != null && contact != "" && group != null && group != "") {
			Key contactKey = KeyFactory.stringToKey(contact);
			Key groupKey = KeyFactory.stringToKey(group);
			canDOService.removeGroupFromContact(user, contactKey, groupKey);
		}

	}
}
