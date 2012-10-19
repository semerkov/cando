package com.appspot.i_can_do.service;

import java.util.List;

import com.appspot.i_can_do.master.security.User;

public interface ICanDOSecurityService {
	void addUser(User user);
	User getUser(String login, String password);
	List<User> getUsers();
}
