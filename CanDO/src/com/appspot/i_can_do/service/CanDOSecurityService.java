package com.appspot.i_can_do.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.exceptions.LoginFailedException;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;
import com.appspot.i_can_do.service.exceptions.LoginNameNotFoundException;
import com.appspot.i_can_do.service.utils.Crypto;

public class CanDOSecurityService implements ICanDOSecurityService {
	private static final EntityManager em = EMF.get().createEntityManager();
	private static final CanDOSecurityService security = new CanDOSecurityService();
	private static final Logger log = Logger
			.getLogger(CanDOSecurityService.class.getName());

	public static CanDOSecurityService instance() {
		return security;
	}

	@Override
	public User addNewUser(User user, String password)
			throws LoginNameExistException {
		User userToAdd = findUser(user.getEmail());
		if (userToAdd == User.NULL_USER) {
			userToAdd = user;
			byte[] pHash = Crypto.hashPassword(password);
			userToAdd.setPasswordHash(pHash);

			em.persist(userToAdd);
			
		} else {
			throw new LoginNameExistException();
		}
		return userToAdd;
	}

	@Override
	public User findUser(String email) {
		Query query = em.createNamedQuery("User.getUser");
		query.setParameter("email", email);
		User u = User.NULL_USER;
		try {
			u = (User) query.getSingleResult();
		} catch (NoResultException e) {
		}
		return u;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() {
		return (List<User>) em.createNamedQuery("User.getUsers")
				.getResultList();
	}

	@Override
	public User saveUser(User user) {
		em.merge(user);
		return user;
	}

	@Override
	public User login(String username, String password)
			throws LoginNameNotFoundException, LoginFailedException {
		log.info("Login Attempt: " + username);
		User user = doLogin(username, password);

		user.setLastLogin(new Date());
		em.merge(user);

		return user;
	}

	private User doLogin(String useremail, String password)
			throws LoginNameNotFoundException, LoginFailedException {
		User user = findUser(useremail);
		if (!user.equals(User.NULL_USER)) {
			byte[] hashPassword = Crypto.hashPassword(password);
			if (user.isDisabled()
					|| !Arrays.equals(hashPassword, user.getPasswordHash())) {
				log.info("Login Failed! " + useremail);
				throw new LoginFailedException();
			}
		} else {
			log.info("User not found: " + useremail);
			throw new LoginNameNotFoundException();
		}

		return user;
	}

}
