package com.appspot.i_can_do.service;

import java.util.List;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.exceptions.LoginFailedException;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;
import com.appspot.i_can_do.service.exceptions.LoginNameNotFoundException;

public interface ICanDOSecurityService {
	User addNewUser(User user, String password) throws LoginNameExistException;
	User findUser(String email);
	User saveUser(User user);
	List<User> getUsers();
	User login(String username, String password) throws LoginNameNotFoundException, LoginFailedException;
	void removeUser(User user);
}
